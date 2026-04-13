Feature: Classic Data Entry

  Background:
    Given I am logged in as secure user and stay on classic

  Scenario: Create lab report and confirm association
    When I search for patient "Surma" "Singh"
    And I click on the Events tab
    And I check the Lab Report count
    And I click on Data Entry in the navigation bar
    And I click on Lab Report
    # We will run populatePatient in our window since Cypress cannot natively handle popups
    And I populate the page with patient Surma J Singh's information 
    And I click Next to navigate to the Lab Report tab
    And I search for Reporting Facility with Quick Code "2"
    And I select a random Program Area
    And I select a random Jursidiction
    And I populate Ordered Test with Measles virus Rubeola antigen
    And I select a random Specimen Source
    And I select a random Specimen Site
    And I select a random Resulted Test
    And I select a random Coded Result
    And I click the Add button under Resulted Tests
    And I click the submit button
    And I go to the Home page
    And I search for patient "Surma" "Singh"
    And I click on the Lab Report tab
    Then there should be one more Lab Report than before

Scenario: Create lab report with multiple results and confirm association
    And I click on Data Entry in the navigation bar
    And I click on Lab Report
    And I populate the page with patient Surma J Singh's information 
    And I click Next to navigate to the Lab Report tab
    And I search for Reporting Facility with Quick Code "2"
    And I select a random Program Area
    And I select a random Jursidiction
    And I populate Ordered Test with Measles virus Rubeola antigen
    And I select a random Specimen Source
    And I select a random Specimen Site
    And I select a random Resulted Test
    And I select a random Coded Result
    And I click the Add button under Resulted Tests
    And I select a random Resulted Test
    And I select a random Coded Result
    And I click the Add button under Resulted Tests
    And I click the submit button
    And I go to the Home page
    And I search for patient "Surma" "Singh"
    And I click on the Lab Report tab
    And the last Lab Report should have multiple resulted tests associated with it

 
  Scenario: Adding a Morbidity Report
    When I click on Data Entry in the navigation bar
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
    When I click on Data Entry in the navigation bar
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
    When I click on Data Entry in the navigation bar
    And I click on the Morbidity Report link
    And I enter patient first name "Homer" and last name "Simpson"
    And I click on the Report Information tab
    And I click the Submit button
    Then I should see validation errors for required fields


  Scenario: Verify missing condition prevents submission
    When I click on Data Entry in the navigation bar
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
    When I click on Data Entry in the navigation bar
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
    When I click on Data Entry in the navigation bar
    And I click on the Morbidity Report link
    And I enter patient first name "Homer" and last name "Simpson"
    And I click on the Report Information tab
    And I select "Brucellosis" from the Condition dropdown menu
    And I select "Dekalb County" from the Jurisdiction dropdown menu
    And I enter the current date in the Date of Morbidity Report field
    And I click on the Patient tab
    Then the patient first name field should contain "Homer"