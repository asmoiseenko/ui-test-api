package com.onevizion.uitest.api.helper.view;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Element;
import com.onevizion.uitest.api.helper.ElementWait;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.helper.jquery.Jquery;
import com.onevizion.uitest.api.helper.tree.Tree;

@Component
public class View {

    public static final String VIEW_NAME = "TestViewOption";
    public static final String UNSAVED_VIEW_NAME = "Unsaved View";
    public static final String GENERAL_INFO_VIEW_NAME = "G:General Info";

    private static final String VIEW_MAIN_ELEMENT_ID_BASE = "newDropdownView";

    private static final String VIEW_SELECT = "ddView";
    private static final String VIEW_CONTAINER = "ddViewContainer";
    private static final String VIEW_SEARCH = "ddViewSearch";
    private static final String BUTTON_CLEAR_SEARCH = "ddViewClearSearch";
    private static final String BUTTON_ORGANIZE = "ddViewBtnOrganize";

    private static final String BUTTON_OPEN = "btnView";
    private static final String FIELD_VIEW_NAME = "txtViewName";
    private static final String BUTTON_SAVE = "unsavedViewIcon";
    private static final String UNSAVED_VIEW = "unsavedViewId";

    private static final String VIEW_DIALOG_CONTAINER = "dialogViewDialogContainer";
    private static final String VIEW_DIALOG_OK = "viewDialogOk";
    private static final String VIEW_DIALOG_CANCEL = "viewDialogCancel";

    private static final String FOLDER_LOCAL = "Local Views";
    private static final String FOLDER_GLOBAL = "Global Views";

    private static final String SCROLL_CONTAINER = "scrollContainer";
    private static final String EXISTING_VIEWS = "ddExistingViews";
    private static final String SAVE_CONTAINER = "ddViewFormSaveContainer";
    private static final String VIEW_TYPE = "lbViewType";

    private static final String LEFT_COLUMNS_DIV_ID = "leftListBox";
    private static final String RIGHT_COLUMNS_DIV_ID = "rightListBox";
    private static final String ADD_BUTTON_ID = "addItem";
    private static final String REMOVE_BUTTON_ID = "removeItem";

    private static final Long COLUMN_DIV_HEIGHT = 28L;

    private static final String BUTTON_GROUP_FIELD = "cfg";
    private static final String BUTTON_GROUP_TASK = "tsg";
    private static final String BUTTON_GROUP_DRILLDOWN = "ddg";
    private static final String BUTTON_GROUP_MARKUP = "mug";
    private static final String BUTTON_GROUP_DATE_PAIR = "dp";

    private static final String COLUMN_LABEL = "labelField";
    private static final String NAV_PANEL = "navPanel";

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Window window;

    @Resource
    private Wait wait;

    @Resource
    private AssertElement assertElement;

    @Resource
    private Tree tree;

    @Resource
    private Grid2 grid2;

    @Resource
    private Js js;

    @Resource
    private ElementWait elementWait;

    @Resource
    private ViewWait viewWait;

    @Resource
    private Jquery jquery;

    @Resource
    private Element element;

    public void checkIsExistViewControl(Long gridIdx, boolean isExist) {
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        int count = seleniumSettings.getWebDriver().findElements(By.id(VIEW_MAIN_ELEMENT_ID_BASE + gridIdx)).size();
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        boolean actualIsExist;
        if (count > 0) {
            actualIsExist = true;
        } else {
            actualIsExist = false;
        }

        Assert.assertEquals(actualIsExist, isExist);
    }

    public int getViewsCount(Long gridIdx) {
        return seleniumSettings.getWebDriver().findElement(By.id(VIEW_SELECT + gridIdx)).findElements(By.className("leaf")).size();
    }

    public List<WebElement> getViews(Long gridIdx) {
        return seleniumSettings.getWebDriver().findElement(By.id(VIEW_SELECT + gridIdx)).findElements(By.className("leaf"));
    }

    public String getCurrentViewName(Long gridIdx) {
        return seleniumSettings.getWebDriver().findElement(By.id(VIEW_SELECT + gridIdx)).findElement(By.className("newGenericDropDownLabel")).getText();
    }

