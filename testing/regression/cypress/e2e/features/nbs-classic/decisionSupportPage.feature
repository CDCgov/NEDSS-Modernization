Feature: Verify Decision Support Alert Management functionality

Background: 
    Given I am logged in as secure user and stay on classic

  Scenario: Managing Decision Support Alerts
    When I click on the "System Management" link in the menu
    And I expand the Decision Support Management menu
    And I click on Manage Alerts
    And I select "2019 Novel Coronavirus" from the "Condition" dropdown
    And I select "Cobb County" from the Jurisdiction dropdown
    And I select "Case Report" from the Public Health Event dropdown
    And I click the Search button
    And I select "High" for Severity from the dropdown
    And I enter "Test alert set" in the Extended Alert Message box
    And I click the Add Alert button
    Then I should see the error 'Please fix the following errors:'
