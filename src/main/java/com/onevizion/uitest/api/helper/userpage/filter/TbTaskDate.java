package com.onevizion.uitest.api.helper.userpage.filter;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.GridHelper;
import com.onevizion.uitest.api.helper.JsHelper;
import com.onevizion.uitest.api.vo.ConfigFieldType;

@Component
public class TbTaskDate {

    @Resource
    private JsHelper jsHelper;

    @Resource
    private GridHelper gridHelper;

    @Resource
    private UserpageFilter userpageFilter;

    @SuppressWarnings("unchecked")
    public void test(String columnId, String value, String startFinish) {
        Long columnIndex = jsHelper.getGridColIndexById(0L, columnId);

        String fieldName = jsHelper.getGridColumnLabelByColIndex(0L, columnIndex, 0L);
        if ("F".equals(startFinish)) {
            columnIndex = columnIndex + 1L;
        }

        Long rowsCnt = gridHelper.getGridRowsCount(0L);
        List<String> cellVals = (List<String>) jsHelper.getGridCellsValuesTxtForColumnByColIndex(0L, rowsCnt, columnIndex);

        userpageFilter.checkFilterOperators(fieldName, Arrays.asList("S", "F"), Arrays.asList("=", ">", "<", ">=", "<=", ">=Today", "<=Today", "Within", "This Wk",
                "This Wk to Dt", "This Mo", "This Mo to Dt", "This FQ", "This FQ to Dt", "This FY", "This FY to Dt", "<>", "Is Null", "Is Not Null"));
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, startFinish, "=", ConfigFieldType.DATE, columnIndex, null, cellVals, null);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, startFinish, ">", ConfigFieldType.DATE, columnIndex, null, cellVals, null);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, startFinish, "<", ConfigFieldType.DATE, columnIndex, null, cellVals, null);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, startFinish, ">=", ConfigFieldType.DATE, columnIndex, null, cellVals, null);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, startFinish, "<=", ConfigFieldType.DATE, columnIndex, null, cellVals, null);
        //TODO
        //>=Today
        //<=Today
        //Within
        //This Wk
        //This Wk to Dt
        //This Mo
        //This Mo to Dt
        //This FQ
        //This FQ to Dt
        //This FY
        //This FY to Dt
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, startFinish, "<>", ConfigFieldType.DATE, columnIndex, null, cellVals, null);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, startFinish, "Is Null", ConfigFieldType.DATE, columnIndex, null, cellVals, null);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, startFinish, "Is Not Null", ConfigFieldType.DATE, columnIndex, null, cellVals, null);
    }

}