Feature: I can submit a search

  Scenario: I can navigate to the simple search page and perform a search
    When I navigate to the simple search page
    Then I enter the Last Name "Singh"
    And I enter the First Name "Surma"
    And I select gender as a "Female"
    And I enter the Date of Birth "10/11/2022"
    And I click on search button
