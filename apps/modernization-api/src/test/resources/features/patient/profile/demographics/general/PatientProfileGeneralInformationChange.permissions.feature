@patient-profile-general-information-change
Feature: Patient Profile General Information Changes Permission

  Background:
    Given I am logged into NBS
    And I can "find" any "patient"
    And I have a patient

  Scenario: I can not update a patient's general information without the appropriate permission
    Given I want to change the patient's general information to include the mother's maiden name of "Maiden Name"
    When the patient profile general information changes are submitted as of 04/17/2013
    Then I am not allowed due to insufficient permissions
