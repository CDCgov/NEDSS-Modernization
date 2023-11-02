Feature: Download Pages


	Scenario: As an admin I can download page library
         Given I am an admin user make an download page library request
         When I make a request to download page library
         Then Page library is downloaded
         
    Scenario: I cannot download page library without being logged in
         Given I am not logged in
         When I make a request to download page library
         Then a no credentials found exception is thrown
         
    Scenario: I cannot download page library without proper permissions
         Given I am a user without permissions
         When I make a request to download page library
         Then an accessdenied exception is thrown

    Scenario: As an admin I can download page metadata
           Given I am an admin user make an download page library request
           When I make a request to download page metadata
           Then Page metadata is downloaded