package gov.cdc.nbs.patient.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import gov.cdc.nbs.patient.identifier.PatientShortIdentifierResolver;
import java.util.OptionalLong;
import org.junit.jupiter.api.Test;

class PatientSearchShortIdentifierResolverTest {

  @Test
  void should_return_short_identifier_from_resolver() {

    PatientShortIdentifierResolver shortIdentifierResolver =
        mock(PatientShortIdentifierResolver.class);

    when(shortIdentifierResolver.resolve(any())).thenReturn(OptionalLong.of(241L));

    PatientSearchShortIdentifierResolver resolver =
        new PatientSearchShortIdentifierResolver(shortIdentifierResolver);

    PatientSearchResult patient = mock(PatientSearchResult.class);
    when(patient.local()).thenReturn("local");

    OptionalLong actual = resolver.resolve(patient);

    assertThat(actual).hasValue(241L);

    verify(shortIdentifierResolver).resolve("local");
  }
}
