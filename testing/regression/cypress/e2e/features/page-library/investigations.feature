Feature: Page Builder - Page builder manage pages investigations

    Background:
        Given I am logged in as secure user and stay on classic
        Then Navigate to Page Library on classic
        Then I add new page

    Scenario: User views the page history in NBS Classic
    And the user clicks on the Page History button
    Then the Manage Pages: View Page History page is displayed