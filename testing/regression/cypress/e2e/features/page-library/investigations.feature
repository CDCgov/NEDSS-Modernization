# uses old flow
@skip-broken
@skip-if-disabled-is-int
Feature: Page Builder - Page builder manage pages investigations

    Background:
        Given I am logged in as secure user and stay on classic
        And Navigate to Page Library on classic
        And I add new page

    Scenario: User views the page history in NBS Classic
    When the user clicks on the Page History button
    Then the Manage Pages: View Page History page is displayed

    Scenario: User publishes draft investigation
	When the user clicks the Publish button
	And the user enters Version Notes as "automated testing"
	And the user clicks the Submit button to publish
	Then success message contains the phrase "successfully published"

    Scenario: User deletes draft investigations 
	When the user clicks the Delete Draft button
	Then success message is displayed
