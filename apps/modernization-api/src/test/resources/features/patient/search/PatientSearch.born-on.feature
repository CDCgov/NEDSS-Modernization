@patient-search
Feature: Patient Search by Date of birth

  Background:
    Given I am logged into NBS
    And I can "find" any "patient"
    Given I have a patient
    And I have another patient

  Scenario: I can find patients born on a specific day
    Given the patient was born on 01/15/1974
    And I have another patient
    And the patient was born on 01/03/1974
    And patients are available for search
    And I add the patient criteria for patient's born on the 3rd day of the month
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result

  Scenario: I can find patients born on a specific day of a month
    Given the patient was born on 11/15/1987
    And I have another patient
    And the patient was born on 11/05/1974
    And patients are available for search
    And I add the patient criteria for patient's born on the 5th day of the month
    And I add the patient criteria for patient's born in the month of November
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result

  Scenario: I can find patients born on a specific month
    Given the patient was born on 01/13/1951
    And I have another patient
    And the patient was born on 08/13/1951
    And patients are available for search
    And I add the patient criteria for patient's born in the month of August
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result

  Scenario: I can find patients born on a specific month in a year
    Given the patient was born on 04/13/1947
    And I have another patient
    And the patient was born on 04/13/1989
    And patients are available for search
    And I add the patient criteria for patient's born in the month of April
    And I add the patient criteria for patient's born in the year 1989
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result

  Scenario: I can find patients born in a specific year
    Given the patient was born on 01/15/1987
    And I have another patient
    And the patient was born on 10/11/2000
    And patients are available for search
    And I add the patient criteria for patient's born in the year 2000
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result

  Scenario: I can find patients born on a specific date
    Given the patient was born on 11/15/1987
    And I have another patient
    And the patient was born on 09/17/1934
    And patients are available for search
    And I add the patient criteria for patient's born in the month of September
    And I add the patient criteria for patient's born on the 17th day of the month
    And I add the patient criteria for patient's born in the year 1934
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result

  Scenario: I can find patients born between two dates
    Given the patient was born on 01/15/1987
    And I have another patient
    And the patient was born on 10/11/2000
    And patients are available for search
    And I add the patient criteria for patient's born between 10/01/2000 and 10/12/2000
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result

  Scenario: I can filter patients by dob with slashes
    Given the patient was born on 01/15/1987
    And I have another patient
    And the patient was born on 10/11/2000
    And patients are available for search
    And I add the patient criteria for patient's born between 10/01/2000 and 10/12/2000
    And I would like to filter search results with age or dob "10/11"
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result

  Scenario: I can filter patients by dob with dashes
    Given the patient was born on 01/15/1987
    And I have another patient
    And the patient was born on 10/11/2000
    And patients are available for search
    And I add the patient criteria for patient's born between 10/01/2000 and 10/12/2000
    And I would like to filter search results with age or dob "10-11"
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result

  Scenario: I can filter patients by dob that does not exist
    Given the patient was born on 01/15/1987
    And I have another patient
    And the patient was born on 10/11/2000
    And patients are available for search
    And I add the patient criteria for patient's born between 10/01/2000 and 10/12/2000
    And I would like to filter search results with age or dob "9999"
    When I search for patients
    And there are 0 patient search results

  Scenario: I can filter patients by age that equals an existing one
    Given the patient was born on 01/15/1987
    And I have another patient
    And the patient was born 10 years ago
    And patients are available for search
    And I would like to filter search results with age or dob "10"
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result

  Scenario: I can filter patients by age that does not exist because it is one year more than an existing one
    Given the patient was born on 01/15/1987
    And I have another patient
    And the patient was born 39 years ago
    And patients are available for search
    And I would like to filter search results with age or dob "40"
    When I search for patients
    Then there are 0 patient search results

  Scenario: I can filter patients by age that does not exist because it is one year less than an existing one
    Given the patient was born on 01/15/1987
    And I have another patient
    And the patient was born 41 years ago
    And patients are available for search
    And I would like to filter search results with age or dob "40"
    When I search for patients
    Then there are 0 patient search results

  Scenario: I can filter patients by age and dob
    Given the patient was born on 10/15/1987
    And I have another patient
    And the patient was born 10 years ago
    And patients are available for search
    And I would like to filter search results with age or dob "10"
    When I search for patients
    Then there are 2 patient search results


  Scenario: I can filter patients by age and dob and name filter
    Given the patient was born on 10/15/1987
    And the patient has the legal name "Joe" "Smith"
    And I have another patient
    And the patient has the legal name "Smith" "Jared"
    And the patient was born 10 years ago
    And patients are available for search
    And I would like to filter search results with name "smith"
    And I would like to filter search results with age or dob "10"
    When I search for patients
    Then there are 2 patient search results
