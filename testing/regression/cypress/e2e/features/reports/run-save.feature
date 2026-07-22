Feature: Save report after report run
    Background:
        Given I am logged in as secure user

    Scenario: I cannot see the save button after running the report if I am not the owner
        And I navigate to list reports
        And I navigate to "Public" report with reportUid: 8 and dataSourceUid: 1
        And I select "AIDS" from the "Condition Code" dropdown menu
        And I select "Appling County" from the "County Code" dropdown menu
        And I click the "Run" button
        And I should see a "heading" labelled "Your report has opened in a new tab."
        And I should not see the "Save" "button"

    Scenario: I can save as the report after running the report
        And I navigate to list reports
        When I navigate to "Public" report with reportUid: 8 and dataSourceUid: 1
        And I select "Anthrax" from the "Condition Code" dropdown menu
        And I select "Fulton County" from the "County Code" dropdown menu
        And I click the "Run" button
        And I should see a "heading" labelled "Your report has opened in a new tab."
        And I click the "Save as new" button
        Then I should see a modal labelled "Save as a new report"

        # Cancelling modal closes it
        When I click the "Cancel" button
        Then I should see a "heading" labelled "Your report has opened in a new tab."

        When I click the "Save as new" button
        And I should see a modal labelled "Save as a new report"
        And I type "Test save as report" into the "Name" field
        And I type "Testing saving as a report" into the "Description" field
        And I select value "Default Report Section" in the "Section name" field
        And I should see "Private" radio selected in the "Group" field
        And I click the "Save as new" button
        Then I am redirected to "/nbs/ManageReports.do"
        And I click on the "Expand Subsections" link
        Then I should see a "link" labelled "Test save as report"

    Scenario: I can save the report after running the report if I am the owner
        And I navigate to list reports
        When I click on the "Expand Subsections" link
        And I click on the "Run" link
        Then I should see option "Anthrax" in the "Condition Code" combobox input field
        Then I should see option "Fulton County" in the "County Code" multi-select combobox input field
        And I select "AIDS" from the "Condition Code" dropdown menu
        And I click the "Run" button
        And I should see a "heading" labelled "Your report has opened in a new tab."
        And I click the "Save" button
        Then I should see a modal labelled "Overwrite saved report?"

        # Cancelling modal closes it
        When I click the "Cancel" button
        Then I should see a "heading" labelled "Your report has opened in a new tab."

        When I click the "Save" button
        And I should see a modal labelled "Overwrite saved report?"
        And I click the "Save" button
        Then I am redirected to "/nbs/ManageReports.do"
        When I click on the "Expand Subsections" link
        And I click on the "Run" link
        Then I should see option "AIDS" in the "Condition Code" combobox input field
        Then I should see option "Fulton County" in the "County Code" multi-select combobox input field
        When I click on the "Back to reports" link
        Then I am redirected to "/nbs/ManageReports.do"

    Scenario: I can delete the reports I created
        And I navigate to manage reports
        And I click on the "Test save as report" link
        Then I should see the "View" configuration page
        And I should see value "Ariella Kent" in the "Owner" field
        When I click the "Delete" button
        Then I should see a modal labelled "Delete report: Test save as report"
        When I click the "Yes, delete" button
        Then I should see the report list
