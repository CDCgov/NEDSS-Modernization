package gov.cdc.nbs.questionbank.page.component.tree;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FlattenedComponentMapperTest {

  @Test
  void should_map_to_flattened_component() throws SQLException {

    FlattenedComponentMapper.Column columns = new FlattenedComponentMapper.Column();

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.identifier())).thenReturn(571L);
    when(resultSet.getInt(columns.type())).thenReturn(1993);
    when(resultSet.getString(columns.name())).thenReturn("name-value");
    when(resultSet.getInt(columns.order())).thenReturn(863);
    when(resultSet.getString(columns.standard())).thenReturn("T");
    when(resultSet.getString(columns.standard())).thenReturn("standard-value");
    when(resultSet.getString(columns.question())).thenReturn("question-value");
    when(resultSet.getString(columns.dataType())).thenReturn("data-type-value");
    when(resultSet.getString(columns.subGroup())).thenReturn("sub-group-value");
    when(resultSet.getString(columns.description())).thenReturn("description-value");
    when(resultSet.getString(columns.coInfection())).thenReturn("T");
    when(resultSet.getString(columns.mask())).thenReturn("mask-value");
    when(resultSet.getString(columns.toolTip())).thenReturn("tool-tip-value");
    when(resultSet.getString(columns.defaultValue())).thenReturn("default-value-value");
    when(resultSet.getString(columns.valueSet())).thenReturn("value-set-value");
    when(resultSet.getString(columns.blockName())).thenReturn("blockName");
    when(resultSet.getInt(columns.dataMartRepeatNumber())).thenReturn(2);
    when(resultSet.getString(columns.defaultRdbTableName())).thenReturn("rdb-table-name");
    when(resultSet.getString(columns.rdbColumnName())).thenReturn("rdb-column-name");
    when(resultSet.getString(columns.defaultLabelInReport())).thenReturn("default-label-in-Report");
    when(resultSet.getString(columns.dataMartColumnName())).thenReturn("dataMart-column-name");
    when(resultSet.getString(columns.dataLocation())).thenReturn("test");
    when(resultSet.getString(columns.isPublished())).thenReturn("F");

    FlattenedComponentMapper mapper = new FlattenedComponentMapper(columns);

    FlattenedComponent actual = mapper.mapRow(resultSet, 0);

    assertThat(actual.identifier()).isEqualTo(571L);
    assertThat(actual.type()).isEqualTo(1993);
    assertThat(actual.name()).isEqualTo("name-value");
    assertThat(actual.visible()).isFalse();
    assertThat(actual.order()).isEqualTo(863);
    assertThat(actual.isStandard()).isFalse();
    assertThat(actual.standard()).isEqualTo("standard-value");
    assertThat(actual.question()).isEqualTo("question-value");
    assertThat(actual.dataType()).isEqualTo("data-type-value");
    assertThat(actual.subGroup()).isEqualTo("sub-group-value");
    assertThat(actual.description()).isEqualTo("description-value");
    assertThat(actual.enabled()).isFalse();
    assertThat(actual.required()).isFalse();
    assertThat(actual.allowFutureDates()).isFalse();
    assertThat(actual.coInfection()).isTrue();
    assertThat(actual.mask()).isEqualTo("mask-value");
    assertThat(actual.toolTip()).isEqualTo("tool-tip-value");
    assertThat(actual.defaultValue()).isEqualTo("default-value-value");
    assertThat(actual.valueSet()).isEqualTo("value-set-value");
    assertThat(actual.isGrouped()).isTrue();
    assertThat(actual.defaultRdbTableName()).isEqualTo("rdb-table-name");
    assertThat(actual.rdbColumnName()).isEqualTo("rdb-column-name");
    assertThat(actual.defaultLabelInReport()).isEqualTo("default-label-in-Report");
    assertThat(actual.dataMartColumnName()).isEqualTo("dataMart-column-name");
    assertThat(actual.dataMartRepeatNumber()).isEqualTo(2);
  }

  @Test
  void should_evaluate_component_to_required() throws SQLException {

    FlattenedComponentMapper.Column columns = new FlattenedComponentMapper.Column();

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.identifier())).thenReturn(571L);
    when(resultSet.getInt(columns.type())).thenReturn(1993);
    when(resultSet.getLong(columns.order())).thenReturn(863L);
    when(resultSet.getString(columns.required())).thenReturn("T");

    FlattenedComponentMapper mapper = new FlattenedComponentMapper(columns);

    FlattenedComponent actual = mapper.mapRow(resultSet, 0);

    assertThat(actual.required()).isTrue();
  }

  @Test
  void should_evaluate_component_to_required_when_E() throws SQLException {

    FlattenedComponentMapper.Column columns = new FlattenedComponentMapper.Column();

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.identifier())).thenReturn(571L);
    when(resultSet.getInt(columns.type())).thenReturn(1993);
    when(resultSet.getLong(columns.order())).thenReturn(863L);
    when(resultSet.getString(columns.required())).thenReturn("E");

    FlattenedComponentMapper mapper = new FlattenedComponentMapper(columns);

    FlattenedComponent actual = mapper.mapRow(resultSet, 0);

    assertThat(actual.required()).isTrue();
  }

  @Test
  void should_evaluate_component_to_not_required_when_F() throws SQLException {

    FlattenedComponentMapper.Column columns = new FlattenedComponentMapper.Column();

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.identifier())).thenReturn(571L);
    when(resultSet.getInt(columns.type())).thenReturn(1993);
    when(resultSet.getLong(columns.order())).thenReturn(863L);
    when(resultSet.getString(columns.required())).thenReturn("F");

    FlattenedComponentMapper mapper = new FlattenedComponentMapper(columns);

    FlattenedComponent actual = mapper.mapRow(resultSet, 0);

    assertThat(actual.required()).isFalse();
  }

  @Test
  void should_evaluate_component_to_not_required_when_null() throws SQLException {

    FlattenedComponentMapper.Column columns = new FlattenedComponentMapper.Column();

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.identifier())).thenReturn(571L);
    when(resultSet.getInt(columns.type())).thenReturn(1993);
    when(resultSet.getLong(columns.order())).thenReturn(863L);

    FlattenedComponentMapper mapper = new FlattenedComponentMapper(columns);

    FlattenedComponent actual = mapper.mapRow(resultSet, 0);

    assertThat(actual.required()).isFalse();
  }

  @Test
  void should_evaluate_component_to_visible() throws SQLException {

    FlattenedComponentMapper.Column columns = new FlattenedComponentMapper.Column();

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.identifier())).thenReturn(571L);
    when(resultSet.getInt(columns.type())).thenReturn(1993);
    when(resultSet.getLong(columns.order())).thenReturn(863L);
    when(resultSet.getString(columns.visible())).thenReturn("T");

    FlattenedComponentMapper mapper = new FlattenedComponentMapper(columns);

    FlattenedComponent actual = mapper.mapRow(resultSet, 0);

    assertThat(actual.visible()).isTrue();
  }

  @Test
  void should_evaluate_component_to_not_visible_when_F() throws SQLException {

    FlattenedComponentMapper.Column columns = new FlattenedComponentMapper.Column();

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.identifier())).thenReturn(571L);
    when(resultSet.getInt(columns.type())).thenReturn(1993);
    when(resultSet.getLong(columns.order())).thenReturn(863L);
    when(resultSet.getString(columns.visible())).thenReturn("F");

    FlattenedComponentMapper mapper = new FlattenedComponentMapper(columns);

    FlattenedComponent actual = mapper.mapRow(resultSet, 0);

    assertThat(actual.visible()).isFalse();
  }

  @Test
  void should_evaluate_component_to_not_visible_when_null() throws SQLException {

    FlattenedComponentMapper.Column columns = new FlattenedComponentMapper.Column();

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.identifier())).thenReturn(571L);
    when(resultSet.getInt(columns.type())).thenReturn(1993);
    when(resultSet.getLong(columns.order())).thenReturn(863L);

    FlattenedComponentMapper mapper = new FlattenedComponentMapper(columns);

    FlattenedComponent actual = mapper.mapRow(resultSet, 0);

    assertThat(actual.visible()).isFalse();
  }

  @Test
  void should_evaluate_component_to_enabled() throws SQLException {

    FlattenedComponentMapper.Column columns = new FlattenedComponentMapper.Column();

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.identifier())).thenReturn(571L);
    when(resultSet.getInt(columns.type())).thenReturn(1993);
    when(resultSet.getLong(columns.order())).thenReturn(863L);
    when(resultSet.getString(columns.enabled())).thenReturn("T");

    FlattenedComponentMapper mapper = new FlattenedComponentMapper(columns);

    FlattenedComponent actual = mapper.mapRow(resultSet, 0);

    assertThat(actual.enabled()).isTrue();
  }

  @Test
  void should_evaluate_component_to_not_enabled_when_F() throws SQLException {

    FlattenedComponentMapper.Column columns = new FlattenedComponentMapper.Column();

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.identifier())).thenReturn(571L);
    when(resultSet.getInt(columns.type())).thenReturn(1993);
    when(resultSet.getLong(columns.order())).thenReturn(863L);
    when(resultSet.getString(columns.enabled())).thenReturn("F");

    FlattenedComponentMapper mapper = new FlattenedComponentMapper(columns);

    FlattenedComponent actual = mapper.mapRow(resultSet, 0);

    assertThat(actual.enabled()).isFalse();
  }

  @Test
  void should_evaluate_component_to_not_enabled_when_null() throws SQLException {

    FlattenedComponentMapper.Column columns = new FlattenedComponentMapper.Column();

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.identifier())).thenReturn(571L);
    when(resultSet.getInt(columns.type())).thenReturn(1993);
    when(resultSet.getLong(columns.order())).thenReturn(863L);

    FlattenedComponentMapper mapper = new FlattenedComponentMapper(columns);

    FlattenedComponent actual = mapper.mapRow(resultSet, 0);

    assertThat(actual.enabled()).isFalse();
  }

  @Test
  void should_evaluate_component_to_allow_future_dates() throws SQLException {

    FlattenedComponentMapper.Column columns = new FlattenedComponentMapper.Column();

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.identifier())).thenReturn(571L);
    when(resultSet.getInt(columns.type())).thenReturn(1993);
    when(resultSet.getLong(columns.order())).thenReturn(863L);
    when(resultSet.getString(columns.allowFutureDates())).thenReturn("T");

    FlattenedComponentMapper mapper = new FlattenedComponentMapper(columns);

    FlattenedComponent actual = mapper.mapRow(resultSet, 0);

    assertThat(actual.allowFutureDates()).isTrue();
  }

  @Test
  void should_evaluate_component_to_not_allow_future_dates_when_F() throws SQLException {

    FlattenedComponentMapper.Column columns = new FlattenedComponentMapper.Column();

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.identifier())).thenReturn(571L);
    when(resultSet.getInt(columns.type())).thenReturn(1993);
    when(resultSet.getLong(columns.order())).thenReturn(863L);
    when(resultSet.getString(columns.allowFutureDates())).thenReturn("F");

    FlattenedComponentMapper mapper = new FlattenedComponentMapper(columns);

    FlattenedComponent actual = mapper.mapRow(resultSet, 0);

    assertThat(actual.allowFutureDates()).isFalse();
  }

  @Test
  void should_evaluate_component_to_not_allow_future_dates_when_null() throws SQLException {

    FlattenedComponentMapper.Column columns = new FlattenedComponentMapper.Column();

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.identifier())).thenReturn(571L);
    when(resultSet.getInt(columns.type())).thenReturn(1993);
    when(resultSet.getLong(columns.order())).thenReturn(863L);

    FlattenedComponentMapper mapper = new FlattenedComponentMapper(columns);

    FlattenedComponent actual = mapper.mapRow(resultSet, 0);

    assertThat(actual.allowFutureDates()).isFalse();
  }
}
