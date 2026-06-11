Feature: View report configuration

    Background:
        Given I am logged in as secure user
        Given I navigate to manage reports
        
    Scenario: I can view the report configuration
        When I click on the "Aggregate Line Listing Report" link
        Then I should see the "View" configuration page
        Then I should see value "nbs_rdb.AGGREGATE_REPORT_DATAMART (AGGREGATE_REPORT_DATAMART_UNSECURE)" in the "Data source" field
        Then I should see value "Aggregate Line Listing Report" in the "Name" field
        Then I should see value "This report allows users to ad hoc reporting from the Aggregate Data Mart" in the "Description" field
        Then I should see value "System" in the "Owner" field
        Then I should see value "Reporting Facility" in the "Group" field
        Then I should see value "Default Report Section" in the "Section name" field
        Then I should see value "nbs_custom (Recommended default. Basic tabular report. Executes the query described by the data source and filters and returns the table)" in the "Report execution library" field
        Then I should see 2 available filters
        When I click the filter 0 "View" button
        Then I should see value "Time Range" in the "Filter" field
        Then I should see value "---" in the "Type" field
        Then I should see value "REPORT_CREATE_DATE (Report Create Date)" in the "Associated column" field
        Then I should see value "No" in the "Required as basic filter?" field
        
    Scenario: I can Add, Edit, Delete a report configuration
        When I click the "Create button" button
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
        When I type "My test report" into the "Name" field
        When I type "My test report is the best report" into the "Description" field
        When I select value "System" in the "Owner" field
        When I select value "Reporting Facility" in the "Group" field
        When I select value "STD Report Section" in the "Section name" field
        When I select value "nbs_custom (Recommended default. Basic tabular report. Executes the query described by the data source and filters and returns the table)" in the "Report execution library" field
        # == Filters ==
        # Add basic
        When I select value "Diseases" in the "Filter" field
        When I select value "Multi" in the "Type" field
        When I select value "PHC_code (Condition Code)" in the "Associated column" field
        When I click the "Add filter" button
        Then I click the filter 0 "View" button
        Then I should see value "Diseases" in the "Filter" field
        Then I should see value "Multi" in the "Type" field
        Then I should see value "PHC_code (Condition Code)" in the "Associated column" field
        Then I should see value "No" in the "Required as basic filter?" field

        # edit
        Then I click the filter 0 "Edit" button
        When I select value "Single" in the "Type" field
        When I click the "Update filter" button
        Then I click the filter 0 "View" button
        Then I should see value "Single" in the "Type" field
        Then I click the filter 0 "View" button

        # add and delete
        When I select value "Where Clause Builder" in the "Filter" field
        When I click the "Add filter" button
        Then I click the filter 1 "View" button
        Then I should see value "Where Clause Builder" in the "Filter" field
        Then I should see value "---" in the "Type" field
        Then I should see value "---" in the "Associated column" field
        Then I should see value "No" in the "Required as basic filter?" field
        When I click the filter 1 "Delete" button

        # add more for editing later
        When I select value "Where Clause Builder" in the "Filter" field
        When I click the "Add filter" button

        When I select value "Duplicate Investigations Time Frame" in the "Filter" field
        When I click the "Add filter" button

        When I select value "Time Period" in the "Filter" field
        When I select value "event_date (Event Date)" in the "Associated column" field
        When I toggle the "Required as basic filter" field
        When I click the "Add filter" button
        When I click the filter 3 "View" button
        Then I should see value "Yes" in the "Required as basic filter?" field

        When I click the "Submit" button
        Then I should see the "View" configuration page
        # Confirm submitted values
        Then I should see value "nbs_ods.PHCDemographic (Line List of Diseases with NBS Security)" in the "Data source" field
        Then I should see value "My test report" in the "Name" field
        Then I should see value "My test report is the best report" in the "Description" field
        Then I should see value "System" in the "Owner" field
        Then I should see value "Reporting Facility" in the "Group" field
        Then I should see value "STD Report Section" in the "Section name" field
        Then I should see value "nbs_custom (Recommended default. Basic tabular report. Executes the query described by the data source and filters and returns the table)" in the "Report execution library" field
        Then I should see 4 available filters
        When I click the filter 0 "View" button
        Then I should see value "Diseases" in the "Filter" field
        Then I should see value "Single" in the "Type" field
        Then I should see value "PHC_code (Condition Code)" in the "Associated column" field
        Then I should see value "No" in the "Required as basic filter?" field
        When I click the filter 2 "View" button
        Then I should see value "Yes" in the "Required as basic filter?" field
        
        # == Edit the Report ==
        When I click the "Edit" button
        # Check state
        Then The "Data source" "combobox" should be disabled
        Then I should see option "nbs_ods.PHCDemographic (Line List of Diseases with NBS Security)" in the "Data source" combobox input field
        Then I should see value "My test report" in the "Name" "textbox" input field
        Then I should see value "My test report is the best report" in the "Description" "textbox" input field
        Then I should see option "System" in the "Owner" combobox input field
        Then I should see option "Reporting Facility" in the "Group" combobox input field
        Then I should see option "STD Report Section" in the "Section name" combobox input field
        Then I should see option "nbs_custom (Recommended default. Basic tabular report. Executes the query described by the data source and filters and returns the table)" in the "Report execution library" combobox input field
        Then I should see 4 available filters
        # Change things
        When I select value "Default Report Section" in the "Section name" field
        # required => non
        When I click the filter 2 "Edit" button
        When I toggle the "Required as basic filter" field
        When I click the "Update filter" button
        # non => required
        When I click the filter 0 "Edit" button
        When I toggle the "Required as basic filter" field
        When I click the "Update filter" button
        # delete
        When I click the filter 1 "Delete" button
        # add
        When I select value "Duplicate Investigations Time Frame" in the "Filter" field
        When I click the "Add filter" button
        When I click the "Submit" button

        # == Check edited values ==
        Then I should see the "View" configuration page
        Then I should see value "nbs_ods.PHCDemographic (Line List of Diseases with NBS Security)" in the "Data source" field
        Then I should see value "My test report" in the "Name" field
        Then I should see value "My test report is the best report" in the "Description" field
        Then I should see value "System" in the "Owner" field
        Then I should see value "Reporting Facility" in the "Group" field
        Then I should see value "Default Report Section" in the "Section name" field
        Then I should see value "nbs_custom (Recommended default. Basic tabular report. Executes the query described by the data source and filters and returns the table)" in the "Report execution library" field
        Then I should see 4 available filters
        
        When I click the filter 0 "View" button
        Then I should see value "Diseases" in the "Filter" field
        Then I should see value "Single" in the "Type" field
        Then I should see value "PHC_code (Condition Code)" in the "Associated column" field
        Then I should see value "Yes" in the "Required as basic filter?" field

        When I click the filter 1 "View" button
        Then I should see value "Time Period" in the "Filter" field
        Then I should see value "---" in the "Type" field
        Then I should see value "event_date (Event Date)" in the "Associated column" field
        Then I should see value "No" in the "Required as basic filter?" field

        When I click the filter 2 "View" button
        Then I should see value "Duplicate Investigations Time Frame" in the "Filter" field
        Then I should see value "---" in the "Type" field
        Then I should see value "---" in the "Associated column" field
        Then I should see value "No" in the "Required as basic filter?" field

        When I click the filter 3 "View" button
        Then I should see value "Where Clause Builder" in the "Filter" field
        Then I should see value "---" in the "Type" field
        Then I should see value "---" in the "Associated column" field
        Then I should see value "No" in the "Required as basic filter?" field

        When I click on the "Back to Manage Reports" link
        Then I should see the report list
        Then I should see the "My test report" link

        # TODO - delete
        
    Scenario: I can cancel adding a report configuration
        When I click the "Create button" button
        Then I should see the "Add" configuration page
        When I select value "nbs_ods.PHCDemographic (Disease Counts by County)" in the "Data source" field
        When I click the "Cancel" button
        Then I should see the report list

        
