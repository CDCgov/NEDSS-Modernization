package gov.cdc.nbs.web.response;

import org.springframework.http.ResponseEntity;

public class Successes {

  private Successes() {}

  public static ResponseEntity<StandardResponse> accepted() {
    return ResponseEntity.accepted().build();
  }
}
