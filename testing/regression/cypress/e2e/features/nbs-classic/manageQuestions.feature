Feature: Classic NBS - User can view and manage data in NBS Questions

  Background:
    Given I am logged in as secure user and stay on classic

  Scenario: Add new question LOCAL
    Then Navigate to Question Library
    And Click on Add new in Question Library
    Then Fill the details to create new "LOCAL" question
    Then Click submit button to create question

  Scenario: Add new question PHIN
    Then Navigate to Question Library
    And Click on Add new in Question Library
    Then Fill the details to create new "PHIN" question
    Then Click submit button to create question

  Scenario: Edit question
    Then Navigate to Question Library
    And Click on a question in Question Library
    Then Click submit button to create question