    public void selectViewInOrganize(String viewName) {
        boolean viewFound = false;

        String globalItemsStr = tree.getAllSubItems(0L, "-1");
        String[] globalItems = globalItemsStr.split(",");
        for (String globalItem : globalItems) {
            if (viewName.equals(tree.getItemTextById(0L, globalItem))) {
                viewFound = true;
                tree.selectItem(0L, globalItem);
            }
        }

        String localItemsStr = tree.getAllSubItems(0L, "-2");
        String[] localItems = localItemsStr.split(",");
        for (String localItem : localItems) {
            if (viewName.equals(tree.getItemTextById(0L, localItem))) {
                viewFound = true;
                tree.selectItem(0L, localItem);
            }
        }

        if (!viewFound) {
            throw new SeleniumUnexpectedException("View not found in organize");
        }
    }

    public void selectByVisibleText(Long gridIdx, String entityPrefix) {
        seleniumSettings.getWebDriver().findElement(By.id(VIEW_SELECT + gridIdx)).click();

        elementWait.waitElementById(VIEW_CONTAINER + gridIdx);
        elementWait.waitElementVisibleById(VIEW_CONTAINER + gridIdx);
        elementWait.waitElementDisplayById(VIEW_CONTAINER + gridIdx);

        if (entityPrefix.equals(UNSAVED_VIEW_NAME)) {
            seleniumSettings.getWebDriver().findElement(By.id(UNSAVED_VIEW + gridIdx)).click();
            grid2.waitLoad(gridIdx);
        } else {
            seleniumSettings.getWebDriver().findElement(By.id(VIEW_SEARCH + gridIdx)).sendKeys(entityPrefix);

            WebElement viewElem = (WebElement) js.getNewDropDownElement(VIEW_CONTAINER + gridIdx, SCROLL_CONTAINER, "newGenericDropDownRow", entityPrefix);
            elementWait.waitElementVisible(viewElem);
            viewElem.click();

            grid2.waitLoad(gridIdx);

            seleniumSettings.getWebDriver().findElement(By.id(VIEW_SELECT + gridIdx)).click();

            elementWait.waitElementById(VIEW_CONTAINER + gridIdx);
            elementWait.waitElementVisibleById(VIEW_CONTAINER + gridIdx);
            elementWait.waitElementDisplayById(VIEW_CONTAINER + gridIdx);

            seleniumSettings.getWebDriver().findElement(By.id(BUTTON_CLEAR_SEARCH + gridIdx)).click();
            seleniumSettings.getWebDriver().findElement(By.id(VIEW_SELECT + gridIdx)).click();
        }
    }

    private void selectFolderForSaveViewByVisibleText(Long gridIdx, String folderName) {
        String currentFolderName = seleniumSettings.getWebDriver().findElement(By.id(EXISTING_VIEWS + gridIdx)).findElement(By.className("newGenericDropDownLabel")).getText();
        if (folderName.equals(currentFolderName)) {
            return;
        }

        seleniumSettings.getWebDriver().findElement(By.id(EXISTING_VIEWS + gridIdx)).click();

        elementWait.waitElementById(SAVE_CONTAINER + gridIdx);
        elementWait.waitElementVisibleById(SAVE_CONTAINER + gridIdx);
        elementWait.waitElementDisplayById(SAVE_CONTAINER + gridIdx);

        seleniumSettings.getWebDriver().findElement(By.id("ddViewFormSaveSearch" + gridIdx)).sendKeys(folderName);

        WebElement viewElem = (WebElement) js.getNewDropDownElement(SAVE_CONTAINER + gridIdx, SCROLL_CONTAINER, "newGenericDropDownRow", folderName);
        elementWait.waitElementVisible(viewElem);
        viewElem.click();

        seleniumSettings.getWebDriver().findElement(By.id(EXISTING_VIEWS + gridIdx)).click();

        elementWait.waitElementById(SAVE_CONTAINER + gridIdx);
        elementWait.waitElementVisibleById(SAVE_CONTAINER + gridIdx);
        elementWait.waitElementDisplayById(SAVE_CONTAINER + gridIdx);

        seleniumSettings.getWebDriver().findElement(By.id("ddViewFormSaveClearSearch" + gridIdx)).click();
        seleniumSettings.getWebDriver().findElement(By.id(EXISTING_VIEWS + gridIdx)).click();
    }

