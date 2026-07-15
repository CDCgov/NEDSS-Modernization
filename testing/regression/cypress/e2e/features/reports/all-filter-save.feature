Feature: View report configuration

    Background:
        Given I am logged in as secure user

    Scenario: I create, save, and save as all the filters
        When I navigate to manage reports
        And I click the "Create button" button
        Then I should see the "Add" configuration page
        # == Data source selecting ==
        Then The "Confirm data source" "button" should be disabled
        When I select value "nbs_ods.PHCDemographic (Disease Counts by County)" in the "Data source" field
        When I click the "Confirm data source" button
        Then I should see a modal labelled "Confirm data source: nbs_ods.PHCDemographic (Disease Counts by County)"
        When I click the "Cancel" button
        When I select value "nbs_ods.PHCDemographic (Line List of Diseases with NBS Security)" in the "Data source" field
        When I click the "Confirm data source" button
        Then I should see a modal labelled "Confirm data source: nbs_ods.PHCDemographic (Line List of Diseases with NBS Security)"
        When I click the "Confirm" button
        Then The "Data source" "combobox" should be disabled
        # == General fields ==
        When I type "All filter test" into the "Name" field
        When I type "All filter test is the best report" into the "Description" field
        When I select value "System" in the "Owner" field
        When I select radio "Public" in the "Group" field
        When I select value "Default Report Section" in the "Section name" field
        When I select value "nbs_custom (Recommended default. Basic tabular report. Executes the query described by the data source and filters and returns the table)" in the "Report execution library" field
        # == Filters ==
        When I add all filters
        Then I should see 22 available filters
        When I click the "Submit" button
        Then I should see the "View" configuration page
        And I navigate to list reports
        And I click on the "Expand Subsections" link
        And I click on the "Expand Subsections" link
        And I click on the "Run" link
        Then I should see a "heading" labelled "All filter test"
        When I fill out all filters with 1
        And I click the "Run" button
        Then I should see a "heading" labelled "Your report has opened in a new tab."

        # Save as the report
        When I click the "Save as new" button
        And I should see a modal labelled "Save as a new report"
        And I type "Test save as all filter report" into the "Name" field
        And I type "Testing saving as a report" into the "Description" field
        And I select value "Default Report Section" in the "Section name" field
        And I should see "Private" radio selected in the "Group" field
        And I click the "Save as new" button
        Then I am redirected to "/nbs/ManageReports.do"
        And I click on the "Expand Subsections" link
        And I click on the "Run" link
        Then All filters should be filled out with 1
