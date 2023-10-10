package gov.cdc.nbs.questionbank.page.detail;

public record DetailedPageRule(
    long id,
    long page,
    String logic,
    String values,
    String function,
    String source,
    String target
) {
}
