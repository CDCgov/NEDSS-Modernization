Feature: User accesses patient profile and can view existing patient data here.

    Background:
        Given I am logged in as secure user

    Scenario: Create Seed Profile Data
        Given create a new patient profile
        Then I set patient id profile ENV

    # Not multiple investigations available
    @skip-broken
    Scenario: User compares investigations
        Given the user navigate to the patient profile page for "63000"
        Then user clicks on a patient's profile "Events" tab
        When the user has selected multiple investigations
        When user clicks on the "Compare investigations" button

    Scenario: User wants to add a new investigation
        Given the user navigate to the patient profile page for "63000"
        Then user clicks on a patient's profile "Events" tab
        When user clicks on the "Add investigation" button
        And Add a new investigation

    Scenario: User wants to add a new lab report
        Given the user navigate to the patient profile page for "63000"
        Then user clicks on a patient's profile "Events" tab
        When user clicks on the "Add lab report" button  

    Scenario: User wants to add a new morbidity report
        Given the user navigate to the patient profile page for "63000"
        Then user clicks on a patient's profile "Events" tab
        When user clicks on the "Add morbidity report" button

    # record not deleting
    @skip-broken
    Scenario: User wants to add a new vaccination record and delete it
        Given the user navigate to the patient profile page for "63000"
        Then user clicks on a patient's profile "Events" tab
        When user clicks on the "Add vaccination" button
        Then user submits "anthrax" vaccination with a popup and deletes it

    Scenario: Display all investigations related to the patient in the "Investigations" section
        Given the user navigate to the patient profile page for "63000"
        Then user clicks on a patient's profile "Events" tab
        Then I should see the following columns for "Investigations" table
            | Select to compare| A check box to compare investigations.                                 |
            | Investigation ID | A link to the selected open investigation screen                       |
            | Start date       | Investigation Start Date                                               |
            | Status           | Investigation status as in the case info/investigation details section |
            | Condition        | Condition selected when opening the investigation                      |
            | Case status      | Case status as displayed in the View Investigation screen              |
            | Notification     | Notification status as displayed in the View Investigation screen      |
            | Jurisdiction     | Investigation selected in the Investigation Details                    |
            | Investigator     | Investigator assigned in the Investigation Details                     |
            | Co-infection ID  | All co-infection(s) related to this investigation are listed           |

    # No lab reports
    @skip-broken
    Scenario: Display all lab reports related to the patient in the "Lab reports" section
        Given the user navigate to the patient profile page for "63000"
        Then user clicks on a patient's profile "Events" tab
        Then I should see the following columns for "Lab reports" table
            | Date received       | Date and time the document is received                              |
            | Facility / provider | Reporting facility, ordering facility, and ordering provider.       |
            | Date collected      | Specimen collection date/time                                       |
            | Test results        | Resulted Test and Coded Result from Lab Report screen.              |
            | Associated with     | A link to the investigation associated with this lab report if any. |
            | Program area        | Program area as in Lab Report screen                                |
            | Jurisdiction        | Jurisdiction as in Lab Report screen                                |
            | Event #             | System generated ID.                                                |

    # No morbidity reports
    @skip-broken
    Scenario: Display all morbidity reports related to the patient in the "Morbidity reports" section
        Given the user navigate to the patient profile page for "63000"
        Then user clicks on a patient's profile "Events" tab
        Then I should see the following columns for "Morbidity reports" table
            | Date received   | date and time the report is received.                      |
            | Provider        | provider selected in the report                            |
            | Report date     | Date of Morbidity Report on the Report Information screen. |
            | Condition       | Condition on the Report Information screen.                |
            | Jurisdiction    | Jurisdiction on the Report Information screen.             |
            | Associated with | Link to the Investigation associated with this report.     |
            | Event #         | System generated ID.                                       |

    # No vaccinations
    @skip-broken
    Scenario: Display all vaccinations related to the patient in the "Vaccinations" section
        Given the user navigate to the patient profile page for "63000"
        Then user clicks on a patient's profile "Events" tab
        Then I should see the following columns for "Vaccinations" table
            | Event ID             | System generated ID.                                        |
            | Date created         | Date the vaccination record is created.                     |
            | Organization/Provider| Vaccination given by provider.                              |
            | Date administered    | Vaccination administered date.                              |
            | Vaccine administered | Type of vaccine administered.                               |
            | Associated with      | Link to the Investigation associated with this vaccination. |

    # No treatment records
    @skip-broken
    Scenario: Display all treatment related to the patient in the "Treatment" section
        Given the user navigate to the patient profile page for "63000"
        Then user clicks on a patient's profile "Events" tab
        Then I should see the following columns for "Treatment" table
            | Date created    | Date the treatment record is created                        |
            | Provider        | Provider selected for the treatment.                        |
            | Treatment date  | Actual treatment date selected in the treatment record.     |
            | Treatment       | Treatment selected in the treatment record.                 |
            | Associated with | Link to the Investigation associated with this vaccination. |
            | Event #         | System generated ID.                                        |

    # No documents
    @skip-broken
    Scenario: Display documents electronically received (eCR) related to the patient in the "Documents" section
        Given the user navigate to the patient profile page for "63000"
        Then user clicks on a patient's profile "Events" tab
        Then I should see the following columns for "Documents" table
            | Date received    | Date the treatment record is created |
            | Type             | Document type.                       |
            | Sending facility | Facility that sent the document.     |
            | Date reported    | Date the document is received        |
            | Condition        |                                      |
            | Associated with  |                                      |
            | Event ID         | System generated ID                  |

    # No contact records
    @skip-broken
    Scenario: Display contact record(s) (contact(s) named by patient) in the "Contact records (contacts named by patient)" section
        Given the user navigate to the patient profile page for "63000"
        Then user clicks on a patient's profile "Events" tab
        Then I should see the following columns for "Contact records (contacts named by patient)" table
            | Date created    | Date the contact record is created.                                  |
            | Contacts named  | Contact’s name named by the patient.                                 |
            | Date named      | Date named in the contact information screen in that contact record. |
            | Description     | Condition of the investigation the contact is named by the patient   |
            | Associated with | Link to the investigation the contact is named by the patient.       |
            | Event #         | Event#: System generated ID.                                         |

    # No contact records
    @skip-broken
    Scenario: Display contact record(s) (patient named by contact(s)) in the "Contact records (patient named by contacts)" section
        Given the user navigate to the patient profile page for "63000"
        Then user clicks on a patient's profile "Events" tab
        Then I should see the following columns for "Contact records (patient named by contacts)" table
            | Date created    | Date the contact record is created.                                  |
            | Named by        | Patient’s name named by the contact.                                 |
            | Date named      | Date named in the contact information screen in that contact record. |
            | Description     | Condition of the investigation the contact is named by the patient   |
            | Associated with | Link to the investigation the contact is named by the patient.       |
            | Event #         | Event#: System generated ID.                                         |
