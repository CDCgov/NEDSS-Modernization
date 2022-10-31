Feature: I can submit a search

  Scenario: I can navigate to the simple search page and perform a search
    When I navigate to the simple search page
    And I enter search criteria
    And I click the search button
    Then a search request is sent
