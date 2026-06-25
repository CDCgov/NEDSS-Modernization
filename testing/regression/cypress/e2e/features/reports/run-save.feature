Feature: Save report after report run
    Background:
        Given I am logged in as secure user

    Scenario: I cannot see the save button after running the report if I am not the owner
        When I navigate to list reports
        And I navigate to report with reportUid: "8" and dataSourceUid: "1"
        And I select "AIDS" from the "Condition Code" dropdown menu
        And I select "Appling County" from the "County Code" dropdown menu
        And I click the "Run" button
        And I should see a "heading" labelled "Your report has opened in a new tab."
        And I should not see the "Save" "button"

    Scenario: I can save the report after running the report if I am the owner
        # change owner permissions on a report
        When I navigate to manage reports
        And I click on the "SR12: Cases of Selected Disease By County By Year" link
        And I click the "Edit" button
        And I select value "Fulton LocalUser" in the "Owner" field
        And I click the "Submit" button
        And I click on the "Logout" link
        And I am logged in as "fulton" username
        And I navigate to list reports

        When I navigate to report with reportUid: "8" and dataSourceUid: "1"
        And I select "AIDS" from the "Condition Code" dropdown menu
        And I select "Appling County" from the "County Code" dropdown menu
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
        And I am redirected to "/nbs/ManageReports.do"
