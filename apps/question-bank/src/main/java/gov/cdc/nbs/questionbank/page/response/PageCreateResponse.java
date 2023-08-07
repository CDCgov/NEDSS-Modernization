package gov.cdc.nbs.questionbank.page.response;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageCreateResponse {
		private Long pageId;
		private String pageName;
		private String message;
		private HttpStatus status;

}
