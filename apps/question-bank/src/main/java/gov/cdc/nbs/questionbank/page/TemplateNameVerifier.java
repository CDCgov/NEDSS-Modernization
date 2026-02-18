package gov.cdc.nbs.questionbank.page;

@FunctionalInterface
public interface TemplateNameVerifier {

  boolean isUnique(final String name);
}
