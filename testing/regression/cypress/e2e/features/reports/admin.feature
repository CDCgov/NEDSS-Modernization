Feature: View report configuration

    Background:
        Given I am logged in as secure user
        Given I navigate to manage reports
        
    Scenario: I can view the report configuration
        When I click on the "Aggregate Line Listing Report" report
        Then I should see the configuration page
        Then I should see value "nbs_rdb.AGGREGATE_REPORT_DATAMART (AGGREGATE_REPORT_DATAMART_UNSECURE)" in the "Data source" field
        Then I should see value "Aggregate Line Listing Report" in the "Name" field
        Then I should see value "This report allows users to ad hoc reporting from the Aggregate Data Mart" in the "Description" field
        Then I should see value "System" in the "Owner" field
        Then I should see value "Reporting Facility" in the "Group" field
        Then I should see value "Default Report Section" in the "Section name" field
        Then I should see value "nbs_custom (Basic tabular report. Executes the query described by the data source and filters and returns the table)" in the "Report execution library" field
        Then I should see 2 available filters
        When I click the filter 0 view button
        Then I should see value "Time Range" in the "Filter" field
        Then I should see value "---" in the "Type" field
        Then I should see value "Report Create Date" in the "Associated column" field
        Then I should see value "No" in the "Required as basic filter?" field
