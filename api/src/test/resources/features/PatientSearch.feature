Feature: Patient search

  Background: 
    Given there are 10 patients
    And I am looking for one of them

  Scenario: I Can find a Patient by patient data using one field
    When I search patients by "<field>" "<qualifier>"
    Then I find the patient
    And I have the option to create a new patient

    Examples: 
      | field            | qualifier |
      | last name        |           |
      | last name        |           |
      | first name       |           |
      | patient id       |           |
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
      | mortality status |           |
      | ethnicity        |           |
      | record status    |           |
