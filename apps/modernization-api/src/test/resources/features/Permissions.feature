@permissions
Feature: User's search results are restricted by permissions

  Scenario Outline: Patient search methods are restricted by authority
    Given I have the authorities: "<authorities>" for the jurisdiction: "<jurisdiction>" and program area: "<programArea>"
    When I search for a patient by "<searchType>"
    Then a "<resultType>" result is returned

    Examples:
      | searchType        | authorities                            | programArea | jurisdiction | resultType            |
      | findInvestigation | FIND-PATIENT,VIEW-INVESTIGATION        | STD         | ALL          | success               |
      | findInvestigation | FIND-PATIENT                           | STD         | ALL          | AccessDeniedException |
      | findLabReport     | FIND-PATIENT,VIEW-OBSERVATIONLABREPORT | STD         | ALL          | success               |
      | findLabReport     | FIND-PATIENT                           | STD         | ALL          | AccessDeniedException |
