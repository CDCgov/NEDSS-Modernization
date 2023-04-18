package gov.cdc.nbs.patient.identifier;

import org.springframework.stereotype.Component;

@Component
class PatientIdentifierSettingsResolver {

    private final PatientIdentifierAttributesFinder finder;

    PatientIdentifierSettingsResolver(final PatientIdentifierAttributesFinder finder) {
        this.finder = finder;
    }

    PatientIdentifierSettings resolve(final long initial) {
        PatientIdentifierAttributes attributes = finder.find()
            .orElseThrow(() -> new IllegalStateException("Unable to resolve Patient Identifier Attributes"));

        return new PatientIdentifierSettings(
            attributes.type(),
            initial,
            attributes.suffix()
        );
    }
}

