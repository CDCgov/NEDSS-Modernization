package gov.cdc.nbs.questionbank.page.component.tree;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

class FlattenedComponentMapper implements RowMapper<FlattenedComponent> {

  private static final String TRUE_VALUE = "T";


  record Column(
      int identifier,
      int type,
      int name,
      int visible,
      int order,
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
      int adminComments
  ) {
    Column() {
      this(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
    }
  }


  private final Column columns;

  FlattenedComponentMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  @NonNull
  public FlattenedComponent mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
    long identifier = resultSet.getLong(this.columns.identifier());
    int type = resultSet.getInt(this.columns.type());
    String name = resultSet.getString(this.columns.name());
    boolean visible = resolveBoolean(this.columns.visible(), resultSet);
    int order = resultSet.getInt(this.columns.order());
    boolean isStandard = resolveBoolean(this.columns.isStandard(), resultSet);
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

    return new FlattenedComponent(
        identifier,
        type,
        name,
        visible,
        order,
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
        adminComments,
        valueSet
    );
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
