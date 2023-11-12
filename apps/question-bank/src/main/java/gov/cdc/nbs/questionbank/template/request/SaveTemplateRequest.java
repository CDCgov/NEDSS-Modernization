package gov.cdc.nbs.questionbank.template.request;

public record SaveTemplateRequest(Long waTemplateUid, String templateName, String templateDescription) {
}
