Feature: Classic NBS - Modern Search - User can search and filter for patients

  Background:
    Given I am logged in as secure user and stay on classic
    Given I am on the modernized Patient Search page

  Scenario: Search patient by exact Patient ID and reset
    Then I fill input id "name.last" with text "rat"
    When I select input id "name.lastOperator" with type "Contains"
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-id" with "63000"
    Then I verify unique search row contains "63000"
    Then Clear search filter "text-filter-id"
    Then Verify top Search result is not "63000"

  Scenario: Clear and Search patient by Patient Name
    Then I fill input id "name.last" with text "rat"
    When I select input id "name.lastOperator" with type "Contains"
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-name" with "xxxx"
    Then I should see No Results found text
    Then Clear search filter "text-filter-name"
    Then Verify top Search result by "rat"

  Scenario: Clear and Search patient by DOB
    Then I fill input id "name.last" with text "rat"
    When I select input id "name.lastOperator" with type "Contains"
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-ageOrDateOfBirth" with "1255"
    Then I should see No Results found text
    Then Clear search filter "text-filter-ageOrDateOfBirth"
    Then Verify top Search result by "1958"

  Scenario: Clear and Search patient by Sex
    Then I fill input id "name.last" with text "rat"
    When I select input id "name.lastOperator" with type "Contains"
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-sex" with "j"
    Then I should see No Results found text
    Then Clear search filter "text-filter-sex"
    Then Verify top Search result by "Female"

  Scenario: Clear and Search patient by Address
    Then I fill input id "name.last" with text "rat"
    When I select input id "name.lastOperator" with type "Contains"
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-address" with "zzzzzz"
    Then I should see No Results found text
    Then Clear search filter "text-filter-address"
    Then Verify top Search result by "East Melissa"

  Scenario: Clear and Search patient by Phone Number
    Then I fill input id "name.last" with text "rat"
    When I select input id "name.lastOperator" with type "Contains"
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-phone" with "999999999"
    Then I should see No Results found text
    Then Clear search filter "text-filter-phone"
    Then Verify top Search result by "732-207-5470"

  Scenario: Clear and Search patient by ID
    Then I fill input id "name.last" with text "rat"
    When I select input id "name.lastOperator" with type "Contains"
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-identification" with "xx"
    Then I should see No Results found text
    Then Clear search filter "text-filter-identification"
    Then Verify top Search result by "123-45-6789"

  Scenario: Clear and Search patient by Email
    Then I fill input id "name.last" with text "rat"
    When I select input id "name.lastOperator" with type "Contains"
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-identification" with "xx"
    Then I should see No Results found text
    Then Clear search filter "text-filter-identification"
    Then Verify top Search result by "Aaaalice@gmail.com"
