package com.onevizion.uitest.api.helper.entity;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Element;
import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.PsSelector;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.grid.Grid2;
import com.onevizion.uitest.api.helper.tab.Tab;
import com.onevizion.uitest.api.vo.entity.TrackorTour;
import com.onevizion.uitest.api.vo.entity.TrackorTourStep;

@Component
public class EntityTrackorTour {

    private static final String LABEL = "label";
    private static final String TRACKOR_TYPE = "xitorTypeId";
    private static final String START_PLACE = "startTourPlace";
    private static final String ORDER_NUMBER = "orderNumber";
    private static final String DESCRIPTION = "description";

    @Resource
    private Window window;

    @Resource
    private Wait wait;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private PsSelector psSelector;

    @Resource
    private Tab tab;

    @Resource
    private Grid grid;

    @Resource
    private Grid2 grid2;

    @Resource
    private AssertElement assertElement;

    @Resource
    private Element element;

    @Resource
    private Js js;

    @Resource
    private EntityTrackorTourStep entityTrackorTourStep;

    public void addWithChilds(TrackorTour trackorTour) {
        add(trackorTour);

        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        tab.goToTab(2L); //Tour Steps
        grid2.waitLoad(2L);

        for (TrackorTourStep trackorTourStep : trackorTour.getSteps()) {
            entityTrackorTourStep.add(trackorTourStep);
        }

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void add(TrackorTour trackorTour) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name(LABEL)).sendKeys(trackorTour.getLabel());

        new Select(seleniumSettings.getWebDriver().findElement(By.name(TRACKOR_TYPE))).selectByVisibleText(trackorTour.getTrackorType());

        new Select(seleniumSettings.getWebDriver().findElement(By.name(START_PLACE))).selectByVisibleText(trackorTour.getStartPlace());

