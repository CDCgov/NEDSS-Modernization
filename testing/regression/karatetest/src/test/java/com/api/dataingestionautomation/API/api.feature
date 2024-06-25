@parallel=true
Feature: Test the API functionality scenarios

  Background:
    * callonce read('common.feature')
    * header Authorization = 'Bearer ' + token
    * header Content-Type = 'text/plain'
    * configure headers = { clientid: '#(clientid)', clientsecret: '#(clientsecret)' }

@api
  Scenario: Transmit an empty HL7 message via POST method successfully and capture the error response
    * header msgType = 'HL7'
    Given url apiurl
    And request ''
    When method POST
    Then status 400
    Then match response.detail == "Failed to read request"

  @api
  Scenario: Transmit a valid Hl7 message via incorrect endpoint URL and validate the error response
    * header msgType = 'HL7'
    Given url wrongapiurl
    And request 'abdef'
    When method POST
    Then status 404
    Then match response.error == "Not Found"

  @api
  Scenario: System should not let users transmit an HL7 message with missing msgType header information
    Given url apiurl
    And request 'abdef'
    When method POST
    Then status 400
    Then match response.detail == "Required header 'msgType' is not present."


  @api
  Scenario: Transmit a valid Hl7 message with just the HL7 header information
    * header msgType = 'HL7'
    * def FakerHelper = Java.type('com.api.dataingestionautomation.API.FakerHelper')
    * def oldfirstname = 'LinkLogic'
    * def randomFirstName = FakerHelper.getRandomFirstName()
    * def hl7Message = "MSH|^~\&|LinkLogic^^|LABCORP^34D0655059^CLIA|ALDOH^^|AL^^|202305251105||ORU^R01^ORU_R01|202305221034-A|P^|2.5.1"
    * def modifiedmsg = hl7Message.replace(oldfirstname, randomFirstName)
    Given url apiurl
    And request modifiedmsg
    When method POST
    Then status 200

  @api
  Scenario: System should not let users transmit an HL7 message with missing clientid and clientsecret header information
    Given url apiurl
    * configure headers = {}
    And request 'abdef'
    When method POST
    Then status 401
    Then match response.details == "Client ID and Client Secret are required"

  @api
  Scenario: System should not let users transmit an HL7 message with missing clientid header information
    Given url apiurl
    * configure headers = { clientsecret: '#(clientsecret)' }
    And request 'abdef'
    When method POST
    Then status 401
    Then match response.details == "Client ID and Client Secret are required"

  @api
  Scenario: System should not let users transmit an HL7 message with missing clientsecret header information
    Given url apiurl
    * configure headers = { clientid: '#(clientid)' }
    And request 'abdef'
    When method POST
    Then status 401
    Then match response.details == "Client ID and Client Secret are required"

  @api
  Scenario: System should not let users transmit an HL7 message with incorrect clientid header value
    Given url apiurl
    * configure headers = { clientid: 'dummyclientid', clientsecret: '#(clientsecret)' }
    And request 'abdef'
    When method POST
    Then status 401
    Then match response.details == "Invalid client or Invalid client credentials"

  @api
  Scenario: System should not let users transmit an HL7 message with incorrect clientsecret header value
    Given url apiurl
    * configure headers = { clientid: '#(clientid)', clientsecret: 'dummycleintsecret' }
    And request 'abdef'
    When method POST
    Then status 401
    Then match response.details == "Invalid client or Invalid client credentials"


  @api
  Scenario: System should not let users transmit an HL7 message with incorrect msgType header value
    * header msgType = 'dummyvalue'
    Given url apiurl
    And request 'abdef'
    When method POST
    Then status 500
    Then match response.details == "Please provide valid value for msgType header"

  @api
  Scenario: System should not let users transmit an HL7 message with empty msgType header value
    * header msgType = ''
    Given url apiurl
    And request 'abdef'
    When method POST
    Then status 500
    Then match response.details == "Required headers should not be null"


  @api
  Scenario: System should not let users transmit an HL7 message with empty clientid header value
    * header msgType = 'HL7'
    * configure headers = { clientid: '', clientsecret: 'dummycleintsecret' }
    Given url apiurl
    And request 'abdef'
    When method POST
    Then status 401
    Then match response.details == "Client ID and Client Secret are required"

  @api
  Scenario: System should not let users transmit an HL7 message with empty clientsecret header value
    * header msgType = 'HL7'
    * configure headers = { clientid: '#(clientid)', clientsecret: '' }
    Given url apiurl
    And request 'abdef'
    When method POST
    Then status 401
    Then match response.details == "Client ID and Client Secret are required"

  @api
  Scenario: System should not let users transmit an HL7 message with missing authorization
    * header msgType = 'HL7'
    * header Authorization = null
    Given url apiurl
    And request 'abdef'
    When method POST
    Then status 401
    Then match response.details == "Full authentication is required to access this resource"

  @api
  Scenario: System should not let users transmit an HL7 message with incorrect token
    * header msgType = 'HL7'
    * header Authorization = 'Bearer ' + 'abcdedgdgdfgdfhgdfhdfhjdfhjdjj'
    Given url apiurl
    And request 'abdef'
    When method POST
    Then status 401
    Then match response.details == "Provided token isn't active"




