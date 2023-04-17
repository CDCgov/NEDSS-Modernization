package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.identifier.PatientShortIdentifierResolver;
import org.junit.jupiter.api.Test;

import java.util.OptionalLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PatientSearchShortIdentifierResolverTest {

    @Test
    void should_return_short_identifier_from_resolver() {

        PatientShortIdentifierResolver shortIdentifierResolver = mock(PatientShortIdentifierResolver.class);

        when(shortIdentifierResolver.resolve(any())).thenReturn(OptionalLong.of(241L));

        PatientSearchShortIdentifierResolver resolver =
            new PatientSearchShortIdentifierResolver(shortIdentifierResolver);

        Person patient = new Person(1949L, "local");

        OptionalLong actual = resolver.resolve(patient);

        assertThat(actual).hasValue(241L);

        verify(shortIdentifierResolver).resolve("local");
    }

}
