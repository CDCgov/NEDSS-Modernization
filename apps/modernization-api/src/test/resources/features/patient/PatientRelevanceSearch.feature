@patient_relevance_search
Feature: Patient relevance search

  Background:
    Given there are 3 patients with soundex legal and secondary names
    And I am looking for one of them
    And I have the authorities: "FIND-PATIENT,VIEW-INVESTIGATION,VIEW-OBSERVATIONLABREPORT" for the jurisdiction: "ALL" and program area: "STD"
    And I have the authorities: "FIND-PATIENT,VIEW-INVESTIGATION,VIEW-OBSERVATIONLABREPORT" for the jurisdiction: "ALL" and program area: "ARBO"

  @patient_search_with__relevance_sorting
  Scenario: I can find the right patient when there are multiple ordered results
    When I search for patients sorted by "<search field>" "<qualifier>" "<sort field>" "<direction>"
    Then I find the patients sorted

    Examples:
      | search field         | qualifier | sort field | direction |
      | last name relevance  |           | relevance  | desc      |
      | first name relevance |           | relevance  | desc      |
