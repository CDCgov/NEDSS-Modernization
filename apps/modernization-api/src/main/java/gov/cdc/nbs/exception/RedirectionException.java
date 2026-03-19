package gov.cdc.nbs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RedirectionException extends RuntimeException {
  public RedirectionException(String message) {
    super(message);
  }
}
