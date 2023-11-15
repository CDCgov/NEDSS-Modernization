package gov.cdc.nbs.questionbank.template.request;

public record TemplateSearchRequest(
    Long id,
    String templateNm,
    String conditionCd,
    String dataMartNm,
    String recordStatusCd) {
}
