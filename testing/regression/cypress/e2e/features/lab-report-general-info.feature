Feature: Laboratory Report Search by general search

  Background:
    Given I am logged in as secure user
    Given I navigate the event laboratory report
    Given I unselect all the lab Entry method

  Scenario: Basic Info - Search by Event date range
    When I select a date event range for laboratory report
    Then I should see Results with the link "Lab report"

  Scenario: Basic Info - Search by Event entry method
    When I select a entry method event laboratory report
    Then I should see Results with the link "Lab report"

  Scenario: Basic Info - Search by Event entered by
    When I select a entered by event laboratory report
    Then I should see No Results found text

  Scenario: Basic Info - Search by Event event status
    When I select a event status event laboratory report
    Then I should see Results with the link "Lab report"

  Scenario: Basic Info - Search by Event event process status
    When I select a process status event laboratory report
    Then I should see Results with the link "Lab report"

  Scenario: Basic Info - Search by Event entry method
    When I select a date event range for laboratory report
    Then I should see Results with the link "Lab report"

  Scenario: Basic Info - Search by Program Area
    When I select program area for event laboratory report
    Then I should see Results with the link "Lab report"

  Scenario: Basic Info - Search by Jurisdiction
    When I select a jurisdiction for event laboratory report
    Then I should see Results with the link "Lab report"

  Scenario: Basic Info - Search by Pregnancy
    When I select a pregnancy for event laboratory report
    Then I should see Results with the link "Lab report"

  Scenario: Basic Info - Search by Event id type
    When I select a event id type for event laboratory report
    Then I should see Results with the link "Lab report"

  Scenario: Basic Info - Search by User Created By
    When I select a user created by for event investigation
    Then I should see Results with the link "Lab report"

  Scenario: Basic Info - Search by Event updated by user
    When I select a user edited by for event investigation
    Then I should see Results with the link "Lab report"

  Scenario: Basic Info - Search by Event Facility
    When I select a facility for event laboratory report
    Then I should see Results with the link "Lab report"

  Scenario: Basic Info - Search by Event Provider
    When I select a provider for event laboratory report
    Then I should see No Results found text
