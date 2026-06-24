package gov.cdc.nbs.report.mappers;

import gov.cdc.nbs.entity.odse.FilterCode;
import gov.cdc.nbs.entity.odse.FilterValue;
import gov.cdc.nbs.report.models.AdvancedFilterRequest;
import gov.cdc.nbs.report.models.BasicFilterRequest;
import gov.cdc.nbs.report.models.FilterType;

import java.util.List;

public class FilterValueMapper {
    private FilterValueMapper() {}

    public static List<FilterValue> fromAdvancedFilterRequest(AdvancedFilterRequest request) {
        return new FilterType(
                filterCode.getId(),
                filterCode.getCodeTable(),
                filterCode.getDescTxt(),
                filterCode.getCode(),
                filterCode.getFilterCodeSetName(),
                filterCode.getFilterType(),
                filterCode.getFilterName());
    }

    public static List<FilterValue> fromBasicFilterRequest(BasicFilterRequest request) {
        return new FilterType(
                filterCode.getId(),
                filterCode.getCodeTable(),
                filterCode.getDescTxt(),
                filterCode.getCode(),
                filterCode.getFilterCodeSetName(),
                filterCode.getFilterType(),
                filterCode.getFilterName());
    }
}
