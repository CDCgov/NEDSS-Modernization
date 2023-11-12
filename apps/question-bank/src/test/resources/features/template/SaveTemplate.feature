Feature: Save Template
	
	Scenario: As an admin I am able to save template
		Given I am an admin user in template
		When I make a request to save template with watemplateuid does not exist
		Then Template should be saved

	Scenario: I cannot save template without logging in
		Given I am not logged in template
		When I make a request to save template
		Then a no credentials found exception is thrown in template