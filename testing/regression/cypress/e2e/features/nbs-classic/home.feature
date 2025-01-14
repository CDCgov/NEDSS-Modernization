Feature: Classic NBS - User can view data in NBS Home

  Background:
    Given I am logged in as secure user and stay on classic

  Scenario: Display selected patient search
    Then Navigate to Patient Search pane
    And Enter Last Name text box input "Smith"
    Then Click on Search in Patient Search pane

  Scenario: Add new lab report
    Then Navigate to Patient Search pane
    And Enter First Name text box input "A"
    Then Click on Search in Patient Search pane
    And Click on Add New button in patient Search pane
    Then Click on Add new lab report in patient Search pane
    And Verify Add Lab Report page displayed

  Scenario Outline: Display selected home page queue
    Then Click each of the following "<Default Queues>"
    Examples:
      | Default Queues                           |
      | Open Investigations                      |
      | Approval Queue for Initial Notifications |
      | Updated Notifications Queue              |
      | Rejected Notifications Queue             |
      | Documents Requiring Security Assignment  |
      | Documents Requiring Review               |
      | Messages Queue                           |
      | Supervisor Review Queue                  |

  Scenario: Display selected home page merge patients
    Then Create two users with same firstname and last name
    And Click on Marge Patients tab on upper left side
    Then Click on Manual Search tab
    And Verify user navigated to Find Patient page
    Then Search user to manual merge
    And Select users and merge
    Then Click on Marge Patients tab on upper left side
    And Click on System Identified tab
    And Verify Merge Candidate List is displayed to user

  Scenario: Display selected home page reports
    Then Click on Reports tab on upper left side
    And Verify user navigated to Reports page
    And Create and run a report

  Scenario: Verify Documents Requiring Security Assignment
    Then Verify Documents Requiring Security Assignment

  Scenario: Verify Documents Requiring Review
    Then Verify Documents Requiring Review

  Scenario: Verify Open Investigations
    Then Verify Open Investigations