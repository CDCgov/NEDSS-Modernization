package gov.cdc.nbs.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public sealed interface StandardResponse {

  record Success() implements StandardResponse {
  }


  record Failure(@JsonProperty(required = true) String reason) implements StandardResponse {
  }
}
