Feature: Patient File - Event Management - Morbidity Report

  Background:
    Given I am logged in as secure user
    And the user navigate to the patient profile page for "63000"
    And Click Events tab on Patient Profile Page

    Scenario: Add new morbidity report without investigation
        When user clicks on the "Add morbidity report" button within the Events tab
        And I select "Botulism, foodborne" from the Condition dropdown menu
        And I select "Clayton County" from the Jurisdiction dropdown menu
        And I enter the current date in the Date of Morbidity Report field
        And I enter "2" in the Facility and Provider Information field
        And I select "No" from the Pregnant dropdown menu
    
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