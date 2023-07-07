package gov.cdc.nbs.questionbank.valueset.response;


import org.springframework.http.HttpStatus;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdatedValueSetResponse {
	private ValueSetUpdateShort body;
	private String message;
	private HttpStatus status;


	public record ValueSetUpdateShort (
			 String codeSetNm,
			 String valueSetNm,
			 String codeSetDescTxt
		) {}

}
