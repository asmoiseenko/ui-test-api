package com.onevizion.uitest.api.helper;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;

@Component
public class UsersSettings {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Wait wait;

    @Resource
    private Window window;

    @Resource
    private ElementWait elementWait;

    @Resource
    private Element element;

    @Resource
    private AssertElement assertElement;

    private ThreadLocal<WebElement> html = new ThreadLocal<>();

    public void openUserSettings() {
        WebElement html = seleniumSettings.getWebDriver().findElement(By.tagName("html"));
        this.html.set(html);

        elementWait.waitElementById("topPanelUserNameLbl");
        elementWait.waitElementVisibleById("topPanelUserNameLbl");
        elementWait.waitElementDisplayById("topPanelUserNameLbl");

        window.openModal(By.id("topPanelUserNameLbl"));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
    }

    public void closeUserSettingsOkWithReloadPage() {
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));

        elementWait.waitElementNotExist(this.html.get());

        wait.waitWebElement(By.id("mainContainer"));
        wait.waitWebElement(By.id("Table1"));
        wait.waitWebElement(By.id("messageInfoDivContainer"));
        wait.waitWebElement(By.id("messageErrorDivContainer"));

        elementWait.waitElementById("topPanelUserNameLbl");
        elementWait.waitElementVisibleById("topPanelUserNameLbl");
        elementWait.waitElementDisplayById("topPanelUserNameLbl");

        wait.waitWebElement(By.id("userNameMenuItemlogoff"));
    }

    public void closeUserSettingsOkWithoutReloadPage() {
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
    }

    public void closeUserSettingsCancel() {
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void changeLanguage(String language) {
        new Select(seleniumSettings.getWebDriver().findElement(By.name("LanguageID"))).selectByVisibleText(language);
    }

    public void changeTimeFormat(String timeFormat) {
        new Select(seleniumSettings.getWebDriver().findElement(By.name("TimeFormat"))).selectByVisibleText(timeFormat);
    }

    public void changeDateFormat(String dateFormat) {
        new Select(seleniumSettings.getWebDriver().findElement(By.name("DateFormat"))).selectByVisibleText(dateFormat);
    }

    public void changeExactQuickSearchForClipboard(String exactQuickSearchForClipboard) {
        element.moveToElementByName("exactSearchClipboard");
        new Select(seleniumSettings.getWebDriver().findElement(By.name("exactSearchClipboard"))).selectByVisibleText(exactQuickSearchForClipboard);
    }

    public void checkHideStartTaskDates(String hideStartTaskDates) {
        assertElement.assertSelect("HideStart", hideStartTaskDates);
    }

}