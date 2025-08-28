@patient-profile-tracing
Feature: Patient Profile Contact Tracing

  Background:
    Given I have a patient
    And I have another patient
    And the patient is a subject of an investigation
    And the patient names the previous patient as a contact on the investigation

  @web-interaction
  Scenario: A Contact is viewed from the Patient Profile
    Given I am logged into NBS and a security log entry exists
    And I can "view" any "CT_Contact"
    When the Contact is viewed from the Patient Profile
    Then the classic profile is prepared to view a Contact
    And I am redirected to Classic NBS to view a Contact

  @web-interaction
  Scenario: A Contact is viewed from the Patient Profile without required permissions
    Given I am logged into NBS and a security log entry exists
    When the Contact is viewed from the Patient Profile
    Then I am not allowed to view a Classic NBS Contact
