Feature: Create ValueSet
	When creating a value set as an admin it should create
	
		Scenario: can not create codeSet when codeSet Group Metadata already exists
			Given: codeSet Group Metadata already exists
			when: I create a value set
			then: valueSet should not create


		Scenario: can not create codeSet when codeSet name  already exists
			Given: A codeSetNm already exists
			when: I create a value set
			then: valueSet should not create


	   Scenario: Given I am admin I create a new value set
			Given: I am admin and a value set does not exists
			when: I create a value set
			then: valueSet should be created
