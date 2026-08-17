Feature: Classic NBS - Dedupe - User can view data in NBS Providers

  Background:
    Given I am logged in as secure user and stay on classic

  Scenario: Add Provider
    When Navigate to classic provider search page
    And Enter last name "Pepper"
    And Enter first name "Doctor"
    And I click on the Submit button
    And Click on Add button on provider add page
    And Enter quick code for new provider
    And Click Submit button on provider add page
    And Navigate to classic provider search page
    And Enter last name "Pepper"
    And Enter first name "Doctor"
    And I click on the Submit button
    Then provider "Pepper", "Doctor" should appear in search results

  Scenario: Edit Provider
    When Navigate to classic provider search page
    And Enter last name "Pepper"
    And Enter first name "Doctor"
    And I click on the Submit button
    And I click View on the search results
    And Click Edit button on provider page
    And Check Edit Provider on the page
    And Click New Provider Edit Radio Option
    And Type new name for Edit Provider first name
    And Click Submit button on provider add page
    And Navigate to classic provider search page
    And Enter first name "TestFirstName"
    And I click on the Submit button
    Then provider "Pepper", "TestFirstName" should appear in search results
