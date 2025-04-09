Feature: Classic NBS - Modern Search - User can search and filter for patients

  Background:
    Given I am logged in as secure user and stay on classic
    Given I am on the modernized Patient Search page

  Scenario: Search patient by exact Patient ID    
    Then I feel input id "name.last" with text "rat"
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-id" with "74146"
    Then I verify unique search row contains "74146"

  Scenario: Search patient by exact Patient exact Last Name    
    Then I feel input id "name.last" with text "rat"
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-name" with "Ratkeyklkb"
    Then I verify unique search row contains "Ratkeyklkb, Caden Benjamin, Esquire"

  Scenario: Search patient by exact Patient DOB
    Then I feel input id "name.last" with text "rat"
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-ageOrDateOfBirth" with "05/16/1961"
    Then I verify unique search row contains "05/16/1961"

  Scenario: Search patient by exact Patient Address
    Then I feel input id "name.last" with text "rat"
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-address" with "90 SE Panda, unit 40606"
    Then I verify unique search row contains "Home"
    Then I verify unique search row contains "90 SE Panda, unit 40606"
    Then I verify unique search row contains "East Melissa, MA 30342"

  Scenario: Search patient by exact Patient Phone
    Then I feel input id "name.last" with text "rat"
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-phone" with "732-721-2970"
    Then I verify all search rows contains "732-721-2970"

  Scenario: Search patient by exact ID
    Then I feel input id "name.last" with text "rat"
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-identification" with "986-37-4923"
    Then I verify unique search row contains "986-37-4923"

  Scenario: Search patient by exact ID
    Then I feel input id "name.last" with text "rat"
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-email" with "CadenRatkeyklkb79@hotmail.com"
    Then I verify unique search row contains "CadenRatkeyklkb79@hotmail.com"
