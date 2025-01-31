##########################          INDEX                 ###############

from selenium.webdriver.common.by import By
from calendar import isleap
from datetime import timedelta, datetime, date
from src.utilities.constants import NBSConstants
from src.page_objects.login.login import LoginPage
import os
import shutil
import time
import random
import string
from src.utilities.properties import Properties
from src.utilities.dbconnections import get_db_connection, close_db_connection
from src.utilities.dbconnections import get_db_connection, close_db_connection_phdc

from src.utilities.log_config import LogUtils
import subprocess
import jenkins
import re

class NBSUtilities:
    baseURL = Properties.getApplicationURL()
    username = Properties.getUserName()
    password = Properties.getUserPassword()
    logger = LogUtils.loggen(__name__)
    logged = False

    @classmethod
    def performLoginValidation(cls, driver):
        if NBSUtilities.logged:
            return True
        driver.get(cls.baseURL)
        lp = LoginPage(driver)
        time.sleep(2)
        lp.setUserName(cls.username)
        lp.setUserPassword(cls.password)
        lp.clickBottomSubmitButton()
        if driver.title == "NBS Dashboard":
            NBSUtilities.logged = True
        else:
            NBSUtilities.logged = False
        return NBSUtilities.logged
    
    @classmethod
    def convertMillis(cls, millisecond):
        seconds, milliseconds = divmod(millisecond, 1000)
        minutes, seconds = divmod(seconds, 60)
        hours, minutes = divmod(minutes, 60)
        return "%02d:%02d:%02d" % (hours, minutes, seconds)

    @classmethod
    def generate_random_string(cls, s_length):
        letters = string.ascii_lowercase
        # Randomly choose characters from letters for the given length of the string
        random_string = ''.join(random.choice(letters) for i in range(s_length))
        return random_string

    @classmethod
    def generate_random_date(cls, start_date, end_date):
        date_range = end_date - start_date
        random_days = random.randint(0, date_range.days)
        random_date = start_date + timedelta(days=random_days)
        return random_date

    @classmethod
    def generate_random_int(cls, s_length):
        letters = string.digits
        # Randomly choose characters from letters for the given length of the string
        random_int = ''.join(random.choice(letters) for i in range(s_length))
        return random_int

    @classmethod
    def getRandomDateByVars(cls):
        random_date = NBSUtilities.generate_random_date(
            datetime(NBSConstants.START_DATE_YEAR, NBSConstants.START_DATE_MONTH,
                     NBSConstants.START_DATE_DAY),
            datetime(NBSConstants.END_DATE_YEAR, NBSConstants.END_DATE_MONTH,
                     NBSConstants.END_DATE_DAY))
        return random_date.strftime("%d"), random_date.strftime("%B"), random_date.strftime("%Y")

    @classmethod
    def selectCalendarDate(cls, cal_type, cal_type_val, selected_day, selected_month, selected_year, driver):
        try:
            element = driver.find_element(cal_type, cal_type_val)
            if element is not None:
                element.click()
                time.sleep(1)
                original_window = driver.current_window_handle
                time.sleep(2)
                for window_handle in driver.window_handles:
                    if window_handle != original_window:
                        driver.switch_to.window(window_handle)
                        if selected_day is None and selected_month is None and selected_year is None:
                            element = driver.find_element(By.CLASS_NAME, NBSConstants.CALENDAR_TODAY_DATE)
                            element.click()
                        else:
                            # specific year
                            cls.findElementInCalendar(By.XPATH,
                                                      NBSConstants.CALENDAR_DISPLAY_YEAR,
                                                      NBSConstants.CALENDAR_PRE_YEAR_ARROW,
                                                      selected_year, driver)
                            # specific Month
                            cls.findElementInCalendar(By.XPATH,
                                                      NBSConstants.CALENDAR_DISPLAY_MONTH,
                                                      NBSConstants.CALENDAR_PRE_MONTH_ARROW,
                                                      selected_month, driver)
                            # specific date
                            all_dates = driver.find_elements(By.XPATH, NBSConstants.CALENDAR_GET_ALL_DAYS)
                            for selected_date_val in all_dates:
                                if selected_date_val.text.strip() == selected_day.strip():
                                    selected_date_val.click()
                                    break
                driver.switch_to.window(original_window)
                time.sleep(2)
        except Exception as ex:
            cls.logger.info("Exception While Working with Calendar Date Selection")

    @classmethod
    def findElementInCalendar(cls, type_code, cal_year_path, cal_prev_path, value, driver):
        try:
            while True:
                element_current = driver.find_element(type_code, cal_year_path)
                if element_current is not None:
                    if element_current.text.strip() == value.strip():
                        break
                    else:
                        driver.find_element(type_code, cal_prev_path).click()
        except Exception as ex:
            cls.logger.info("Exception occurred while setting value for calendar")

    @classmethod
    def setComponent_ByID(cls, component_path, value, driver):
        """
        Set component value using component ID
        :param component_path: str, component path
        :param value: str,  value to set
        :param driver: driver object
        :return: boolean true
        """
        driver.find_element(By.ID, component_path).clear()
        driver.find_element(By.ID, component_path).send_keys(value)
        return True

    @classmethod
    def searchElement(cls, elem, elem_path, last_name, driver):
        """
        Search using component last name
        :param elem: element name
        :param elem_path: str, component path
        :param last_name: str, component last name
        :param driver: object, driver object
        :return: boolean, true
        """
        driver.find_element(elem, elem_path).clear()
        driver.find_element(elem, elem_path).send_keys(last_name)
        time.sleep(1)
        driver.find_element(elem, "Submit").click()
        return True

    @staticmethod
    def generate_random_string(s_length):
        letters = string.ascii_lowercase
        # Randomly choose characters from letters for the given length of the string
        random_string = ''.join(random.choice(letters) for i in range(s_length))
        return random_string

    @staticmethod
    def add_years(d: date, years: int):
        """Add years to a date."""
        year = d.year + years
        # if leap day and the new year is not leap, replace year and day otherwise, only replace year
        if d.month == 2 and d.day == 29 and not isleap(year):
            return d.replace(year=year, day=28)
        d.replace(year=year)
        return d.replace(year=year)
