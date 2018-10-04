package com.onevizion.uitest.api.helper.userpage.filter;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.vo.ConfigFieldType;
import com.onevizion.uitest.api.vo.FilterOperatorType;

@Component
public class TbCalculatedField {

    @Resource
    private Js js;

    @Resource
    private Grid grid;

    @Resource
    private UserpageFilter userpageFilter;

    @SuppressWarnings("unchecked")
    public void test(String columnId, String value, boolean supportOuterOperations, List<String> ... cellValsKeys) {
        Long columnIndex = js.getGridColIndexById(0L, columnId);

        String fieldName = js.getGridColumnLabelByColIndex(0L, columnIndex, 0L);

        Long rowsCnt = grid.getGridRowsCount(0L);
        List<String> cellVals = (List<String>) js.getGridCellsValuesTxtForColumnByColIndex(0L, rowsCnt, columnIndex);

        List<FilterOperatorType> operators = FilterOperatorType.getCalculatedOperators(supportOuterOperations);
        userpageFilter.checkFilterOperators(fieldName, null, operators);

        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, null, "=", ConfigFieldType.CALCULATED, columnIndex, null, cellVals, null);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, null, "<>", ConfigFieldType.CALCULATED, columnIndex, null, cellVals, null);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, null, "Is Null", ConfigFieldType.CALCULATED, columnIndex, null, cellVals, null);
        userpageFilter.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, null, "Is Not Null", ConfigFieldType.CALCULATED, columnIndex, null, cellVals, null);

        if (supportOuterOperations) {
            //TODO null pointer exception
            //on TB for parent and child fields
            //userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, null, "(+)=", FieldDataType.CALCULATED, columnIndex, null, cellVals, null, cellValsKeys);
            //userFilterHelper.checkFilterAttributeAndOperatorAndValue(fieldName, null, value, null, "(+)<>", FieldDataType.CALCULATED, columnIndex, null, cellVals, null, cellValsKeys);
        }
    }

}