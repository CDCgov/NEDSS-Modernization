package gov.cdc.nbs.message;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@NoArgsConstructor
public class PatientDeleteRequest {
	private String requestId;
	
	
	public PatientDeleteRequest(String requestId) {
		this.requestId = requestId;
	}


}