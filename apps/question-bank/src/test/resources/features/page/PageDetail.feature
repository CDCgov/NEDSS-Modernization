Feature: Page Details


	Scenario: As an admin I can get page details
         Given I am an admin user make a request for page details
         When I make a request to get page details
         Then Page Details are returned
         
    Scenario: I cannot get page details without being logged in
         Given I am not logged in
         When I make a request to get page details
         Then a no credentials found exception is thrown
         
    Scenario: I cannot get page details without proper permissions
         Given I am a user without permissions
         When I make a request to get page details
         Then an accessdenied exception is thrown
