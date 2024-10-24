@patient @patient-search
Feature: Patient Search Randomized

  Background:
    Given I am logged into NBS
    And I can "find" any "patient"
    And I want patients sorted by "relevance" "desc"
    And there are 5 patients available for search
    And I am looking for one of them

  Scenario Outline: I can find a Patient by patient data using <field>
    Given I add the patient criteria "<field>" "<qualifier>"
    When I search for patients
    Then I find the patient

    Examples:
      | field            | qualifier |
      | last name        |           |
      | first name       |           |
      | race             |           |
      | patient short id |           |
      | phone number     |           |
      | birthday         | before    |
      | birthday         | after     |
      | birthday         | equal     |
      | birthday day     |           |
      | birthday month   |           |
      | birthday year    |           |
      | birthday low     |           |
      | birthday high    |           |
      | gender           |           |
      | deceased         |           |
      | address          |           |
      | city             |           |
      | state            |           |
      | country          |           |
      | zip code         |           |
      | ethnicity        |           |
      | email            |           |

  Scenario Outline: I can find a Patient by patient data using <field>, <field2>, and <field3> fields
    Given I add the patient criteria "<field>" "<qualifier>"
    And I add the patient criteria "<field2>" "<qualifier2>"
    And I add the patient criteria "<field3>" "<qualifier3>"
    When I search for patients
    Then I find the patient

    Examples:
      | field        | qualifier | field2     | qualifier2 | field3  | qualifier3 |
      | last name    |           | first name |            | city    |            |
      | first name   |           | deceased   |            | gender  |            |
      | race         |           | address    |            | city    |            |
      | deceased     |           | first name |            | city    |            |
      | phone number |           | ethnicity  |            | city    |            |
      | birthday     | before    | first name |            | city    |            |
      | birthday     | after     | last name  |            | address |            |
      | birthday     | equal     | first name |            | city    |            |
      | gender       |           | first name |            | city    |            |
      | deceased     |           | gender     |            | city    |            |
      | address      |           | first name |            | gender  |            |
      | city         |           | zip code   |            | state   |            |
      | state        |           | first name |            | city    |            |
      | country      |           | first name |            | gender  |            |
      | zip code     |           | first name |            | city    |            |
      | ethnicity    |           | first name |            | race    |            |

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
      | last name      | city       |
      | phone number   |            |
      | identification |            |

