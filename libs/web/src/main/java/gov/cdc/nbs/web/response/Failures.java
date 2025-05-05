package gov.cdc.nbs.web.response;

import org.springframework.http.ResponseEntity;

public class Failures {

  public static ResponseEntity<StandardResponse> failure(final Throwable throwable) {
    return failure(throwable.getMessage());
  }

  public static ResponseEntity<StandardResponse> failure(final String reason) {

    return ResponseEntity.badRequest()
        .body(new StandardResponse.Failure(reason));
  }

  private Failures() {

  }


}
