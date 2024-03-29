package com.onevizion.uitest.api.helper.view;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Js;

@Component
class ViewJs extends Js {

    Boolean isReadyLeftListBox() {
        //TODO firefox 59 bug
        //https://github.com/mozilla/geckodriver/issues/1067
        //https://bugzilla.mozilla.org/show_bug.cgi?id=1420923
        //return Boolean.valueOf(execJs("return window.leftListBox.isReady == true;"));
        return Boolean.valueOf(execJs("return getLeftListBox().isReady == true;"));
    }

    Boolean isReadyRightListBox() {
        //TODO firefox 59 bug
        //https://github.com/mozilla/geckodriver/issues/1067
        //https://bugzilla.mozilla.org/show_bug.cgi?id=1420923
        //return Boolean.valueOf(execJs("return window.rightListBox.isReady == true;"));
        return Boolean.valueOf(execJs("return getRightListBox().isReady == true;"));
    }

}