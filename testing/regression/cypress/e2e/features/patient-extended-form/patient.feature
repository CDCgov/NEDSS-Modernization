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

  Scenario: Valid "Information as of Date"
    Given I am on the New patient Extended form
    And I have filled out all Information as of Date field
    When I click the Save button
    Then Form should be submitted successfully without errors
    And I should receive a confirmation message

  Scenario: Invalid "Information as of Date"
    Given I am on the New patient Extended form
    And I have filled out future date in Information as of Date field
    Then Error message should appear right above Information as of Date field

  Scenario: Comments Text Field Accepts Text Up to 2000 Characters
    Given I am on the New patient Extended form
    And I have filled out text in Comments field up to 2000 characters
    When I click the Save button
    Then Form should be submitted successfully without errors
    And I should receive a confirmation message

  Scenario: Error message if Comments Text field over 2000 Characters and no error with optional comment
    Given I am on the New patient Extended form
    And I have filled out invalid text in Comments field
    Then Error message should appear right above Comments field
    Then I clear Comments sections field
    Then I click the Save button
    Then Form should be submitted successfully without errors

  Scenario: Valid Name Input
    Given I am on the New patient Extended form
    When I enter a valid name in the First and Last name fields
    When I click the Save button
    Then Form should be submitted successfully without errors
    And I should receive a confirmation message

Scenario: Required Name Fields
    Given I am on the New patient Extended form
    When I leave the Type field empty
    Then the system should display an error message indicating that the field is required

  Scenario: Valid Address Input
    Given I am on the New patient Extended form
    And I have filled out Address input fields
    When I click the Save button
    Then Form should be submitted successfully without errors
    And I should receive a confirmation message

