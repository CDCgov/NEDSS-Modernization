package gov.cdc.nbs.web.response;

import org.springframework.http.ResponseEntity;

public class Failures {

  public static <R> ResponseEntity<StandardResponse<R>> failure(final Throwable throwable) {
    return failure(throwable.getMessage());
  }

  public static <R> ResponseEntity<StandardResponse<R>> failure(final String reason) {

    return ResponseEntity.badRequest()
        .body(new Failure<R>(reason));
  }

  private Failures() {

  }


}
