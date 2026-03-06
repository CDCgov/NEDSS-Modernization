Feature: User can add a patient profile

    Background:
        Given I am logged in as secure user

    Scenario: User add a patient
        Then the user has searched for a patient by "Person number" as "63506872864"
        When nagivate to add new patient page
        Then I create a new patient
        Then I should see profile of new patient


    Scenario: User add a patient without enter field 
        Then the user has searched for a patient by "Person number" as "63506872864"
        When nagivate to add new patient page
        Then I clear the Information as of Date field
        Then I create a new patient without enter field and get error message

    Scenario: User add a patient to enter 'information as of Date' field only 
        Then the user has searched for a patient by "Person number" as "63506872864"
        When nagivate to add new patient page
        Then I create a new patient to enter information as of Date field only
        Then I should see profile of new patient

    # Scenario: User add a patient to enter 1 year later date in 'information as of Date' field  
    #     Then the user has searched for a patient by "Person number" as "63506872864"
    #     When nagivate to add new patient page
    #     Then I create a new patient to enter 1 year later date in information as of Date field
    #     Then I should see "You have successfully added"

    Scenario: User add a patient create and delete profile
        Scenario: View the patient detail of new created patient profile
        Then the user has searched for a patient by "Person number" as "63506872864"
        When nagivate to add new patient page
        Then I create a new patient
        Then I should see profile of new patient
        # Then delete the patient profile
        # Then I should see "Perform a search to see results"

    # Scenario: User add a patient create and check delete patient status
    #     Then the user has searched for a patient by "Person number" as "63506872864"
    #     When nagivate to add new patient page
    #     Then I create a new patient
    #     Then I should see "You have successfully added"
    #     Then navigate to patient profile page
    #     Then navigate to delete patient page
    #     Then user navigate to Yes,delete button
    #     When the user navigate to the patient profile page for "95055"
        ## no result is found 

    Scenario: User add a patient to enter numeric and special character in name field
        Then the user has searched for a patient by "Person number" as "63506872864"
        When nagivate to add new patient page
        Then I create a new patient to enter numeric and special character in name field
        Then I should see profile of new patient

    Scenario: User add a patient to enter other information with Information as of Date field
        Then the user has searched for a patient by "Person number" as "63506872864"
        When nagivate to add new patient page
        Then I create a new patient to enter other information with Information as of Date
        Then I should see profile of new patient

    # Selecting state doesn't work, so county doesn't work
    @skip-broken
    Scenario: User add a patient to enter Address with Information as of Date field
        Then the user has searched for a patient by "Person number" as "63506872864"
        When nagivate to add new patient page
        Then I create a new patient to enter address with Information as of Date
        Then I should see profile of new patient

    Scenario: View the patient detail of new created patient profile
        Then the user has searched for a patient by "Person number" as "63506872864"
        When nagivate to add new patient page
        Then I create a new patient
        Then I should see profile of new patient
        Then I should see on Patient Profile "Patient ID: "

    Scenario: Verify the detail of new created patient
        Then the user has searched for a patient by "Person number" as "63506872864"
        When nagivate to add new patient page
        Then I create a new patient 
        Then I should see profile of new patient
        Then I should see on Patient Profile "Patient ID: "
        # Then I should see "SEX"
        # Then I should see "PHONE NUMBER"
        # Then I should see "DATE OF BIRTH"

    # Scenario: User add a patient to check patient ID
    #     Then the user has searched for a patient by "Person number" as "63506872864"
    #     When nagivate to add new patient page
    #     Then I create a new patient
    #     Then I should see "You have successfully added"
    #     Then I should see profile of new patient
    #     Then I should see "Patient ID: "

    # Scenario: User add a patient to add another patient ID
    #     Then the user has searched for a patient by "Person number" as "63506872864"
    #     When nagivate to add new patient page
    #     Then I create a new patient
    #     Then I should see "You have successfully added"
    #     Then the user add another patient profile
    #     Then I create a new patient
    #     Then I should see "You have successfully added"

    # Selects don't work
    @skip-broken
    Scenario: User add a patient to select one Ethnicity option
        Then the user has searched for a patient by "Person number" as "63506872864"
        When nagivate to add new patient page
        Then I create a new patient with select Ethnicity option
        Then I should see profile of new patient

    # Scenario: User add a patient to select two Ethnicity option
    #     Then the user has searched for a patient by "Person number" as "63506872864"
    #     When nagivate to add new patient page
    #     Then I create a new patient to select two Ethnicity option of a patient
    #     ## unable to select two options

    # Selects don't work
    @skip-broken
    Scenario: User add a patient to select one option in Race 
        Then the user has searched for a patient by "Person number" as "63506872864"
        When nagivate to add new patient page
        Then I create a new patient to select one optios of Race of the patient
        Then I should see profile of new patient

    # Scenario: User add a patient to select more then one options of the Race  
    #     Then the user has searched for a patient by "Person number" as "63506872864"
    #     When nagivate to add new patient page
    #     Then I create a new patient to select three optios of Race of the patient
    #     Then I should see "You have successfully added"

    # Scenario: User add a patient to select ID type option of the Identification
    #     Then the user has searched for a patient by "Person number" as "63506872864"
    #     When nagivate to add new patient page
    #     Then I create a new patient to select ID type option of the Identification
    #     Then I should see "You have successfully added"

    # Scenario: User add a patient to select Assigning authority option of the Identification
    #     Then the user has searched for a patient by "Person number" as "63506872864"
    #     When nagivate to add new patient page
    #     Then I create a new patient to select Assigning authority option of the Identification
    #     Then I should see "You have successfully added"

    # Scenario: User add a patient to select Add another ID
    #     Then the user has searched for a patient by "Person number" as "63506872864"
    #     When nagivate to add new patient page
    #     Then I create a new patient to select Add another ID in the Identification
    #     Then I should see "You have successfully added"

        # bug scnario

    # Scenario: Verify that the full name of the patient is correctly displayed when all name fields (first name, middle name, last name) are filled and saved.
    #     Then the user has searched for a patient by "Person number" as "63506872864"
    #     When nagivate to add new patient page
    #     Then I create a new patient and display full name
    #     Then I should see "You have successfully added"

    # Scenario: User is entering maximum letter in any field
    #     Then the user has searched for a patient by "Person number" as "63506872864"
    #     When nagivate to add new patient page
    #     Then I create a new patient to entering maximum letter in field 

    # Scenario: User add a patient to select “Information as of Date" and type charecter in the field
    #     Then the user has searched for a patient by "Person number" as "63506872864"
    #     When nagivate to add new patient page
    #     Then I create a new patient to select “Information as of Date" and type charecter
    #     #user shold get error message 
    #     Then I should see "You have successfully added"

    # Scenario: User add a patient to select future date in the “Information as of Date “ field
    #     Then the user has searched for a patient by "Person number" as "63506872864"
    #     When nagivate to add new patient page
    #     Then I create a new patient to select future date in the “Information as of Date “ field
    #     #user shold get error message 
    #     Then I should see "You have successfully added"

    # Scenario:  User add a patient to enter a special character  and numeric in the name field.
    #     Then the user has searched for a patient by "Person number" as "63506872864"
    #     When nagivate to add new patient page
    #     Then I create a new patient to enter a special character  and numeric in the name field
    #     #user shold get error message 
    #     Then I should see "You have successfully added"

    # Scenario:  User add a patient to select a future date in the DOB field
    #     Then the user has searched for a patient by "Person number" as "63506872864"
    #     When nagivate to add new patient page
    #     Then I create a new patient select a future date in the DOB field
    #     #user shold get error message 
    #     Then I should see "You have successfully added"

    # Scenario:  User add a patient to select a future date in the Date Of Death field
    #     Then the user has searched for a patient by "Person number" as "63506872864"
    #     When nagivate to add new patient page
    #     Then I create a new patient select a future date in the Date Of Death field
    #     #user shold get error message 
    #     Then I should see "You have successfully added"
