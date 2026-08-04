Feature: Patient File - Event Management - Morbidity Report

  Background:
    Given I am logged in as secure user
    And the user navigate to the patient profile page for "63000"
    And Click Events tab on Patient Profile Page

    Scenario: Add new morbidity report without investigation
        When I check the current count of morbidity reports in the Events tab
        And user clicks on the "Add morbidity report" button within the Events tab
        And I select "Botulism, foodborne" from the Condition dropdown menu
        And I select "Cobb County" from the Jurisdiction dropdown menu
        And I enter the current date in the Date of Morbidity Report field
        And I enter "2" in the Facility and Provider Information field
        And I click on the Code Lookup button
        And I select "No" from the Pregnant dropdown menu
        And I click the Submit button
        And Click Events tab on Patient Profile Page
        Then the morbidity report count should increase by 1 in the Events tab
    
    # Scenario: Add new morbidity report and create investigation

    # Scenario: Cancel creation of a new morbidity report

    # Scenario: Edit existing morbidity report

    # Scenario: Print morbidity report

    # Scenario: Transfer ownership of morbidity report
    # Scenario: Create investigation from morbidity report
    # Scenario: Associate investigation from morbidity report
    # Scenario: Mark morbidity report as reviewed
    # Scenario: Mark morbidity report as reviewed - STD
    # Scenario: Delete morbidity report