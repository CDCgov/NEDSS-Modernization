package gov.cdc.nbs.questionbank.page.component.tree;

record FlattenedComponent(
    long identifier,
    int type,
    String name,
    boolean visible,
    int order,
    boolean isStandard,
    String standard,
    String question,
    String dataType,
    String subGroup,
    String description,
    boolean enabled,
    boolean required,
    boolean allowFutureDates,
    boolean coInfection,
    String mask,
    String toolTip,
    String defaultValue,
    String valueSet,
    String adminComments,
    String fieldLength,
    String defaultRdbTableName,
    String rdbColumnName,
    String defaultLabelInReport,
    String dataMartColumnName,
    boolean isGrouped,
    boolean isPublished) {

  FlattenedComponent(
      long identifier,
      int type,
      String name,
      boolean visible,
      int order

  ) {
    this(
        identifier,
        type,
        name,
        visible,
        order,
        false,
        null,
        null,
        null,
        null,
        null,
        false,
        false,
        false,
        false,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        false,
        false);
  }
}
