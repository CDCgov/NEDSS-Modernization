package gov.cdc.nbs.questionbank.support;

import org.springframework.beans.factory.annotation.Autowired;
import io.cucumber.java.Before;

public class ExceptionSteps {

  @Autowired
  private ExceptionHolder exceptionHolder;

  @Before
  public void clearExceptions() {
    exceptionHolder.clear();
  }
}
