package gov.cdc.nbs.model;

import gov.cdc.nbs.entity.odse.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientUpdateResponse {
	private String requestId;
	private Person updatedPerson;

}
