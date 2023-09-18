@patient_search
Feature: Patient search

  Background:
    Given I am logged into NBS
    And I can "find" any "patient"
    And I want patients sorted by "relevance" "desc"
    And there are 5 patients available for search
    And I am looking for one of them

  Scenario Outline: I can find a Patient by patient data using one field
    Given I add the patient criteria "<field>" "<qualifier>"
    When I search for patients
    Then I find the patient

    Examples:
      | field            | qualifier |
      | last name        |           |
      | first name       |           |
      | race             |           |
      | patient id       |           |
      | patient short id |           |
      | ssn              |           |
      | phone number     |           |
      | date of birth    | before    |
      | date of birth    | after     |
      | date of birth    | equal     |
      | gender           |           |
      | deceased         |           |
      | address          |           |
      | city             |           |
      | state            |           |
      | country          |           |
      | zip code         |           |
      | ethnicity        |           |
      | record status    |           |
      | email            |           |
      | identification   |           |

  Scenario Outline: I can find a Patient by patient data using multiple fields
    Given I add the patient criteria "<field>" "<qualifier>"
    And I add the patient criteria "<field2>" "<qualifier2>"
    And I add the patient criteria "<field3>" "<qualifier3>"
    When I search for patients
    Then I find the patient

    Examples:
      | field            | qualifier | field2     | qualifier2 | field3  | qualifier3 |
      | last name        |           | first name |            | city    |            |
      | first name       |           | ssn        |            | gender  |            |
      | race             |           | address    |            | city    |            |
      | patient id       |           | first name |            | city    |            |
      | patient short id |           | first name |            | city    |            |
      | ssn              |           | first name |            | city    |            |
      | phone number     |           | ethnicity  |            | city    |            |
      | date of birth    | before    | first name |            | city    |            |
      | date of birth    | after     | last name  |            | address |            |
      | date of birth    | equal     | first name |            | city    |            |
      | gender           |           | first name |            | city    |            |
      | deceased         |           | ssn        |            | city    |            |
      | address          |           | first name |            | ssn     |            |
      | city             |           | zip code   |            | state   |            |
      | state            |           | first name |            | city    |            |
      | country          |           | first name |            | gender  |            |
      | zip code         |           | first name |            | city    |            |
      | ethnicity        |           | first name |            | race    |            |
      | record status    |           | first name |            | city    |            |

  Scenario Outline: I can find a Patient by patient data using multiple partial fields
    Given I add the partial patient criteria "<field>"
    And I add the partial patient criteria "<field2>"
    When I search for patients
    Then I find the patient

    Examples:
      | field          | field2     |
      | last name      |            |
      | first name     |            |
      | address        |            |
      | city           |            |
      | last name      | first name |
      | first name     | address    |
      | last name      | address    |
      | city           |            |
      | last name      | city       |
      | phone number   |            |
      | ssn            |            |
      | identification |            |

  Scenario Outline: I can find the right patient when there are multiple ordered results
    Given I add the patient criteria "<search field>" "<qualifier>"
    And I want patients sorted by "<sort field>" "<direction>"
    When I search for patients
    Then I find the patients sorted

    Examples:
      | search field  | qualifier | sort field | direction |
      | record status |           | last name  | asc       |
      | record status |           | last name  | desc      |
      | record status |           | birthday   | asc       |
      | record status |           | birthday   | desc      |

  Scenario Outline: When search criteria ends with a space, only the expected patients are returned
    Given I add the patient criteria "<field>" with a space
    When I search for patients
    Then I find only the expected patient when searching by "<field>"

    Examples:
      | field      |
      | first name |
      | last name  |
      | address    |
      | city       |

  Scenario: I can find patients with active record status
    Given I would like patients that are "active"
    When I search for patients
    Then I find "active" patients

  Scenario: I can find patients with deleted record status
    Given I can "findInactive" any "patient"
    And I have a patient
    And the patient is inactive
    And the patient is searchable
    And I would like patients that are "deleted"
    When I search for patients
    Then I find "deleted" patients

  Scenario: I cant search for deleted records without the correct permission
    Given I would like patients that are "deleted"
    When I search for patients
    Then I am not able to execute the search
