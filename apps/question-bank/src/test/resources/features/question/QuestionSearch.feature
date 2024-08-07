@question_search
Feature: Question search

  Background:
    Given No questions exist
    Given A text question exists
    And A date question exists

  Scenario: I can get all questions
    Given I am an admin user
    When I get all questions
    Then questions are returned

  Scenario: I cannot get all questions without logging in
    Given I am not logged in
    When I get all questions
    Then a no credentials found exception is thrown

  Scenario: I cannot get all questions without proper permission
    Given I am a user without permissions
    When I get all questions
    Then an accessdenied exception is thrown

  Scenario: I can search for questions
    Given I am an admin user
    When I search for questions
    Then questions are returned

  Scenario: I can find questions by different fields
    Given I am an admin user
    When I search for a question by "<field>"
    Then I find the question

    Examples:
      | field    |
      | name     |
      | id       |
      | local id |
      | label    |

  Scenario: I cannot search without being logged in
    Given I am not logged in
    When I search for questions
    Then a no credentials found exception is thrown

  Scenario: I cannot search without proper permission
    Given I am a user without permissions
    When I search for questions
    Then an accessdenied exception is thrown

  Scenario: I can search for a specific question
    Given I am an admin user
    When I get a question
    Then the question is returned

  Scenario: I cannot search for a specific question without being logged in
    Given I am not logged in
    When I get a question
    Then a no credentials found exception is thrown

  Scenario: I cannot search for a specific question without proper permissions
    Given I am a user without permissions
    When I get a question
    Then an accessdenied exception is thrown
