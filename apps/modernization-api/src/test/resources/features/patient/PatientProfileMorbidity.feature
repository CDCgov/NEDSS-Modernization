@patient-profile-morbidity-reports
Feature: Patient Profile Morbidity Reports

  Background:
    Given I have a patient

  @web-interaction
  Scenario: A morbidity report is viewed from the Patient Profile
    Given I am logged into NBS and a security log entry exists
    And I can "VIEW" any "OBSERVATIONMORBIDITYREPORT"
    And the patient has a Morbidity Report
    When the morbidity report is viewed from the Patient Profile
    Then the classic profile is prepared to view a morbidity report
    And I am redirected to Classic NBS to view a morbidity report

  @web-interaction
  Scenario: A morbidity report is viewed from the Patient Profile without required permissions
    Given I am logged into NBS and a security log entry exists
    And the patient has a Morbidity Report
    When the morbidity report is viewed from the Patient Profile
    Then I am not allowed to view a Classic NBS morbidity report

  @web-interaction
  Scenario: A morbidity report is added from the Patient Profile
    Given I am logged into NBS and a security log entry exists
    And I can "ADD" any "OBSERVATIONMORBIDITYREPORT"
    When a morbidity report is added from a Patient Profile
    Then the classic profile is prepared to add a morbidity report
    And I am redirected to Classic NBS to add a morbidity report

  @web-interaction
  Scenario: A morbidity report is added from the Patient Profile without required permissions
    Given I am logged into NBS and a security log entry exists
    When a morbidity report is added from a Patient Profile
    Then I am not allowed to add a Classic NBS morbidity report

  @web-interaction
  Scenario: A morbidity report is submitted from Classic NBS
    Given I am logged into NBS and a security log entry exists
    When a morbidity report is submitted from Classic NBS
    Then the morbidity report is submitted to Classic NBS
    Then I am redirected to the Modernized Patient Profile

  @web-interaction
  Scenario: A morbidity report is submitted from Classic NBS and an Investigation is Created
    Given I am logged into NBS and a security log entry exists
    When a morbidity report is submitted and the user has chosen to also create an investigation
    Then I am redirected to Classic NBS to Create an Investigation from the Morbidity Report
