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

  Scenario: Missing Address Information
    Given I am on the New patient Extended form
    And I have not filled out all Address input fields
    Then Error message should appear right above Address fields

  Scenario: Valid Dropdown Selections
    Given I am on the New patient Extended form
    And I have filled out Dropdowns fields
    When I click the Save button
    Then Form should be submitted successfully without errors
    And I should receive a confirmation message

  Scenario: Valid Phone Number
    Given I am on the New patient Extended form
    And I enter a valid phone number in the Phone field
    Then I click the Save button
    Then Form should be submitted successfully without errors
    And I should receive a confirmation message

  Scenario: Missing Required Dropdown Selections
    Given I am on the New patient Extended form
    And I have not filled out Dropdowns fields
    Then Error message should appear right above dropdown fields

  Scenario: Invalid Identification
    Given I am on the New patient Extended form
    And I click Add Identification Button
    Then Error section "identifications" with error "Type is required."
    Then Error section "identifications" with error "ID value is required."

  Scenario: Add Valid Phone Number
    Given I am on the New patient Extended form
    Then I add type and use for phone
    Then I click Add Phone and Email Button

  Scenario: Add Valid Identification
    Given I am on the New patient Extended form
    Then Select section "identifications" with id "identification-type" option "Medicaid number"
    Then Type section "identifications" with id "id" with text "23123"
    And I click Add Identification Button

  Scenario: Adding a Lab Report After Creating a New Patient
    Given I have successfully added a new patient
    And Add Patient Success modal is displayed
    When I click the Add lab report button
    Then I should be redirected to the Add Lab Report form
    When I enter a valid Reporting Facility
    And I select a valid Program Area
    And I select a valid Jurisdiction
    And I select a valid Resulted Test and fill in the details
    When I click the Submit button in Report form
    Then I should see the patients profile displayed with the added lab report

  Scenario: Valid Mortality Information
    Given I am on the New patient Extended form
    And I select yes to Is the patient deceased
    And I complete the Mortality fields
    When I click the Save button
    Then Form should be submitted successfully without errors
    And I should receive a confirmation message

  Scenario: Adding an Investigation After Creating a New Patient
    Given I have successfully added a new patient
    And Add Patient Success modal is displayed
    When I click the Add investigation button
    Then I should be redirected to the Add Investigation form
    When I select a valid Condition
    And I select a valid Jurisdiction in investigation form
    When I click the Submit button in Add Investigation Form
    Then I should see a success message indicating that the investigation has been added successfully

  Scenario: Searching for a patient by Last Name using "Starts with"
    Given I am on the modernized Patient Search page
    When I select Starts with for Last Name
    And I enter a partial Last Name "simpson"
    And Click on Search in Patient Search pane
    Then the system should return patients whose Last Name starts with the entered value

  Scenario: Searching for a patient by Last Name using "Contains"
    Given I am on the modernized Patient Search page
    When I select Contains for Last Name
    And I enter a partial Last Name "simpson"
    And Click on Search in Patient Search pane
    Then the system should return patients whose Last Name contains the entered value

