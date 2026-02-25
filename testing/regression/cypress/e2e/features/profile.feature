Feature: User can view existing and add/edit certain patient demographic data here.

    Background:
        Given I am logged in as secure user

    Scenario: User accesses a patient profile
        Given create a new patient profile
        Then the patient's profile is displayed

    Scenario: User fails to search for a patient
        When the user has enters for a patient by "Person number" as ""
        Then I should see search button disabled

    Scenario: User accesses printable patient demographics page
        Given create a new patient profile
        Then I set patient id profile ENV
        When the user clicks on the Print button
        Then the user is directed to the printable patient demographics page
        
    Scenario: User deletes patient profile
        Given create a new patient profile
        When the user clicks on the Delete Patient button
        Then confirms the deletion of the patient
        Then the user is directed to the Home screen

    Scenario: User cancels patient profile deletion
        Given create a new patient profile
        When the user clicks on the Delete Patient button
        Then cancels the deletion of the patient
        Then the user remains on the same profile page

    Scenario: User cannot delete patient with a related event in the profile
        Given create a new patient profile
        When the user clicks on the Delete Patient button
        Then confirms the deletion of the patient
        Then the user remains on the same profile page

    Scenario: User navigates to the top of the page
        Given create a new patient profile
        When the user clicks on the Back to top button
        Then the user is taken to the top of the page


    # Flow is old-style - needs updating
    @skip-broken
    Scenario: Data remains displayed after selecting the patient profile summary
        Given the user navigate to a new patient profile page
        Then user clicks on a patient's profile "Summary" tab
        Then I should see the following elements
            | Patient ID    |
            | SEX           |
            | DATE OF BIRTH |
            | PHONE         |
            | EMAIL         |
            | ADDRESS       |
            | ETHNICITY     |
            | RACE          |

    # Flow is old-style - needs updating
    @skip-broken
    Scenario: Data remains displayed after selecting patient profile events
        Given the user navigate to a new patient profile page
        Then user clicks on a patient's profile "Events" tab
        Then I should see the following elements
            | Patient ID    |
            | SEX           |
            | DATE OF BIRTH |
            | PHONE         |
            | EMAIL         |
            | ADDRESS       |
            | ETHNICITY     |
            | RACE          |

    # Edit flow is old-style small edits vs whole page - needs updating
    @skip-broken
    Scenario: Add a comment for patient
        Given create a new patient profile
        Then user clicks on a patient's profile "Demographics" tab
        When user clicks on the "Add comment" button
        Then user adds the comments
        Then comment is displayed on the patient profile page


    # Edit flow is old-style small edits vs whole page - needs updating
    @skip-broken
    Scenario: Add a name for patient
        Given create a new patient profile
        Then user clicks on a patient's profile "Demographics" tab
        When user clicks on the "Add name" button
        Then user adds the name "Alice"
        And Name "Alice" information is displayed on the patient profile page

    # Edit flow is old-style small edits vs whole page - needs updating
    @skip-broken
    Scenario: Add an address for a patient
        Given create a new patient profile
        Then user clicks on a patient's profile "Demographics" tab
        When user clicks on the "Add address" button
        And user adds the new address as "Houston"
        And Address information is updated

    # Edit flow is old-style small edits vs whole page - needs updating
    @skip-broken
    Scenario: Add phone and email for patient
        Given create a new patient profile
        Then user clicks on a patient's profile "Demographics" tab
        When user clicks on the "Add phone & email" button
        And user adds the new phone as "5123345670"
        And phone number is updated

    # Edit flow is old-style small edits vs whole page - needs updating
    @skip-broken
    Scenario: Add identification for patient
        Given create a new patient profile
        Then user clicks on a patient's profile "Demographics" tab
        When user clicks on the "Add identification" button
        And user adds the new Add identification as "234512334567"
        Then identification information is added successfully


    # Edit flow is old-style small edits vs whole page - needs updating
    @skip-broken
    Scenario: Add race for patient
        Given create a new patient profile
        Then user clicks on a patient's profile "Demographics" tab
        When user clicks on the "Add race" button
        Then user adds the new detail race as "Asian Indian"
        Then race information as "Indian" is displayed


    # Edit flow is old-style small edits vs whole page - needs updating
    @skip-broken
    Scenario: Edit general patient information
        Given create a new patient profile
        Then user clicks on a patient's profile "Demographics" tab
        When User clicks on the edit button under "General patient information"
        Then user adds the general patient info with the mother's name as "Araceli"
        Then general information as "Araceli" is displayed

    # Edit flow is old-style small edits vs whole page - needs updating
    @skip-broken
    Scenario: Edit ethnicity for a patient
        Given create a new patient profile
        Then user clicks on a patient's profile "Demographics" tab
        When User clicks on the edit button under "Ethnicity"
        Then user adds ethnicity as "Hispanic or latino"
        Then ethnicity information "Hispanic or Latino" is displayed

    # Edit flow is old-style small edits vs whole page - needs updating
    @skip-broken
    Scenario: Edit the sex and birth of a patient
        Given create a new patient profile
        Then user clicks on a patient's profile "Demographics" tab
        When User clicks on the edit button under "Sex & birth"
        When user adds sex and birth for current sex as "Female"
        Then sex and birth information is added and the current sex is shown as "Female"

    # Edit flow is old-style small edits vs whole page - needs updating
    @skip-broken
    Scenario: Edit mortality for patient
        Given create a new patient profile
        Then user clicks on a patient's profile "Demographics" tab
        When User clicks on the edit button under "Mortality"
        Then user edits mortality with the deceased option as "Yes"
        Then mortality information is saved with the deceased option as "Yes"
