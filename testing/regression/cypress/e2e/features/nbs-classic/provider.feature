Feature: Classic NBS - Dedupe - User can view data in NBS Providers

  Background:
    Given I am logged in as secure user and stay on classic

  Scenario: Add Provider
    Then Navigate to classic provider add page
    Then Click on Add button on provider add page
    Then Enter quick code for new provider
    Then Click Submit button on provider add page
