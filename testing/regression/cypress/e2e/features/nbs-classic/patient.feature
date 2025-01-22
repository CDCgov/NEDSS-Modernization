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

  Scenario: Successfully Submitting a Completed New Patient Extended Form
    Given I am on the New patient Extended form
    And I have filled out all required fields in all sections
    When I click the Save button
    Then Form should be submitted successfully without errors
    And I should receive a confirmation message

  Scenario: Scenario: Valid "Information as of Date"
    Given I am on the New patient Extended form
    And I have filled out all Information as of Date field
    When I click the Save button
    Then Form should be submitted successfully without errors
    And I should receive a confirmation message

