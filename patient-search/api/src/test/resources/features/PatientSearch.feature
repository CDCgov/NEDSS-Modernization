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
      | field         | qualifier |
      | last name     |           |
      | first name    |           |
      | race          |           |
      | patient id    |           |
      | ssn           |           |
      | phone number  |           |
      | date of birth | before    |
      | date of birth | after     |
      | date of birth | equal     |
      | gender        |           |
      | deceased      |           |
      | address       |           |
      | city          |           |
      | state         |           |
      | country       |           |
      | zip code      |           |
      | ethnicity     |           |
      | record status |           |

  @patient_multi_data_search
  Scenario: I can find a Patient by patient data using multiple fields
    When I search patients by "<field>" "<qualifier>" "<field2>" "<qualifier2>" "<field3>" "<qualifier3>"
    Then I find the patient

    Examples: 
      | field         | qualifier | field2     | qualifier2 | field3  | qualifier3 |
      | last name     |           | first name |            | city    |            |
      | first name    |           | ssn        |            | gender  |            |
      | race          |           | address    |            | city    |            |
      | patient id    |           | first name |            | city    |            |
      | ssn           |           | first name |            | city    |            |
      | phone number  |           | ethnicity  |            | city    |            |
      | date of birth | before    | first name |            | city    |            |
      | date of birth | after     | last name  |            | address |            |
      | date of birth | equal     | first name |            | city    |            |
      | gender        |           | first name |            | city    |            |
      | deceased      |           | ssn        |            | city    |            |
      | address       |           | first name |            | ssn     |            |
      | city          |           | zip code   |            | state   |            |
      | state         |           | first name |            | city    |            |
      | country       |           | first name |            | gender  |            |
      | zip code      |           | first name |            | city    |            |
      | ethnicity     |           | first name |            | race    |            |
      | record status |           | first name |            | city    |            |

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

  @patient_multi_investigation_search
  Scenario: I can find a patient using multiple fields in the investigation data
    Given Investigations exist
    When I search investigation events by "<field>" "<qualifier>" "<field2>" "<qualifier2>" "<field3>" "<qualifier3>"
    Then I find the patient

    Examples: 
      | field            | qualifier           | field2       | qualifier2   | field3       | qualifier3   |
      | condition        | Bacterial Vaginosis | jurisdiction | jd1          | program area | STD          |
      | condition        | Trichomoniasis      | jurisdiction | jd1          | program area | STD          |
      | program area     | STD                 | jurisdiction | jd1          | event id     | ABCS_CASE_ID |
      | program area     | ARBO                | jurisdiction | jd1          | created by   |              |
      | jurisdiction     | jd1                 | event id     | ABCS_CASE_ID | program area | STD          |
      | jurisdiction     | jd2                 | event id     | ABCS_CASE_ID | program area | STD          |
      | pregnancy status |                     | jurisdiction | jd1          | program area | STD          |
      | event id         | ABCS_CASE_ID        | jurisdiction | jd1          | program area | STD          |
      | event id         | CITY_COUNTY_CASE_ID | jurisdiction | jd1          | program area | STD          |
      | event id         | INVESTIGATION_ID    | jurisdiction | jd1          | program area | STD          |
      | event id         | NOTIFICATION_ID     | jurisdiction | jd1          | program area | STD          |
      | event id         | STATE_CASE_ID       | jurisdiction | jd1          | program area | STD          |
      | created by       |                     | jurisdiction | jd1          | program area | STD          |
      | updated by       |                     | jurisdiction | jd1          | program area | STD          |

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

  @patient_multi_lab_search
  Scenario: I can find a patient by many fields in the laboratory report
    Given A lab report exist
    When I search laboratory events by "<field>" "<qualifier>" "<field2>" "<qualifier2>" "<field3>" "<qualifier3>"
    Then I find the patient

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