    private void isExistAndSelectedView(Long gridIdx, String entityPrefix) {
        boolean isSavedView = false;
        seleniumSettings.getWebDriver().findElement(By.id(VIEW_SELECT + gridIdx)).click();
        for (WebElement view : getViews(gridIdx)) {
            if (view.getText().equals(entityPrefix)) {
                isSavedView = true;
            }
        }
        seleniumSettings.getWebDriver().findElement(By.id(VIEW_SELECT + gridIdx)).click();
        Assert.assertEquals(isSavedView, true, "View " + entityPrefix + " isn't saved");

        viewWait.waitCurrentViewName(gridIdx, entityPrefix);
        grid2.waitLoad(gridIdx);
    }

    public void openViewForm(Long gridIdx) {
        window.openModal(By.id(BUTTON_OPEN + gridIdx));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        waitLeftListBoxReady();
        waitRightListBoxReady();
    }

    public void closeViewFormOk(Long gridIdx) {
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad(gridIdx);

        viewWait.waitCurrentViewName(gridIdx, UNSAVED_VIEW_NAME);
        grid2.waitLoad(gridIdx);
    }

    public void closeViewFormCancel() {
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void openSaveViewForm(Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id(VIEW_SELECT + gridIdx)).click();

        elementWait.waitElementById(VIEW_CONTAINER + gridIdx);
        elementWait.waitElementVisibleById(VIEW_CONTAINER + gridIdx);
        elementWait.waitElementDisplayById(VIEW_CONTAINER + gridIdx);

        seleniumSettings.getWebDriver().findElement(By.id(BUTTON_SAVE + gridIdx)).click();

        elementWait.waitElementById(VIEW_DIALOG_CONTAINER + gridIdx);
        elementWait.waitElementVisibleById(VIEW_DIALOG_CONTAINER + gridIdx);
        elementWait.waitElementDisplayById(VIEW_DIALOG_CONTAINER + gridIdx);

        wait.waitWebElement(By.id(VIEW_TYPE + gridIdx));
        wait.waitWebElement(By.id(FIELD_VIEW_NAME + gridIdx));
        wait.waitWebElement(By.id(VIEW_DIALOG_OK + gridIdx));
    }

