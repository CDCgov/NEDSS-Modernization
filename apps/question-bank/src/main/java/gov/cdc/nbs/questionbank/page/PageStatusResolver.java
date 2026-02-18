package gov.cdc.nbs.questionbank.page;

public class PageStatusResolver {

  public static String resolve(final String type, final Integer publishVersion) {
    return ("Draft".equalsIgnoreCase(type)) ? resolveDraftStatus(publishVersion).display() : type;
  }

  private static PageStatus resolveDraftStatus(final Integer publishVersion) {
    return (publishVersion == null) ? PageStatus.INITIAL_DRAFT : PageStatus.PUBLISHED_WITH_DRAFT;
  }

  private PageStatusResolver() {}
}
