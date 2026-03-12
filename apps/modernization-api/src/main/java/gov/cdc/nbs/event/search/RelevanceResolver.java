package gov.cdc.nbs.event.search;

public class RelevanceResolver {

  public static double resolve(final Double score) {
    if (score == null) {
      return 0;
    } else if (score.isNaN()) {
      return -1;
    } else {
      return score;
    }
  }

  private RelevanceResolver() {}
}
