package gov.cdc.nbs.questionbank.page;

@FunctionalInterface
public interface PageNameVerifier {

  boolean isUnique(final String name);
}
