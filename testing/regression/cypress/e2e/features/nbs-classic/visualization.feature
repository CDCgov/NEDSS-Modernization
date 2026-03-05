Feature: Visualizations on the Homepage

  Background:
    Given I am logged in as secure user and stay on classic

  Scenario: Verify "Cases created - Last 7 Days" visualization on the homepage
    When I select "Cases created - Last 7 Days" from the dropdown
    Then I should see the "Cases created - Last 7 Days" graph displayed

  Scenario: Verify "Labs created - Last 7 Days" visualization on the homepage
    When I select "Labs created - Last 7 Days" from the dropdown
    Then I should see the "Labs created - Last 7 Days" graph displayed

  Scenario: Verify "Confirmed Case Counts" on the homepage
    When I select "Confirmed Case Counts" from the dropdown
    Then I should see the "Confirmed Case Counts" table displayed

  Scenario: Verify "Cases Assigned - Last 14 Days" visualization on the homepage
    When I select "Cases Assigned - Last 14 Days" from the dropdown
    Then I should see the "Cases Assigned - Last 14 Days" graph displayed
