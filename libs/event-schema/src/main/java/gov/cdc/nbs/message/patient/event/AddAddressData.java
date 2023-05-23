package gov.cdc.nbs.message.patient.event;

import java.io.Serializable;
import java.time.Instant;

public record AddAddressData(
                long patientId,
                long id,
                String requestId,
                long updatedBy,
                Instant asOf,
                String streetAddress1,
                String streetAddress2,
                String city,
                String stateCode,
                String countyCode,
                String countryCode,
                String zip,
                String censusTract) implements Serializable {
}
