package gov.cdc.nbs.testing.authorization.permission;

import gov.cdc.nbs.testing.authorization.ActiveUser;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import gov.cdc.nbs.testing.authorization.jurisdiction.JurisdictionIdentifier;
import gov.cdc.nbs.testing.authorization.programarea.ProgramAreaIdentifier;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Transactional
public class AuthorizationSteps {


  private final Active<ActiveUser> activeUser;
  private final Available<ActiveUser> users;
  private final PermissionSetMother setMother;
  private final AuthorizationRoleMother roleMother;


  AuthorizationSteps(
      final Active<ActiveUser> activeUser,
      final Available<ActiveUser> users,
      final PermissionSetMother setMother,
      final AuthorizationRoleMother roleMother) {
    this.activeUser = activeUser;
    this.users = users;
    this.setMother = setMother;
    this.roleMother = roleMother;
  }

  @Before
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void clean() {
    roleMother.reset();
    //  need to figure out how to remove permission sets also
  }

  @Given("I can {string} any {string}")
  public void authorize(
      final String operation,
      final String object
  ) {

    ActiveUser user = activeUser.active();

    long set = setMother.allow(operation, object);

    roleMother.allowAny(user.id(), set);

  }

  @Given("I am a master system administrator")
  public void systemAdmin() {
    ActiveUser user = activeUser.active();
    roleMother.systemAdmin(user.id());
  }

  @Given("I am a security administrator")
  public void securityAdmin() {
    ActiveUser user = activeUser.active();
    roleMother.securityAdmin(user.id());
  }

  @Given("I can {string} any {string} for {programArea} within all jurisdictions")
  public void authorize(
      final String operation,
      final String object,
      final ProgramAreaIdentifier programArea
  ) {
    activeUser.maybeActive().ifPresent(user -> roleMother.allowAny(
            user.id(),
            setMother.allow(operation, object),
            programArea.code(),
            "ALL"
        )
    );
  }

  @Given("I can {string} any {string} for {programArea} in {jurisdiction}")
  public void authorize(
      final String operation,
      final String object,
      final ProgramAreaIdentifier programArea,
      final JurisdictionIdentifier jurisdiction
  ) {

    activeUser.maybeActive().ifPresent(user -> roleMother.allowAny(
            user.id(),
            setMother.allow(operation, object),
            programArea.code(),
            jurisdiction.code()
        )
    );
  }

  @Given("I can {string} a shared {string}")
  public void shared(
      final String operation,
      final String object
  ) {

    ActiveUser user = activeUser.active();

    long set = setMother.allow(operation, object);

    roleMother.allowShared(user.id(), set);

  }

  @Given("I can {string} a shared {string} for {programArea} in {jurisdiction}")
  public void shared(
      final String operation,
      final String object,
      final ProgramAreaIdentifier programArea,
      final JurisdictionIdentifier jurisdiction
  ) {
    ActiveUser user = activeUser.active();

    long set = setMother.allow(operation, object);

    roleMother.allowShared(user.id(), set, programArea.code(), jurisdiction.code());
  }

  @Given("the {string} user can {string} any {string} for {programArea} within all jurisdictions")
  public void shared(
      final String user,
      final String operation,
      final String object,
      final ProgramAreaIdentifier programArea
  ) {
    authorize(
        user,
        operation,
        object,
        programArea,
        JurisdictionIdentifier.ALL
    );
  }

  @Given("the {string} user can {string} any {string} for {programArea} only within {jurisdiction}")
  public void authorize(
      final String user,
      final String operation,
      final String object,
      final ProgramAreaIdentifier programArea,
      final JurisdictionIdentifier jurisdiction
  ) {

    ActiveUser authorizedUser = this.users.all().filter(u -> Objects.equals(user, u.username()))
        .findFirst()
        .orElseThrow();

    long set = setMother.allow(operation, object);

    roleMother.allowAny(authorizedUser.id(), set, programArea.code(), jurisdiction.code());
  }
}
