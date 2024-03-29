package com.onevizion.uitest.api;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;

public class BrowserFirefox {

    public static FirefoxOptions create(SeleniumSettings seleniumSettings) {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("dom.disable_beforeunload", false); //TODO BUG IN W3C https://github.com/w3c/webdriver/issues/1294
        profile.setPreference("dom.successive_dialog_time_limit", 0);
        profile.setPreference("dom.max_script_run_time", 1000);
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.dir", seleniumSettings.getUploadFilesPath().replaceAll("\\\\", "\\\\\\\\"));
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/xml");
        //profile.setPreference("security.sandbox.content.level", 5);
        //profile.setEnableNativeEvents(false); /* TODO selenium 5374 issue */

        FirefoxOptions options = new FirefoxOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NONE);
        //options.addPreference("security.sandbox.content.level", 5);
        //options.setLegacy(true);
        //options.setLogLevel(FirefoxDriverLogLevel.TRACE);
        options.setProfile(profile);
        //TODO https://github.com/mozilla/geckodriver/issues/617
        //https://bugzilla.mozilla.org/show_bug.cgi?id=1264259
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
        options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        options.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);

        options.setHeadless(seleniumSettings.getHeadlessMode());

        return options;
    }

}