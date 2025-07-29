@patient-profile-treatments
Feature: Patient Profile Treatments

  Background:
    Given I have a patient
    And the patient is a subject of an investigation

  Scenario: A Treatment is viewed from the Patient Profile
    Given I am logged into NBS and a security log entry exists
    And I can "VIEW" any "TREATMENT"
    And the patient is a subject of a Treatment
    When the Treatment is viewed from the Patient Profile
    Then the classic profile is prepared to view a Treatment
    And I am redirected to Classic NBS to view a Treatment

  Scenario: A Treatment is viewed from the Patient Profile without required permissions
    Given I am logged into NBS and a security log entry exists
    And the patient is a subject of a Treatment
    When the Treatment is viewed from the Patient Profile
    Then I am not allowed to view a Classic NBS Treatment