###################### PHDC RELATED######################
    @classmethod
    def copy_xml_file(cls, original_file_path, destination_folder):
        timestamp = datetime.datetime.now().strftime("%Y%m%d%H%M%S")
        new_file_name = f"2019_NovelCoronaVirus_{timestamp}.xml"
        new_file_path = os.path.join(destination_folder, new_file_name)

        if not os.path.exists(destination_folder):
            os.makedirs(destination_folder)

        shutil.copy2(original_file_path, new_file_path)
        cls.logger.info(f"Copied Template file to: {new_file_path}")
        return new_file_path

    @classmethod
    def edit_xml_file(cls, file_path):
        with open(file_path, 'r', encoding='utf-8') as file:
            content = file.read()

        new_number = ''.join(random.choices(string.digits, k=4))
        content = content.replace('5599', new_number, 1) # Replacing middle CAS ID
        content = content.replace('5631', new_number, 1) # Replacing middle PSN ID

        timestamp = datetime.datetime.now().strftime("%Y%m%d%H%M%S")
        first_name = f"Automation_PHDC_{timestamp}"
        content = content.replace('<given><![CDATA[FirstName]]></given>', f'<given><![CDATA[{first_name}]]></given>')

        last_name = f"CoronaVirus_{timestamp}"
        content = content.replace('<family><![CDATA[LastName]]></family>', f'<family><![CDATA[{last_name}]]></family>')

        with open(file_path, 'w', encoding='utf-8') as file:
            file.write(content)

        cls.logger.info(f"XML File Opened, Edited Saved to: {file_path}")
        new_file_name = os.path.basename(file_path)
        return new_number, first_name, last_name, new_file_name


    @classmethod
    def command_process1(cls, command, flag):
        if flag:
            process = subprocess.run(command, capture_output=True, text=True, shell=True)
            cls.logger.info(f"Output: {process.stdout}")
            return process.stdout
        else:
            process = subprocess.Popen(command, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
            stdout, stderr = process.communicate()
            # Check for errors
            if process.returncode != 0:
                cls.logger.error(f"Error: {stderr.decode('utf-8')}")
            else:
                cls.logger.info(f"Output: {stdout.decode('utf-8')}")
            return stdout, stderr, process.returncode


    @classmethod
    def get_available_drive_letter(cls):
        import string
        excluded_letters = ['A', 'B', 'P']
        for drive_letter in string.ascii_uppercase:
            if drive_letter not in excluded_letters and not os.path.exists(f"{drive_letter}:\\"):
                return drive_letter
        raise Exception("No available drive letters found.")

    @classmethod
    def transfer_file_net_use(cls, duplicated_file_path, new_file_name):
        drive_letter = cls.get_available_drive_letter()
        print(f"Drive letter : {drive_letter}")
        try:
            hostname = Properties.getRemoteHostname()
            username = Properties.getRemoteUsername()
            password = Properties.getRemotePassword()
            remote_share_path = f'\\\\{hostname}\\Temp2'         
            command1 = f'net use {drive_letter}: {remote_share_path} /user:{username} {password}'
            cls.command_process1(command1, True)
            command2 = f'copy "{duplicated_file_path}" "{drive_letter}:\\{new_file_name}"'
            cls.command_process1(command2, True)
            cls.logger.info(f"File copied to remote: {drive_letter}:\\{new_file_name}")
        except Exception as e:
            cls.logger.error(f"An error occurred during file transfer: {e}")
            raise
        finally:
            # Unmap network drive
            command_unmap = f'net use {drive_letter}: /delete'
            stdout, stderr, return_code = cls.command_process1(command_unmap, False)
            if return_code == 0:
                cls.logger.info(f"Drive {drive_letter}: disconnected successfully.")
            else:
                cls.logger.error(f"Failed to disconnect drive {drive_letter}")

    @classmethod
    def execute_sql_statements(cls, local_file_name):
        conn = None
        nbs_interface_uid = None
        try:
            conn = get_db_connection()
            cursor = conn.cursor()

            insert_sql = """
               INSERT INTO nbs_msgoute..NBS_interface
               (payload, imp_exp_ind_cd, record_status_cd, record_status_time, add_time, system_nm, doc_type_cd)
               VALUES (NULL, 'I', 'QUEUED', getDate(), getDate(), 'TEST1', 'PHC236');
               """
            cursor.execute(insert_sql)
            conn.commit()

            select_sql = "SELECT TOP 1 nbs_interface_uid FROM NBS_MSGOUTE..NBS_interface ORDER BY 1 DESC"
            cursor.execute(select_sql)
            nbs_interface_uid = cursor.fetchone()[0]

            update_sql = f"""
               UPDATE NBS_MSGOUTE..nbs_interface
               SET imp_exp_ind_cd='I', record_status_cd='QUEUED', payload=(
                   SELECT * FROM OPENROWSET(
                       BULK 'C:\\TEMP\\{local_file_name}', SINGLE_CLOB
                   ) AS x
               )
               WHERE nbs_interface_uid={nbs_interface_uid}
               """
            cursor.execute(update_sql)
            conn.commit()

            cls.logger.info(f"SQL operations successful. nbs_interface_uid: {nbs_interface_uid}")
        except Exception as e:
            cls.logger.error(f"Error executing SQL statements: {e}")
        finally:
            if conn:
                close_db_connection_phdc(conn)
        return nbs_interface_uid

    @classmethod
    def get_jenkins_server(cls):
        url = Properties.getJenkinsURL()
        username = Properties.getJenkinsUsername()
        password = Properties.getJenkinsPassword()
        server = jenkins.Jenkins(url, username=username, password=password)
        return server

    #Below code is to get the document-id for a new document
    @classmethod
    def run_jenkins_pipeline(cls, server, job_name, node_name):
        try:
            job_info = server.get_job_info(job_name)
            #cls.logger.info(f"Job info: {job_info}")

            build_params = {'Node_Name': node_name}
            server.build_job(job_name, parameters=build_params)
            cls.logger.info(f"Triggered build for job {job_name} with parameters {build_params}")

            next_build_number = server.get_job_info(job_name)['nextBuildNumber']
            cls.logger.info(f"Next build number: {next_build_number}")

            time.sleep(10)

            while True:
                build_info = server.get_build_info(job_name, next_build_number)
                if build_info['building']:
                    cls.logger.info(f"Build {next_build_number} is still in progress...")
                    time.sleep(10)
                else:
                    break

            result = build_info['result']
            if result == 'SUCCESS':
                cls.logger.info(f"Build {next_build_number} succeeded.")
            else:
                cls.logger.info(f"Build {next_build_number} failed.")

            console_output = server.get_build_console_output(job_name, next_build_number)
            cls.logger.info("Console output:")
            cls.logger.info(console_output)

            document_id_match = re.search(r'Document\s+(DOC[^\s]+)\s+created', console_output)
            document_id = document_id_match.group(1) if document_id_match else None
        
            errors = []
            if 'actions' in build_info:
                for action in build_info['actions']:
                    if 'failures' in action:
                        errors.extend(action['failures'])

            return result, console_output, document_id, errors

        except Exception as e:
            cls.logger.error(f"An error occurred while running the Jenkins pipeline: {e}")
            raise


    @classmethod
    def modify_xml_with_new_values(cls, file_path):
        with open(file_path, 'r', encoding='utf-8') as file:
            content = file.read()

        modifications = {
            '19511005': '19511004',
            'Notification Comments to CDC3': 'PHDC Automation Test Comment',
            # Add other replacements here
        }

        for old_value, new_value in modifications.items():
            content = content.replace(old_value, new_value)

        with open(file_path, 'w', encoding='utf-8') as file:
            file.write(content)
        
        return modifications
    
    
    @classmethod
    def run_jenkins_pipeline_updated_docid(cls, server, job_name, node_name):
        try:
            job_info = server.get_job_info(job_name)
            # cls.logger.info(f"Job info: {job_info}")

            build_params = {'Node_Name': node_name}
            server.build_job(job_name, parameters=build_params)
            cls.logger.info(f"Triggered build for job {job_name} with parameters {build_params}")

            next_build_number = server.get_job_info(job_name)['nextBuildNumber']
            cls.logger.info(f"Next build number: {next_build_number}")

            time.sleep(10)

            while True:
                build_info = server.get_build_info(job_name, next_build_number)
                if build_info['building']:
                    cls.logger.info(f"Build {next_build_number} is still in progress...")
                    time.sleep(10)
                else:
                    break

            result = build_info['result']
            if result == 'SUCCESS':
                cls.logger.info(f"Build {next_build_number} succeeded.")
            else:
                cls.logger.info(f"Build {next_build_number} failed.")

            console_output = server.get_build_console_output(job_name, next_build_number)
            cls.logger.info("Console output:")
            cls.logger.info(console_output)

            # Extract the updated document ID from the console output
            document_id_match = re.search(r'Document\s+marked\s+as\s+an\s+Update\s+to\s+(DOC[^\s]+)', console_output)
            updated_document_id = document_id_match.group(1) if document_id_match else None

            errors = []
            if 'actions' in build_info:
                for action in build_info['actions']:
                    if 'failures' in action:
                        errors.extend(action['failures'])

            return result, console_output, updated_document_id, errors

        except Exception as e:
            cls.logger.error(f"An error occurred while running the Jenkins pipeline: {e}")
            raise

