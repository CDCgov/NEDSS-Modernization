package gov.cdc.nbs.questionbank.page;

@FunctionalInterface
public interface DatamartNameVerifier {
  boolean isUnique(final String name);
}
