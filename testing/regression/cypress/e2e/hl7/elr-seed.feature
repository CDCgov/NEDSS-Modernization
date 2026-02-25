@skip-if-no-di-api
Feature: End to end testing of ELR processing

  Background:
    Given I am authenticated with the DI API
    Given I am logged in as secure user

#  Scenario: A Hepatitis B seed notification is created automatically when ingesting #an ELR
#    Given I have a HL7 seed with: gender "F", test type "Hepatitis B", first #"Zzzalice", last "Zzzratkey", middle "ZzzBennice", suffix "ESQ", ssn "123456789", email "Zzzalice@gmail.com", street "856 Zz Zzanda", state "WY", #city "Cheyenne", zipcode "82001", building "unit 492", dob "1955"
#    When I submit the HL7 message
#
#  Scenario: A Hepatitis B seed notification is created automatically when ingesting #an ELR
#    Given I have a HL7 seed with: gender "F", test type "Hepatitis B", first "Aaaalice", last "Aaaratkey", middle "Aaamiddle", suffix "ESQ", ssn "111100000", email "Aaaalice@gmail.com", street "856 Aaa Aaanda", state "AL", city "Montgomery", zipcode "36104", building "unit 99", dob "1958"
#    When I submit the HL7 message
#
#   Scenario: A Hepatitis B seed notification is created for Male
#    Given I have a HL7 seed with: gender "M", test type "Hepatitis B", first "Aaacaden", last "Aaaratkey", middle "Aaabenji", suffix "ESQ", ssn "111100002", email "Aaacaden@gmail.com", street "856 Aaa Aaanda", state "AL", city #"Montgomery", zipcode "36104", building "unit 98", dob "1960"
#    When I submit the HL7 message


#  Scenario: A Hepatitis B seed notification is created for Male
#    Given I have a HL7 seed with: gender "M", test type "Hepatitis B", first "Caden", last "Ratkeyklkb", middle "Benjamin", suffix "ESQ", ssn "123456789", email "Ratkeyklkb77@gmail.com", street "90 SE Panda", state "KY", city "East Melissa", zipcode "30342", building "unit 40606", dob "1977"
#    When I submit the HL7 message
