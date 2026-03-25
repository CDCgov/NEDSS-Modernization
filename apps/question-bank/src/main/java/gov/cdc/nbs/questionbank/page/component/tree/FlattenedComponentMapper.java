package gov.cdc.nbs.questionbank.page.component.tree;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

class FlattenedComponentMapper implements RowMapper<FlattenedComponent> {

  private static final String TRUE_VALUE = "T";
  private static final String BATCH_TRUE_VALUE = "Y";

  record Column(
      int identifier,
      int type,
      int name,
      int visible,
      int order,
      int questionGroupSeq,
      int isStandardNnd,
      int isStandard,
      int standard,
      int question,
      int dataType,
      int subGroup,
      int description,
      int enabled,
      int required,
      int allowFutureDates,
      int coInfection,
      int mask,
      int toolTip,
      int defaultValue,
      int valueSet,
      int adminComments,
      int fieldLength,
      int defaultRdbTableName,
      int rdbColumnName,
      int defaultLabelInReport,
      int dataMartColumnName,
      int blockName,
      int dataMartRepeatNumber,
      int dataLocation,
      int isPublished,
      int appearsInBatch,
      int batchLabel,
      int batchWidth,
      int componentBehavior,
      int componentName,
      int classCode) {
    Column() {
      this(
          1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,
          26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37);
    }
  }

  private final Column columns;

  FlattenedComponentMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  @NonNull public FlattenedComponent mapRow(final ResultSet resultSet, final int rowNum)
      throws SQLException {
    long identifier = resultSet.getLong(this.columns.identifier());
    int type = resultSet.getInt(this.columns.type());
    String name = resultSet.getString(this.columns.name());
    boolean visible = resolveBoolean(this.columns.visible(), resultSet);
    int order = resultSet.getInt(this.columns.order());
    int questionGroupSeq = resultSet.getInt(this.columns.questionGroupSeq());
    boolean isStandard = resolveBoolean(this.columns.isStandard(), resultSet);
    boolean isStandardNnd = resolveBoolean(this.columns.isStandardNnd(), resultSet);
    String standard = resultSet.getString(this.columns.standard());
    String question = resultSet.getString(this.columns.question());
    String dataType = resultSet.getString(this.columns.dataType());
    String subGroup = resultSet.getString(this.columns.subGroup());
    String description = resultSet.getString(this.columns.description());
    boolean enabled = resolveBoolean(this.columns.enabled(), resultSet);
    boolean required = resolveRequired(this.columns.required(), resultSet);
    boolean allowFutureDates = resolveBoolean(this.columns.allowFutureDates(), resultSet);
    boolean coInfection = resolveBoolean(this.columns.coInfection(), resultSet);
    String mask = resultSet.getString(this.columns.mask());
    String toolTip = resultSet.getString(this.columns.toolTip());
    String defaultValue = resultSet.getString(this.columns.defaultValue());
    String valueSet = resultSet.getString(this.columns.valueSet());
    String adminComments = resultSet.getString(this.columns.adminComments());

    String fieldLength = resultSet.getString(this.columns.fieldLength());
    String defaultRdbTableName = resultSet.getString(this.columns.defaultRdbTableName());
    String rdbColumnName = resultSet.getString(this.columns.rdbColumnName());
    String defaultLabelInReport = resultSet.getString(this.columns.defaultLabelInReport());
    String dataMartColumnName = resultSet.getString(this.columns.dataMartColumnName());

    boolean isSubsectionGrouped = resultSet.getString(this.columns.blockName()) != null;
    boolean isPublished = resolveBoolean(this.columns.isPublished, resultSet);
    String dataLocation = resultSet.getString(this.columns.dataLocation());
    String blockName = resultSet.getString(this.columns.blockName());
    Integer dataMartRepeatNumber = resultSet.getInt(this.columns.dataMartRepeatNumber());
    boolean appearsInBatch = resolveBatchBoolean(this.columns.appearsInBatch(), resultSet);
    String batchLabel = resultSet.getString(this.columns.batchLabel());
    Integer batchWidth = resultSet.getInt(this.columns.batchWidth());
    String componentBehavior = resultSet.getString(this.columns.componentBehavior());
    String componentName = resultSet.getString(this.columns.componentName());
    String classCode = resultSet.getString(this.columns.classCode());

    return new FlattenedComponent(
        identifier,
        type,
        name,
        visible,
        order,
        questionGroupSeq,
        isStandardNnd,
        isStandard,
        standard,
        question,
        dataType,
        subGroup,
        description,
        enabled,
        required,
        allowFutureDates,
        coInfection,
        mask,
        toolTip,
        defaultValue,
        valueSet,
        adminComments,
        fieldLength,
        defaultRdbTableName,
        rdbColumnName,
        defaultLabelInReport,
        dataMartColumnName,
        isSubsectionGrouped,
        dataLocation,
        isPublished,
        blockName,
        dataMartRepeatNumber,
        appearsInBatch,
        batchLabel,
        batchWidth,
        componentBehavior,
        componentName,
        classCode);
  }

  private boolean resolveBatchBoolean(final int column, final ResultSet resultSet)
      throws SQLException {
    String value = resultSet.getString(column);
    return Objects.equals(BATCH_TRUE_VALUE, value);
  }

  private boolean resolveBoolean(final int column, final ResultSet resultSet) throws SQLException {
    String value = resultSet.getString(column);
    return Objects.equals(TRUE_VALUE, value);
  }

  private boolean resolveRequired(final int column, final ResultSet resultSet) throws SQLException {
    String value = resultSet.getString(column);
    return Objects.equals(TRUE_VALUE, value) || Objects.equals("E", value);
  }
}
