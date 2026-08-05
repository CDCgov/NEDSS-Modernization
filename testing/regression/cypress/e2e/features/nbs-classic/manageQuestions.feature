Feature: Classic NBS - User can view and manage data in NBS Questions

  Background:
    Given I am logged in as secure user and stay on classic

  Scenario: Add new question LOCAL
    When Navigate to Question Library
    And Click on Add new in Question Library
    And Fill the details to create new "LOCAL" question
    And Click submit button to create question

  Scenario: Add new question PHIN
    When Navigate to Question Library
    And Click on Add new in Question Library
    And Fill the details to create new "PHIN" question
    And Click submit button to create question

  Scenario: Edit question
    When Navigate to Question Library
    And Click on a question in Question Library
    And Click submit button to create question
