package gov.cdc.nbs.patient.demographic;



import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import gov.cdc.nbs.patient.PatientCommand;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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
            LocalDate.parse("2010-03-03"),
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
        .returns(LocalDate.parse("2010-03-03"), GeneralInformation::asOf)
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
  void should_clear_general_information_fields() {

    GeneralInformation actual = new GeneralInformation();

    actual.update(
        new PatientCommand.UpdateGeneralInfo(
            121L,
            LocalDate.parse("2010-03-03"),
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

    actual.clear();

    assertThat(actual)
        .returns(null, GeneralInformation::asOf)
        .returns(null, GeneralInformation::maritalStatus)
        .returns(null, GeneralInformation::mothersMaidenName)
        .returns(null, GeneralInformation::adultsInHouse)
        .returns(null, GeneralInformation::childrenInHouse)
        .returns(null, GeneralInformation::occupation)
        .returns(null, GeneralInformation::educationLevel)
        .returns(null, GeneralInformation::primaryLanguage)
        .returns(null, GeneralInformation::speaksEnglish);
  }

  @Test
  void should_not_clear_as_of_when_state_HIV_case_is_present() {

    PermissionScope allowed = mock(PermissionScope.class);
    when(allowed.allowed()).thenReturn(true);

    PermissionScopeResolver resolver = mock(PermissionScopeResolver.class);
    when(resolver.resolve(any())).thenReturn(allowed);

    GeneralInformation actual = new GeneralInformation();

    actual.update(
        new PatientCommand.UpdateGeneralInfo(
            121L,
            LocalDate.parse("2010-03-03"),
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

    actual.associate(
        resolver,
        new PatientCommand.AssociateStateHIVCase(
            263L,
            "case-number",
            12L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        )
    );

    actual.clear();

    assertThat(actual)
        .returns(LocalDate.parse("2010-03-03"), GeneralInformation::asOf);
  }

  @Test
  void should_associate_state_HIV_Case_fields_when_HIV_access_is_allowed() {

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
  void should_disassociate_state_HIV_Case_fields_when_HIV_access_is_allowed() {

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

    actual.disassociate(
        resolver,
        new PatientCommand.DisassociateStateHIVCase(
            263L,
            12L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        )
    );

    assertThat(actual.stateHIVCase()).isNull();
  }

  @Test
  void should_not_associate_state_HIV_Case_fields_when_HIV_access_is_not_allowed() {

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

  @Test
  void should_not_disassociate_state_HIV_Case_fields_when_HIV_access_is_not_allowed() {

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

    PermissionScope notAllowed = mock(PermissionScope.class);
    when(notAllowed.allowed()).thenReturn(false);

    when(notAllowed.allowed()).thenReturn(false);
    when(resolver.resolve(any())).thenReturn(allowed);

    assertThat(actual.stateHIVCase()).isEqualTo("case-number");

  }

  @Test
  void should_not_clear_as_of_when_martial_status_is_present() {

    PermissionScope allowed = mock(PermissionScope.class);
    when(allowed.allowed()).thenReturn(true);

    PermissionScopeResolver resolver = mock(PermissionScopeResolver.class);
    when(resolver.resolve(any())).thenReturn(allowed);

    GeneralInformation actual = new GeneralInformation();

    actual.update(
        new PatientCommand.UpdateGeneralInfo(
            121L,
            LocalDate.parse("2010-03-03"),
            "marital status",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            12L,
            LocalDateTime.parse("2023-07-11T23:29:31")
        )
    );

    actual.disassociate(
        resolver,
        new PatientCommand.DisassociateStateHIVCase(
            263L,
            12L,
            LocalDateTime.parse("2023-07-11T23:29:31")
        )
    );

    assertThat(actual)
        .returns(LocalDate.parse("2010-03-03"), GeneralInformation::asOf);
  }

  @Test
  void should_not_clear_as_of_when_mothers_maiden_name_is_present() {

    PermissionScope allowed = mock(PermissionScope.class);
    when(allowed.allowed()).thenReturn(true);

    PermissionScopeResolver resolver = mock(PermissionScopeResolver.class);
    when(resolver.resolve(any())).thenReturn(allowed);

    GeneralInformation actual = new GeneralInformation();

    actual.update(
        new PatientCommand.UpdateGeneralInfo(
            121L,
            LocalDate.parse("2010-03-03"),
            null,
            "mothers maiden name",
            null,
            null,
            null,
            null,
            null,
            null,
            12L,
            LocalDateTime.parse("2023-07-11T23:29:31")
        )
    );

    actual.disassociate(
        resolver,
        new PatientCommand.DisassociateStateHIVCase(
            263L,
            12L,
            LocalDateTime.parse("2023-07-11T23:29:31")
        )
    );

    assertThat(actual)
        .returns(LocalDate.parse("2010-03-03"), GeneralInformation::asOf);
  }

  @Test
  void should_not_clear_as_of_when_adults_in_house_is_present() {

    PermissionScope allowed = mock(PermissionScope.class);
    when(allowed.allowed()).thenReturn(true);

    PermissionScopeResolver resolver = mock(PermissionScopeResolver.class);
    when(resolver.resolve(any())).thenReturn(allowed);

    GeneralInformation actual = new GeneralInformation();

    actual.update(
        new PatientCommand.UpdateGeneralInfo(
            121L,
            LocalDate.parse("2010-03-03"),
            null,
            null,
            1,
            null,
            null,
            null,
            null,
            null,
            12L,
            LocalDateTime.parse("2023-07-11T23:29:31")
        )
    );

    actual.disassociate(
        resolver,
        new PatientCommand.DisassociateStateHIVCase(
            263L,
            12L,
            LocalDateTime.parse("2023-07-11T23:29:31")
        )
    );

    assertThat(actual)
        .returns(LocalDate.parse("2010-03-03"), GeneralInformation::asOf);
  }

  @Test
  void should_not_clear_as_of_when_children_in_house_is_present() {

    PermissionScope allowed = mock(PermissionScope.class);
    when(allowed.allowed()).thenReturn(true);

    PermissionScopeResolver resolver = mock(PermissionScopeResolver.class);
    when(resolver.resolve(any())).thenReturn(allowed);

    GeneralInformation actual = new GeneralInformation();

    actual.update(
        new PatientCommand.UpdateGeneralInfo(
            121L,
            LocalDate.parse("2010-03-03"),
            null,
            null,
            null,
            2,
            null,
            null,
            null,
            null,
            12L,
            LocalDateTime.parse("2023-07-11T23:29:31")
        )
    );

    actual.disassociate(
        resolver,
        new PatientCommand.DisassociateStateHIVCase(
            263L,
            12L,
            LocalDateTime.parse("2023-07-11T23:29:31")
        )
    );

    assertThat(actual)
        .returns(LocalDate.parse("2010-03-03"), GeneralInformation::asOf);
  }

  @Test
  void should_not_clear_as_of_when_occupation_is_present() {

    PermissionScope allowed = mock(PermissionScope.class);
    when(allowed.allowed()).thenReturn(true);

    PermissionScopeResolver resolver = mock(PermissionScopeResolver.class);
    when(resolver.resolve(any())).thenReturn(allowed);

    GeneralInformation actual = new GeneralInformation();

    actual.update(
        new PatientCommand.UpdateGeneralInfo(
            121L,
            LocalDate.parse("2010-03-03"),
            null,
            null,
            null,
            null,
            "occupation code",
            null,
            null,
            null,
            12L,
            LocalDateTime.parse("2023-07-11T23:29:31")
        )
    );

    actual.disassociate(
        resolver,
        new PatientCommand.DisassociateStateHIVCase(
            263L,
            12L,
            LocalDateTime.parse("2023-07-11T23:29:31")
        )
    );

    assertThat(actual)
        .returns(LocalDate.parse("2010-03-03"), GeneralInformation::asOf);
  }

  @Test
  void should_not_clear_as_of_when_education_level_is_present() {

    PermissionScope allowed = mock(PermissionScope.class);
    when(allowed.allowed()).thenReturn(true);

    PermissionScopeResolver resolver = mock(PermissionScopeResolver.class);
    when(resolver.resolve(any())).thenReturn(allowed);

    GeneralInformation actual = new GeneralInformation();

    actual.update(
        new PatientCommand.UpdateGeneralInfo(
            121L,
            LocalDate.parse("2010-03-03"),
            null,
            null,
            null,
            null,
            null,
            "education level",
            null,
            null,
            12L,
            LocalDateTime.parse("2023-07-11T23:29:31")
        )
    );

    actual.disassociate(
        resolver,
        new PatientCommand.DisassociateStateHIVCase(
            263L,
            12L,
            LocalDateTime.parse("2023-07-11T23:29:31")
        )
    );

    assertThat(actual)
        .returns(LocalDate.parse("2010-03-03"), GeneralInformation::asOf);
  }

  @Test
  void should_not_clear_as_of_when_primary_language_is_present() {

    PermissionScope allowed = mock(PermissionScope.class);
    when(allowed.allowed()).thenReturn(true);

    PermissionScopeResolver resolver = mock(PermissionScopeResolver.class);
    when(resolver.resolve(any())).thenReturn(allowed);

    GeneralInformation actual = new GeneralInformation();

    actual.update(
        new PatientCommand.UpdateGeneralInfo(
            121L,
            LocalDate.parse("2010-03-03"),
            null,
            null,
            null,
            null,
            null,
            null,
            "primary language",
            null,
            12L,
            LocalDateTime.parse("2023-07-11T23:29:31")
        )
    );

    actual.disassociate(
        resolver,
        new PatientCommand.DisassociateStateHIVCase(
            263L,
            12L,
            LocalDateTime.parse("2023-07-11T23:29:31")
        )
    );

    assertThat(actual)
        .returns(LocalDate.parse("2010-03-03"), GeneralInformation::asOf);
  }

  @Test
  void should_not_clear_as_of_when_speaks_english_is_present() {

    PermissionScope allowed = mock(PermissionScope.class);
    when(allowed.allowed()).thenReturn(true);

    PermissionScopeResolver resolver = mock(PermissionScopeResolver.class);
    when(resolver.resolve(any())).thenReturn(allowed);

    GeneralInformation actual = new GeneralInformation();

    actual.update(
        new PatientCommand.UpdateGeneralInfo(
            121L,
            LocalDate.parse("2010-03-03"),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            "speaks english",
            12L,
            LocalDateTime.parse("2023-07-11T23:29:31")
        )
    );

    actual.disassociate(
        resolver,
        new PatientCommand.DisassociateStateHIVCase(
            263L,
            12L,
            LocalDateTime.parse("2023-07-11T23:29:31")
        )
    );

    assertThat(actual)
        .returns(LocalDate.parse("2010-03-03"), GeneralInformation::asOf);
  }
}
