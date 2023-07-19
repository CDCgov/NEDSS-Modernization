package gov.cdc.nbs.questionbank.valueset.response;

import java.time.Instant;

import org.springframework.http.HttpStatus;

import gov.cdc.nbs.questionbank.entity.CodesetId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateValueSetResponse {
	private ValueSetCreateShort body;
	private String message;
	private HttpStatus status;
	 

	 


public record ValueSetCreateShort (
	 CodesetId id,
	 Instant addTime,
	 Long addUserId,
	 String valueSetNm,
	 Long codeSetGroupId
) {}


}
