Feature: Patient Search

  Scenario: I Can find a Patient by patient data using one field
    Given there are 10 patients
    And I am looking for one of them
    When I search patients by "<field>" "<value>" "<qualifier>"
    Then I find the patient
    And I have the option to create a new patient

    Examples: 
      | field     | value | qualifier |
      | lastName  | Doe   |           |
      | firstName | John  |           |
