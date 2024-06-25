@abc
Feature: test5y6567

  Background:
    * configure driver = { type: 'chrome', addOptions: ["--remote-allow-origins=*"]}
    * def HL7Parser = Java.type('com.api.dataingestionautomation.API.HL7Parser')
    * def parser = new HL7Parser('src/test/java/com/api/dataingestionautomation/API/try.json')
    * def formattedMessages = parser.getFormattedMessages()
    * print 'Formatted HL7 Messages:', formattedMessages
    * karate.write(formattedMessages, 'parsed_hl7_message/formattedMessages.txt')
    * def Thread = Java.type('java.lang.Thread')
    * def mapSexValue =
"""
function(hl7msg_sex) {
  if (hl7msg_sex === 'U') return 'Unknown';
  if (hl7msg_sex === 'M') return 'Male';
  if (hl7msg_sex === 'F') return 'Female';
  else return hl7msg_sex;

}
"""
    * def formatDate =
"""
function(hl7msg_date) {
  if (hl7msg_date.length != 12) {
    throw new Error('Input date must be 12 characters long.');
  }
  var year = hl7msg_date.substring(0, 4);
  var month = hl7msg_date.substring(4, 6);
  var day = hl7msg_date.substring(6, 8);
  return month + "/" + day + "/" + year;
}
"""

    * def calculateAge =
