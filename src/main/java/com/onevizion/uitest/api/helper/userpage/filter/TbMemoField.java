package com.onevizion.uitest.api.helper.userpage.filter;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.vo.ConfigFieldType;
import com.onevizion.uitest.api.vo.FilterOperatorType;

@Component
public class TbMemoField {

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

        List<FilterOperatorType> operators = FilterOperatorType.getMemoOperators(supportOuterOperations);
        userpageFilter.checkFilterOperators(fieldName, null, operators);

        userpageFilter.checkFilter(fieldName, null, value, null, FilterOperatorType.EQUAL, ConfigFieldType.MEMO, columnIndex, null, cellVals, null);
        userpageFilter.checkFilter(fieldName, null, value, null, FilterOperatorType.NOT_EQUAL, ConfigFieldType.MEMO, columnIndex, null, cellVals, null);
        userpageFilter.checkFilter(fieldName, null, value, null, FilterOperatorType.NULL, ConfigFieldType.MEMO, columnIndex, null, cellVals, null);
        userpageFilter.checkFilter(fieldName, null, value, null, FilterOperatorType.NOT_NULL, ConfigFieldType.MEMO, columnIndex, null, cellVals, null);

        if (supportOuterOperations) {
            userpageFilter.checkFilter(fieldName, null, value, null, FilterOperatorType.EQUAL_AND_EMPTY_FOR_OTHER, ConfigFieldType.MEMO, columnIndex, null, cellVals, null, cellValsKeys);
            userpageFilter.checkFilter(fieldName, null, value, null, FilterOperatorType.NOT_EQUAL_AND_EMPTY_FOR_OTHER, ConfigFieldType.MEMO, columnIndex, null, cellVals, null, cellValsKeys);
        }
    }

}