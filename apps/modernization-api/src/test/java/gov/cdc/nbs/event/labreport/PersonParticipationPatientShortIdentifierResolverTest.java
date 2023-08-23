package gov.cdc.nbs.event.labreport;

import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPersonParticipation;
import gov.cdc.nbs.patient.identifier.PatientShortIdentifierResolver;
import org.junit.jupiter.api.Test;

import java.util.OptionalLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PersonParticipationPatientShortIdentifierResolverTest {

    @Test
    void should_return_short_identifier_from_resolver() {

        PatientShortIdentifierResolver shortIdentifierResolver = mock(PatientShortIdentifierResolver.class);

        when(shortIdentifierResolver.resolve(any())).thenReturn(OptionalLong.of(241L));

        PersonParticipationPatientShortIdentifierResolver resolver =
                new PersonParticipationPatientShortIdentifierResolver(shortIdentifierResolver);

        ElasticsearchPersonParticipation participation = ElasticsearchPersonParticipation.builder()
                .localId("local")
                .build();

        OptionalLong actual = resolver.resolve(participation);

        assertThat(actual).hasValue(241L);

        verify(shortIdentifierResolver).resolve("local");
    }

}