        if ("Page".equals(trackorTour.getStartPlace())) {
            psSelector.selectSpecificValue(By.name("btngridPageName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1L, trackorTour.getPageName(), 1L);
        } else if ("Config Applet".equals(trackorTour.getStartPlace())) {
            psSelector.selectSpecificValue(By.name("btnconfigAppName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1L, trackorTour.getAppletName(), 1L);
        } else if ("Config Tab".equals(trackorTour.getStartPlace())) {
            psSelector.selectSpecificValue(By.name("btnconfigGroupName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1L, trackorTour.getTabName(), 1L);
        } else {
            throw new SeleniumUnexpectedException("Not support StartPlace [" + trackorTour.getStartPlace() + "]");
        }

        new Select(seleniumSettings.getWebDriver().findElement(By.name(ORDER_NUMBER))).selectByVisibleText(trackorTour.getOrderNumber());

        seleniumSettings.getWebDriver().findElement(By.name(DESCRIPTION)).sendKeys(trackorTour.getDescription());

        tab.goToTab(2L); //Role Assignments
        grid2.waitLoad(2L);
        grid.clearAssignmentGridColumn2(2L, 0L);
        grid.selectAssignmentGridColumn2New(2L, 0L, 2L, trackorTour.getRoles());

        element.clickById(AbstractSeleniumCore.BUTTON_APPLY_ID);
        wait.waitReloadForm("reloaded=1");
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        tab.goToTab(4L); //Components Package
        grid2.waitLoad(4L);
        grid.clearAssignmentGridColumn2(4L, 0L);
        grid.selectAssignmentGridColumn2New(4L, 0L, 2L, trackorTour.getPackages());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void edit(TrackorTour trackorTour) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        seleniumSettings.getWebDriver().findElement(By.name(LABEL)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(LABEL)).sendKeys(trackorTour.getLabel());

        new Select(seleniumSettings.getWebDriver().findElement(By.name(TRACKOR_TYPE))).selectByVisibleText(trackorTour.getTrackorType());

        new Select(seleniumSettings.getWebDriver().findElement(By.name(START_PLACE))).selectByVisibleText(trackorTour.getStartPlace());

        if ("Page".equals(trackorTour.getStartPlace())) {
            psSelector.selectSpecificValue(By.name("btngridPageName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1L, trackorTour.getPageName(), 1L);
        } else if ("Config Applet".equals(trackorTour.getStartPlace())) {
            psSelector.selectSpecificValue(By.name("btnconfigAppName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1L, trackorTour.getAppletName(), 1L);
        } else if ("Config Tab".equals(trackorTour.getStartPlace())) {
            psSelector.selectSpecificValue(By.name("btnconfigGroupName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + 0L), 1L, trackorTour.getTabName(), 1L);
        } else {
            throw new SeleniumUnexpectedException("Not support StartPlace [" + trackorTour.getStartPlace() + "]");
        }

        new Select(seleniumSettings.getWebDriver().findElement(By.name(ORDER_NUMBER))).selectByVisibleText(trackorTour.getOrderNumber());

        seleniumSettings.getWebDriver().findElement(By.name(DESCRIPTION)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(DESCRIPTION)).sendKeys(trackorTour.getDescription());

        tab.goToTab(3L);//Role Assignments
        grid2.waitLoad(3L);
        grid.clearAssignmentGridColumn2(3L, 0L);
        grid.selectAssignmentGridColumn2New(3L, 0L, 2L, trackorTour.getRoles());

        tab.goToTab(4L); //Components Package
        grid2.waitLoad(4L);
        grid.clearAssignmentGridColumn2(4L, 0L);
        grid.selectAssignmentGridColumn2New(4L, 0L, 2L, trackorTour.getPackages());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        grid2.waitLoad();
    }

    public void testWithChilds(TrackorTour trackorTour) {
        testInGrid(AbstractSeleniumCore.getGridIdx(), 0L, trackorTour);
        testOnForm(trackorTour);

        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        tab.goToTab(2L); //Tour Steps
        grid2.waitLoad(2L);

        Assert.assertEquals(grid.getGridRowsCount(2L), Long.valueOf(trackorTour.getSteps().size()));
        for (int i = 0; i < trackorTour.getSteps().size(); i++) {
            js.selectGridRow(2L, Long.valueOf(i));
            entityTrackorTourStep.testInGrid(2L, Long.valueOf(i), trackorTour.getSteps().get(i));
            entityTrackorTourStep.testOnForm(trackorTour.getSteps().get(i));
        }

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testOnForm(TrackorTour trackorTour) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        assertElement.assertText(LABEL, trackorTour.getLabel());
        assertElement.assertSelect(TRACKOR_TYPE, trackorTour.getTrackorType());
        assertElement.assertSelect(START_PLACE, trackorTour.getStartPlace());

        if ("Page".equals(trackorTour.getStartPlace())) {
            assertElement.assertRadioPsSelector("gridPageName", "btngridPageName", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorTour.getPageName(), 1L, true);
        } else if ("Config Applet".equals(trackorTour.getStartPlace())) {
            assertElement.assertRadioPsSelector("configAppName", "btnconfigAppName", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorTour.getAppletName(), 1L, true);
        } else if ("Config Tab".equals(trackorTour.getStartPlace())) {
            assertElement.assertRadioPsSelector("configGroupName", "btnconfigGroupName", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, trackorTour.getTabName(), 1L, true);
        } else {
            throw new SeleniumUnexpectedException("Not support StartPlace [" + trackorTour.getStartPlace() + "]");
        }

        assertElement.assertSelect(ORDER_NUMBER, trackorTour.getOrderNumber());
        assertElement.assertText(DESCRIPTION, trackorTour.getDescription());

        tab.goToTab(3L); //Role Assignments
        grid2.waitLoad(3L);
        grid.checkAssignmentGridColumn2New(3L, 0L, 2L, trackorTour.getRoles());

        tab.goToTab(4L); //Components Package
        grid2.waitLoad(4L);
        grid.checkAssignmentGridColumn2New(4L, 0L, 2L, trackorTour.getPackages());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, TrackorTour trackorTour) {
        Map<Long, String> gridVals = new HashMap<>();

        gridVals.put(js.getColumnIndexByLabel(gridId, "Tour Label"), trackorTour.getLabel());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Trackor Type"), trackorTour.getTrackorType());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Page Name"), trackorTour.getPageName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Config App Name"), trackorTour.getAppletName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Config Tab Name"), trackorTour.getTabName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Order Number"), trackorTour.getOrderNumber());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Description"), trackorTour.getDescription());

        grid.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

}