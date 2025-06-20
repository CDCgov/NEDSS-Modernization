@patient-file @patient-gender-demographics
Feature: Viewing the gender demographics of a patient

  Background:
    Given I am logged into NBS
    And I can "view" any "patient"
    And I have a patient

  Scenario: I can view a Patient's gender demographics
    Given the patient's gender as of 07/11/2013 is Female
    When I view the patient's gender demographics
    Then the patient file gender demographics are as of 07/11/2013
    And the patient file gender demographics has the current gender of Female

  Scenario: I can view Patient's gender demographics with additional gender
    Given the patient has the "additional" additional gender
    And the patient has a preferred gender of Transgender Unspecified
    When I view the patient's gender demographics
    Then the patient file gender demographics has the "additional" additional gender
    And the patient file gender demographics has a preferred gender of Transgender Unspecified

  Scenario: I can view a Patient's gender demographics when they are unknown
    Given the patient's current gender is unknown with the reason being Did not ask
    When I view the patient's gender demographics
    Then the patient's current gender is unknown with the reason being Did not ask

  Scenario: I can not view a Patient's gender demographics if there aren't any
    When I view the patient's gender demographics
    Then no value is returned
