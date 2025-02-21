@patient-search @patient-search-results
Feature: Patient Search Result Identifications

  Background:
    Given I am logged into NBS
    And I can "find" any "patient"
    And I want patients sorted by "relevance" "desc"
    And I have a patient

  Scenario: I can search for a Patient using an Identification
    Given the patient can be identified with an "other" of "888-88-8888"
    And I have another patient
    And the patient can be identified with a Social Security of "888-88-8888"
    And patients are available for search
    And I add the patient criteria for an identification type equal to Social Security
    And I add the patient criteria for an "identification value" equal to "888-88-8888"
    When I search for patients
    Then there is only one patient search result
    And the search results have a patient with an "identification type" equal to "Social Security"
    And the search results have a patient with an "identification value" equal to "888-88-8888"

  Scenario Outline: I can search for a Patient using a partial a Identification number
    Given the patient can be identified with an <first-type> of <first-value>
    And the patient can be identified with a <patient-type> of <patient-value>
    And patients are available for search
    And I add the patient criteria for an identification type equal to <patient-type>
    And I add the patient criteria for an "identification value" equal to <search>
    When I search for patients
    Then the patient is in the search results
    Examples:
      | first-type      | first-value   | patient-type    | patient-value | search |
      | "MN"            | "888-88-8000" | Social Security | "888-88-8888" | "888"  |
      | Social Security | "888-88-8000" | Social Security | "888-88-8888" | "888"  |
      | Social Security | "111-88-8000" | Social Security | "888-88-8888" | "888"  |

  Scenario: I can search for a Patient using a specific Identification
    Given the patient can be identified with a Medicare number of "5507"
    And the patient can be identified with a Driver's license number of "4099"
    And patients are available for search
    And I add the patient criteria for an identification type equal to Medicare number
    And I add the patient criteria for an "identification value" equal to "4099"
    When I search for patients
    Then the patient is not in the search results

  Scenario: I can search for a Patient using a value that is contained in the Identification
    Given the patient can be identified with a Medicare number of "1234"
    And I have another patient
    And the patient can be identified with a Medicare number of "1345"
    And patients are available for search
    And I add the patient criteria for an identification type equal to Medicare number
    And I add the patient criteria for an "identification value" equal to "23"
    When I search for patients
    Then there is only one patient search result
    And the search results have a patient with an "identification value" equal to "1234"

  Scenario: BUG: CNFT1-2008 I can search for a Patient with invalid identification
    Given the patient has the legal name "Max" "Headroom"
    And the patient can be identified with a Medicare Number of "1009"
    And I have another patient
    And the patient has the legal name "Max" "Smart"
    And the patient has the gender Male
    And the patient can be identified with an Account Number without a value
    And patients are available for search
    When I search for patients
    And the search results have a patient with a "last name" equal to "Headroom"
    And the search results have a patient with a "last name" equal to "Smart"

  Scenario: I can search for a Patient using a specific Identification in lower case when it's stored in upper
    Given the patient can be identified with a Driver's license number of "A123"
    And patients are available for search
    And I add the patient criteria for an identification type equal to Driver's license number
    And I add the patient criteria for an "identification value" equal to "a"
    When I search for patients
    Then the patient is in the search results

  Scenario: I can search for a Patient using a specific Identification in upper case when it's stored in lower
    Given the patient can be identified with a Driver's license number of "a123"
    And patients are available for search
    And I add the patient criteria for an identification type equal to Driver's license number
    And I add the patient criteria for an "identification value" equal to "A"
    When I search for patients
    Then the patient is in the search results

  Scenario: I can search for a Patient using a specific Identification and filter
    Given the patient can be identified with a Medicare number of "5507"
    And the patient can be identified with a Driver's license number of "4099"
    And patients are available for search
    And I add the patient criteria for an "identification value" equal to "4099"
    And I would like to filter search results with identification "09"
    When I search for patients
    Then there are 1 patient search results

  Scenario: I can search for a Patient using a specific Identification and filter with dashes
    Given the patient can be identified with a Medicare number of "5507"
    And the patient can be identified with a Driver's license number of "4099"
    And patients are available for search
    And I add the patient criteria for an "identification value" equal to "4099"
    And I would like to filter search results with identification "40-99"
    When I search for patients
    Then there are 1 patient search results

  Scenario: I can search for a Patient using a specific Identification and non matching filter
    Given the patient can be identified with a Medicare number of "5507"
    And the patient can be identified with a Driver's license number of "4099"
    And patients are available for search
    And I add the patient criteria for an "identification value" equal to "4099"
    And I would like to filter search results with identification "111"
    When I search for patients
    Then there are 0 patient search results

  Scenario: I can search for a Patient using a specific Identification and two filters
    Given the patient can be identified with a Medicare number of "5507"
    And the patient can be identified with a Driver's license number of "4099"
    And the patient has the gender Female
    And patients are available for search
    And I add the patient criteria for an "identification value" equal to "4099"
    And I add the patient criteria for sex filter of "F"
    And I would like to filter search results with identification "09"
    When I search for patients
    Then there are 1 patient search results

  Scenario: Patient Search Results contain identifications ordered by as of date
    Given the patient can be identified with a Account number of "THREE0823" as of 05/13/1997
    And the patient can be identified with a Person number of "ONE1051" as of 11/07/2023
    And the patient can be identified with a Other of "TWO0941" as of 07/11/2023
    And patients are available for search
    And I would like to search for a patient using a local ID
    When I search for patients
    Then the search results have a patient with the 1st "identification value" equal to "ONE1051"
    And the search results have a patient with the 2nd "identification value" equal to "TWO0941"
    And the search results have a patient with the 3rd "identification value" equal to "THREE0823"
