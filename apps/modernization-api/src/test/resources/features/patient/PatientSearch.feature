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
      | field              | qualifier |
      | last name          |           |
      | last name soundex  |           |
      | first name         |           |
      | first name soundex |           |
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
      | email         |           |
      | identification|           |

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

  @patient_multi_data_partial_search
  Scenario: I can find a Patient by patient data using multiple partial fields
    When I search patients using partial data "<field>" "<qualifier>" "<field2>" "<qualifier2>"
    Then I find the patient

    Examples:
      | field       | qualifier | field2     | qualifier2 |
      | last name   |           |            |            |
      | first name  |           |            |            |
      | address     |           |            |            |
      | city        |           |            |            |
      | last name   |           | first name |            |
      | first name  |           | address    |            |
      | last name   |           | address    |            |
      | city        |           |            |            |
      | last name   |           | city       |            |
      | phone number|           |            |            |
      | ssn         |           |            |            |
      | identification|           |

  @patient_search_with_sorting
  Scenario: I can find the right patient when there are multiple ordered results
    When I search for patients sorted by "<search field>" "<qualifier>" "<sort field>" "<direction>"
    Then I find the patients sorted

    Examples:
      | search field  | qualifier | sort field | direction |
      | record status |           | lastNm     | asc       |
      | record status |           | lastNm     | desc      |
      | record status |           | birthTime  | asc       |
      | record status |           | birthTime  | desc      |

  @patient_search_with_trailing_space
  Scenario: When search criteria ends with a space, only the expected patients are returned
    When I search for a patient by "<field>" and there is a space at the end
    Then I find only the expected patient

    Examples:
      | field      |
      | first name |
      | last name  |
      | address    |
      | city       |

  @patient_search_record_status_deleted
  Scenario: I can find patients with deleted record status
    Given I have the authorities: "FINDINACTIVE-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And A deleted patient exists
    When I search for a record status of "LOG_DEL"
    Then I find patients with "LOG_DEL" record status

  @patient_search_record_status_active
  Scenario: I can find patients with active record status
    When I search for a record status of "ACTIVE"
    Then I find patients with "ACTIVE" record status

  @patient_search_record_status_deleted_invalid_permission
  Scenario: I cant search for deleted records without the correct permission
    Given A deleted patient exists
    When I search for a record status of "LOG_DEL"
    Then I dont have permissions to execute the search