    public void closeSaveViewFormOk(Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id(VIEW_DIALOG_OK + gridIdx)).click();
    }

    public void closeSaveViewFormCancel(Long gridIdx) {
        seleniumSettings.getWebDriver().findElement(By.id(VIEW_DIALOG_CANCEL + gridIdx)).click();
    }

    public void saveView(Long gridIdx, String entityPrefix, boolean isLocal, boolean isNew) {
        int beforeSaveSize = getViewsCount(gridIdx);

        openSaveViewForm(gridIdx);

        if (isNew) {
            new Select(seleniumSettings.getWebDriver().findElement(By.id(VIEW_TYPE + gridIdx))).selectByVisibleText("New");
        } else {
            new Select(seleniumSettings.getWebDriver().findElement(By.id(VIEW_TYPE + gridIdx))).selectByVisibleText("Existing");
        }

        if (isLocal) {
            selectFolderForSaveViewByVisibleText(gridIdx, FOLDER_LOCAL);
        } else {
            selectFolderForSaveViewByVisibleText(gridIdx, FOLDER_GLOBAL);
        }

        if (isNew) {
            seleniumSettings.getWebDriver().findElement(By.id(FIELD_VIEW_NAME + gridIdx)).sendKeys(entityPrefix);
        } else {
            if (isLocal) {//TODO
                new Select(seleniumSettings.getWebDriver().findElement(By.id("lbViewName"))).selectByVisibleText(AbstractSeleniumCore.PREFIX_LOCAL + entityPrefix);//TODO
            } else {//TODO
                new Select(seleniumSettings.getWebDriver().findElement(By.id("lbGViewName"))).selectByVisibleText(AbstractSeleniumCore.PREFIX_GLOBAL + entityPrefix);//TODO
            }
        }

        closeSaveViewFormOk(gridIdx);

        if (isNew) {
            wait.waitViewsCount(gridIdx, beforeSaveSize + 1);
        } else {
            wait.waitViewsCount(gridIdx, beforeSaveSize);
        }

        if (isLocal) {
            isExistAndSelectedView(gridIdx, AbstractSeleniumCore.PREFIX_LOCAL + entityPrefix);
        } else {
            isExistAndSelectedView(gridIdx, AbstractSeleniumCore.PREFIX_GLOBAL + entityPrefix);
        }
    }

    public void deleteView(Long gridIdx, String entityPrefix) {
        String currentViewName = getCurrentViewName(gridIdx);

        int beforeDeleteSize = getViewsCount(gridIdx);

        seleniumSettings.getWebDriver().findElement(By.id(VIEW_SELECT + gridIdx)).click();

        elementWait.waitElementById(VIEW_CONTAINER + gridIdx);
        elementWait.waitElementVisibleById(VIEW_CONTAINER + gridIdx);
        elementWait.waitElementDisplayById(VIEW_CONTAINER + gridIdx);

        window.openModal(By.id(BUTTON_ORGANIZE + gridIdx));
        tree.waitLoad(0L);
        wait.waitFormLoad();
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));

        selectViewInOrganize(entityPrefix);

        seleniumSettings.getWebDriver().findElement(By.name(AbstractSeleniumCore.BUTTON_DELETE_TREE_ID_BASE + 0L)).click();
        wait.waitAlert();
        seleniumSettings.getWebDriver().switchTo().alert().accept();
        tree.waitLoad(0L);

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
        grid2.waitLoad(gridIdx);

        wait.waitViewsCount(gridIdx, beforeDeleteSize - 1);

        boolean isDeletedView = false;
        seleniumSettings.getWebDriver().findElement(By.id(VIEW_SELECT + gridIdx)).click();
        for (WebElement view : getViews(gridIdx)) {
            if (view.getText().equals(entityPrefix)) {
                isDeletedView = true;
            }
        }
        Assert.assertEquals(isDeletedView, false, "View " + entityPrefix + " isn't deleted");

        if (currentViewName.equals(entityPrefix)) {
            viewWait.waitCurrentViewName(gridIdx, UNSAVED_VIEW_NAME);
            grid2.waitLoad(gridIdx);
        } else {
            viewWait.waitCurrentViewName(gridIdx, currentViewName);
            grid2.waitLoad(gridIdx);
        }
    }

    public List<WebElement> getLeftColumns() {
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        List<WebElement> leftColumns = seleniumSettings.getWebDriver().findElement(By.id(LEFT_COLUMNS_DIV_ID)).findElements(By.className("record"));
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return leftColumns;
    }

    public void selectLastRightColumn() {
        List<WebElement> actualRightColumns = getRightColumns();
        js.scrollNewDropDownTop(RIGHT_COLUMNS_DIV_ID, "scrollContainer", (actualRightColumns.size() - 1) * COLUMN_DIV_HEIGHT);
        actualRightColumns.get(actualRightColumns.size() - 1).click();
    }

    public List<WebElement> getRightColumns() {
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        List<WebElement> rightColumns = seleniumSettings.getWebDriver().findElement(By.id(RIGHT_COLUMNS_DIV_ID)).findElements(By.className("record"));
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return rightColumns;
    }

    public void checkViewOptionsNew(Long gridIdx, String entityPrefix,
            Long gridColumnsUnsavedView, List<String> leftColumnsUnsavedView, List<String> rightColumnsUnsavedView,
            Long gridColumnsLocalView1, List<String> leftColumnsLocalView1, List<String> rightColumnsLocalView1,
            Long gridColumnsGlobalView1, List<String> leftColumnsGlobalView1, List<String> rightColumnsGlobalView1,
            Long gridColumnsLocalView2, List<String> leftColumnsLocalView2, List<String> rightColumnsLocalView2,
            Long gridColumnsGlobalView2, List<String> leftColumnsGlobalView2, List<String> rightColumnsGlobalView2) {
        //Save Local View 1
        selectColumns(gridIdx, rightColumnsLocalView1);
        Assert.assertEquals(js.getGridColumnsCount(gridIdx), gridColumnsLocalView1);
        checkColumns(gridIdx, leftColumnsLocalView1, rightColumnsLocalView1);
        saveView(gridIdx, entityPrefix + VIEW_NAME + "1", true, true);
        Assert.assertEquals(js.getGridColumnsCount(gridIdx), gridColumnsLocalView1);
        checkColumns(gridIdx, leftColumnsLocalView1, rightColumnsLocalView1);

        //Save Global View 1
        selectColumns(gridIdx, rightColumnsGlobalView1);
        Assert.assertEquals(js.getGridColumnsCount(gridIdx), gridColumnsGlobalView1);
        checkColumns(gridIdx, leftColumnsGlobalView1, rightColumnsGlobalView1);
        saveView(gridIdx, entityPrefix + VIEW_NAME + "1", false, true);
        Assert.assertEquals(js.getGridColumnsCount(gridIdx), gridColumnsGlobalView1);
        checkColumns(gridIdx, leftColumnsGlobalView1, rightColumnsGlobalView1);

        //Save Local View 2
        selectColumns(gridIdx, rightColumnsLocalView2);
        Assert.assertEquals(js.getGridColumnsCount(gridIdx), gridColumnsLocalView2);
        checkColumns(gridIdx, leftColumnsLocalView2, rightColumnsLocalView2);
        saveView(gridIdx, entityPrefix + VIEW_NAME + "2", true, true);
        Assert.assertEquals(js.getGridColumnsCount(gridIdx), gridColumnsLocalView2);
        checkColumns(gridIdx, leftColumnsLocalView2, rightColumnsLocalView2);

        //Save Global View 2
        selectColumns(gridIdx, rightColumnsGlobalView2);
        Assert.assertEquals(js.getGridColumnsCount(gridIdx), gridColumnsGlobalView2);
        checkColumns(gridIdx, leftColumnsGlobalView2, rightColumnsGlobalView2);
        saveView(gridIdx, entityPrefix + VIEW_NAME + "2", false, true);
        Assert.assertEquals(js.getGridColumnsCount(gridIdx), gridColumnsGlobalView2);
        checkColumns(gridIdx, leftColumnsGlobalView2, rightColumnsGlobalView2);

        //Unsaved View
        selectColumns(gridIdx, rightColumnsUnsavedView);
        Assert.assertEquals(js.getGridColumnsCount(gridIdx), gridColumnsUnsavedView);
        checkColumns(gridIdx, leftColumnsUnsavedView, rightColumnsUnsavedView);

        //Select Local View 1
        selectByVisibleText(gridIdx, AbstractSeleniumCore.PREFIX_LOCAL + entityPrefix + VIEW_NAME + "1");
        Assert.assertEquals(js.getGridColumnsCount(gridIdx), gridColumnsLocalView1);
        checkColumns(gridIdx, leftColumnsLocalView1, rightColumnsLocalView1);

        //Select Global View 1
        selectByVisibleText(gridIdx, AbstractSeleniumCore.PREFIX_GLOBAL + entityPrefix + VIEW_NAME + "1");
        Assert.assertEquals(js.getGridColumnsCount(gridIdx), gridColumnsGlobalView1);
        checkColumns(gridIdx, leftColumnsGlobalView1, rightColumnsGlobalView1);

        //Select Unsaved View
        selectByVisibleText(gridIdx, UNSAVED_VIEW_NAME);
        Assert.assertEquals(js.getGridColumnsCount(gridIdx), gridColumnsUnsavedView);
        checkColumns(gridIdx, leftColumnsUnsavedView, rightColumnsUnsavedView);

        //Select Local View 2
        selectByVisibleText(gridIdx, AbstractSeleniumCore.PREFIX_LOCAL + entityPrefix + VIEW_NAME + "2");
        Assert.assertEquals(js.getGridColumnsCount(gridIdx), gridColumnsLocalView2);
        checkColumns(gridIdx, leftColumnsLocalView2, rightColumnsLocalView2);

        //Select Global View 2
        selectByVisibleText(gridIdx, AbstractSeleniumCore.PREFIX_GLOBAL + entityPrefix + VIEW_NAME + "2");
        Assert.assertEquals(js.getGridColumnsCount(gridIdx), gridColumnsGlobalView2);
        checkColumns(gridIdx, leftColumnsGlobalView2, rightColumnsGlobalView2);

        //Delete Local 1
        deleteView(gridIdx, AbstractSeleniumCore.PREFIX_LOCAL + entityPrefix + VIEW_NAME + "1");
        Assert.assertEquals(js.getGridColumnsCount(gridIdx), gridColumnsGlobalView2);
        checkColumns(gridIdx, leftColumnsGlobalView2, rightColumnsGlobalView2);

        //Delete Global 1
        deleteView(gridIdx, AbstractSeleniumCore.PREFIX_GLOBAL + entityPrefix + VIEW_NAME + "1");
        Assert.assertEquals(js.getGridColumnsCount(gridIdx), gridColumnsGlobalView2);
        checkColumns(gridIdx, leftColumnsGlobalView2, rightColumnsGlobalView2);

        //Delete Local 2
        deleteView(gridIdx, AbstractSeleniumCore.PREFIX_LOCAL + entityPrefix + VIEW_NAME + "2");
        Assert.assertEquals(js.getGridColumnsCount(gridIdx), gridColumnsGlobalView2);
        checkColumns(gridIdx, leftColumnsGlobalView2, rightColumnsGlobalView2);

        //Delete Global 2
        deleteView(gridIdx, AbstractSeleniumCore.PREFIX_GLOBAL + entityPrefix + VIEW_NAME + "2");
        Assert.assertEquals(js.getGridColumnsCount(gridIdx), gridColumnsUnsavedView);
        checkColumns(gridIdx, leftColumnsUnsavedView, rightColumnsUnsavedView);
    }

    public Select selectApplet(Select apps, Select tabs, String appletName, int cntTabs) {
        apps.selectByVisibleText(appletName);
        jquery.waitLoad(); //wait load tabs and fields
        wait.waitListBoxLoad(tabs);
        jquery.waitLoad(); //wait load tabs and fields
        Assert.assertEquals(apps.getFirstSelectedOption().getText(), appletName);
        jquery.waitLoad(); //wait load tabs and fields
        wait.waitListBoxLoadCnt(tabs, cntTabs);
        jquery.waitLoad(); //wait load tabs and fields
        Assert.assertEquals(tabs.getOptions().size(), cntTabs, "Tabs have wrong cnt");
        return tabs;
    }

    public Select selectTab(Select tabs, Select allFields, String tabName, int cntFields) {
        tabs.selectByVisibleText(tabName);
        jquery.waitLoad(); //wait load tabs and fields
        wait.waitListBoxLoad(allFields);
        jquery.waitLoad(); //wait load tabs and fields
        wait.waitListBoxLoadCnt(allFields, cntFields);
        jquery.waitLoad(); //wait load tabs and fields
        Assert.assertEquals(allFields.getOptions().size(), cntFields, "All Fields have wrong cnt");
        return allFields;
    }

    public WebElement selectTab(Select tabs, WebElement allFields, String tabName, int cntFields) {
        tabs.selectByVisibleText(tabName);
        jquery.waitLoad(); //wait load tabs and fields
        wait.waitListBoxLoad(allFields);
        jquery.waitLoad(); //wait load tabs and fields
        wait.waitListBoxLoadCnt(allFields, cntFields);
        jquery.waitLoad(); //wait load tabs and fields
        Assert.assertEquals(allFields.findElements(By.tagName("div")).size(), cntFields, "All Fields have wrong cnt");
        return allFields;
    }

    public void selectAllColumns(Long gridIdx) {
        openViewForm(gridIdx);

        List<WebElement> actualRightColumns = getRightColumns();
        js.scrollNewDropDownTop(RIGHT_COLUMNS_DIV_ID, SCROLL_CONTAINER, (actualRightColumns.size() - 1) * COLUMN_DIV_HEIGHT);
        actualRightColumns.get(actualRightColumns.size() - 1).click();

        while (seleniumSettings.getWebDriver().findElement(By.id(ADD_BUTTON_ID)).isEnabled()) {
            seleniumSettings.getWebDriver().findElement(By.id(ADD_BUTTON_ID)).click();
        }

        closeViewFormOk(gridIdx);
    }

    public void removeAllColumns() {
        while (seleniumSettings.getWebDriver().findElement(By.id(REMOVE_BUTTON_ID)).isEnabled()) {
            seleniumSettings.getWebDriver().findElement(By.id(REMOVE_BUTTON_ID)).click();
        }
    }

    public void selectAndCheckColumns(Long gridIdx, Long gridColumns, List<String> leftColumns, List<String> rightColumns) {
        selectColumns(gridIdx, rightColumns);
        Assert.assertEquals(js.getGridColumnsCount(gridIdx), gridColumns);
        checkColumns(gridIdx, leftColumns, rightColumns);
    }

    private void selectColumns(Long gridIdx, List<String> rightColumns) {
        openViewForm(gridIdx);

        List<WebElement> actualRightColumns = getRightColumns();
        js.scrollNewDropDownTop(RIGHT_COLUMNS_DIV_ID, SCROLL_CONTAINER, (actualRightColumns.size() - 1) * COLUMN_DIV_HEIGHT);
        actualRightColumns.get(actualRightColumns.size() - 1).click();

        removeAllColumns();

        for (String rightColumn : rightColumns) {
            List<WebElement> actualLeftColumns = getLeftColumns();
            for (int i = 0; i < actualLeftColumns.size(); i++) {
                js.scrollNewDropDownTop(LEFT_COLUMNS_DIV_ID, SCROLL_CONTAINER, i * COLUMN_DIV_HEIGHT);
                if (actualLeftColumns.get(i).findElements(By.className(COLUMN_LABEL)).get(0).getText().equals(rightColumn)) {
                    actualLeftColumns.get(i).click();
                    seleniumSettings.getWebDriver().findElement(By.id(ADD_BUTTON_ID)).click();
                    break;
                }
            }
        }

        closeViewFormOk(gridIdx);
    }

    private void checkColumns(Long gridIdx, List<String> leftColumns, List<String> rightColumns) {
        openViewForm(gridIdx);

        List<WebElement> actualLeftColumns = getLeftColumns();
        Assert.assertEquals(actualLeftColumns.size(), leftColumns.size());
        for (int i = 0; i < actualLeftColumns.size(); i++) {
            js.scrollNewDropDownTop(LEFT_COLUMNS_DIV_ID, SCROLL_CONTAINER, i * COLUMN_DIV_HEIGHT);
            Assert.assertEquals(actualLeftColumns.get(i).findElements(By.className(COLUMN_LABEL)).get(0).getText(), leftColumns.get(i));
        }

        List<WebElement> actualRightColumns = getRightColumns();
        Assert.assertEquals(actualRightColumns.size(), rightColumns.size());
        for (int i = 0; i < actualRightColumns.size(); i++) {
            js.scrollNewDropDownTop(RIGHT_COLUMNS_DIV_ID, SCROLL_CONTAINER, i * COLUMN_DIV_HEIGHT);
            Assert.assertEquals(actualRightColumns.get(i).findElements(By.className(COLUMN_LABEL)).get(0).getText(), rightColumns.get(i));
        }

        closeViewFormCancel();
    }

    public void switchToRootSubgroup() {
        element.click(seleniumSettings.getWebDriver().findElement(By.id(NAV_PANEL)).findElement(By.tagName("input")));
        waitLeftListBoxReady();
    }

    public void switchToParentSubgroup() {
        List<WebElement> links = seleniumSettings.getWebDriver().findElement(By.id(NAV_PANEL)).findElements(By.className("navLink"));
        element.click(links.get(links.size() - 2));
        waitLeftListBoxReady();
    }

    public void switchToSubgroup(String subgroupName) {
        element.click(seleniumSettings.getWebDriver().findElement(By.id(NAV_PANEL)).findElement(By.name(subgroupName)));
        waitLeftListBoxReady();
    }

    public void switchToSubgroupInList(String text) {
        WebElement subgroupElement = null;
        List<WebElement> subgroups = seleniumSettings.getWebDriver().findElement(By.id("listBoxContent")).findElements(By.className("groupRecord"));
        for (WebElement subgroup : subgroups) {
            if (subgroup.getAttribute("innerText").trim().equals(text)) {
                if (subgroupElement != null) {
                    throw new SeleniumUnexpectedException("Subgroup with text[" + text + "] found many times");
                }
                subgroupElement = subgroup;
            }
        }

        if (subgroupElement == null) {
            throw new SeleniumUnexpectedException("Subgroup with text[" + text + "] not found");
        }

        element.click(subgroupElement);
        waitLeftListBoxReady();
    }

    public void switchToFieldGroup() {
        element.clickById(BUTTON_GROUP_FIELD);
        waitLeftListBoxReady();
    }

    public void switchToTaskGroup() {
        element.clickById(BUTTON_GROUP_TASK);
        waitLeftListBoxReady();
    }

    public void switchToDrillDownGroup() {
        element.clickById(BUTTON_GROUP_DRILLDOWN);
        waitLeftListBoxReady();
    }

    public void switchToMarkupGroup() {
        element.clickById(BUTTON_GROUP_MARKUP);
        waitLeftListBoxReady();
    }

    public void switchToDatePairGroup() {
        element.clickById(BUTTON_GROUP_DATE_PAIR);
        waitLeftListBoxReady();
    }

    public void waitCurrentViewName(Long gridIdx, String viewName) {
        viewWait.waitCurrentViewName(gridIdx, viewName);
    }

    public void waitLeftListBoxReady() {
        viewWait.waitLeftListBoxReady();
    }

    public void waitRightListBoxReady() {
        viewWait.waitRightListBoxReady();
    }

}