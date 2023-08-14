Feature: Add Page


	Scenario: As an admin I can create a Page
         Given I am an admin user make an add page request
         When I make a request to create a Page
         Then A page is created
         
    Scenario: I cannot create a page without being logged in
         Given I am not logged in
         When I make a request to create a Page
         Then a no credentials found exception is thrown
         
    Scenario: I cannot create a page without proper permissions
         Given I am a user without permissions
         When I make a request to create a Page
         Then an accessdenied exception is thrown
