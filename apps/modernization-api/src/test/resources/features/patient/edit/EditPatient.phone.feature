@patient-edit
Feature: Editing of Patient phone demographics

  Background:
    Given I am logged into NBS
    And I can "edit" any "patient"
    And I can "viewworkup" any "patient"
    And I have a patient
  
  Scenario: I can edit a patient to add a phone demographic
    Given I am entering the Phone - Home number of "123-456-7890" as of 08/07/2025
    When I edit the patient with entered demographics
    And I view the patient's phone demographics
    Then the patient file phone demographics includes a Phone - Home number of "123-456-7890" as of 08/07/2025
  
  Scenario: I can edit a patient to add an email demographic
    Given I am entering the Email Address - Home email address of "BubbaB@Beaux.com" as of 08/01/2025
    When I edit the patient with entered demographics
    And I view the patient's phone demographics
    Then the patient file phone demographics includes an Email Address - Home email address of "BubbaB@Beaux.com" as of 08/01/2025


  Scenario: I can edit a patient to update a phone demographic
    Given I am entering the Phone - Home number of "123-456-7890" as of 08/07/2025
    And I edit the patient with entered demographics
    And I want to change the patient's phone numbers
    And I select the entered phone that is as of 08/07/2025
    And I am entering the Fax - Mobile Contact number of "888-888-8888" as of 08/08/2025
    And I edit the patient with entered demographics
    When I view the patient's phone demographics
    Then the patient file phone demographics includes a Fax - Mobile Contact number of "888-888-8888" as of 08/08/2025


  Scenario: I can edit a patient to remove a phone demographic
    Given I am entering the Phone - Home number of "222-333-4444" as of 08/09/2025
    And I edit the patient with entered demographics
    And I want to change the patient's phone numbers
    And I remove the entered phone as of 08/09/2025
    And I edit the patient with entered demographics
    When I view the patient's phone demographics
    Then the patient file phone demographics does not include an entry with as of 08/09/2025
