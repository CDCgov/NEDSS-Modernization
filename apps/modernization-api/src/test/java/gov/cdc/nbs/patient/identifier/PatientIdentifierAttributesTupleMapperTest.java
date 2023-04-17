package gov.cdc.nbs.patient.identifier;

import com.querydsl.core.Tuple;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PatientIdentifierAttributesTupleMapperTest {

    @Test
    void should_map_patient_identifier_attributes_from_tuple() {

        PatientIdentifierAttributesTupleMapper.Tables tables = new PatientIdentifierAttributesTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.localUniqueIdentifier().uidPrefixCd)).thenReturn("prefix");
        when(tuple.get(tables.localUniqueIdentifier().uidSuffixCd)).thenReturn("suffix");

        PatientIdentifierAttributesTupleMapper mapper = new PatientIdentifierAttributesTupleMapper(tables);

        PatientIdentifierAttributes actual = mapper.map(tuple);

        assertThat(actual.type()).isEqualTo("prefix");

        assertThat(actual.suffix()).isEqualTo("suffix");

    }
}
