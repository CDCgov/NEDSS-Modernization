@patient-edit
Feature: Editing of Patient name demographics

  Background:
    Given I am logged into NBS
    And I can "edit" any "patient"
    And I can "viewworkup" any "patient"
    And I have a patient

  Scenario: I can edit a patient to add a name demographic
    Given I am entering a legal name as of 08/01/2025
    And I enter the prefix Brother on the current name
    And I enter the first name "Jimmy" on the current name
    And I enter the middle name "S" on the current name
    And I enter the second middle name "Bob" on the current name
    And I enter the last name "Johns" on the current name
    And I enter the second last name "Scooter" on the current name
    And I enter the degree PhD on the current name
    When I edit the patient with entered demographics
    And I view the patient's name demographics
    Then the patient file name demographics as of 08/01/2025 contains the prefix Brother
    And the patient file name demographics as of 08/01/2025 contains the first name "Jimmy"
    And the patient file name demographics as of 08/01/2025 contains the middle name "S"
    And the patient file name demographics as of 08/01/2025 contains the second middle name "Bob"
    And the patient file name demographics as of 08/01/2025 contains the last name "Johns"
    And the patient file name demographics as of 08/01/2025 contains the second last name "Scooter"
    And the patient file name demographics as of 08/01/2025 contains the degree PhD

  Scenario: I can edit a patient to update an existing name demographic
    Given I am entering a legal name as of 08/02/2025
    And I enter the prefix Brother on the current name
    And I enter the first name "Billy" on the current name
    And I enter the middle name "J" on the current name
    And I enter the second middle name "Cruise" on the current name
    And I enter the last name "Something" on the current name
    And I enter the second last name "Another" on the current name
    And I enter the degree PhD on the current name
    And I edit the patient with entered demographics
    And I want to change the patient's names
    And I select the entered name that is as of 08/02/2025
    And I enter the prefix Bishop on the current name
    And I enter the first name "John" on the current name
    And I enter the middle name "Anthony" on the current name
    And I enter the second middle name "Stewart" on the current name
    And I enter the last name "Travis" on the current name
    And I enter the second last name "Chuck" on the current name
    And I enter the degree DDS on the current name
    When I edit the patient with entered demographics
    And I view the patient's name demographics
    Then the patient file name demographics as of 08/02/2025 contains the prefix Bishop
    And the patient file name demographics as of 08/02/2025 contains the first name "John"
    And the patient file name demographics as of 08/02/2025 contains the middle name "Anthony"
    And the patient file name demographics as of 08/02/2025 contains the second middle name "Stewart"
    And the patient file name demographics as of 08/02/2025 contains the last name "Travis"
    And the patient file name demographics as of 08/02/2025 contains the second last name "Chuck"
    And the patient file name demographics as of 08/02/2025 contains the degree DDS

  Scenario: I can edit a patient to remove an existing name demographic
    Given I am entering a legal name as of 08/03/2025
    And I enter the prefix Brother on the current name
    And I enter the first name "Bubba" on the current name
    And I enter the middle name "B" on the current name
    And I enter the second last name "Beaux" on the current name
    And I edit the patient with entered demographics
    And I want to change the patient's names
    And I remove the entered name as of 08/03/2025
    When I edit the patient with entered demographics
    And I view the patient's name demographics
    Then the patient file name demographics does not include a name as of 08/03/2025
