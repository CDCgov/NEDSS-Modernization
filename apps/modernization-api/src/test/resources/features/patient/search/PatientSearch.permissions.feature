@patient @patient-search
Feature: Patient Search Permission

  Scenario: I cannot search for Patients without permission
    Given I am logged into NBS
    And I want patients sorted by "relevance" "desc"
    When I search for patients
    Then the Patient Search Results are not accessible
