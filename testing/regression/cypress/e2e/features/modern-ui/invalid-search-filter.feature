Feature: Classic NBS - Modern Search - User can search using filter with invalid data

  Background:
    Given I am logged in as secure user and stay on classic
    And I am on the modernized Patient Search page

  Scenario: Search patient by with invalid Patient ID    
    When I fill input id "name.last" with text "rat"
    And Click on Search in Patient Search pane
    And I click search filter result icon
    And I search filter column "text-filter-id" with "zzz"  
    Then I should see No Results found text

  Scenario: Search patient by with invalid Last Name    
    When I fill input id "name.last" with text "rat"
    And Click on Search in Patient Search pane
    And I click search filter result icon
    And I search filter column "text-filter-name" with "555"    
    Then I should see No Results found text

  Scenario: Search patient by with invalid  Patient DOB
    When I fill input id "name.last" with text "rat"
    And Click on Search in Patient Search pane
    And I click search filter result icon
    And I search filter column "text-filter-ageOrDateOfBirth" with "ZZZ"
    Then I should see No Results found text

  Scenario: Search patient by with invalid  Patient Address
    When I fill input id "name.last" with text "rat"
    And Click on Search in Patient Search pane
    And I click search filter result icon
    And I search filter column "text-filter-address" with "xxxt"
    Then I should see No Results found text

  Scenario: Search patient by with invalid Gender
    When I fill input id "name.last" with text "rat"
    And Click on Search in Patient Search pane
    And I click search filter result icon
    And I search filter column "text-filter-sex" with "8888"
    Then I should see No Results found text

  Scenario: Search patient by with invalid Patient Phone
    When I fill input id "name.last" with text "rat"
    And Click on Search in Patient Search pane
    And I click search filter result icon
    And I search filter column "text-filter-phone" with "8888"
    Then I should see No Results found text

  Scenario: Search patient by with invalid ID
    When I fill input id "name.last" with text "rat"
    And Click on Search in Patient Search pane
    And I click search filter result icon
    And I search filter column "text-filter-identification" with "zzz"
    Then I should see No Results found text

  Scenario: Search patient by with invalid Email
    When I fill input id "name.last" with text "rat"
    And Click on Search in Patient Search pane
    And I click search filter result icon
    And I search filter column "text-filter-email" with "zzz"
    Then I should see No Results found text
