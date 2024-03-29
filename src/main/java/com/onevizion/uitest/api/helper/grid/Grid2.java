package com.onevizion.uitest.api.helper.grid;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.jquery.Jquery;
import com.onevizion.uitest.api.vo.LockType;

@Component
public class Grid2 {

    @Resource
    private Grid2Wait grid2Wait;

    @Resource
    private Grid2Js grid2Js;

    @Resource
    private Wait wait;

    @Resource
    private Js js;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource //TODO bug in Grid-115098 load views/filters before load grid
    private Jquery jquery; //TODO bug in Grid-115098 load views/filters before load grid

    public void waitLoad() {
        waitLoad(AbstractSeleniumCore.getGridIdx());
    }

    public void waitLoad(Long gridId) {
        jquery.waitLoad(); //TODO bug in Grid-115098 load views/filters before load grid
        wait.waitWebElement(By.id(AbstractSeleniumCore.GRID_ID_BASE + gridId));
        wait.waitWebElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE + gridId));

        final Long parentGridId;
        if (js.getIsSubGrid(gridId)) {
            parentGridId = js.getParentGridIdx(gridId);
        } else {
            parentGridId = gridId;
        }

        wait.waitWebElement(By.id(AbstractSeleniumCore.GRID_ID_BASE + parentGridId));
        wait.waitWebElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE + parentGridId));
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for grid with id=[" + parentGridId + "] is failed")
            .until(webdriver -> !webdriver.findElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE + parentGridId)).isDisplayed());
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for grid with id=[" + gridId + "] is failed")
            .until(webdriver -> js.isGridLoaded(gridId).equals("1"));
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for grid with id=[" + gridId + "] is failed")
            .until(webdriver -> js.isGridDataLoaded(gridId));
    }

    public void waitLoadAllRows(Long gridIdx) {
        grid2Wait.waitLoadAllRows(gridIdx);
    }

    public void waitGridCellLockType(Long gridId, Long columnIndex, Long rowIndex, LockType lockType) {
        grid2Wait.waitGridCellLockType(gridId, columnIndex, rowIndex, lockType);
    }

    public String getPageName(Long gridId) {
        return grid2Js.getPageName(gridId);
    }

}