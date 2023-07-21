Feature: Read Template
	When retrieving templates or searching templates as an admin it should return
	
	
		Scenario: As an admin I am able to retrieve all value templates
			Given: I am an admin user 
			when: I make a request to retrieve all templates
			then: Templates should be returned
			
		Scenario: As an admin I search for a template that does not exist
			Given: I am an admin user
			when: I make a request for a template that does not exist
			then: A template should not be returned
			
		Scenario: As an admin I search for a template set that exists
			Given: I am an admin user
			when: I search for a template that exists
			then: A template should be returned