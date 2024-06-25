@postMsgwithflagasfalseFeature
@parallel=false
Feature: Read various HL7 messages from JSON file and Post them using a REST API into DI service and NBS system. This is with validationActive flag as false.

  Background:
    * callonce read('common.feature')
    * header Authorization = 'Bearer ' + token
    * def responses = {}
    * def Thread = Java.type('java.lang.Thread')
    * def oldfirstname = 'LinkLogic'
    * def oldlastname = 'datateam'
    * def config = karate.call('classpath:karate-config.js')
    * def FakerHelper = Java.type('com.api.dataingestionautomation.API.FakerHelper')
    * def randomFirstName = FakerHelper.getRandomFirstName()
    * def randomLastName = FakerHelper.getRandomLastName()
    * def Thread = Java.type('java.lang.Thread')

  Scenario: Read messages from JSON and post each message
    * def messages = read('data1.json')
    * def results = call read('@postMessage') messages
    * def responses = results.map(response => response.response)
    * print 'Initial UUID Responses:', responses
    * eval Thread.sleep(130000)
    * def transformedResponses = responses.map(id => { return { 'ID': id } })
    * print transformedResponses
    * def jsonResponse = karate.toJson(transformedResponses)
    * print jsonResponse
    * def ids = transformedResponses
    * print ids
    * def statusResults = call read('@checkstatus') ids


  @postMessage @ignore
  Scenario: Post a single message
    * header Content-Type = 'text/plain'
    * header msgType = 'HL7'
    * header validationActive = 'true'
    * url apiurl
    * def hl7Message = data
    * def modifiedmsg = hl7Message.replace(oldfirstname, randomFirstName)
    * def modifiedData = modifiedmsg.replace(oldlastname, randomLastName)
    * request modifiedData
    When method post
    Then status 200

  @checkstatus @ignore
  Scenario: validate the status of the posted HL7 messages into the NBS system
    * def id = ID
    * print id
    * header Authorization = 'Bearer ' + token
    * url checkstatusurl + id
    When method get
    Then status 200
    * print response
    * match response.status == 'Success'