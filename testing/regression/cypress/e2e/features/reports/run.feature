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
