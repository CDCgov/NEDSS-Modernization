package gov.cdc.nbs;

import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthenticationSteps {


  @Before
  public void clearContext() {
    TestContext.clear();
  }

  @Then("I get redirect to timeout")
  public void i_get_a_302_found_response() {
    assertEquals(HttpStatus.FOUND.value(), TestContext.response.getResponse().getStatus());
    assertEquals("/nbs/timeout", TestContext.response.getResponse().getHeader("Location"));
  }


}
