@patient-edit
Feature: Editing of Patient identification demographics

  Background:
    Given I am logged into NBS
    And I can "edit" any "patient"
    And I can "viewworkup" any "patient"
    And I have a patient

  Scenario: I can edit a patient to add an identification demographic
    Given I am entering an Account Number identification of "1234" as of 08/10/2025
    When I edit the patient with entered demographics
    And I view the patient's identification demographics
    Then the patient file identification demographics includes an Account Number of "1234" as of 08/10/2025

  Scenario: I can edit a patient to update an existing identification demographic
    Given I am entering an Account Number identification of "1234" as of 08/10/2025
    And I edit the patient with entered demographics
    And I want to change the patient's identifications
    And I select the entered identification that is as of 08/10/2025
    And I am entering a Person Number identification of "56789" as of 08/12/2025
    And I edit the patient with entered demographics
    When I view the patient's identification demographics
    Then the patient file identification demographics includes an Person Number of "56789" as of 08/12/2025

  Scenario: I can edit a patient to delete an existing identification demographic
    Given I am entering an Account Number identification of "4444" as of 08/11/2025
    And I edit the patient with entered demographics
    And I want to change the patient's identifications
    And I remove the entered identification with as of 08/11/2025
    And I edit the patient with entered demographics
    When I view the patient's identification demographics
    Then the patient file identification demographics does not include an entry with as of 08/11/2025