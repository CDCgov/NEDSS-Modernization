@create_hyperlink
Feature: Create Hyperlink 

    Background:
        Given I have a page
        And the page has a tab
        And the page has a section in the 1st tab
        And the page has a sub-section named "sub-section" in the 1st section

    Scenario: I can create a hyperlink static element
        Given I am an admin user
        When I send a hyperlink request with "<label>" and "<link>"
        Then a hyperlink is created with "<label>" and "<link>"
        Examples:
            | label      |    | link       |
            | google     |    | google.com |
            | yahoo      |    | yahoo.com  |
            
            
            

    Scenario: I cannot create a hyperlink element without logging in
        Given I am not logged in
        When I send a hyperlink request with "<label>" and "<link>"
        Then a no credentials found exception is thrown

    Scenario: I cannot create a hyperlink element without having permissions
        Given I am a user without permissions
        When I send a hyperlink request with "<label>" and "<link>"
        Then an accessdenied exception is thrown