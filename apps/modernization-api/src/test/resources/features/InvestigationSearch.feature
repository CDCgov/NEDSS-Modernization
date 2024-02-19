@investigation_search
Feature: Investigation search

  Background:
    Given I am logged in
    And I can "FIND" any "PATIENT"
    And I can "VIEW" any "INVESTIGATION" for "STD" within all jurisdictions
    And I can "VIEW" any "INVESTIGATION" for "ARBO" within all jurisdictions
  @investigation_status_field
  Scenario Outline: I can find an investigation by different status fields
    Given An Investigation with a "<status>" status of "<value>" exists
    When I search for an investigation with a "<status>" status of "<value>"
    Then I find investigations with only a "<status>" status of "<value>"

    Examples:
      | status       | value                  |
      | processing   | UNASSIGNED             |
      | processing   | AWAITING_INTERVIEW     |
      | processing   | FIELD_FOLLOW_UP        |
      | processing   | NO_FOLLOW_UP           |
      | processing   | OPEN_CASE              |
      | processing   | SURVEILLANCE_FOLLOW_UP |
      | notification | UNASSIGNED             |
      | notification | APPROVED               |
      | notification | COMPLETED              |
      | notification | MESSAGE_FAILED         |
      | notification | PENDING_APPROVAL       |
      | notification | REJECTED               |
      | case         | UNASSIGNED             |
      | case         | CONFIRMED              |
      | case         | NOT_A_CASE             |
      | case         | PROBABLE               |
      | case         | SUSPECT                |
      | case         | UNKNOWN                |

  @investigation_search_single_field
  Scenario Outline: I can find an investigation by one field in the investigation data
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
  Scenario Outline: I can find an investigation using multiple fields in the investigation data
    Given Investigations exist
    When I search investigation events by "<field>" "<qualifier>" "<field2>" "<qualifier2>" "<field3>" "<qualifier3>"
    Then I find the investigation

    Examples:
      | field            | qualifier             | field2       | qualifier2      | field3       | qualifier3   |
      | condition        | Bacterial VaginosisId | jurisdiction | jd2             | program area | STD          |
      | condition        | TrichomoniasisId      | jurisdiction | jd1             | program area | ARBO         |
      | program area     | STD                   | jurisdiction | jd2             | event id     | ABCS_CASE_ID |
      | program area     | ARBO                  | jurisdiction | jd1             | created by   |              |
      | jurisdiction     | jd1                   | event id     | NOTIFICATION_ID | program area | ARBO         |
      | jurisdiction     | jd2                   | event id     | ABCS_CASE_ID    | program area | STD          |
      | pregnancy status |                       | jurisdiction | jd1             | program area | ARBO         |
      | event id         | ABCS_CASE_ID          | jurisdiction | jd2             | program area | STD          |
      | event id         | CITY_COUNTY_CASE_ID   | jurisdiction | jd2             | program area | STD          |
      | event id         | INVESTIGATION_ID      | jurisdiction | jd2             | program area | STD          |
      | event id         | NOTIFICATION_ID       | jurisdiction | jd1             | program area | ARBO         |
      | event id         | STATE_CASE_ID         | jurisdiction | jd1             | program area | ARBO         |
      | created by       |                       | jurisdiction | jd1             | program area | ARBO         |
      | updated by       |                       | jurisdiction | jd1             | program area | ARBO         |
      | patient id       |                       | jurisdiction | jd1             | program area | ARBO         |
