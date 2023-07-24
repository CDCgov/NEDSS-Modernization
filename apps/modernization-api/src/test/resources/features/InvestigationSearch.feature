@investigation_search
Feature: Investigation search

  Background: 
    Given I have the authorities: "FIND-PATIENT,VIEW-INVESTIGATION" for the jurisdiction: "ALL" and program area: "STD"
    And I have the authorities: "FIND-PATIENT,VIEW-INVESTIGATION" for the jurisdiction: "ALL" and program area: "ARBO"

  @investigation_status_field
  Scenario: I can find an investigation by different status fields
    Given An Investigation with "<field>" set to "<status>" exists
    When I search for an investigation with "<field>" of "<status>"
    Then I find investigations with "<field>" of "<status>"

    Examples: 
      | field              | status                 |
      | processingStatus   | UNASSIGNED             |
      | processingStatus   | AWAITING_INTERVIEW     |
      | processingStatus   | FIELD_FOLLOW_UP        |
      | processingStatus   | NO_FOLLOW_UP           |
      | processingStatus   | OPEN_CASE              |
      | processingStatus   | SURVEILLANCE_FOLLOW_UP |
      | notificationStatus | UNASSIGNED             |
      | notificationStatus | APPROVED               |
      | notificationStatus | COMPLETED              |
      | notificationStatus | MESSAGE_FAILED         |
      | notificationStatus | PENDING_APPROVAL       |
      | notificationStatus | REJECTED               |
      | caseStatus         | UNASSIGNED             |
      | caseStatus         | CONFIRMED              |
      | caseStatus         | NOT_A_CASE             |
      | caseStatus         | PROBABLE               |
      | caseStatus         | SUSPECT                |
      | caseStatus         | UNKNOWN                |

  @investigation_search_single_field
  Scenario: I can find an investigation by one field in the investigation data
    Given Investigations exist
    When I search investigation events by "<field>" "<qualifier>"
    Then I find the investigation

    Examples: 
      | field            | qualifier                 |
      | condition        | Bacterial VaginosisId     |
      | condition        | TrichomoniasisId          |
      | program area     | STD                       |
      | program area     | ARBO                      |
      | jurisdiction     | jd1                       |
      | jurisdiction     | jd2                       |
      | pregnancy status |                           |
      | event id         | ABCS_CASE_ID              |
      | event id         | CITY_COUNTY_CASE_ID       |
      | event id         | INVESTIGATION_ID          |
      | event id         | NOTIFICATION_ID           |
      | event id         | STATE_CASE_ID             |
      | created by       |                           |
      | updated by       |                           |
      | patient id       |                           |
      | event date       | DATE_OF_REPORT            |
      | event date       | INVESTIGATION_CLOSED_DATE |
      | event date       | INVESTIGATION_CREATE_DATE |
      | event date       | INVESTIGATION_START_DATE  |
      | event date       | LAST_UPDATE_DATE          |
      | event date       | NOTIFICATION_CREATE_DATE  |

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
      | patient id       |                     | jurisdiction | jd1             | program area | ARBO         |
