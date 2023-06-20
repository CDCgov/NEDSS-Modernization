package gov.cdc.nbs.questionbank.valueset.response;

import org.springframework.http.HttpStatus;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteValueSetResponse {
	private String codeSetNm;
	private String statusCd;
	private String message;
	private HttpStatus status;

}
