package com.onevizion.uitest.api.helper.colorpicker;

import javax.annotation.Resource;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
class ColorPickerWait {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private ColorPickerJs colorPickerJs;

    void waitColorPicker() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for ColorPicke is failed")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return colorPickerJs.isColorPickerExist();
                }
            });
    }

}