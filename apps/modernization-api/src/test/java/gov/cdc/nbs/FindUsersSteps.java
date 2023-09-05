package gov.cdc.nbs;

import gov.cdc.nbs.authentication.entity.AuthAudit;
import gov.cdc.nbs.authentication.entity.AuthPermSet;
import gov.cdc.nbs.authentication.entity.AuthPermSetRepository;
import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.authentication.entity.AuthUserRepository;
import gov.cdc.nbs.authentication.entity.AuthUserRole;
import gov.cdc.nbs.authentication.entity.AuthUserRoleRepository;
import gov.cdc.nbs.authorization.TestAuthorizedUser;
import gov.cdc.nbs.controller.UserController;
import gov.cdc.nbs.support.PermissionMother;
import gov.cdc.nbs.support.TestAvailable;
import gov.cdc.nbs.support.UserMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FindUsersSteps {

    @Autowired
    private AuthPermSetRepository permSetRepository;
    @Autowired
    private AuthUserRoleRepository roleRepository;
    @Autowired
    private UserController userController;
    @Autowired
    private AuthUserRepository userRepository;

    @Autowired
    TestAvailable<TestAuthorizedUser> availableUsers;

    @Autowired
    TestAuthentication testAuthentication;

    private Page<AuthUser> users;

    @Given("A clerical permission set exists")
    public void a_clerical_permission_set_exists() {
        permSetRepository.save(PermissionMother.clericalPermissionSet());
    }

    @Given("The {string} user has the {string} permission set for the {string} program area")
    public void The_user_has_the_permission_set_for_the_program_area(String userString, String permissionSetString,
        String programArea) {
        AuthUser user;
        AuthPermSet permSet;

        switch (userString) {
            case "clerical":
                var userId = UserMother.clerical().getUserId();
                user = userRepository.findByUserId(userId)
                    .orElseThrow(() -> new RuntimeException("Failed to find user with id: " + userId));
                break;
            default:
                throw new IllegalArgumentException("Unsupported user type: " + userString);
        }

        switch (permissionSetString) {
            case "clerical":
                var permSetId = PermissionMother.clericalPermissionSet().id();
                permSet = permSetRepository.findById(permSetId)
                    .orElseThrow(() -> new RuntimeException("Failed to find permission set with id: " + permSetId));
                break;
            default:
                throw new IllegalArgumentException("Unsupported permission set type: " + permissionSetString);
        }

        addUserToPermissionSet(user, permSet, programArea);
    }

    private void addUserToPermissionSet(AuthUser user, AuthPermSet permSet, String programArea) {
        var existingRole = roleRepository.findByUserAndPermissionSet(user.getId(), permSet.id(), programArea);
        if (!existingRole.isPresent()) {
            var now = Instant.now();
            var audit = new AuthAudit(10000000L, now);
            var newRole = new AuthUserRole(user, permSet)
                .name(permSet.name())
                .programArea(programArea)
                .jurisdiction("ALL")
                .guest('F')
                .sequence(0)
                .audit(audit);
            roleRepository.save(newRole);
        }
    }

    @When("I search for users")
    public void i_search_for_users() {
        users = testAuthentication.authenticated(() -> userController.findAllUsers(null));
    }

    @Then("The clerical user is returned")
    public void a_user_is_returned() {

        TestAuthorizedUser authorizedUser = this.availableUsers.all().filter(u -> Objects.equals("clerical", u.name()))
            .findFirst()
            .orElseThrow();

        assertThat(users.getContent())
            .anySatisfy(actual -> assertThat(actual).returns(authorizedUser.id(), AuthUser::getId));
    }

    @Then("The clerical user is not returned")
    public void a_user_is_not_returned() {
        TestAuthorizedUser authorizedUser = this.availableUsers.all().filter(u -> Objects.equals("clerical", u.name()))
            .findFirst()
            .orElseThrow();

        assertThat(users.getContent())
            .noneSatisfy(actual -> assertThat(actual).returns(authorizedUser.id(), AuthUser::getId));
    }

}
