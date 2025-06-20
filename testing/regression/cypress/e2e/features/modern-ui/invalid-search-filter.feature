Feature: Classic NBS - Modern Search - User can search using filter with invalid data

  Background:
    Given I am logged in as secure user and stay on classic
    Given I am on the modernized Patient Search page

  Scenario: Search patient by with invalid Patient ID    
    Then I feel input id "name.last" with text "rat"
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-id" with "zzz"  
    Then I should see No Results found text

  Scenario: Search patient by with invalid Last Name    
    Then I feel input id "name.last" with text "rat"
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-name" with "555"    
    Then I should see No Results found text

  Scenario: Search patient by with invalid  Patient DOB
    Then I feel input id "name.last" with text "rat"
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-ageOrDateOfBirth" with "ZZZ"
    Then I should see No Results found text

  Scenario: Search patient by with invalid  Patient Address
    Then I feel input id "name.last" with text "rat"
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-address" with "xxxt"
    Then I should see No Results found text

  Scenario: Search patient by with invalid Gender
    Then I feel input id "name.last" with text "rat"
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-sex" with "8888"
    Then I should see No Results found text

  Scenario: Search patient by with invalid Patient Phone
    Then I feel input id "name.last" with text "rat"
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-phone" with "8888"
    Then I should see No Results found text

  Scenario: Search patient by with invalid ID
    Then I feel input id "name.last" with text "rat"
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-identification" with "zzz"
    Then I should see No Results found text

  Scenario: Search patient by with invalid Email
    Then I feel input id "name.last" with text "rat"
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-email" with "zzz"
    Then I should see No Results found text
