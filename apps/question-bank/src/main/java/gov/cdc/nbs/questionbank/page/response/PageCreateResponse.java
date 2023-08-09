package gov.cdc.nbs.questionbank.page.response;

import org.springframework.http.HttpStatus;


public record PageCreateResponse (
		 Long pageId,
		 String pageName,
		 String message,
		 HttpStatus status

      ) {}
