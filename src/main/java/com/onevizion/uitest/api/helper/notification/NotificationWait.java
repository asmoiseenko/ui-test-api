package com.onevizion.uitest.api.helper.notification;

import javax.annotation.Resource;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
class NotificationWait {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private NotificationJs notificationJs;

    void waitTreeLoad() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for treeLoaded is failed.")
            .until(webdriver -> notificationJs.isTreeLoaded());
    }

}