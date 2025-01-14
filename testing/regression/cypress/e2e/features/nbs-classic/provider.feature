Feature: Classic NBS - Dedupe - User can view data in NBS Providers

  Background:
    Given I am logged in as secure user and stay on classic

  Scenario: Add Provider
    Then Navigate to classic provider add page
    Then Click on Add button on provider add page
    Then Enter quick code for new provider
    Then Click Submit button on provider add page

  Scenario: Edit Provider
    Then Navigate to classic provider edit page
    Then Click Edit button on provider page
    Then Check Edit Provider on the page
    Then Click New Provider Edit Radio Option
    Then Type new name for Edit Provider first name
    Then Click Submit button on provider add page
    