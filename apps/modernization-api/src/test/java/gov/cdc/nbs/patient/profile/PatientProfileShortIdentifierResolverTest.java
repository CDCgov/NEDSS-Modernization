package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.patient.identifier.PatientShortIdentifierResolver;
import org.junit.jupiter.api.Test;

import java.util.OptionalLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PatientProfileShortIdentifierResolverTest {

  @Test
  void should_return_short_identifier_from_resolver() {

    PatientShortIdentifierResolver shortIdentifierResolver = mock(PatientShortIdentifierResolver.class);

    when(shortIdentifierResolver.resolve(any())).thenReturn(OptionalLong.of(241L));

    PatientProfileShortIdentifierResolver resolver =
        new PatientProfileShortIdentifierResolver(shortIdentifierResolver);

    PatientProfile profile = new PatientProfile(3911L, "local", (short) 67);

    OptionalLong actual = resolver.resolve(profile);

    assertThat(actual).hasValue(241L);

    verify(shortIdentifierResolver).resolve("local");
  }
}
