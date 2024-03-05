@lab_report_search
Feature: Lab report search

  Background:
    Given I am logged into NBS
    And I can "find" any "patient"
    And I can "view" any "ObservationLabReport" for "STD" within all jurisdictions
    And I can "view" any "ObservationLabReport" for "ARBO" within all jurisdictions
    And I have a patient

  Scenario Outline: I can find a lab report by one field in the laboratory report
    Given A lab report exist
    And I add the lab report criteria for "<field>"
    When I search for lab reports
    Then I find the lab report

    Examples:
      | field                          |
      | program area                   |
      | jurisdiction                   |
      | pregnancy status               |
      | accession number               |
      | lab id                         |
      | date of report                 |
      | Date Received by Public Health |
      | Date of specimen collection    |
      | lab report create date         |
      | lab report update date         |
      | entry method                   |
      | entered by                     |
      | event status                   |
      | processing status              |
      | created by                     |
      | last updated by                |
      | Ordering Facility              |
      | Ordering Provider              |
      | Reporting Facility             |
      | resulted test                  |
      | coded result                   |
      | patient id                     |

  Scenario Outline: I can find a lab report by many fields in the laboratory report
    Given A lab report exist
    And I add the lab report criteria for "<field>"
    And I add the lab report criteria for "<field2>"
    And I add the lab report criteria for "<field3>"
    When I search for lab reports
    Then I find the lab report

    Examples:
      | field                          | field2        | field3       |
      | program area                   | jurisdiction  | lab id       |
      | jurisdiction                   | event status  | lab id       |
      | pregnancy status               | jurisdiction  | lab id       |
      | accession number               | jurisdiction  | entry method |
      | lab id                         | jurisdiction  | entry method |
      | Date of Report                 | jurisdiction  | lab id       |
      | Date Received by Public Health | jurisdiction  | lab id       |
      | Date of specimen collection    | jurisdiction  | lab id       |
      | lab report create date         | jurisdiction  | lab id       |
      | lab report update date         | jurisdiction  | lab id       |
      | entry method                   | jurisdiction  | lab id       |
      | entered by                     | jurisdiction  | lab id       |
      | event status                   | resulted test | lab id       |
      | processing status              | jurisdiction  | lab id       |
      | created by                     | jurisdiction  | lab id       |
      | last updated by                | jurisdiction  | lab id       |
      | Ordering Facility              | jurisdiction  | lab id       |
      | Ordering Provider              | jurisdiction  | lab id       |
      | Reporting Facility             | jurisdiction  | lab id       |
      | resulted test                  | jurisdiction  | lab id       |
      | coded result                   | jurisdiction  | lab id       |
      | patient id                     | jurisdiction  | lab id       |
