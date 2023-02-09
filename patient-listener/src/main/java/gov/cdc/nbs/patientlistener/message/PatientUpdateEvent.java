package gov.cdc.nbs.patientlistener.message;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class PatientUpdateEvent {
	private String requestId;
	private boolean success;
	private PatientUpdateParams params;
	
	
	public static PatientUpdateEvent success(String requestId)  {
		return new PatientUpdateEvent().setRequestId(requestId).setSuccess(true); 
	}
	
	public static PatientUpdateEvent failure(String requestId) {
		return new PatientUpdateEvent().setRequestId(requestId).setSuccess(false); 
	}
	

}
