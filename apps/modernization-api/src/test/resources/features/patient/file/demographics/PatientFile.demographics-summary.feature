@patient-file @patient-demographics-summary
Feature: Summarizing the demographics of a patient

  Background:
    Given I am logged into NBS
    And I can "viewworkup" any "patient"
    And I have a patient

  Scenario:  I cannot view the demographics summary of the patient that does not exist
    When I view the demographics summary for patient 1039
    Then it was not found

  Scenario: Not existent demographics are not included
    When I view the demographics summary of the patient
    Then the demographics summary of the patient does not include an address
    And the demographics summary of the patient does not include a phone
    And the demographics summary of the patient does not include an email address
    And the demographics summary of the patient does not contain any identifications
    And the demographics summary of the patient does not contain any races

  Scenario: I can view the patient's most recent address in the summarized demographics
    Given the patient was born in the city of "Castle Rock" as of 04/15/2020
    And the patient was born in the state of Maine
    And the patient has a House - Home address at "307 One Street" "Miami" "12345" as of 05/17/1999
    And the patient has an Office - Primary Business address at "673 Work Blvd" "San Francisco" "92112" as of 04/15/2020
    And the patient has a House - Home address at "227 Electric Ave" "Derry" Maine 12345 as of 04/15/2020
    When I view the demographics summary of the patient
    Then the demographics summary of the patient has an address with a use of "Home"
    And the demographics summary of the patient has an address with a street address of "227 Electric Ave"
    And the demographics summary of the patient has an address with a city of "Derry"
    And the demographics summary of the patient has an address with a state of "ME"

  Scenario: I can view the patient's most recent ethnicity in the summarized demographics
    Given the patient has the ethnicity Unknown
    When I view the demographics summary of the patient
    Then the demographics summary of the patient has an ethnicity of "Unknown"

  Scenario: I can view the patient's most recent phone number in the summarized demographics
    Given the patient has the Email Address - Home number of "555-555-5555" as of 11/07/2023
    And the patient has the Fax - Home number of "555-444-4444" as of 11/07/2024
    And the patient has the Phone - Home number of "555-111-1111" as of 11/07/2024
    And the patient has the Email Address - Home email address of "xyz@test.com" as of 11/07/2024
    When I view the demographics summary of the patient
    Then the demographics summary of the patient has a phone with a type of "Phone"
    And the demographics summary of the patient has a phone with a use of "Home"
    And the demographics summary of the patient has a phone the with number "555-111-1111"

  Scenario: I can view the patient's most recent email address in the summarized demographics
    Given the patient has the Email Address - Home email address of "other@ema.il" as of 11/07/2024
    And the patient has the Phone - Home email address of "abc@test.com" as of 11/07/2024
    And the patient has the Phone - Home number of "555-111-1111" as of 11/07/2024
    And the patient has the Email Address - Home email address of "xyz@test.com" as of 11/07/2023
    When I view the demographics summary of the patient
    Then the demographics summary of the patient has an email address of "abc@test.com"

  Scenario: I can view the patient's most recent Identification for each type in the summarized demographics
    Given the patient can be identified with an Other of "773" as of 11/07/2023
    And the patient can be identified with a Driver's license number of "00123" as of 05/13/1997
    And the patient can be identified with a Driver's license number of "123" as of 10/17/2001
    And the patient can be identified with an Other of "999-31-1033" as of 10/07/2004
    And the patient can be identified with an Other of "999-11-1039" as of 11/07/2023
    When I view the demographics summary of the patient
    Then the 1st identification in the demographics summary of the patient is an "Other" of "999-11-1039"
    And the 2nd identification in the demographics summary of the patient is a "Driver's license number" of "123"

  Scenario: I can view the patient's races in the summarized demographics
    Given the patient's race is Unknown as of 07/24/1974
    And the patient's race is Asian as of 11/05/2022
    And the patient race of Asian includes Japanese
    And the patient race of Asian includes Burmese
    And the patient's race is Other as of 08/17/2000
    When I view the demographics summary of the patient
    Then the 1st race in the demographics summary of the patient is "Asian"
    And the 2nd race in the demographics summary of the patient is "Other"
    And the 3rd race in the demographics summary of the patient is "Unknown"
