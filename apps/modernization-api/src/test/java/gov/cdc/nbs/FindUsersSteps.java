package gov.cdc.nbs;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import gov.cdc.nbs.authentication.AuthPermSet;
import gov.cdc.nbs.authentication.AuthPermSetRepository;
import gov.cdc.nbs.authentication.AuthUser;
import gov.cdc.nbs.authentication.AuthUserRepository;
import gov.cdc.nbs.authentication.AuthUserRole;
import gov.cdc.nbs.authentication.AuthUserRoleRepository;
import gov.cdc.nbs.controller.UserController;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.support.PermissionMother;
import gov.cdc.nbs.support.UserMother;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

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

    private Page<AuthUser> users;

    @Before
    public void clearAuth() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

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
                var permSetId = PermissionMother.clericalPermissionSet().getId();
                permSet = permSetRepository.findById(permSetId)
                        .orElseThrow(() -> new RuntimeException("Failed to find permission set with id: " + permSetId));
                break;
            default:
                throw new IllegalArgumentException("Unsupported permission set type: " + permissionSetString);
        }

        addUserToPermissionSet(user, permSet, programArea);
    }

    private void addUserToPermissionSet(AuthUser user, AuthPermSet permSet, String programArea) {
        var existingRole = roleRepository.findByUserAndPermissionSet(user.getId(), permSet.getId(), programArea);
        if (!existingRole.isPresent()) {
            var now = Instant.now();
            var newRole = AuthUserRole
                    .builder()
                    .authRoleNm(permSet.getPermSetNm())
                    .progAreaCd(programArea)
                    .jurisdictionCd("ALL")
                    .authUserUid(user)
                    .authPermSetUid(permSet)
                    .roleGuestInd('F')
                    .readOnlyInd('T')
                    .dispSeqNbr(0)
                    .addTime(now)
                    .addUserId(10000000L)
                    .lastChgTime(now)
                    .lastChgUserId(10000000L)
                    .recordStatusCd(RecordStatus.ACTIVE)
                    .recordStatusTime(now)
                    .build();
            roleRepository.save(newRole);
        }
    }

    @When("I search for users")
    public void i_search_for_users() {
        users = userController.findAllUsers(null);
    }

    @Then("The clerical user is returned")
    public void a_user_is_returned() {
        assertNotEquals(0, users.getSize());
        assertNotEquals(0, users.getContent().size());
        var clericalUserId = UserMother.clerical().getUserId();
        var clerical = users.getContent().stream().filter(u -> u.getUserId().equals(clericalUserId)).findFirst();
        assertTrue(clerical.isPresent());
    }

    @Then("The clerical user is not returned")
    public void a_user_is_not_returned() {
        var clericalUserId = UserMother.clerical().getUserId();
        var clerical = users.getContent().stream().filter(u -> u.getUserId().equals(clericalUserId)).findFirst();
        assertFalse(clerical.isPresent());
    }

}
