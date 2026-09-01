Feature: Patient File - Event Management - Lab Report

  Background:
    Given I am logged in as secure user

  Scenario: Add new lab report without an investigation and confirm it appears in Lab reports
    Given the user navigate to the patient profile page for "63000"
    When user clicks on a patient's profile "Events" tab
    And I check the Lab reports count on the Events tab
    And user clicks on the "Add lab report" button
    And I search for Reporting Facility with Quick Code "2"
    And I select a random Program Area
    And I select a random Jursidiction
    And I select a random Resulted Test
    And I select a random Coded Result
    And I click the Add button under Resulted Tests
    And I click the submit button
    And the user navigate to the patient profile page for "63000"
    And user clicks on a patient's profile "Events" tab
    Then the Lab reports count should have increased by 1

  Scenario: Add new lab report and create an investigation from it
    Given the user navigate to the patient profile page for "63000"
    And I check the Open investigations count on the Summary tab
    When user clicks on a patient's profile "Events" tab
    And user clicks on the "Add lab report" button
    And I search for Reporting Facility with Quick Code "2"
    And user selects "STD" from the Program Area dropdown
    And user selects "Fulton County" from the Jurisdiction dropdown
    And user selects "Syphilis serology" from the Resulted Test dropdown
    And user selects "positive" from the Coded Result dropdown
    And I click the Add button under Resulted Tests
    And user clicks the Submit and Create Investigation button
    And user selects "Syphilis, Unknown" as the condition for the new investigation
    And user sets the processing decision to "FF" for a "New" investigation and submits
    And user fills the Field Follow-up investigator with Quick Code "1"
    And user sets the Field Follow-up date assigned to match the investigation start date
    And user selects "6-Yes, Notifiable" for Patient Eligible for Notification of Exposure
    And user clicks the Submit button on the investigation
    Then the investigation should be saved successfully
    And the user navigate to the patient profile page for "63000"
    Then the Open investigations count should have increased by 1

  Scenario: Cancel creation of new lab report and confirm none was created
    Given the user navigate to the patient profile page for "63000"
    When user clicks on a patient's profile "Events" tab
    And I check the Lab reports count on the Events tab
    And user clicks on the "Add lab report" button
    And I search for Reporting Facility with Quick Code "2"
    And I select a random Program Area
    And I select a random Jursidiction
    And user clicks the Cancel button on the Lab Report page and confirms
    Then the Lab reports count should not have changed

  Scenario: View an existing lab report and confirm it matches the entered data
    Given the user navigate to the patient profile page for "63000"
    When user clicks on a patient's profile "Events" tab
    And user clicks on the "Add lab report" button
    And I search for Reporting Facility with Quick Code "2"
    And user selects "STD" from the Program Area dropdown
    And user selects "Fulton County" from the Jurisdiction dropdown
    And user selects "Syphilis serology" from the Resulted Test dropdown
    And user selects "positive" from the Coded Result dropdown
    And I click the Add button under Resulted Tests
    And I click the submit button
    And the user navigate to the patient profile page for "63000"
    And user clicks on a patient's profile "Events" tab
    And user clicks the newest Lab report link
    Then the Lab Report view should show Reporting Facility "CHOA - Scottish Rite"
    And the Lab Report view should show Program Area "STD"
    And the Lab Report view should show Jurisdiction "Fulton County"
    And the Lab Report view should show Resulted Test "Syphilis serology"

  Scenario: Edit an existing lab report and confirm the change is present
    Given the user navigate to the patient profile page for "63000"
    When user clicks on a patient's profile "Events" tab
    And user clicks the newest Lab report link
    And user clicks the Edit button on the Lab Report page
    And user changes the Specimen Collection Date to today's date on the Lab Report edit page
    And user clicks the Submit button on the Lab Report edit page
    And the user navigate to the patient profile page for "63000"
    And user clicks on a patient's profile "Events" tab
    And user clicks the newest Lab report link
    Then the Lab Report view should show the updated Specimen Collection Date

  Scenario: Delete existing lab report and confirm it is removed
    Given the user navigate to the patient profile page for "63000"
    When user clicks on a patient's profile "Events" tab
    And I check the Lab reports count on the Events tab
    And user clicks the newest Lab report link
    And user clicks the Delete button on the Lab Report page and confirms
    And the user navigate to the patient profile page for "63000"
    And user clicks on a patient's profile "Events" tab
    Then the Lab reports count should have decreased by 1

  Scenario: Create an investigation from an existing lab report
    When I add and import an ELR for the "GCD" program area
    And I navigate to "/nbs/HomePage.do?method=loadHomePage" path
    And I search for and open the patient file with the email "test@hogwarts.edu"
    And I click the "Events" button
    And I click entry 1 under the "Lab reports" section
    And I click the "Create Investigation" button
    And Select "Hepatitis A, acute" condition from the dropdown in Select Condition Page
    And I click the submit button
    And Click submit on edit page in Open Investigation
    Then I should see a green success message containing text "Investigation has been successfully saved in the system."
    When I click on the "View File" link
    Then I check the "Open investigations" section contains an entry with "Hepatitis A, acute"
    When I click the "Events" button
    Then I check the column "Associated with" for entry 1 under the "Lab reports" section contains "Hepatitis A, acute"

  Scenario: Associate and disassociate investigation from existing lab report
    When I add and import an ELR for the "GCD" program area
    And I navigate to "/nbs/HomePage.do?method=loadHomePage" path
    And I search for and open the patient file with the email "test@hogwarts.edu"
    And I click the "Events" button
    And I click the "Add investigation" button
    And Select "Hepatitis A, acute" condition from the dropdown in Select Condition Page
    And I click the submit button
    And Click submit on edit page in Open Investigation
    Then I should see a green success message containing text "Investigation has been successfully saved in the system."
    When user is able to click the "Return To File: Events" link to return to Patient Profile Summary page
    Then I check the column "Associated with" for entry 1 under the "Lab reports" section contains "---"
    When I click entry 1 under the "Investigations" section
    And the user clicks on Manage Associations
    And I "associate" entry 1 under the "Lab Reports" section with the investigation
    And user is able to click the "Return to File: Summary" link to return to Patient Profile Summary page
    And I click the "Events" button
    Then I check the column "Associated with" for entry 1 under the "Lab reports" section contains "Hepatitis A, acute"
    # unassociate lab report
    And I click entry 1 under the "Investigations" section
    Then I can open the investigation for "Hepatitis A, acute"
    When the user clicks on Manage Associations
    And I "disassociate" entry 1 under the "Lab Reports" section with the investigation
    And user is able to click the "Return to File: Summary" link to return to Patient Profile Summary page
    And I click the "Events" button
    Then I check the column "Associated with" for entry 1 under the "Lab reports" section contains "---"

  Scenario: Print the lab report
    When I add and import an ELR for the "GCD" program area
    And I navigate to "/nbs/HomePage.do?method=loadHomePage" path
    And I search for and open the patient file with the email "test@hogwarts.edu"
    And I click the "Events" button
    And I click entry 1 under the "Lab reports" section
    And I can print the lab report

  Scenario: Transfer ownership of lab report
    When I add and import an ELR for the "GCD" program area
    And I navigate to "/nbs/HomePage.do?method=loadHomePage" path
    And I search for and open the patient file with the email "test@hogwarts.edu"
    And I click the "Events" button
    And I click entry 1 under the "Lab reports" section
    And I click the "TransferOwn" input button
    And I change the program area to "ARBO"
    And I change the jurisdiction to "Gwinnett County"
    And I click the submit button
    Then I see a transfer confirmation message to program area "ARBO" and jurisdiction "Gwinnett County"
    When I click on the "View File" link
    Then I check the column "Program area" for entry 1 under the "Lab reports" section contains "ARBO"
    And I check the column "Jurisdiction" for entry 1 under the "Lab reports" section contains "Gwinnett County"

  Scenario: Mark lab report as reviewed - non-STD
    When I add and import an ELR for the "GCD" program area
    And I navigate to "/nbs/HomePage.do?method=loadHomePage" path
    And I search for and open the patient file with the email "test@hogwarts.edu"
    And I click the "Events" button
    And I click entry 1 under the "Lab reports" section
    And I click the "markReviewd" input button
    Then I should see a green success message containing text "The Lab Report has been successfully marked as Reviewed."

  Scenario: Mark lab report as reviewed - STD
    When I add and import an ELR for the "STD" program area
    And I navigate to "/nbs/HomePage.do?method=loadHomePage" path
    And I search for and open the patient file with the email "test@hogwarts.edu"
    And I click the "Events" button
    And I click entry 1 under the "Lab reports" section
    And I click the "markReviewd" input button
    # can't mark as reviewed because of popup flow so just check the pop up opens
    Then I see a pop up to mark the STD lab report as reviewed
