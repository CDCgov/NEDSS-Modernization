Feature: Run report

    Background:
        Given I am logged in as secure user
        Given I navigate to list reports
        
    Scenario: I can run and export a report
        When I click on the "Expand Subsections" link
        # first click doesn't show anything, but changes to "collapse"
        When I click on the "Expand Subsections" link
        When I click on the "Run" link
        Then I should see a "heading" labelled "SR10: Multi-Year Line Graph of Disease Cases"
        When I click the "Run" button
        Then I should see "The Condition Code is required." validation error
        When I select value "AIDS" in the "Condition Code" field
        When I click the "Run" button
        Then I should see a loading indicator
        Then I should see a "heading" labelled "Your report has opened in a new tab."
        When I click the "Refine Report" button
        Then I should see a "heading" labelled "SR10: Multi-Year Line Graph of Disease Cases"
        When I select value "Anthrax" in the "Condition Code" field
        When I click the "Export" button
        Then I should see a loading indicator
        Then I should see a "heading" labelled "Your report has downloaded."

    Scenario: I can run a report for library nbs_sr_5
        When I navigate to "Public" report with reportUid: 2 and dataSourceUid: 1
        And I select "AIDS" from the "Condition Code" dropdown menu
        And I click the "Run" button
        Then I should see a "heading" labelled "Your report has opened in a new tab."

    Scenario: I can run a report for library nbs_sr_08
        When I navigate to "Public" report with reportUid: 4 and dataSourceUid: 1
        And I enter "04/28/2025" to the From date
        And I enter "04/28/2026" to the To date
        And I select "AIDS" from the "Condition Code" dropdown menu
        And I click the "Run" button
        Then I should see a "heading" labelled "Your report has opened in a new tab."

    Scenario: I can run a report for library nbs_sr_12
        When I navigate to "Public" report with reportUid: 8 and dataSourceUid: 1
        And I select "AIDS" from the "Condition Code" dropdown menu
        And I select "Appling County" from the "County Code" dropdown menu
        And I click the "Run" button
        Then I should see a "heading" labelled "Your report has opened in a new tab."

    Scenario Outline: I can run a report for library nbs_sr_19
        When I navigate to "Public" report with reportUid: <reportUid> and dataSourceUid: <dataSourceUid>
        And I enter From Month: "1" and From Year: "2025"
        And I enter To Month: "12" and To Year: "2025"
        And I click the "Run" button
        Then I should see a "heading" labelled "Your report has opened in a new tab."

        Examples:
            | reportUid | dataSourceUid |
            | 10066724  | 15            |
            | 10066726  | 15            |

    Scenario: I can run a report for library nbs_custom
        When I navigate to "Template" report with reportUid: 10066768 and dataSourceUid: 30
        And I enter "04/28/2025" to the From date
        And I enter "04/28/2026" to the To date
        And I select the column "AST Result"
        And I click the "Run" button
        Then I should see a "heading" labelled "Your report has opened in a new tab."
