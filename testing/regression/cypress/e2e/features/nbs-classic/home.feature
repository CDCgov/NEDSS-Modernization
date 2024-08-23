Feature: User can view data in classic NBS Home

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

