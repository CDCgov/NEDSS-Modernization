Feature: Verify Open Investigation Queue functionality

Background: 
    Given I am logged in as secure user and stay on classic

  Scenario: Accessing and navigating Open Investigation Queue
    When I click on "Open Investigation" in the menu bar
    Then I should land on the "Open Investigation Queue" page

  Scenario: Navigating through pages
    When I click on "Open Investigation" in the menu bar
    When I click on the "Next" link
    Then I should see the next page of results
    When I click on the "Previous" link
    Then I should see the previous page of results

  Scenario: Sorting by Case Status
    When I click on "Open Investigation" in the menu bar
    When I open the sort menu
    And I click on "Select All"
    And I select "Confirmed" from the sort options
    And I click the "Ok" button
    Then I should see the results sorted by "Confirmed" status
    When I open the sort menu
    And I deselect "Confirmed"
    And I select "Probable" from the sort options
    And I click the "Cancel" button
    Then I should see no change in sorting

  Scenario: Removing filters and sorts
    When I click on "Open Investigation" in the menu bar
    When I click on the "Remove All Filters Sorts" link
    Then all filters and sorts should be cleared