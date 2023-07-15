Feature: Read ValueSet
	When retrieving all value sets or searching value sets as an admin it should return
	
	
		Scenario: As an admin I am able to retrieve all value sets
			Given: I am an admin user 
			when: I make a request to retrieve all value sets
			then: Value sets should be returned
			
			
		Scenario: As an admin I search for a value set that does not exist
			Given: I am an admin user
			when: I make a request for a value set that does not exist
			then: A value sets should not be returned
			
			
		Scenario: As an admin I search for a value set that exists
			Given: I am an admin user
			when: I search for a value set that exists
			then: A value set should be returned