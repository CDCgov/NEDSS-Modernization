package gov.cdc.nbs.patient;

import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.identifier.PatientShortIdentifierResolver;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PatientIdentifierGeneratorTest {

    @Test
    void should_generate_patient_identifier() {


        IdGeneratorService generatorService = mock(IdGeneratorService.class);

        when(generatorService.getNextValidId(IdGeneratorService.EntityType.NBS))
            .thenReturn(
                IdGeneratorService.GeneratedId.builder()
                    .id(191L)
                    .prefix("nbs-prefix")
                    .suffix("nbs-suffix")
                    .build()
            );

        when(generatorService.getNextValidId(IdGeneratorService.EntityType.PERSON))
            .thenReturn(
                IdGeneratorService.GeneratedId.builder()
                    .id(2131L)
                    .prefix("person-prefix")
                    .suffix("person-suffix")
                    .build()
            );

        PatientShortIdentifierResolver resolver = mock(PatientShortIdentifierResolver.class);


        PatientIdentifierGenerator generator = new PatientIdentifierGenerator(generatorService, resolver);

        PatientIdentifier actual = generator.generate();

        assertThat(actual)
            .returns(191L, PatientIdentifier::id)
            .returns("person-prefix2131person-suffix", PatientIdentifier::local);

    }
}
