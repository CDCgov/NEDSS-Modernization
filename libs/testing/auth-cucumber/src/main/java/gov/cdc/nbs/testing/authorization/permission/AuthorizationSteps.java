package gov.cdc.nbs.testing.authorization.permission;

import gov.cdc.nbs.testing.authorization.ActiveUser;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@SuppressWarnings("java:S100")
@Transactional
public class AuthorizationSteps {

  @Autowired
  Active<ActiveUser> activeUser;

  @Autowired
  Available<ActiveUser> users;

  @Autowired
  PermissionSetMother setMother;

  @Autowired
  AuthorizationRoleMother roleMother;

  @Before
  public void clean() {
    roleMother.reset();
    setMother.reset();
  }

  @Given("I can {string} any {string}")
  public void the_active_user_can_operate_on_any_object(
      final String operation,
      final String object
  ) {

    ActiveUser user = activeUser.active();

    long set = setMother.allow(operation, object);

    roleMother.allowAny(user.id(), set);

  }

  @Given("I can {string} any {string} for {string} within all jurisdictions")
  public void the_active_user_can_operate_on_any_object_for_a_program_area_within_all_jurisdictions(
      final String operation,
      final String object,
      final String programArea
  ) {
    the_active_user_can_operate_on_any_object_for_a_program_area_within_a_jurisdiction(
        operation,
        object,
        programArea,
        "all"
    );
  }

  @Given("I can {string} any {string} for {string} within {string}")
  public void the_active_user_can_operate_on_any_object_for_a_program_area_within_a_jurisdiction(
      final String operation,
      final String object,
      final String programArea,
      final String jurisdiction
  ) {
    ActiveUser user = activeUser.active();

    long set = setMother.allow(operation, object);

    roleMother.allowAny(user.id(), set, programArea, jurisdiction);
  }

  @Given("I can {string} a shared {string}")
  public void the_active_user_can_operate_on_a_shared_object(
      final String operation,
      final String object
  ) {

    ActiveUser user = activeUser.active();

    long set = setMother.allow(operation, object);

    roleMother.allowShared(user.id(), set);

  }

  @Given("I can {string} a shared {string} for {string} within {string}")
  public void the_active_user_can_operate_on_a_shared_object_for_a_program_area_within_a_jurisdiction(
      final String operation,
      final String object,
      final String programArea,
      final String jurisdiction
  ) {
    ActiveUser user = activeUser.active();

    long set = setMother.allow(operation, object);

    roleMother.allowShared(user.id(), set, programArea, jurisdiction);
  }

  @Given("the {string} user can {string} any {string} for {string} within all jurisdictions")
  public void the_user_can_operate_on_any_object_for_a_program_area_within_all_jurisdictions(
      final String user,
      final String operation,
      final String object,
      final String programArea
  ) {
    the_user_can_operate_on_any_object_for_a_program_area_within_a_jurisdiction(
        user,
        operation,
        object,
        programArea,
        "all"
    );
  }

  @Given("the {string} user can {string} any {string} for {string} within {string}")
  public void the_user_can_operate_on_any_object_for_a_program_area_within_a_jurisdiction(
      final String user,
      final String operation,
      final String object,
      final String programArea,
      final String jurisdiction
  ) {

    ActiveUser authorizedUser = this.users.all().filter(u -> Objects.equals(user, u.username()))
        .findFirst()
        .orElseThrow();

    long set = setMother.allow(operation, object);

    roleMother.allowAny(authorizedUser.id(), set, programArea, jurisdiction);
  }
}
