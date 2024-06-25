@abc11
Feature: test5y6567

  Background:
    * configure driver = { type: 'chrome' }
    * driver 'https://app.dts1.nbspreview.com/nbs/login'
    * driver.screenshot()
    * def lastname = 'Lisa'

  Scenario: Navigate to ELR message screen on UI
    * input('#id_UserName', 'superuser')
    * driver.screenshot()
    And click('img#id_Submit_bottom_ToolbarButtonGraphic')
    * delay(10000)
    * driver.screenshot()
    * click("//a[contains(text(),'Documents Requiring Review')]")
    * driver.screenshot()
    * delay(9000)
    * waitFor("//table[@id='parent']/thead/tr/th[5]/img").click()
    * driver.screenshot()
    * input("#SearchText1", lastname)
    * driver.screenshot()
    * waitFor("#b2SearchText1").click()
    * driver.screenshot()
    * click("//a[contains(text(),'Lab Report')]")
    * driver.screenshot()
    * delay(10000)


        # Scenario:  Validate the Facility and Provider Information
    * def Reporting_Facility = text('#NBS_LAB365')
    * print Reporting_Facility
    * def Ordering_Facility = text('#NBS_LAB367')
    * print Ordering_Facility
    * def OrderingProvider = text('#NBS_LAB366')
    * print OrderingProvider

  # Scenario:  Validate the Order Details Information

    * def ProgramArea = text('#INV108')
    * print ProgramArea
    * def Jurisdiction = text('#INV107')
    * print Jurisdiction
    * def Shared_Indicator = text('#NBS012L')
    * print Shared_Indicator
    * def Lab_Report_Date = text('#NBS_LAB197')
    * print Lab_Report_Date
    * def Date_Received_by_Public_Health = text('#NBS_LAB201')
    * print Date_Received_by_Public_Health
    * def PregnancyStatus = text('#INV178')
    * print PregnancyStatus
    * def Weeks = text('#NBS128L')
    * print Weeks

  # Scenario:  Validate the Ordered Test Information

    * def Ordered_Test = text('#NBS_LAB112')
    * print Ordered_Test
    * def Ordered_Test_Codes = text('#NBS_LAB269')
    * print Ordered_Test_Codes
    * def Status = text('#NBS_LAB196')
    * print Status
    * def Accession_Number = text('#LAB125')
    * print Accession_Number
    #* def Specimen_Source = text('#NBS_LAB165')
    # * print Specimen_Source
    * def Specimen_Collection_Date_or_Time = text('#LAB163')
    * print Specimen_Collection_Date_or_Time
    * def Specimen_Details = text('#NBS_LAB262')
    * print Specimen_Details

  # Scenario:  Validate the Yellow panel information on UI

    * def fullname = text('#Name')
    * print fullname
    * def Sex = text('#Sex')
    * print Sex
    * def Dob = text('#Dob')
    * print Dob
    * def Address = text('#Address')
    * print Address
    * def SSN = text('#SSN')
    * print SSN





















