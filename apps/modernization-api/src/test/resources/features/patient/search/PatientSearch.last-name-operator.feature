@patient-search @patient-search-results
Feature: Searching patient's by name

  Background:
    Given I am logged into NBS
    And I can "find" any "patient"
    And I have a patient

  Scenario: I can find a patient with a last name that equals a value
    Given I have another patient
    And the patient has the legal name "JoeEqual" "Smith"
    And patients are available for search
    And I add the patient criteria for a last name that equals "Smith"
    When I search for patients
    Then search result 1 has a "first name" of "JoeEqual"
    And search result 1 has a "last name" of "Smith"
    And there are 1 patient search results

  Scenario: I can find a patient with a last name that does not equal a value
    Given the patient has the legal name "Joe" "not-equal"
    And I have another patient
    And the patient has the legal name "JoeEqual" "Smith"
    And patients are available for search
    And I add the patient criteria for a last name that does not equal "Smith"
    When I search for patients
    Then there are 1 patient search results
    And the patient is not in the search results

  Scenario: I can find a patient with a last name that contains a value
    Given the patient has the Alias Name name "Joe" "Aerosmithy"
    And I have another patient
    And the patient has the legal name "Samantha" "Smother"
    And patients are available for search
    And I add the patient criteria for a last name that contains "smith"
    When I search for patients
    Then search result 1 has a "first name" of "Joe"
    And search result 1 has a "last name" of "Aerosmithy"
    And there are 1 patient search results

  Scenario: I can find a patient with a last name that starts with a value
    Given the patient has the Alias Name name "Joe" "another-value"
    And I have another patient
    And the patient has the legal name "Sam" "starts-value"
    And patients are available for search
    And I add the patient criteria for a last name that starts with "starts"
    When I search for patients
    Then search result 1 has a "first name" of "Sam"
    And search result 1 has a "last name" of "starts-value"
    And there are 1 patient search results

  Scenario: I can find a patient with a last name that sounds like a value
    Given I have another patient
    And the patient has the legal name "Joe" "Smooth"
    And patients are available for search
    And I add the patient criteria for a last name that sounds like "Smith"
    When I search for patients
    Then search result 1 has a "first name" of "Joe"
    And search result 1 has a "last name" of "Smooth"
    And there are 1 patient search results

  Scenario: I can find a patient with a last name with multiple names that equals a value
    Given I have another patient
    And the patient has the legal name "JoeEqual" "Smith Jones"
    And patients are available for search
    And I add the patient criteria for a last name that equals "Smith Jones"
    When I search for patients
    Then search result 1 has a "first name" of "JoeEqual"
    And search result 1 has a "last name" of "Smith Jones"
    And there are 1 patient search results

  Scenario: I can find a patient with a last name with hyphens that equals a value
    Given I have another patient
    And the patient has the legal name "JoeEqual" "Smith-Jones"
    And patients are available for search
    And I add the patient criteria for a last name that equals "Smith-Jones"
    When I search for patients
    Then search result 1 has a "first name" of "JoeEqual"
    And search result 1 has a "last name" of "Smith-Jones"
    And there are 1 patient search results

  Scenario: I can find a patient with a last name with multiple names that starts with a value
    Given the patient has the legal name "Samantha" "Smith"
    And I have another patient
    And the patient has the legal name "JoeEqual" "Smith Jones"
    And patients are available for search
    And I add the patient criteria for a last name that starts with "Smith J"
    When I search for patients
    Then search result 1 has a "first name" of "JoeEqual"
    And search result 1 has a "last name" of "Smith Jones"
    And there are 1 patient search results

  Scenario: I can find a patient with a last name with hyphens that starts with a value
    Given the patient has the legal name "Samantha" "Smith"
    And I have another patient
    And the patient has the legal name "JoeEqual" "Smith-Jones"
    And patients are available for search
    And I add the patient criteria for a last name that starts with "Smith J"
    When I search for patients
    Then search result 1 has a "first name" of "JoeEqual"
    And search result 1 has a "last name" of "Smith-Jones"
    And there are 1 patient search results

  Scenario: I can find a patient with a last name with multiple names that contains a value
    Given the patient has the legal name "Samantha" "Smith"
    And I have another patient
    And the patient has the legal name "JoeEqual" "Smith Jones"
    And patients are available for search
    And I add the patient criteria for a last name that contains "mith J"
    When I search for patients
    Then search result 1 has a "first name" of "JoeEqual"
    And search result 1 has a "last name" of "Smith Jones"
    And there are 1 patient search results

  Scenario: I can find a patient with a last name with hyphens that contains a value
    Given the patient has the legal name "Samantha" "Smith"
    And I have another patient
    And the patient has the legal name "JoeEqual" "Smith-Jones"
    And patients are available for search
    And I add the patient criteria for a last name that contains "mith j"
    When I search for patients
    Then search result 1 has a "first name" of "JoeEqual"
    And search result 1 has a "last name" of "Smith-Jones"
    And there are 1 patient search results

  Scenario: I can find a patient with a last name with multiple names that does not equal a value
    Given the patient has the legal name "JoeEqual" "Smith Jones"
    And patients are available for search
    And I add the patient criteria for a last name that does not equal "Smith Jones"
    When I search for patients
    Then there are 0 patient search results

  Scenario: I can find a patient with a last name with hyphens that does not equal a value
    Given the patient has the legal name "JoeEqual" "Smith-Jones"
    And patients are available for search
    And I add the patient criteria for a last name that does not equal "Smith-Jones"
    When I search for patients
    Then there are 0 patient search results
