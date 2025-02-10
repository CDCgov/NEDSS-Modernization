@notification_processing_status
Feature: Notification status from TransportQ_out table

  Background:
    Given I am logged in
    And I can "VIEW" any "INVESTIGATION"
    And I have a patient
    And the patient is a subject of an investigation
    And the investigation has a notification status of APPROVED


  Scenario: I can view a notification's status in the TransportQ_out table
    Given the notification exists in the CN_TransportQ_out table with status of queued
    When I query for a notifications transport status
    Then I receive a notification transport status of queued

  Scenario: I can view a notification's status in the TransportQ_out table
    When I query for a notifications transport status
    Then I receive a notification transport status of null
