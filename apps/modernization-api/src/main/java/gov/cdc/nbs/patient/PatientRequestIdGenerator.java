package gov.cdc.nbs.patient;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PatientRequestIdGenerator {

    public String generate(final String type) {
        return String.format(type + "_%s", UUID.randomUUID());
    }

}
