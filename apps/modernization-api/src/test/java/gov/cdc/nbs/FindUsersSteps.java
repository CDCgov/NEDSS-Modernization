package gov.cdc.nbs;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Objects;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.authorization.TestAuthorizedUser;
import gov.cdc.nbs.controller.UserController;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FindUsersSteps {

    @Autowired
    private UserController userController;

    @Autowired
    Available<TestAuthorizedUser> availableUsers;

    @Autowired
    Authenticated authenticated;

    private Page<AuthUser> users;

    @When("I retrieve the user list")
    public void i_search_for_users() {
        users = authenticated.perform(() -> userController.findAllUsers(null));
    }

    @Then("The {string} user is returned")
    public void a_user_is_returned(final String expected) {

        TestAuthorizedUser authorizedUser = this.availableUsers.all().filter(u -> Objects.equals(expected, u.name()))
            .findFirst()
            .orElseThrow();

        assertThat(users.getContent())
            .anySatisfy(actual -> assertThat(actual).returns(authorizedUser.id(), AuthUser::getId));
    }

    @Then("The {string} user is not returned")
    public void a_user_is_not_returned(final String expected) {
        TestAuthorizedUser authorizedUser = this.availableUsers.all().filter(u -> Objects.equals(expected, u.name()))
            .findFirst()
            .orElseThrow();

        assertThat(users.getContent())
            .noneSatisfy(actual -> assertThat(actual).returns(authorizedUser.id(), AuthUser::getId));
    }

}
