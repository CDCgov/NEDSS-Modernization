@lab_report_search
Feature: Lab report search

  Background:
    Given I am logged in
    And I can "FIND" any "PATIENT"
    And I can "VIEW" any "OBSERVATIONLABREPORT" for "STD" within all jurisdictions
    And I can "VIEW" any "OBSERVATIONLABREPORT" for "ARBO" within all jurisdictions

  @lab_report_search_single_field
  Scenario Outline: I can find a lab report by one field in the laboratory report
    Given A lab report exist
    When I search laboratory events by "<field>" "<qualifier>"
    Then I find the lab report

    Examples:
      | field             | qualifier                      |
      | program area      |                                |
      | jurisdiction      |                                |
      | pregnancy status  |                                |
      | event id          | accession number               |
      | event id          | lab id                         |
      | event date        | DATE_OF_REPORT                 |
      | event date        | DATE_RECEIVED_BY_PUBLIC_HEALTH |
      | event date        | DATE_OF_SPECIMEN_COLLECTION    |
      | event date        | LAB_REPORT_CREATE_DATE         |
      | event date        | LAST_UPDATE_DATE               |
      | entry method      |                                |
      | entered by        |                                |
      | event status      |                                |
      | processing status |                                |
      | created by        |                                |
      | last updated by   |                                |
      | provider search   | ORDERING_FACILITY              |
      | provider search   | ORDERING_PROVIDER              |
      | provider search   | REPORTING_FACILITY             |
      | resulted test     |                                |
      | coded result      |                                |
      | patient id        |                                |

  @lab_report_search_multi_field
  Scenario Outline: I can find a lab report by many fields in the laboratory report
    Given A lab report exist
    When I search laboratory events by "<field>" "<qualifier>" "<field2>" "<qualifier2>" "<field3>" "<qualifier3>"
    Then I find the lab report

    Examples:
      | field             | qualifier                      | field2        | qualifier2 | field3       | qualifier3 |
      | program area      |                                | jurisdiction  |            | event id     | lab id     |
      | jurisdiction      |                                | event status  |            | event id     | lab id     |
      | pregnancy status  |                                | jurisdiction  |            | event id     | lab id     |
      | event id          | accession number               | jurisdiction  |            | entry method |            |
      | event id          | lab id                         | jurisdiction  |            | entry method |            |
      | event date        | DATE_OF_REPORT                 | jurisdiction  |            | event id     | lab id     |
      | event date        | DATE_RECEIVED_BY_PUBLIC_HEALTH | jurisdiction  |            | event id     | lab id     |
      | event date        | DATE_OF_SPECIMEN_COLLECTION    | jurisdiction  |            | event id     | lab id     |
      | event date        | LAB_REPORT_CREATE_DATE         | jurisdiction  |            | event id     | lab id     |
      | event date        | LAST_UPDATE_DATE               | jurisdiction  |            | event id     | lab id     |
      | entry method      |                                | jurisdiction  |            | event id     | lab id     |
      | entered by        |                                | jurisdiction  |            | event id     | lab id     |
      | event status      |                                | resulted test |            | event id     | lab id     |
      | processing status |                                | jurisdiction  |            | event id     | lab id     |
      | created by        |                                | jurisdiction  |            | event id     | lab id     |
      | last updated by   |                                | jurisdiction  |            | event id     | lab id     |
      | provider search   | ORDERING_FACILITY              | jurisdiction  |            | event id     | lab id     |
      | provider search   | ORDERING_PROVIDER              | jurisdiction  |            | event id     | lab id     |
      | provider search   | REPORTING_FACILITY             | jurisdiction  |            | event id     | lab id     |
      | resulted test     |                                | jurisdiction  |            | event id     | lab id     |
      | coded result      |                                | jurisdiction  |            | event id     | lab id     |
      | patient id        |                                | jurisdiction  |            | event id     | lab id     |
