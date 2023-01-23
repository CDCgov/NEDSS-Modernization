@investigation_search
Feature: Investigation search

  Background: 
    Given I have the authorities: "FIND-PATIENT,VIEW-INVESTIGATION" for the jurisdiction: "ALL" and program area: "STD"
    And I have the authorities: "FIND-PATIENT,VIEW-INVESTIGATION" for the jurisdiction: "ALL" and program area: "ARBO"

  @investigation_search_single_field
  Scenario: I can find an investigation by one field in the investigation data
    Given Investigations exist
    When I search investigation events by "<field>" "<qualifier>"
    Then I find the investigation

    Examples: 
      | field            | qualifier           |
      | condition        | Bacterial Vaginosis |
      | condition        | Trichomoniasis      |
      | program area     | STD                 |
      | program area     | ARBO                |
      | jurisdiction     | jd1                 |
      | jurisdiction     | jd2                 |
      | pregnancy status |                     |
      | event id         | ABCS_CASE_ID        |
      | event id         | CITY_COUNTY_CASE_ID |
      | event id         | INVESTIGATION_ID    |
      | event id         | NOTIFICATION_ID     |
      | event id         | STATE_CASE_ID       |
      | created by       |                     |
      | updated by       |                     |

  @investigation_search_multi_field
  Scenario: I can find an investigation using multiple fields in the investigation data
    Given Investigations exist
    When I search investigation events by "<field>" "<qualifier>" "<field2>" "<qualifier2>" "<field3>" "<qualifier3>"
    Then I find the investigation

    Examples: 
      | field            | qualifier           | field2       | qualifier2      | field3       | qualifier3   |
      | condition        | Bacterial Vaginosis | jurisdiction | jd2             | program area | STD          |
      | condition        | Trichomoniasis      | jurisdiction | jd1             | program area | ARBO         |
      | program area     | STD                 | jurisdiction | jd2             | event id     | ABCS_CASE_ID |
      | program area     | ARBO                | jurisdiction | jd1             | created by   |              |
      | jurisdiction     | jd1                 | event id     | NOTIFICATION_ID | program area | ARBO         |
      | jurisdiction     | jd2                 | event id     | ABCS_CASE_ID    | program area | STD          |
      | pregnancy status |                     | jurisdiction | jd1             | program area | ARBO         |
      | event id         | ABCS_CASE_ID        | jurisdiction | jd2             | program area | STD          |
      | event id         | CITY_COUNTY_CASE_ID | jurisdiction | jd2             | program area | STD          |
      | event id         | INVESTIGATION_ID    | jurisdiction | jd2             | program area | STD          |
      | event id         | NOTIFICATION_ID     | jurisdiction | jd1             | program area | ARBO         |
      | event id         | STATE_CASE_ID       | jurisdiction | jd1             | program area | ARBO         |
      | created by       |                     | jurisdiction | jd1             | program area | ARBO         |
      | updated by       |                     | jurisdiction | jd1             | program area | ARBO         |
