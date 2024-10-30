Feature: Classic Data Entry

  Background: 
    Given I am logged in as secure user

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

Scenario: Adding a Morbidity Report
    When I click on Data Entry in the menu bar
    And I click on the Morbidity Report link
    And I click on the Report Information tab
    And I select "Brucellosis" from the Condition dropdown menu
    And I select "Dekalb County" from the Jurisdiction dropdown menu
    And I enter the current date in the Date of Morbidity Report field
    And I enter "2" in the Facility and Provider Information field
    And I click on the Code Lookup button
    And I click the Submit button
    And I confirm the submission by clicking "Ok"