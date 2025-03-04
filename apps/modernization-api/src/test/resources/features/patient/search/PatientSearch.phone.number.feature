Feature: Patient Search Result Phone Numbers

  Background:
    Given I am logged into NBS
    And I can "find" any "patient"
    And I want patients sorted by "relevance" "desc"
    And I have a patient

  Scenario: I can search for a Patient using a phone number
    Given the patient has the phone number "1"-"888-240-2200" x"1009"
    And I have another patient
    And the patient has the Answering service - Temporary number of "613-240-2200"
    And patients are available for search
    And I add the patient criteria for an "phone number" equal to "613-240-2200"
    When I search for patients
    Then the search results have a patient with a Answering service - Temporary number of "613-240-2200"

  Scenario: I can search for a Patient using a partial phone number
    Given the patient has a "phone number" of "888-240-2200"
    And I have another patient
    And the patient has a "phone number" of "613-240-2200"
    And patients are available for search
    And I add the patient criteria for an "phone number" equal to "613-240-2200"
    When I search for patients
    Then the search results have a patient with an "phone number" equal to "613-240-2200"

  Scenario: I can search for a Patient using a phone number and filter˚
    Given the patient has a "phone number" of "888-240-2200"
    And I have another patient
    And the patient has a "phone number" of "613-240-2200"
    And patients are available for search
    And I add the patient criteria for an "phone number" equal to "613-240-2200"
    And I would like to filter search results with phone "32402"
    When I search for patients
    Then the search results have a patient with an "phone number" equal to "613-240-2200"

  Scenario: I can search for a Patient using a phone number and a filter that doesn't exist
    Given the patient has a "phone number" of "888-240-2200"
    And I have another patient
    And the patient has a "phone number" of "613-240-2200"
    And patients are available for search
    And I add the patient criteria for an "phone number" equal to "613-240-2200"
    And I would like to filter search results with phone "111111"
    When I search for patients
    Then there are 0 patient search results

  Scenario: I can search for a Patient using a phone number two filters
    Given the patient has a "phone number" of "888-240-2200"
    And I have another patient
    And the patient has a "phone number" of "613-240-2200"
    And the patient has an "email address" of "emailaddress@mail.com"
    And patients are available for search
    And I add the patient criteria for an "phone number" equal to "613-240-2200"
    And I would like to filter search results with email "address"
    And I would like to filter search results with phone "3-240-2"
    When I search for patients
    Then the search results have a patient with an "phone number" equal to "613-240-2200"

  Scenario: Patient Search results contain phone number order by as of date
    Given the patient has the Cellular Phone - Home phone number of "1" "888-222-3333" - "1234" as of 01/01/1999
    And the patient has the Phone - Home phone number of "1" "543-123-3333" - "54" as of 01/01/2010
    And the patient has the Phone - Home phone number of "1" "222-231-5553" - "23" as of 01/01/2020
    And patients are available for search
    And I would like to search for a patient using a local ID
    When I search for patients
    Then the search results have a patient with the 1st "phone" equal to "222-231-5553"
    And the search results have a patient with the 2nd "phone" equal to "543-123-3333"
    And the search results have a patient with the 3rd "phone" equal to "888-222-3333"

  Scenario: Patient Search results contain phone number order by uid
    Given the patient has the Cellular Phone - Home phone number of "1" "888-222-3333" - "1234" as of 01/01/1999
    And the patient has the Phone - Home phone number of "1" "543-123-3333" - "54" as of 01/01/1999
    And patients are available for search
    And I would like to search for a patient using a local ID
    When I search for patients
    Then the search results have a patient with the 1st "phone" equal to "543-123-3333"
    And the search results have a patient with the 2nd "phone" equal to "888-222-3333"

