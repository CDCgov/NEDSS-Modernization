Feature: Classic NBS - Dedupe - User can view data in NBS Organization Search Page

  Background:
    Given I am logged in as secure user and stay on classic

  # Data Entry Tests

  ## Search Organization

  Scenario: Verify organization exists by searching name
    When I navigate to classic organization Search pane
    And I enter organization name in input text field "Piedmont Hospital"
    And I click on the Submit button 
    Then the search results should include "Piedmont Hospital"

  Scenario: Verify organization exists by searching address
    When I navigate to classic organization Search pane
    And I enter organization address in input text field "1364 Clifton Road"
    And I click on the Submit button
    Then the search results should include "Emory University Hospital"
    And the search results should include address "1364 Clifton Road"

  Scenario: Add new organization with basic info
    When I navigate to classic organization Search pane
    And I enter a unique organization name in the name field
    And I click on the Submit button 
    Then I should see a message that no matching organizations were found
    And I click the Add button
    And I select the role "Allergy clinic"
    And I click on the Submit button 
    And I click the Add button
    And I navigate to classic organization Search pane
    And I enter the same organization name in the Search Pane
    And I click on the Submit button 
    Then the organization should appear in search results
  
  Scenario: Add new organization with all info
    When I navigate to classic organization Search pane
    And I enter a unique organization name in the name field
    And I click on the Submit button 
    Then I should see a message that no matching organizations were found
    And I click the Add button
    And I select a random ID Type
    And I select a random Assigning Authority
    And I enter a random ID Value
    And I click the Add Identification button
    And I select address use "Primary Work Place"
    And I select address type "Office"
    And I enter street address "1600 Clifton Road NE"
    And I enter city "Atlanta"
    And I enter zip code "30333"
    And I click the Add Address button
    And I select telephone use "Primary Work Place"
    And I select telephone type "Phone"
    And I enter telephone number "404", "639", "3311"
    And I click the Add Telephone button
    And I click on the Submit button 
    And I navigate to classic organization Search pane
    And I enter the same organization name in the Search Pane
    And I click on the Submit button 
    Then the organization should appear in search results

  ## View Organization

  Scenario: All organization details exist
    When I navigate to classic organization Search pane
    And I enter organization name in input text field "Piedmont Hospital"
    And I click on the Submit button 
    And I click View on the search results
    Then I should see all organization details are correct

  Scenario: Edit with overwrite
    When I navigate to classic organization Search pane
    And I enter organization name in input text field "CHOA - Scottish Rite"
    And I click on the Submit button
    And I click View on the search results
    And I click the Edit button
    And I select "Typographical error correction or additional information" as the reason for edit
    And I select address use "Primary Work Place"
    And I select address type "Office"
    And I enter street address "1600 Clifton Road NE"
    And I enter city "Atlanta"
    And I enter zip code "30333"
    And I click the Add Address button
    And I select telephone use "Primary Work Place"
    And I select telephone type "Phone"
    And I enter telephone number "404", "639", "3311"
    And I click the Add Telephone button
    And I click on the Submit button
    And I navigate to classic organization Search pane
    And I enter organization name in input text field "CHOA - Scottish Rite"
    And I click on the Submit button
    Then the search results should include "CHOA - Scottish Rite"
    And the search results should include address "1600 Clifton Road NE"
    And the search results should include telephone "404-639-3311"

  Scenario: Edit with new organization
    When I navigate to classic organization Search pane
    And I enter organization name in input text field "CHOA - Scottish Rite"
    And I click on the Submit button
    And I save the number of search results
    And I click View on the search results
    And I click the Edit button
    And I select "A change to existing information for non typographical reasons" as the reason for edit
    And I enter a unique quick code
    And I delete the existing address
    And I select address use "Organizational Contact"
    And I select address type "Postal/Mailing"
    And I enter street address "456 Main Street"
    And I click the Add Address button
    And I click on the Submit button
    And I navigate to classic organization Search pane
    And I enter organization name in input text field "CHOA - Scottish Rite"
    And I click on the Submit button
    Then there should be 1 more search result than before
    And one of the search results should have the generated quick code
    And the search results should include address "456 Main Street"

  Scenario: Provide multiple contact records (two new telephone numbers) for organization
    When I navigate to classic organization Search pane
    And I enter organization name in input text field "Northside Hospital"
    And I click on the Submit button
    And I click View on the search results
    And I click the Edit button
    And I select "Typographical error correction or additional information" as the reason for edit
    And I add a new telephone with use "Primary Work Place", type "Phone", number "770"-"555"-"1234"
    And I add a new telephone with use "Alternate Work Place", type "Phone", number "678"-"555"-"5678"
    And I click on the Submit button
    And I navigate to classic organization Search pane
    And I enter organization name in input text field "Northside Hospital"
    And I click on the Submit button
    Then the search results should include telephone "770-555-1234"
    And the search results should include telephone "678-555-5678"

  Scenario: Provide multiple contact records (two new addresses) for organization
    When I navigate to classic organization Search pane
    And I enter organization name in input text field "St. Joseph's Hospital"
    And I click on the Submit button
    And I click View on the search results
    And I click the Edit button
    And I select "Typographical error correction or additional information" as the reason for edit
    And I select address use "Alternate Work Place"
    And I select address type "Office"
    And I enter street address "123 Drury Lane"
    And I enter city "Oakland"
    And I enter zip code "94611"
    And I click the Add Address button
    And I select address use "Organizational Contact"
    And I select address type "Postal/Mailing"
    And I enter street address "456 Main Street"
    And I enter city "Oakland"
    And I enter zip code "94611"
    And I click the Add Address button
    And I click on the Submit button
    And I navigate to classic organization Search pane
    And I enter organization name in input text field "St. Joseph's Hospital"
    And I click on the Submit button
    Then the search results should include address "123 Drury Lane"
    And the search results should include address "456 Main Street"

  Scenario: Provide multiple ID records for organization
    When I navigate to classic organization Search pane
    And I enter organization name in input text field "St. Joseph's Hospital"
    And I click on the Submit button
    And I click View on the search results
    And I click the Edit button
    And I select "Typographical error correction or additional information" as the reason for edit
    And I add a new ID record with type "ABCs Hospital ID", authority "CMS Provider", value "CMS1234"
    And I add a new ID record with type "Organization identifier", authority "CLIA (CMS)", value "CLIA5678"
    And I click on the Submit button
    And I navigate to classic organization Search pane
    And I enter organization name in input text field "St. Joseph's Hospital"
    And I click on the Submit button
    Then the search results should include ID Type "ABCs Hospital ID" with ID Value "CMS1234"
    And the search results should include ID Type "Organization identifier" with ID Value "CLIA5678"

  Scenario: Inactivate an existing organization

    When I navigate to classic organization Search pane
    And I enter a unique organization name in the name field
    And I click on the Submit button
    And I click the Add button
    And I select the role "Allergy clinic"
    And I click on the Submit button 
    And I click the Add button
    And I navigate to classic organization Search pane
    And I enter the same organization name in the Search Pane
    And I click on the Submit button 
    And I click View on the search results
    And I click the Inactivate button
    And I confirm the inactivation
    And I navigate to classic organization Search pane
    And I enter the same organization name in the Search Pane
    And I click on the Submit button
    Then I should see a message that no matching organizations were found