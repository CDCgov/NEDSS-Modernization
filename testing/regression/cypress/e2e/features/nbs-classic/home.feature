Feature: User can view data in classic NBS Home

  Background:
    Given I am logged in as secure user and stay on classic

  Scenario: Display selected patient search
    Then Navigate to Patient Search pane
    And Enter Last Name text box input "Smith"
    Then Click on Search in Patient Search pane

