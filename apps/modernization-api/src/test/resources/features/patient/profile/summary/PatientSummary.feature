@patient @patient-summary
Feature: Patient Profile Summary

  Background:
    Given I am logged into NBS
    And I have a patient

  Scenario: I can retrieve a patient's summary
    Given I can "find" any "patient"
    When I view the Patient Profile Summary
    Then the Patient Profile Summary is found

  Scenario: I can not retrieve a patient's summary without permissions
    When I view the Patient Profile Summary
    Then the Patient Profile Summary is not accessible

  Scenario: A patient's identification in the Patient Profile Summary
    Given I can "find" any "patient"
    And the patient can be identified with a "Medicare number" of "5507"
    And the patient can be identified with a "Driver's license number" of "4099"
    When I view the Patient Profile Summary
    Then the Patient Profile Summary has an "identification type" of "Driver's license number"
    And the Patient Profile Summary has an "identification value" of "4099"
    And the Patient Profile Summary has an "identification type" of "Medicare number"
    And the Patient Profile Summary has an "identification value" of "5507"

  Scenario Outline: A patient's race is in the Patient Profile Summary
    Given I can "find" any "patient"
    And the patient has a "race" of <race>
    When I view the Patient Profile Summary
    Then the Patient Profile Summary has a "race" of <race>

    Examples:
      | race            |
      | "Alaska Native" |
      | "Unknown"       |
      | "Black"         |
      | "Asian"         |

  Scenario: Each patient race is in the Patient Profile Summary
    Given I can "find" any "patient"
    And the patient has a "race" of "Alaska Native"
    And the patient has a "race" of "Black"
    When I view the Patient Profile Summary
    Then the Patient Profile Summary has a "race" of "Alaska Native"
    Then the Patient Profile Summary has a "race" of "Black"

  Scenario Outline: Null-flavor races are not displyed in the Patient Profile Summary
    Given I can "find" any "patient"
    And the patient has a "race" of <race>
    When I view the Patient Profile Summary
    Then the Patient Profile Summary does not contain a "race"

    Examples:
      | race         |
      | "Not Asked"  |
      | "Refused"    |
      | "Multi-Race" |



  Scenario: A patient race takes precedence over Unknown in the Patient Profile Summary
    Given I can "find" any "patient"
    And the patient has a "race" of "Alaska Native"
    And the patient has a "race" of "Unknown"
    When I view the Patient Profile Summary
    Then the Patient Profile Summary has a "race" of "Alaska Native"
    And the Patient Profile Summary does not have a "race" of "Unknown"
