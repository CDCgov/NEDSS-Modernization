Feature: Summary Data

    Background:
        Given I am logged in as secure user and stay on classic

    Scenario: View Summary Data
        When I click on Open Data Entry in the menu bar
        Then I should see the Summary Data link
        When I click on the Summary Data link
        Then I should see the Summary Data page
        When I select 2025 from the MMWR Year dropdown
        Then I should see 53 options in the MMWR Week dropdown
