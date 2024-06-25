@parallel=true
Feature:Before labs and providers can leverage the Data Ingestion APIs, they must first acquire an authentication token. Registered labs and
  providers can obtain this token by providing their client username and secret.


  @token
  Scenario: Allow users to create a new token when correct header info is added
    * configure headers = { clientid: '#(clientid)', clientsecret: '#(clientsecret)' }
    Given url tokenurl
    When method POST
    Then status 200
    * def token = response

  @token
  Scenario: Validate that the token is not generated when clientsecret header is missing
    * configure headers = { clientid: '#(clientid)' }
    Given url tokenurl
    When method POST
    Then status 400
    Then match response.detail == "Required header 'clientsecret' is not present."

  @token
  Scenario: Validate that the token is not generated when clientid header is missing
    * configure headers = { clientsecret: '#(clientsecret)' }
    Given url tokenurl
    When method POST
    Then status 400
    Then match response.detail == "Required header 'clientid' is not present."

  @token
  Scenario: Validate that the token is not generated when both clientid and clientsecret headers are missing
    Given url tokenurl
    When method POST
    Then status 400
    Then match response.detail == "Required header 'clientid' is not present."

  @token
  Scenario: Validate that the token is not generated when both clientid and clientsecret headers are added but incorrect clientsecret value is entered.
    * configure headers = { clientid: '#(clientid)', clientsecret: 'dummyclientsecret' }
    Given url tokenurl
    When method POST
    Then status 401
    Then match karate.toString(response) contains "Invalid client or Invalid client credentials"
  @token
  Scenario: Validate that the token is not generated when both clientid and clientsecret headers are added but incorrect clientid value is entered.
    * configure headers = { clientid: 'dummyclientid', clientsecret: '#(clientsecret)' }
    Given url tokenurl
    When method POST
    Then status 401
    Then match karate.toString(response) contains "Invalid client or Invalid client credentials"

  @token
  Scenario: Validate that the token is not generated when both clientid and clientsecret headers are added with incorrect values.
    * configure headers = { clientid: 'dummyclientid', clientsecret: 'dummyclientsecret' }
    Given url tokenurl
    When method POST
    Then status 401
    Then match karate.toString(response) contains "Invalid client or Invalid client credentials"

  @token
  Scenario: Validate that the token is not generated when both clientid and clientsecret headers are added with missing values.
    * configure headers = { clientid: '', clientsecret: '' }
    Given url tokenurl
    When method POST
    Then status 401
    Then match karate.toString(response) contains "Invalid client or Invalid client credentials"

  @token
  Scenario: Validate that the token is not generated when both clientid and clientsecret headers are added with missing clientid value.
    * configure headers = { clientid: '', clientsecret: '#(clientsecret)' }
    Given url tokenurl
    When method POST
    Then status 401
    Then match karate.toString(response) contains "Invalid client or Invalid client credentials"

  @token
  Scenario: Validate that the token is not generated when both clientid and clientsecret headers are added with missing clientsecret value.
    * configure headers = { clientid: '#(clientid)', clientsecret: '' }
    Given url tokenurl
    When method POST
    Then status 401
    Then match karate.toString(response) contains "Invalid client or Invalid client credentials"
