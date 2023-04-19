package gov.cdc.nbs.provider;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.entity.odse.QPersonName;
import gov.cdc.nbs.message.enums.Suffix;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProviderNameTupleMapperTest {

    @Test
    void should_map_full_provider_name_from_tuple() {

        ProviderNameTupleMapper.Tables tables = new ProviderNameTupleMapper.Tables(QPersonName.personName);

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.name().nmPrefix)).thenReturn("prefix");
        when(tuple.get(tables.name().firstNm)).thenReturn("first-name");
        when(tuple.get(tables.name().lastNm)).thenReturn("last-name");
        when(tuple.get(tables.name().nmSuffix)).thenReturn(Suffix.ESQ);

        ProviderNameTupleMapper mapper = new ProviderNameTupleMapper(tables);

        String actual = mapper.map(tuple);

        assertThat(actual).isEqualTo("prefix first-name last-name ESQ");

    }

    @Test
    void should_map_full_provider_name_from_tuple_without_prefix() {

        ProviderNameTupleMapper.Tables tables = new ProviderNameTupleMapper.Tables(QPersonName.personName);

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.name().firstNm)).thenReturn("first-name");
        when(tuple.get(tables.name().lastNm)).thenReturn("last-name");
        when(tuple.get(tables.name().nmSuffix)).thenReturn(Suffix.ESQ);

        ProviderNameTupleMapper mapper = new ProviderNameTupleMapper(tables);

        String actual = mapper.map(tuple);

        assertThat(actual).isEqualTo("first-name last-name ESQ");

    }

    @Test
    void should_map_full_provider_name_from_tuple_without_first_name() {

        ProviderNameTupleMapper.Tables tables = new ProviderNameTupleMapper.Tables(QPersonName.personName);

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.name().nmPrefix)).thenReturn("prefix");
        when(tuple.get(tables.name().lastNm)).thenReturn("last-name");
        when(tuple.get(tables.name().nmSuffix)).thenReturn(Suffix.ESQ);

        ProviderNameTupleMapper mapper = new ProviderNameTupleMapper(tables);

        String actual = mapper.map(tuple);

        assertThat(actual).isEqualTo("prefix last-name ESQ");

    }

    @Test
    void should_map_full_provider_name_from_tuple_without_last_name() {

        ProviderNameTupleMapper.Tables tables = new ProviderNameTupleMapper.Tables(QPersonName.personName);

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.name().nmPrefix)).thenReturn("prefix");
        when(tuple.get(tables.name().firstNm)).thenReturn("first-name");
        when(tuple.get(tables.name().nmSuffix)).thenReturn(Suffix.ESQ);

        ProviderNameTupleMapper mapper = new ProviderNameTupleMapper(tables);

        String actual = mapper.map(tuple);

        assertThat(actual).isEqualTo("prefix first-name ESQ");

    }

    @Test
    void should_map_full_provider_name_from_tuple_without_suffix() {

        ProviderNameTupleMapper.Tables tables = new ProviderNameTupleMapper.Tables(QPersonName.personName);

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.name().nmPrefix)).thenReturn("prefix");
        when(tuple.get(tables.name().firstNm)).thenReturn("first-name");
        when(tuple.get(tables.name().lastNm)).thenReturn("last-name");

        ProviderNameTupleMapper mapper = new ProviderNameTupleMapper(tables);

        String actual = mapper.map(tuple);

        assertThat(actual).isEqualTo("prefix first-name last-name");

    }
}
