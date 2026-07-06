Feature: Save report after report run
    Background:
        Given I am logged in as secure user
        And I navigate to list reports

    Scenario: I cannot see the save button after running the report if I am not the owner
        And I navigate to report with reportUid: "8" and dataSourceUid: "1"
        And I select "AIDS" from the "Condition Code" dropdown menu
        And I select "Appling County" from the "County Code" dropdown menu
        And I click the "Run" button
        And I should see a "heading" labelled "Your report has opened in a new tab."
        And I should not see the "Save" "button"

    Scenario: I can save as the report after running the report
        When I navigate to report with reportUid: "8" and dataSourceUid: "1"
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
        And I select radio "Public" in the "Group" field
        And I click the "Save as new" button
        Then I am redirected to "/nbs/ManageReports.do"
        And I click on the "Expand subsections" link
        And I click on the "Expand subsections" link
    # TODO APP-668: uncomment these tests and get passing
    #     Then I should see a "link" labelled "Test save as report"

    # Scenario: I can save the report after running the report if I am the owner
    #     When I click on the "Expand subsections" link
    #     And I click on the "Expand subsections" link
    #     And I click on the "Test save as report" link
    #     Then I should see option "Antrhax" in the "Condition Code" combobox input field
    #     Then I should see option "Fulton County" in the "County Code" combobox input field
    #     And I select "AIDS" from the "Condition Code" dropdown menu
    #     And I click the "Run" button
    #     And I should see a "heading" labelled "Your report has opened in a new tab."
    #     And I click the "Save" button
    #     Then I should see a modal labelled "Overwrite saved report?"

    #     # Cancelling modal closes it
    #     When I click the "Cancel" button
    #     Then I should see a "heading" labelled "Your report has opened in a new tab."

    #     When I click the "Save" button
    #     And I should see a modal labelled "Overwrite saved report?"
    #     And I click the "Save" button
    #     Then I am redirected to "/nbs/ManageReports.do"
    #     And I click on the "Test save as report" link
    #     Then I should see option "AIDS" in the "Condition Code" combobox input field
    #     Then I should see option "Fulton County" in the "County Code" combobox input field
