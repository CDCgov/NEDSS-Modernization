@create_hyperlink
Feature: Create Hyperlink 

    Background:
        Given I have a page
        And the page has a tab
        And the page has a section in the 1st tab
        And the page has a sub-section named "sub-section" in the 1st section

    Scenario: I can create a hyperlink static element
        Given I am logged in 
        And I can "LDFAdministration" any "System"
        And I create a hyperlink request with "<label>" and "<link>"
        When I send a hyperlink request
        Then a hyperlink is created
        Examples:
            | label      |    | link       |
            | google     |    | google.com |
            | yahoo      |    | yahoo.com  |