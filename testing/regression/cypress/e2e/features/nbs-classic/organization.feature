Feature: Classic NBS - Dedupe - User can view data in NBS Organization Search Page

  Background:
    Given I am logged in as secure user and stay on classic

  Scenario: Search organization
    Then Navigate to classic organization Search pane
    Then Enter organization name in input text field "test"
    Then Click on Search in classic organization Search pane
    Then View organization details through classic search