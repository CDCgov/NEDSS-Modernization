package gov.cdc.nbs.patient.identifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.id.IdGeneratorService;
import org.junit.jupiter.api.Test;

class PatientLocalIdentifierGeneratorTest {

  @Test
  void should_generate_local_identifier() {

    IdGeneratorService service = mock(IdGeneratorService.class);

    when(service.getNextValidId(any()))
        .thenReturn(new IdGeneratorService.GeneratedId(1301L, "prefix", "suffix"));

    PatientLocalIdentifierGenerator generator = new PatientLocalIdentifierGenerator(service);

    String actual = generator.generate();

    assertThat(actual).isEqualTo("prefix1301suffix");
  }
}
