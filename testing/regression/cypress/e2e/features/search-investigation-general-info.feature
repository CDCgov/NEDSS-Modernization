Feature: Investigation Search by general search

  Background:
    Given I am logged in as "superuser" and password ""
    Given I navigate the event investigation

  Scenario: Basic Info - Search by Condition
    When I select a condition for event investigation
    Then I should see Results with the link "Dengue"

  Scenario: Basic Info - Search by Program Area
    When I select a program area for event investigation
    Then I should see Results with the link "Dengue"

  Scenario: Basic Info - Search by Jurisdiction
    When I select a jurisdiction for event investigation
    Then I should see Results with the text "Cobb County"

  Scenario: Basic Info - Search by Pregnancy
    When I select a pregnancy for event investigation
    Then I should see No Results found text

  Scenario: Basic Info - Search by Event created by user
    When I select a user created by for event investigation
    Then I should see Results with the link "Dengue"

  Scenario: Basic Info - Search by Event date range
    When I select a date event range for event investigation
    Then I should see Results with the link "Dengue"

  Scenario: Basic Info - Search by Event id type
    When I select a event id type for event investigation
    Then I should see Results with the link "Hansen disease (Leprosy)"
