Feature: Classic NBS - Modern Search - User can search and filter for patients

  Background:
    Given I am logged in as secure user and stay on classic
    And I am on the modernized Patient Search page

  Scenario: Search patient by exact Patient ID and reset
    When I fill input id "name.last" with text "sing"
    And I select input id "name.lastOperator" with type "Contains"
    And Click on Search in Patient Search pane
    And I click search filter result icon
    And I search filter column "text-filter-id" with "123"
    Then I should see No Results found text
    When Clear search filter "text-filter-id"
    Then Verify top Search result by "63000"

  Scenario: Clear and Search patient by Patient Name
    When I fill input id "name.last" with text "sing"
    And I select input id "name.lastOperator" with type "Contains"
    And Click on Search in Patient Search pane
    And I click search filter result icon
    And I search filter column "text-filter-name" with "xxxx"
    Then I should see No Results found text
    When Clear search filter "text-filter-name"
    Then Verify top Search result by "Sing"

  Scenario: Clear and Search patient by DOB
    When I fill input id "name.last" with text "sing"
    And I select input id "name.lastOperator" with type "Contains"
    And Click on Search in Patient Search pane
    And I click search filter result icon
    And I search filter column "text-filter-ageOrDateOfBirth" with "1255"
    Then I should see No Results found text
    When Clear search filter "text-filter-ageOrDateOfBirth"
    Then Verify top Search result by "1990"

  Scenario: Clear and Search patient by Sex
    When I fill input id "name.last" with text "sing"
    And I select input id "name.lastOperator" with type "Contains"
    And Click on Search in Patient Search pane
    And I click search filter result icon
    And I search filter column "text-filter-sex" with "j"
    Then I should see No Results found text
    When Clear search filter "text-filter-sex"
    Then Verify top Search result by "Male"

  Scenario: Clear and Search patient by Address
    When I fill input id "name.last" with text "sing"
    And I select input id "name.lastOperator" with type "Contains"
    And Click on Search in Patient Search pane
    And I click search filter result icon
    And I search filter column "text-filter-address" with "zzzzzz"
    Then I should see No Results found text
    When Clear search filter "text-filter-address"
    Then Verify top Search result by "123 Main"

  Scenario: Clear and Search patient by Phone Number
    When I fill input id "name.last" with text "sing"
    And I select input id "name.lastOperator" with type "Contains"
    And Click on Search in Patient Search pane
    And I click search filter result icon
    And I search filter column "text-filter-phone" with "999999999"
    Then I should see No Results found text
    When Clear search filter "text-filter-phone"
    Then Verify top Search result by "232-322-2222"

  Scenario: Clear and Search patient by ID
    When I fill input id "name.last" with text "sing"
    And I select input id "name.lastOperator" with type "Contains"
    And Click on Search in Patient Search pane
    And I click search filter result icon
    And I search filter column "text-filter-identification" with "xx"
    Then I should see No Results found text
    When Clear search filter "text-filter-identification"
    Then Verify top Search result by "3453453533"

  Scenario: Clear and Search patient by Email
    When I fill input id "name.last" with text "sing"
    And I select input id "name.lastOperator" with type "Contains"
    And Click on Search in Patient Search pane
    And I click search filter result icon
    And I search filter column "text-filter-identification" with "xx"
    Then I should see No Results found text
    When Clear search filter "text-filter-identification"
    Then Verify top Search result by "fdsfs@dsds.com"
