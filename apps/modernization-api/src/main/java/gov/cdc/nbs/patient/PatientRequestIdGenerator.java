package gov.cdc.nbs.patient;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PatientRequestIdGenerator {

    public String generate(final String type) {
        return "%s_%s".formatted(type, UUID.randomUUID());
    }

}
