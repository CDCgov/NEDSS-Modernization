import time
from selenium.webdriver.common.by import By
from src.page_objects.BasePageObject import BasePageObject
from src.utilities.constants import NBSConstants
from src.utilities.log_config import LogUtils
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait

class HomePageSearch(BasePageObject):
    logger = LogUtils.loggen(__name__)

    # Home Page Search locators
    event_id_type_txtbox_Xpath = "//*[@id='patientSearchByDetails']/table[2]/tbody/tr[3]/td[2]/input"
    event_id_txtbox_ID = "ESR101"
    search_btn_Xpath = "//input[@value='Search']"
    firstrow_patient_id_Xpath = "//*[@id='searchResultsTable']/tbody/tr[1]/td[1]/a"
    eventidtype = "Document ID"
    eventid = "P10010"

    def __init__(self, driver):
        super().__init__(driver)
        self.driver = driver

    def select_event_id(self, value, documentid):
        element = self.driver.find_element(By.NAME, 'ESR100_textbox')
        element.send_keys(value)
        self.driver.find_element(By.ID, 'DEM229').send_keys("")
        time.sleep(2)
        
        ele = self.driver.find_element(By.XPATH, "//*[@id='ESR101TR']/td/input")
        ele.click()
        
        if ele.is_enabled():
            ele.send_keys(documentid)
        
        self.driver.find_element(By.XPATH, "//*[@type='button' and @value='Search']").click() # Click the button search
        time.sleep(3)
        self.driver.find_element(By.XPATH, '//*[@id="searchResultsTable"]/tbody/tr/td[1]/a').click() # Clicking the first link in the search results
        time.sleep(2)
        self.driver.find_element(By.ID, "tabs0head1").click() #Clicking the Events tab in the patient file
        time.sleep(2)

        # Locate the table
        table = self.driver.find_element(By.XPATH, '//*[@id="caseReports"]')

        # Get all rows in the table (including header row if any)
        rows = table.find_elements(By.TAG_NAME, 'tr')

        # Define column count as 7
        column_count = 7

        search_text = documentid

         # Loop through each row (excluding header if present)
        for i in range(1, len(rows) + 1):  # Assuming the first row might be header
            # Locate the cell in the 7th column of the current row
            try:
                cell_xpath = f'//*[@id="eventCaseReports"]/tbody/tr[{i}]/td[{column_count}]'
                cell = WebDriverWait(self.driver, 10).until(
                    EC.presence_of_element_located((By.XPATH, cell_xpath))
                )
                cell_text = cell.text.strip()
                

                # Check if the cell text matches the search text
                if cell_text == search_text:
                    print(f"Match found in row {i}. Clicking the link in the 1st column.")

                    # Click on the link in the 1st column of the current row
                    link_xpath = f'//*[@id="eventCaseReports"]/tbody/tr[{i}]/td[1]/a'
                    link = WebDriverWait(self.driver, 10).until(
                        EC.element_to_be_clickable((By.XPATH, link_xpath))
                    )
                    link.click()
                    print(f"Link clicked for row {i}.")
                    time.sleep(10)
                    break  # Exit the loop after clicking the link
            except Exception as e:
                print(f"Error locating element in row {i}: {e}")
                continue
         # Wait for the next page to load and check for the document ID
        try:
            doc_id_element = WebDriverWait(self.driver, 10).until(
                EC.presence_of_element_located((By.XPATH, '//*[@id="bd"]/table[4]/tbody/tr[3]/td[2]/span[2]'))
            )
            doc_id_text = doc_id_element.text.strip()
            print(f"Document ID on the next page: {doc_id_text}")

            if doc_id_text == documentid:
                print("Document ID matches on the next page.")
            else:
                print("Document ID does not match on the next page.")
            
            self.driver.find_element(By.XPATH, '//*[@id="bd"]/table[1]/tbody/tr/td[1]/table/tbody/tr/td[1]/a').click()

        except Exception as e:
            print(f"Error locating document ID on the next page: {e}")




    def verify_update_phdc(self, value, documentid):
        element = self.driver.find_element(By.NAME, 'ESR100_textbox')
        element.send_keys(value)
        self.driver.find_element(By.ID, 'DEM229').send_keys("")
        time.sleep(2)
        
        ele = self.driver.find_element(By.XPATH, "//*[@id='ESR101TR']/td/input")
        ele.click()
        
        if ele.is_enabled():
            ele.send_keys(documentid)
        
        self.driver.find_element(By.XPATH, "//*[@type='button' and @value='Search']").click()
        time.sleep(3)
        self.driver.find_element(By.XPATH, '//*[@id="searchResultsTable"]/tbody/tr/td[1]/a').click()
        time.sleep(2)
        self.driver.find_element(By.ID, "tabs0head1").click()
        time.sleep(2)

        # Locate the table
        table = self.driver.find_element(By.XPATH, '//*[@id="caseReports"]')

        # Get all rows in the table (including header row if any)
        rows = table.find_elements(By.TAG_NAME, 'tr')

        # Define column count as 7
        column_count = 7

        # Append "(Update)" to the document ID for search
        search_text = documentid + " (Update)"

         # Loop through each row (excluding header if present)
        for i in range(1, len(rows) + 1):  # Assuming the first row might be header
            # Locate the cell in the 7th column of the current row
            try:
                cell_xpath = f'//*[@id="eventCaseReports"]/tbody/tr[{i}]/td[{column_count}]'
                cell = WebDriverWait(self.driver, 10).until(
                    EC.presence_of_element_located((By.XPATH, cell_xpath))
                )
                cell_text = cell.text.strip()
                

                # Check if the cell text matches the search text
                if cell_text == search_text:
                    print(f"Match found in row {i}. Clicking the link in the 1st column.")

                    # Click on the link in the 1st column of the current row
                    link_xpath = f'//*[@id="eventCaseReports"]/tbody/tr[{i}]/td[1]/a'
                    link = WebDriverWait(self.driver, 10).until(
                        EC.element_to_be_clickable((By.XPATH, link_xpath))
                    )
                    link.click()
                    print(f"Link clicked for row {i}.")
                    time.sleep(10)
                    break  # Exit the loop after clicking the link
            except Exception as e:
                print(f"Error locating element in row {i}: {e}")
                continue
         # Wait for the next page to load and check for the document ID
        # try:
            doc_id_element = WebDriverWait(self.driver, 10).until(
                EC.presence_of_element_located((By.XPATH, '//*[@id="bd"]/table[4]/tbody/tr[3]/td[2]/span[2]'))
            )
            date_of_report = WebDriverWait(self.driver, 10).until(
                EC.presence_of_element_located((By.XPATH, '//*[@id="clinicalInfo"]/tbody/tr[35]/td[4]"]')))

            notification_comments = WebDriverWait(self.driver, 10).until(
                EC.presence_of_element_located((By.XPATH, '//*[@id="clinicalInfo"]/tbody/tr[141]/td[2]"]')))
            
            doc_id_text = doc_id_element.text.strip()
            print(f"Actual Document ID on the PHDC Viewer page: {doc_id_text}")

            act_date_of_report_text = date_of_report.text.strip()
            print(f"Actual Date of Report on the PHDC Viewer page: {act_date_of_report_text}")

            act_notification_comments_text = notification_comments.text.strip()
            print(f"Actual Notification Comments on the PHDC Viewer page: {act_notification_comments_text}")

            if doc_id_text == documentid:
                print("Expected and Actual Document ID matches on the PHDC Viewer page.")
            else:
                print("Expected and Actual Document ID does not match on the PHDC Viewer page.")

            if act_date_of_report_text == "October 4, 1951":
                print("Expected and Actual Date of Report matches on the PHDC Viewer page.")
            else:
                print("Expected and Actual Date of Report does not match on the PHDC Viewer page.")

            if act_notification_comments_text == "PHDC Automation Test Comment":
                print("Expected and Actual Notification Comments matches on the PHDC Viewer page.")
            else:
                print("Expected and Actual Notification Comments does not match on the PHDC Viewer page.")
            
        # except Exception as e:
        #     print(f"Error locating document ID on the next page: {e}")


    def verify_document_id(self, document_id):
        # Check if the first row in the search results contains the Document ID
        if self.is_element_present(NBSConstants.TYPE_CODE_XPATH, HomePageSearch.firstrow_patient_id_Xpath):
            self.logger.info(f"Document ID {document_id} Found in the NBS Application: PASSED")
            return True
        else:
            self.logger.info(f"Document ID {document_id} NOT Found in the NBS Application: FAILED")
            return False

    def searchDocId(self, document_id):
        pass


