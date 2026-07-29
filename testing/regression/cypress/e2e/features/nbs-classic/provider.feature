Feature: Classic NBS - Dedupe - User can view data in NBS Providers

  Background:
    Given I am logged in as secure user and stay on classic

  Scenario: Add Provider
    When Navigate to classic provider add page
    And Click on Add button on provider add page
    And Enter quick code for new provider
    And Click Submit button on provider add page

  Scenario: Edit Provider
    When Navigate to classic provider edit page
    And Click Edit button on provider page
    And Check Edit Provider on the page
    And Click New Provider Edit Radio Option
    And Type new name for Edit Provider first name
    And Click Submit button on provider add page
