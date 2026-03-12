package gov.cdc.nbs.authorization.permission;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gov.cdc.nbs.testing.authorization.ActiveUser;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

public class ClientPermissionSteps {

  @Autowired Active<ActiveUser> activeUser;

  @Autowired MockMvc mvc;

  private final Active<ResultActions> activeResult = new Active<>();

  @When("I access NBS from the client")
  public void the_user_accesses_NBS_from_the_client() throws Exception {

    ActiveUser user = activeUser.active();

    activeResult.active(
        mvc.perform(get("/nbs/api/me").header("Authorization", "Bearer " + user.token().value()))
            .andExpect(status().isOk()));
  }

  @Then("I am able to {string} {string} from the client")
  public void the_user_can_operate_on_a_shared_object(final String operation, final String object)
      throws Exception {
    ResultActions actions = activeResult.active();

    String expected = operation.toUpperCase() + "-" + object.toUpperCase();

    actions
        .andExpect(jsonPath("$.firstName").exists())
        .andExpect(jsonPath("$.permissions").value(Matchers.hasItem(expected)));
  }
}
