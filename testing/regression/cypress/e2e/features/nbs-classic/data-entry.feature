Feature: Classic Data Entry

  Background:
    Given I am logged in as secure user and stay on classic

  Scenario: Adding a Lab Report with detailed fields
    When I click on Data Entry in the menu bar
    And I click on Lab Report
    And I click on the Lab Report tab
    And I enter "2" in the Reporting Facility field
    And I click the Quick Code Lookup button
    And I check the "Same as Reporting Facility" checkbox
    And I select the third element in the Jurisdiction dropdown
    And I select the sixth element in the Resulted Test dropdown
    And I select the second element in the Coded Result dropdown
    And I enter "1" in the Numeric Result field
    And I select % for the Units field
    And I enter "automated test results" in the Text Result field
    And I click the add button to add the lab report
    And I click the submit button

  Scenario: Create comprehensive Morbidity Report with patient entry and verification
    When I click on Data Entry in the menu bar
    And I click on the Morbidity Report link
    And I enter patient first name "Homer" and last name "Simpson"
    And I click on the Report Information tab
    And I select "Brucellosis" from the Condition dropdown menu
    And I select "Dekalb County" from the Jurisdiction dropdown menu
    And I enter the current date in the Date of Morbidity Report field
    And I enter "2" in the Facility and Provider Information field
    And I click on the Code Lookup button
    And I click the Submit button
    And I confirm the submission by clicking "Ok"
    Then the morbidity report should be submitted successfully

  Scenario: Create Morbidity Report with different condition type
    When I click on Data Entry in the menu bar
    And I click on the Morbidity Report link
    And I enter patient first name "Homer" and last name "Simpson"
    And I click on the Report Information tab
    And I select "Acute flaccid myelitis" from the Condition dropdown menu
    And I select "Autauga County" from the Jurisdiction dropdown menu
    And I enter the current date in the Date of Morbidity Report field
    And I enter "2" in the Facility and Provider Information field
    And I click on the Code Lookup button
    And I click the Submit button
    And I confirm the submission by clicking "Ok"
    Then the morbidity report should be submitted successfully

  Scenario: Validate required fields when creating Morbidity Report
    When I click on Data Entry in the menu bar
    And I click on the Morbidity Report link
    And I enter patient first name "Homer" and last name "Simpson"
    And I click on the Report Information tab
    And I click the Submit button
    Then I should see validation errors for required fields

  Scenario: Verify missing condition prevents submission
    When I click on Data Entry in the menu bar
    And I click on the Morbidity Report link
    And I enter patient first name "Homer" and last name "Simpson"
    And I click on the Report Information tab
    And I select "Dekalb County" from the Jurisdiction dropdown menu
    And I enter the current date in the Date of Morbidity Report field
    And I enter "2" in the Facility and Provider Information field
    And I click on the Code Lookup button
    And I click the Submit button
    Then I should see a validation error for the Condition field

  Scenario: Verify missing jurisdiction prevents submission
    When I click on Data Entry in the menu bar
    And I click on the Morbidity Report link
    And I enter patient first name "Homer" and last name "Simpson"
    And I click on the Report Information tab
    And I select "Brucellosis" from the Condition dropdown menu
    And I clear the Jurisdiction field
    And I enter the current date in the Date of Morbidity Report field
    And I enter "2" in the Facility and Provider Information field
    And I click on the Code Lookup button
    And I click the Submit button
    Then I should see a validation error for the Jurisdiction field

  Scenario: Verify form state preservation after switching form tabs
    When I click on Data Entry in the menu bar
    And I click on the Morbidity Report link
    And I enter patient first name "Homer" and last name "Simpson"
    And I click on the Report Information tab
    And I select "Brucellosis" from the Condition dropdown menu
    And I select "Dekalb County" from the Jurisdiction dropdown menu
    And I enter the current date in the Date of Morbidity Report field
    And I click on the Patient tab
    Then the patient first name field should contain "Homer"
