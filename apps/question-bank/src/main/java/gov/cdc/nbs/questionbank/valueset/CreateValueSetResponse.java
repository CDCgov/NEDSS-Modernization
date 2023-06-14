package gov.cdc.nbs.questionbank.valueset;

import java.time.Instant;

import org.springframework.http.HttpStatus;

import gov.cdc.nbs.questionbank.entity.CodeSetId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateValueSetResponse {
	private ValueSetCreateShort body;
	private String message;
	private HttpStatus status;
	 

	 

@Data
@AllArgsConstructor
@NoArgsConstructor
public static class ValueSetCreateShort {
	 private CodeSetId id;
	 private Instant addTime;
	 private Long addUserId;
	 private String valueSetNm;
	
}


}
