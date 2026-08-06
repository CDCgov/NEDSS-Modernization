Feature: Patient File - Event Management - Morbidity Report

  Background:
    Given I am logged in as secure user
    And the user navigate to the patient profile page for "63000"
    And Click Events tab on Patient Profile Page

    Scenario: Add new morbidity report without investigation
        When I check the current count of "Morbidity reports" in the Events tab
        And user clicks on the "Add morbidity report" button within the Events tab
        And I select "Botulism, foodborne" from the Condition dropdown menu
        And I select "Cobb County" from the Jurisdiction dropdown menu
        And I enter the current date in the Date of Morbidity Report field
        And I enter "2" in the Facility and Provider Information field
        And I click on the Code Lookup button
        And I select "No" from the Pregnant dropdown menu
        And I click the Submit button
        And Click Events tab on Patient Profile Page
        Then the "Morbidity reports" count should increase by 1 in the Events tab
    
    Scenario: Add new morbidity report and create investigation
        When I check the current count of "Investigations" in the Events tab
        And user clicks on the "Add morbidity report" button within the Events tab
        And I select "Botulism, foodborne" from the Condition dropdown menu
        And I select "Cobb County" from the Jurisdiction dropdown menu
        And I enter the current date in the Date of Morbidity Report field
        And I enter "2" in the Facility and Provider Information field
        And I click on the Code Lookup button
        And I select "No" from the Pregnant dropdown menu
        And I click the Submit and Create Investigation button
        And I click the Submit button
        And user clicks the "View File" link, the user is returned to Patient profile summary page
        And Click Events tab on Patient Profile Page
        Then the "Investigations" count should increase by 1 in the Events tab

    Scenario: Cancel creation of a new morbidity report
        When I check the current count of "Morbidity reports" in the Events tab
        And user clicks on the "Add morbidity report" button within the Events tab
        And I select "Botulism, foodborne" from the Condition dropdown menu
        And I click the Cancel button on the Morbidity Report page
        And I confirm the submission by clicking "Ok"
        And I click the Cancel button on the Morbidity Report page
        Then the "Morbidity reports" count should remain the same in the Events tab

    Scenario: Edit existing morbidity report
        When I count the number of treatments in the first morbidity report
        And I click on the first morbidity report link in the Events tab and store its Event ID
        And I click the Edit button on the Morbidity Report page
        And I enter the current date in the Treatment Date field
        And I select "Acyclovir, 800 mg, PO, BID, x 5 days" from the Treatment dropdown menu
        And I click the Add Treatment button
        And I click the Submit button
        And user clicks the "View File" link, the user is returned to Patient profile summary page
        And Click Events tab on Patient Profile Page
        Then the treatment count should increase by 1 in the stored morbidity report
   
    Scenario: Print morbidity report
        When I click on the first morbidity report link in the Events tab and store its Event ID
        Then clicking the Print button should display the print preview

    Scenario: Transfer ownership of morbidity report
        When I click on the first morbidity report link in the Events tab and store its Event ID
        And I click the Transfer Ownership button on the Morbidity Report page
        And I select "Gwinnett County" from the Jurisdiction dropdown menu
        And I click the Submit button
        And user clicks the "View File" link, the user is returned to Patient profile summary page
        Then the saved morbidity report should have jurisdiction "Gwinnett County" 
   
    Scenario: Create investigation from morbidity report
        When I click on the first unassociated morbidity report link in the Events tab and store its Event ID
        And I click the Create Investigation button on the Morbidity Report page
        And I click the Submit button
        And user clicks the "View File" link, the user is returned to Patient profile summary page
        And Click Events tab on Patient Profile Page
        Then the saved morbidity report should be associated with an investigation
   
    Scenario: Associate investigation from morbidity report
        When user clicks on the "Add morbidity report" button within the Events tab
        And I select "Botulism, foodborne" from the Condition dropdown menu
        And I select "Cobb County" from the Jurisdiction dropdown menu
        And I enter the current date in the Date of Morbidity Report field
        And I enter "2" in the Facility and Provider Information field
        And I click on the Code Lookup button
        And I select "No" from the Pregnant dropdown menu
        And I click the Submit button
        And Click Events tab on Patient Profile Page
        And I click on the first morbidity report link in the Events tab and store its Event ID
        And I click the Associate Investigation button on the Morbidity Report page
        And I check an investigation with the condition "Botulism, foodborne"
        And I click the Submit button
        And I store the Investigation ID from the association message
        And user clicks the "View File" link, the user is returned to Patient profile summary page
        And Click Events tab on Patient Profile Page
        And I click on the stored Investigation ID in the Events tab
        Then the stored morbidity report should be associated with the investigation

    # Scenario: Mark morbidity report as reviewed
    # Scenario: Mark morbidity report as reviewed - STD
    # Scenario: Delete morbidity report