"""
function(formattedDate) {
  var today = new Date();
  var formattedDate = new Date(formattedDate);
  var age = today.getFullYear() - formattedDate.getFullYear();
  var m = today.getMonth() - formattedDate.getMonth();
  if (m < 0 || (m === 0 && today.getDate() < formattedDate.getDate())) {
    age--;
  }
  return Math.floor(age);
}
"""



  Scenario: Navigate to ELR message screen on UI
    * driver nbsurl
    * driver.fullscreen()
    * driver.screenshot()
    * input('#username', nbsusername )
    * input('#password', '@test' )
    * driver.screenshot()
    * waitUntil("document.readyState === 'complete'")
    * waitFor('input#kc-login').click()
    * waitUntil("document.readyState === 'complete'")
    * driver.screenshot()
    * waitFor("//a[contains(text(),'Documents Requiring Review')]").click()
    * driver.screenshot()
    * waitUntil("document.readyState === 'complete'")
    * waitFor("//table[@id='parent']/thead/tr/th[5]/img").click()
    * driver.screenshot()
    * input("#SearchText1", lastName)
    * driver.screenshot()
    * waitFor("#b2SearchText1").click()
    * driver.screenshot()
    * waitFor("//a[contains(text(),'Lab Report')]").click()
    * driver.screenshot()
    * delay(1000)
    # Data within the yellow panel
    * def fullname = text('#Name').trim()
    * match fullname == firstName + '  ' + lastName
    * def Sex = text('#Sex').trim()
    * print Sex
    * def hl7msg_sex = formattedMessages.find(item => item.startsWith('PID.8')).split(' - ')[1].trim()
    * print hl7msg_sex
    * def formattedSex = mapSexValue(hl7msg_sex)
    * print formattedSex
    * match Sex == formattedSex
    * def Dob = text('#Dob').trim()
    * print Dob
    * def hl7msg_date = formattedMessages.find(item => item.startsWith('PID.7')).split(' - ')[1].trim()
    * print hl7msg_date
    * def formattedDate = formatDate(hl7msg_date)
    * print formattedDate
    * def age  = calculateAge(formattedDate)
    * print age
    * match Dob == formattedDate + ' (' + age + ' Years)'
    * def Address = text('#Address').trim()
    * def hl7msg_Address1 = formattedMessages.find(item => item.startsWith('PID.11.1')).split(' - ')[1].trim()
    * def hl7msg_Address2 = formattedMessages.find(item => item.startsWith('PID.11.2')).split(' - ')[1].trim()
    * def hl7msg_Address3 = formattedMessages.find(item => item.startsWith('PID.11.3')).split(' - ')[1].trim()
    * def hl7msg_Address4 = formattedMessages.find(item => item.startsWith('PID.11.4')).split(' - ')[1].trim()
    * def hl7msg_Address5 = formattedMessages.find(item => item.startsWith('PID.11.5')).split(' - ')[1].trim()
    * def hl7_newaddress =  hl7msg_Address1+','+ '  ' + hl7msg_Address3 + ',' + '  ' + hl7msg_Address4 + ' ' + hl7msg_Address5
    * match hl7_newaddress == Address
    * def SSN = text('#SSN')
    * print SSN
    * def cleanSSN = SSN.replaceAll('-', '')
    * def hl7msg_SSN = formattedMessages.find(item => item.startsWith('PID.2.1')).split(' - ')[1].trim()
    * match cleanSSN == hl7msg_SSN
    * def Last_Updated = script("document.evaluate(\"//*[@id='bd']/table[1]/tbody/tr[4]/td[2]/span[2]\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.textContent")
    * print Last_Updated
    * def Created = script("document.evaluate(\"//*[@id='bd']/table[1]/tbody/tr[3]/td[2]/span[2]\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.textContent")
    * print Created
    * def Lab_Report_Date = script("document.evaluate(\"//*[@id='bd']/table[1]/tbody/tr[5]/td[2]/span[2]\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.textContent")
    * print Lab_Report_Date
    * def Date_Received_by_Public_Health = script("document.evaluate(\"//*[@id='bd']/table[1]/tbody/tr[5]/td[3]/span[2]\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.textContent")
    * print Date_Received_by_Public_Health
    * def By = script("document.evaluate(\"//*[@id='bd']/table[1]/tbody/tr[4]/td[3]/span[2]\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.textContent")
    * print By
    * def Collection_Date = script("document.evaluate(\"//*[@id='bd']/table[1]/tbody/tr[5]/td[1]/span[2]\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.textContent")
    * print Collection_Date
    * def Lab_ID = script("document.evaluate(\"//*[@id='bd']/table[1]/tbody/tr[3]/td[1]/span[2]\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.textContent")
    * print Lab_ID
    * def Processing_Decision = script("document.evaluate(\"//*[@id='bd']/table[1]/tbody/tr[6]/td[1]/span[2]\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.textContent")
    * print Processing_Decision
    * def Processing_Decision_Notes = script("document.evaluate(\"//*[@id='bd']/table[1]/tbody/tr[6]/td[2]/span[2]\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.textContent")
    * print Processing_Decision_Notes

    # Validate the Facility and Provider Information
    * def Reporting_Facility = text('#NBS_LAB365')
    * match Reporting_Facility == formattedMessages.find(item => item.startsWith('MSH.3.1')).split(' - ')[1].trim()
    * def Ordering_Facility = text('#NBS_LAB367')
    * print Ordering_Facility
    * def OrderingProvider = text('#NBS_LAB366')
    * print OrderingProvider

    # Validate the Order Details Information
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

    # Validate the Ordered Test Information
    * def Ordered_Test = text('#NBS_LAB112')
    * print Ordered_Test
    * def Ordered_Test_Codes = text('#NBS_LAB269')
    * print Ordered_Test_Codes
    * def Status = text('#NBS_LAB196')
    * print Status
    * def Accession_Number = text('#LAB125')
    * print Accession_Number
    * def Specimen_Source = text('#LAB165')
    * print Specimen_Source
    * def Specimen_Collection_Date_or_Time = text('#LAB163')
    * print Specimen_Collection_Date_or_Time
    * def Specimen_Details = text('#NBS_LAB262')
    * print Specimen_Details
    #* delay(10000)
    * waitUntil("document.readyState === 'complete'")
    * waitFor("input[name=markReviewd]").click()
    * driver.screenshot()
    * def success_msg = text('#successMessages').trim()
    * match success_msg == 'The Lab Report has been successfully marked as Reviewed.'
    * waitFor("//a[contains(text(),'Return to Documents Requiring Review')]").click()
    * waitFor("//a[contains(text(),'Home')]").click()
    * driver.screenshot()
    * def firstname = 'Ashley'
    * input('#DEM104', firstName )
    * driver.screenshot()
    * input('#DEM102', lastName)
    * driver.screenshot()
    * eval Thread.sleep(20000)
    * click('tr:nth-child(8) input:nth-child(1)')
    * waitUntil("document.readyState === 'complete'")
    * driver.screenshot()
    * def fullname = firstName + ' ' + lastName
    * waitFor("//a[contains(text(), '" + fullname + "')]").click
    * driver.quit()












