package gov.cdc.nbs.patient.identifier;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PatientLocalIdentifierResolverTest {

    @Test
    void should_resolve_local_identifier_from_short_identifier() {

        PatientIdentifierSettings settings = new PatientIdentifierSettings(
            "prefix",
            6329,
            "suffix"
        );

        PatientLocalIdentifierResolver resolver = new PatientLocalIdentifierResolver(settings);

        String actual = resolver.resolve(106580L);

        assertThat(actual).isEqualTo("prefix112909suffix");

    }

}
