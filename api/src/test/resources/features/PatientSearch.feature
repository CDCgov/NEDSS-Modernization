@patient_search
Feature: Patient search

  Background: 
    Given there are 10 patients
    And I am looking for one of them
    And I have the authorities: "FIND-PATIENT,VIEW-INVESTIGATION,VIEW-OBSERVATIONLABREPORT" for the jurisdiction: "ALL" and program area: "STD"
    And I have the authorities: "FIND-PATIENT,VIEW-INVESTIGATION,VIEW-OBSERVATIONLABREPORT" for the jurisdiction: "ALL" and program area: "ARBO"

  @patient_data_search
  Scenario: I can find a Patient by patient data using one field
    When I search patients by "<field>" "<qualifier>"
    Then I find the patient

    Examples: 
      | field          | qualifier |
      | last name      |           |
      | first name     |           |
      | race           |           |
      | identification |           |
      | patient id     |           |
      | ssn            |           |
      | phone number   |           |
      | date of birth  | before    |
      | date of birth  | after     |
      | date of birth  | equal     |
      | gender         |           |
      | deceased       |           |
      | address        |           |
      | city           |           |
      | state          |           |
      | country        |           |
      | zip code       |           |
      | ethnicity      |           |
      | record status  |           |

  @patient_investigation_search
  Scenario: I can find a patient by one field in the investigation data
    Given Investigations exist
    When I search investigation events by "<field>" "<qualifier>"
    Then I find the patient

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

  @patient_lab_search
  Scenario: I can find a patient by one field in the laboratory report
    Given A lab report exist
    When I search laboratory events by "<field>" "<qualifier>"
    Then I find the patient

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
