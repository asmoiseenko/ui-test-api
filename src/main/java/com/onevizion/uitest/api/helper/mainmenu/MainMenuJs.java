package com.onevizion.uitest.api.helper.mainmenu;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Js;

@Component
class MainMenuJs extends Js {

    Boolean isLeftMenuSearchUpdated() {
        return Boolean.valueOf(execJs("return leftMenuSearchUpdatedCount == 0;"));
    }

}