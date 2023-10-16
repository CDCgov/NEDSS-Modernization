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

  Scenario Outline: Null-flavor races are not part of the Patient Profile Summary
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

    Scenario: A patient's home address is in the Patient Profile Summary
      Given I can "find" any "patient"
      And the patient has a "home" address at "1111 Street Pl." "Raccoon City" "00001"
      And the patient has a "temporary" address at "2222 Other Rd." "Angel Grove" "10001"
      When I view the Patient Profile Summary
      Then the Patient Profile Summary has a "home address" of "1111 Street Pl."
      And the Patient Profile Summary has a "home city" of "Raccoon City"
      And the Patient Profile Summary has a "home zipcode" of "00001"
      And the Patient Profile Summary does not have an "address" of "1111 Street Pl."
      And the Patient Profile Summary does not have a "city" of "Raccoon City"
      And the Patient Profile Summary does not have a "zipcode" of "00001"

  Scenario: A patient's addresses are in the Patient Profile Summary
    Given I can "find" any "patient"
    And the patient has a "home" address at "1111 Street Pl." "Raccoon City" "00001"
    And the patient has a "temporary" address at "2222 Other Rd." "Angel Grove" "10001"
    When I view the Patient Profile Summary
    Then the Patient Profile Summary has a "home address" of "1111 Street Pl."
    And the Patient Profile Summary has a "home city" of "Raccoon City"
    And the Patient Profile Summary has a "home zipcode" of "00001"
    And the Patient Profile Summary does not have an "address" of "1111 Street Pl."
    And the Patient Profile Summary does not have a "city" of "Raccoon City"
    And the Patient Profile Summary does not have a "zipcode" of "00001"
