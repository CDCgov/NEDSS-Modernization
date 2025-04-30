Feature: User access the Patient Profile Summary page.

    Background:
        Given I am logged in as secure user

    Scenario: User accesses patient profile
        Given the user navigate to the patient profile page for "78917"
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

    Scenario: User navigates to the top of the page
        Given the user navigate to the patient profile page for "78917"
        When the user clicks on the Back to top button
        Then the user is taken to the top of the page

    Scenario: Display all investigations related to the patient in the "Investigations" section
        Given the user navigate to the patient profile page for "78917"
        Then user clicks on a patient's profile "Summary" tab
        Then I should see the following columns for "Open investigations" table
            | Investigation # | A link to the selected open investigation screen                  |
            | Start date      | Investigation Start Date                                          |
            | Condition       | Condition selected when opening the investigation                 |
            | Case status     | Case status as displayed in the View Investigation screen         |
            | Notification    | Notification status as displayed in the View Investigation screen |
            | Jurisdiction    | Investigation selected in the Investigation Details               |
            | Investigator    | Investigator assigned in the Investigation Details                |
            | Co-infection #  | All co-infection(s) related to this investigation are listed      |
        And user is able to click the Investigation Number to View Investigation page
        And user is able to click the "Return to File: Summary" link to return to Patient Profile Summary page
        Then user clicks on a patient's profile "Summary" tab

    Scenario: Display only open documents in the "Documents requiring review" section
        Given the user navigate to the patient profile page for "78917"
        Then user clicks on a patient's profile "Summary" tab
        Then I should see the following columns for "Documents requiring review" table
            | Document type                 |
            | Date received                 |
            | Reporting facility / provider |
            | Event date                    |
            | Description                   |
            | Event #                       |
        And user clicks on Document type "Laboratory Report", the View Lab Report page is displayed
        And user is able to click the "Return to File: Events" link to return to Patient Profile Summary page
        Then user clicks on a patient's profile "Summary" tab
        And user clicks on Document type "Morbidity Report", the View Lab Report page is displayed
        And user clicks the "View File" link, the user is returned to Patient profile summary page
        Then user clicks on a patient's profile "Summary" tab

    Scenario Outline: User sorts records by column value
        Given the user navigate to the patient profile page for "78917"
        Then user clicks on a patient's profile "Summary" tab
        When the User sorts Open Investigations by "<column>" "<sorted>"
        Then Open Investigations are sorted by "<column>" "<sorted>"

        Examples:
            | column       | sorted     |
            | Jurisdiction | ascending  |
            | Jurisdiction | descending |
            | Condition    | ascending  |
            | Condition    | descending |

    Scenario Outline: User sorts records by column value
        Given the user navigate to the patient profile page for "78917"
        Then user clicks on a patient's profile "Summary" tab
        When the User sorts Documents requiring review by "<column>" "<sorted>"
        Then Documents requiring review are sorted by "<column>" "<sorted>"
        Examples:
            | column        | sorted     |
            | Document type | ascending  |
            | Document type | descending |
