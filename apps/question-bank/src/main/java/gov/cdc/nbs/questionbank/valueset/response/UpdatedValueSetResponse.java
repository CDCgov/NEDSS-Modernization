package gov.cdc.nbs.questionbank.valueset.response;


import org.springframework.http.HttpStatus;

import gov.cdc.nbs.questionbank.entity.CodesetId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdatedValueSetResponse {
	private ValueSetUpdateShort body;
	private String message;
	private HttpStatus status;


	public record ValueSetUpdateShort (
			 CodesetId id,
			 Long updateUserId,
			 String valueSetNm
		) {}

}
