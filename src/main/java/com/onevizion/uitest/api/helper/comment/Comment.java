package com.onevizion.uitest.api.helper.comment;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumLogger;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.Element;
import com.onevizion.uitest.api.helper.ElementWait;
import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.Tb;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.grid.Grid2;

@Component
public class Comment {

    @Resource
    private Tb tb;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private SeleniumLogger seleniumLogger;

    @Resource
    private Element element;

    @Resource
    private CommentJs commentJs;

    @Resource
    private Wait wait;

    @Resource
    private Grid grid;

    @Resource
    private Js js;

    @Resource
    private Grid2 grid2;

    @Resource
    private ElementWait elementWait;

    public void openCommentFormFromForm(String fieldId, boolean isShowMenu, int elementPosition) {
        String id;
        if (elementPosition > 1) {
            //id = tbHelper.getLastFieldIndex(fieldId, elementPosition);
            id = "";
            id = "idx" + id;
        } else {
            id = seleniumSettings.getWebDriver().findElement(By.name(fieldId)).getAttribute("id");
            id = id.replace("_disp", ""); //for efile field
        }

        if (isShowMenu) {
            element.moveToElementById(id + "_lbl");
            seleniumSettings.getWebDriver().findElement(By.id(id + "_lbl")).click();
            int i = 0;
            do {
                try {
                    for (WebElement webElement : seleniumSettings.getWebDriver().findElements(By.className("sub_item_text"))) {
                        if ("Comments".equals(webElement.getText())) {
                            //windowHelper.openModal(element);
                            openCommentForm(webElement);
                            break;
                        }
                    }
                } catch (StaleElementReferenceException e) {
                    
                }
                i++;
            } while (i < 5);
        } else {
            //windowHelper.openModal(seleniumSettings.getWebDriver().findElement(By.id(id + "_lbl")));
        }

        //waitHelper.waitWebElement(By.id(AbstractSeleniumInternalPage.BUTTON_CANCEL_ID_BASE));
        //waitHelper.waitGridLoad(1L, 1L);
    }

    public void openCommentFormFromGrid(Long rowIndex, Long columnIndex) {
        tb.rightClickCell(0L, rowIndex, columnIndex);

        int i = 0;
        do {
            try {
                for (WebElement webElement : seleniumSettings.getWebDriver().findElements(By.className("sub_item_text"))) {
                    if ("Comments".equals(webElement.getText())) {
                        //windowHelper.openModal(element);
                        openCommentForm(webElement);
                        break;
                    }
                }
            } catch (StaleElementReferenceException e) {
                
            }
            i++;
        } while (i < 5);
    }

    private void openCommentForm(WebElement webElement) {
        webElement.click();

        wait.waitWebElement(By.className("dhxwin_active"));

        WebElement iframe = commentJs.getIframe();

        seleniumSettings.getWebDriver().switchTo().frame(iframe);
        wait.waitWebElement(By.id("comment"));
        wait.waitWebElement(By.id("btnSubmit"));
        wait.waitFormLoad();
        grid2.waitLoad();
    }

    public void addComment(String text) {
        elementWait.waitElementDisabledById("btnSubmit");
        elementWait.waitElementAttributeById("comment", "value", "");

        Long rowsCntBefore = grid.getGridRowsCount(0L);

        seleniumSettings.getWebDriver().findElement(By.id("comment")).sendKeys(text);
        elementWait.waitElementEnabledById("btnSubmit");
        seleniumSettings.getWebDriver().findElement(By.id("btnSubmit")).findElement(By.xpath("..")).click();

        grid2.waitLoad();

        wait.waitGridRowsCount(0L, rowsCntBefore + 1L);

        elementWait.waitElementDisabledById("btnSubmit");
        elementWait.waitElementAttributeById("comment", "value", "");

        correctSleep(500L);
    }

    public void deleteComment(String text) {
        Long rowsCntBefore = grid.getGridRowsCount(0L);

        Long rowIndex = null;
        for (Long i = 0L; i < rowsCntBefore; i++) {
            String value = js.getGridCellValueByRowIndexAndColIndex(0L, i, 0L);
            if (text.equals(value)) {
                if (rowIndex != null) {
                    throw new SeleniumUnexpectedException("Found many rows with same value[" + text + "]");
                }
                rowIndex = i;
            }
        }

        if (rowIndex == null) {
            throw new SeleniumUnexpectedException("Row with value[" + text + "] not found");
        }

        js.selectGridRow(0L, rowIndex);
        String rowId = js.getGridRowIdByIndex(0L, rowIndex);

        elementWait.waitElementById("btnDelete" + rowId);
        elementWait.waitElementVisibleById("btnDelete" + rowId);
        elementWait.waitElementDisplayById("btnDelete" + rowId);

        seleniumSettings.getWebDriver().findElement(By.id("btnDelete" + rowId)).click();

        wait.waitAlert();
        seleniumSettings.getWebDriver().switchTo().alert().accept();

        grid2.waitLoad();

        wait.waitGridRowsCount(0L, rowsCntBefore - 1L);
    }

    public void closeCommentForm() {
        seleniumSettings.getWebDriver().switchTo().parentFrame();

        seleniumSettings.getWebDriver().findElement(By.className("dhxwin_active")).findElement(By.className("dhxwin_button_close")).click();
    }

    private void correctSleep(Long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            seleniumLogger.error("Interrupted!");
            Thread.currentThread().interrupt();
        }
    }

}