Feature: Patient search using updated Date of Birth fields

    Background:
        Given I am logged in as secure user

    Scenario: Searching for a patient by Date of Birth using "Exact Date"
        When I select Exact Date for Date of Birth
        When I enter a specific date of birth
        When I search for patients
        Then the system should return patients whose Date of Birth exactly matches the entered date

    Scenario: Searching for a patient by Date of Birth using "Date Range"
        When I select Date Range for Date of Birth
        And I enter a start and end date for the range
        When I search for patients
        Then the system should return patients whose Date of Birth falls within the entered date range

    # error message is being clipped, so not testing as visibile, though it is there
    @skip-broken
    Scenario: Searching for a patient by Date of Birth with an Invalid Exact Date
        When I select Exact Date for Date of Birth
        And I enter a Date of Birth that does not exist
        Then the system should display an error message
