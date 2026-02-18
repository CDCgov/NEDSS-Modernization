package gov.cdc.nbs.questionbank.page.util;

public class PageConstants {

  private PageConstants() {}

  // Page State Change
  public static final String SAVE_DRAFT_SUCCESS = "Draft was saved successfully.";
  public static final String SAVE_DRAFT_NOCHANGE = "Page already has draft.";
  public static final String SAVE_DRAFT_FAIL = " Failed to update to draft status: ";
  public static final String DELETE_DRAFT_FAIL = " Failed to delete page draft. ";
  public static final String DRAFT_DELETE_SUCCESS = "page draft has been successfully deleted.";
  public static final String PUBLISHED_WITH_DRAFT = "Published With Draft";
  public static final String DRAFT = "Draft";
  public static final String PUBLISHED = "Published";

  // Page Create
  public static final String ADD_PAGE_MESSAGE = " page has been successfully added";
  public static final String ADD_PAGE_FAIL = "could not successfully add page";
  public static final String ADD_PAGE_NAME_EMPTY = "Page name is required.";
  public static final String ADD_PAGE_TEMPLATENAME_EXISTS =
      "A Template  with Template Name %s already exists in the system";
  public static final String ADD_PAGE_CONDITION_EMPTY = "At least one condition is required.";
  public static final String ADD_PAGE_EVENTTYPE_EMPTY = "EventType is required.";
  public static final String ADD_PAGE_TEMPLATE_EMPTY = "Template is required.";
  public static final String ADD_PAGE_DATAMART_NAME_EXISTS =
      "A Page with Data Mart Name %s already exists in the system";
  public static final String ADD_PAGE_MMG_EMPTY = "Reporting mechanism is required.";
  public static final String ADD_PAGE_MMG_INVALID = "Invalid reporting mechanism provided.";

  // Page Details
  public static final Long PAGE_COMPONENT = 1002L;
  public static final Long TAB_COMPONENT = 1010L;
  public static final Long SECTION_COMPONENT = 1015L;
  public static final Long SUB_SECTION_COMPONENT = 1016L;
  public static final Long GEN_QUESTION_COMPONENT = 1007L;
  public static final Long SPE_QUESTION_COMPONENT = 1008L;

  // General
  public static final String BAD_REQUEST = "Could not process request. ";
}
