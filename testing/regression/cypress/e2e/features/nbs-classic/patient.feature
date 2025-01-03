Feature: Classic NBS - Dedupe - User can view data in NBS Patient Search Page

  Background:
    Given I am logged in as secure user and stay on classic

  Scenario: Search patient
    Then Navigate to classic Patient Search pane
    Then Enter Last Name in input text field "Simpson"
    Then Click on Search in classic Patient Search pane

  Scenario: Edit patient
    Then Select a patient to edit through classic search
    Then Edit patient details showing on the page