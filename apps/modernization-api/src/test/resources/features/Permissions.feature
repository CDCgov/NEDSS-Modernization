@permissions
Feature: User's search results are restricted by permissions

  Scenario: Patient search methods are restricted by authority
    Given I have the authorities: "<authorities>" for the jurisdiction: "<jurisdiction>" and program area: "<programArea>"
    When I search for a patient by "<searchType>"
    Then a "<resultType>" result is returned

    Examples:
      | searchType                 | authorities                            | programArea | jurisdiction | resultType            |
      | findPatientsByFilter       | FIND-PATIENT                           | STD         | ALL          | success               |
      | findPatientsByFilter       |                                        | STD         | ALL          | AccessDeniedException |
      | findInvestigation          | FIND-PATIENT,VIEW-INVESTIGATION        | STD         | ALL          | success               |
      | findInvestigation          | FIND-PATIENT                           | STD         | ALL          | AccessDeniedException |
      | findLabReport              | FIND-PATIENT,VIEW-OBSERVATIONLABREPORT | STD         | ALL          | success               |
      | findLabReport              | FIND-PATIENT                           | STD         | ALL          | AccessDeniedException |
      | findPatientsByOrganization | FIND-PATIENT                           | STD         | ALL          | success               |
      | findPatientsByOrganization |                                        | STD         | ALL          | AccessDeniedException |
      | findAllPatients            | FIND-PATIENT                           | STD         | ALL          | success               |
      | findAllPatients            |                                        | STD         | ALL          | AccessDeniedException |
      | findPatientById            | FIND-PATIENT                           | STD         | ALL          | success               |
      | findPatientById            |                                        | STD         | ALL          | AccessDeniedException |
