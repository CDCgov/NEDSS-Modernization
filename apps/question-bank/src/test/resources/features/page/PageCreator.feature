Feature: Add Page


	Scenario: As an admin I can create a Page
         Given I am an admin user make an add page request
         When I make a request to create a Page
         Then A page is created