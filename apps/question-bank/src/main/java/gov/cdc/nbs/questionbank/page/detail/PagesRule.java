package gov.cdc.nbs.questionbank.page.detail;

public record PagesRule(
    long id,
    long page,
    String logic,
    String values,
    String function,
    String source,
    String target) {}
