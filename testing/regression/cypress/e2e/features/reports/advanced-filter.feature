Feature: Report Execution Advanced Filter

    Background:
        Given I am logged in as secure user
        And I navigate to manage reports

    Scenario: I add a report to test advanced filters
        When I click the "Create button" button
        And I select value "nbs_ods.PHCDemographic (Disease Counts by County)" in the "Data source" field
        And I click the "Confirm data source" button
        And I click the "Confirm" button
        And I type "Advanced filter test report" into the "Name" field
        And I type "Advanced filter test report description" into the "Description" field
        And I select value "Ariella Kent" in the "Owner" field
        And I select radio "Private" in the "Group" field
        And I select value "Default Report Section" in the "Section name" field
        And I select value "nbs_custom (Recommended default. Basic tabular report. Executes the query described by the data source and filters and returns the table)" in the "Report execution library" field
        And I select value "Where Clause Builder" in the "Filter" field
        And I click the "Add filter" button
        And I click the "Submit" button
        Then I should see the "View" configuration page

    Scenario: I can build advanced filters
        When I click on the "Advanced filter test report" link
        And I click the "Run" button
        And I add incomplete or incorrect advanced filters
        And I click the "Run" button
        Then I see errors related to incomplete or incorrect advanced filters
        When I remove all rules from an entire rule group
        And I click the "Run" button
        Then I see an error to remove a blank rule group
        When I remove a rule group
        And I enter complete and accurate advanced filter values
        And I click the "Run" button
        Then I should see a "heading" labelled "Your report has opened in a new tab."

    Scenario: Delete the report
        Given I navigate to manage reports
        When I click on the "Advanced filter test report" link
        Then I should see the "View" configuration page
        When I click the "Delete" button
        When I click the "Yes, delete" button
        Then I should see the report list
