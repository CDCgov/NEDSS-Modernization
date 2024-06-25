@postMessagesFeature113
@parallel=false
Feature: Read various HL7 messages from JSON file and Post them using a REST API

  Background:
    * callonce read('common.feature')
    * header Authorization = 'Bearer ' + token
    * configure headers = { clientid: '#(clientid)', clientsecret: '#(clientsecret)' }
    * def Thread = Java.type('java.lang.Thread')
    * def oldfirstname = 'LinkLogic'
    * def oldlastname = 'datateam'
    * def config = karate.call('classpath:karate-config.js')
    * def FakerHelper = Java.type('com.api.dataingestionautomation.API.FakerHelper')
    * def randomFirstName = FakerHelper.getRandomFirstName()
    * def randomLastName = FakerHelper.getRandomLastName()
    * def finalmessages = []
    * def modifiedData = {}
    * def appendToFinalMessages = function(msg){ karate.get('finalmessages').push(msg); }



  Scenario: Read messages from JSON and post each message
    * def messages = read('try.json')
    * def results = call read('@postMessage') messages
    * def responses = results.map(response => response.response)
    * print 'Initial UUID Responses:', responses
    * def transformedResponses = responses.map(id => { return { 'ID': id } })
    * print transformedResponses
    * def jsonResponse = karate.toJson(transformedResponses)
    * print jsonResponse
    * def ids = transformedResponses
    * print ids
    * eval Thread.sleep(130000)
    * def statusResults = call read('@checkstatus') ids
    * karate.forEach(statusResults, function(item){ appendToFinalMessages(item.finalMessage); })
    * print 'Final Messages:', finalmessages

  @postMessage @ignore
  Scenario: Post HL7 messages into Data service
    * header Content-Type = 'text/plain'
    * header msgType = 'HL7'
    * url apiurl
    * def hl7Message = data
    * def modifiedmsg = hl7Message.replace(oldfirstname, randomFirstName)
    * def modifiedData = modifiedmsg.replace(oldlastname, randomLastName)
    * request modifiedData
    * method post
    * status 200
    * karate.set('currentModifiedData', modifiedData)

  @checkstatus @ignore
  Scenario: Validate the status of the posted HL7 messages
    * def id = ID
    * def modifiedData = modifiedData
    * header Authorization = 'Bearer ' + token
    * def newurl = checkstatusurl + id
    * url newurl
    * method get
    * status 200
    * def NBSresponse = response.status
    * def NBSerrorresponse = response.error_message
    * def handleFailureOrQueued = NBSresponse == 'Failure' || NBSresponse == 'QUEUED'
    * def Success = NBSresponse == 'Success'
    * def isNotSuccess = NBSerrorresponse == 'Provided UUID is not present in the database. Either provided an invalid UUID or the injected message failed validation.'
    * eval Thread.sleep(5000)
    * def modifiedData = karate.get('currentModifiedData')
    * def finalMessage = 'ID: ' + id + ', Modified Data: ' + modifiedData + ', NBS Response: ' + NBSresponse
    * def errorFeatureResult = isNotSuccess ? karate.call('error.feature', { id: id }) : null
    * if (isNotSuccess) finalMessage += ', Test Case failed at DI validation'
    * if (errorFeatureResult != null) finalMessage += ', ' + errorFeatureResult.errorresponse
    * if (handleFailureOrQueued) finalMessage += ', Test Case failed at NBS validation'
    * if (Success) finalMessage += ', Test Case Passed'
    * karate.set('finalMessage', finalMessage)