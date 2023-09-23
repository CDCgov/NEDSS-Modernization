@patient @patient-search
Feature: Patient Search!

  Background:
    Given I am logged into NBS
    And I can "find" any "patient"
    And I want patients sorted by "relevance" "desc"
    And I have a patient

  Scenario: I can find a patient by Patient ID
    When I search for patients
    Then the patient search results contains the Patient ID


  Scenario: I can find patients with active record status
    Given I have another patient
    And the patient is inactive
    And I would like patients that are "active"
    When I search for patients
    Then the search results have a patient with a "status" equal to "Active"

  Scenario: I can find patients with deleted record status
    Given I can "findInactive" any "patient"
    And the patient is inactive
    And I would like patients that are "deleted"
    When I search for patients
    Then the search results have a patient with a "status" equal to "Inactive"

  Scenario: I cant search for deleted records without the correct permission
    Given I would like patients that are "deleted"
    When I search for patients
    Then I am not able to execute the search


  Scenario Outline: When search criteria ends with a space, only the expected patients are returned
    Given the patient has a "<field>" of "<value>"
    And I add the patient criteria for a "<field>" equal to "<value> "
    When I search for patients
    Then the patient is in the search results
    When I search for patients
    Then the patient is in the search results
    Examples:
      | field      | value        |
      | first name | First        |
      | last name  | Last         |
      | city       | City         |
      | address    | 1234 Address |

  Scenario: I can find the patients ordered by birthday
    Given  the patient has a "birthday" of "1987-01-15"
    And I have another patient
    And the patient has a "birthday" of "1999-11-19"
    And I have another patient
    And the patient has a "birthday" of "1974-05-29"
    And I want patients sorted by "birthday" "asc"
    When I search for patients
    And search result 1 has a "birthday" of "1974-05-29"
    And search result 2 has a "birthday" of "1987-01-15"
    And search result 3 has a "birthday" of "1999-11-19"

  Scenario: I can find the most relevant patient when searching by first name
    Given the patient has the "legal" name "Something" "Other"
    And I have another patient
    And the patient has the "legal" name "Jon" "Snow"
    And I have another patient
    And the patient has the "legal" name "John" "Little"
    And I add the patient criteria for a "first name" equal to "John"
    When I search for patients
    Then search result 1 has a "first name" of "John"
    And search result 1 has a "last name" of "Little"
    And search result 2 has a "first name" of "Jon"
    And search result 2 has a "last name" of "Snow"

  Scenario: I can find the most relevant patient when searching by last name
    Given the patient has the "legal" name "Something" "Other"
    And I have another patient
    And the patient has the "legal" name "Albert" "Smyth"
    And I have another patient
    And the patient has the "legal" name "Fatima" "Smith"
    And I add the patient criteria for a "last name" equal to "Smith"
    When I search for patients
    Then search result 1 has a "first name" of "Fatima"
    And search result 1 has a "last name" of "Smith"
    And search result 2 has a "first name" of "Albert"
    And search result 2 has a "last name" of "Smyth"

  Scenario: I can search for a Patient using a partial phone number
    Given the patient has a "phone number" of "888-240-2200"
    And I have another patient
    And the patient has a "phone number" of "613-240-2200"
    And I add the patient criteria for an "phone number" equal to "613-240-2200"
    When I search for patients
    Then the search results have a patient with an "phone number" equal to "613-240-2200"

  Scenario: I can search for a Patient using an Identification
    Given the patient can be identified with an "other" of "888-88-8888"
    And I have another patient
    And the patient can be identified with a "SS" of "888-88-8888"
    And I add the patient criteria for an "identification type" equal to "SS"
    And I add the patient criteria for an "identification value" equal to "888-88-8888"
    When I search for patients
    Then there is only one patient search result
    And the search results have a patient with an "identification type" equal to "Social Security"
    And the search results have a patient with an "identification value" equal to "888-88-8888"

  Scenario Outline: I can search for a Patient using a partial a Identification number
    Given the patient can be identified with an <first-type> of <first-value>
    And the patient can be identified with a <patient-type> of <patient-value>
    And I add the patient criteria for an "identification type" equal to <patient-type>
    And I add the patient criteria for an "identification value" equal to <search>
    When I search for patients
    Then the patient is in the search results
    Examples:
      | first-type | first-value   | patient-type | patient-value | search |
      | "MN"       | "888-88-8000" | "SS"         | "888-88-8888" | "888"  |
      | "SS"       | "888-88-8000" | "SS"         | "888-88-8888" | "888"  |
      | "SS"       | "111-88-8000" | "SS"         | "888-88-8888" | "888"  |

  Scenario: I can search for a Patient using a specific Identification
    Given the patient can be identified with a "MC" of "5507"
    And the patient can be identified with a "DL" of "4099"
    And I add the patient criteria for an "identification type" equal to "MC"
    And I add the patient criteria for an "identification value" equal to "4099"
    When I search for patients
    Then the patient is not in the search results
