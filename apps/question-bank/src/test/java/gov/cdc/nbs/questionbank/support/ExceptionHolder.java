package gov.cdc.nbs.questionbank.support;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ExceptionHolder {

  private Exception exception;

  public void clear() {
    this.exception = null;
  }
}
