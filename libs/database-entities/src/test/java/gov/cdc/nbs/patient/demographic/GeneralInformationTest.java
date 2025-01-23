package gov.cdc.nbs.patient.demographic;



import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import gov.cdc.nbs.patient.PatientCommand;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GeneralInformationTest {

  @Test
  void should_update_general_information_fields() {

    GeneralInformation actual = new GeneralInformation();

    actual.update(
        new PatientCommand.UpdateGeneralInfo(
            121L,
            Instant.parse("2010-03-03T10:15:30.00Z"),
            "marital status",
            "mothers maiden name",
            1,
            2,
            "occupation code",
            "education level",
            "prim language",
            "speaks english",
            12L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        )
    );

    assertThat(actual)
        .returns(Instant.parse("2010-03-03T10:15:30Z"), GeneralInformation::asOf)
        .returns("marital status", GeneralInformation::maritalStatus)
        .returns("mothers maiden name", GeneralInformation::mothersMaidenName)
        .returns(1, GeneralInformation::adultsInHouse)
        .returns(2, GeneralInformation::childrenInHouse)
        .returns("occupation code", GeneralInformation::occupation)
        .returns("education level", GeneralInformation::educationLevel)
        .returns("prim language", GeneralInformation::primaryLanguage)
        .returns("speaks english", GeneralInformation::speaksEnglish);
  }

  @Test
  void should_update_state_HIV_Case_fields_when_HIV_access_is_allowed() {

    PermissionScope allowed = mock(PermissionScope.class);
    when(allowed.allowed()).thenReturn(true);

    PermissionScopeResolver resolver = mock(PermissionScopeResolver.class);
    when(resolver.resolve(any())).thenReturn(allowed);

    GeneralInformation actual = new GeneralInformation();

    actual.associate(
        resolver,
        new PatientCommand.AssociateStateHIVCase(
            263L,
            "case-number",
            12L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        )
    );

    assertThat(actual.stateHIVCase()).isEqualTo("case-number");
  }

  @Test
  void should_not_update_state_HIV_Case_fields_when_HIV_access_is_not_allowed() {

    PermissionScope notAllowed = mock(PermissionScope.class);
    when(notAllowed.allowed()).thenReturn(false);

    PermissionScopeResolver resolver = mock(PermissionScopeResolver.class);
    when(resolver.resolve(any())).thenReturn(notAllowed);

    GeneralInformation actual = new GeneralInformation();

    actual.associate(
        resolver,
        new PatientCommand.AssociateStateHIVCase(
            263L,
            "case-number",
            12L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        )
    );

    assertThat(actual.stateHIVCase()).isNull();

  }
}
