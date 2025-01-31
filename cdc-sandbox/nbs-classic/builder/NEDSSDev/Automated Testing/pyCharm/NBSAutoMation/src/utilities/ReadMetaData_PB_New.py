import random
import time
from datetime import datetime

import pyautogui
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.select import Select

from src.utilities.constants import NBSConstants
from src.utilities.dbconnections import get_db_connection
from src.utilities.log_config import LogUtils


class ReadMetaData_PB_New:

    def __init__(self, driver):
        self.driver = driver
        self.rowNum = 0
        self.robot = None
        self.iselementdisplayed = False
        self.iselementpopulated = False
        self.iselementdisplayed1 = False
        self.log = None
        self.df = None
        self.comUtils = None
        self.comPBUtils = None
        self.element = None
        self.jse = None
        self.url = ""
        self.con = None
        self.stmt = None
        self.rs = None
        self.dbName = "nedss-dbsql"
        self.instance = "test1"
        self.text = ""
        self.length = 0
        self.Unit_Type_CD = "Dropdown value"
        self.repeatCount = 1
        self.repeatCountAddBtn = 1
        self.rNumber = 1
        self.tbTabOrder = 1
        self.dt = "10/01/1951"  # start date
        self.Var_Rash_onset_dt = "10/01/1951"  # start date;
        self.tub195 = "09/01/1951"
        self.DOB = "10/01/1950"  # start date;
        self.num_of_lesions, self.num_of_macules, self.num_of_papules, self.num_of_vesicles = [1, 1, 1, 1]
        self.numeric_value = 1
        self.specialChars = 'Deep^~&#~!@#$%^&*()_+{}:<>?`-=[],.\'\"\\'
        self.investigation_date_of_report = "10/01/1959"
        self.metadata = []
        self.logger = LogUtils.loggen(__name__)
        self.var_illness_onset_dt = "02/10/2024"
        # self.ynu_random = 'Yes'
        self.random_number_for_patient = datetime.now().strftime("%m%d%y")
        self.patient_lastname = NBSConstants.APPEND_TEST_FOR_LAST_NAME.__add__("-").__add__(
            str(self.random_number_for_patient))
        self.patient_firstname = NBSConstants.APPEND_TEST_FOR_FIRST_NAME.__add__("-").__add__(
            str(self.random_number_for_patient))
        self.patient_middlename = NBSConstants.APPEND_TEST_FOR_MIDDLE_NAME.__add__("-").__add__(
            str(self.random_number_for_patient))

    def setUpDriver(self, driver):
        self.driver = driver

    def setUpDataFile(self, df):
        self.df = df

    def isElementDisplayed_check(self, elm_path, elm_val):
        try:
            elm = self.driver.find_element(elm_path, elm_val)
            if elm:
                return True
            else:
                return False
        except Exception as e:
            self.logger.info(str(e))
            return False

    def randIntMultiple(self, min_value, max_value, size):
        return [random.randint(min_value, max_value) for _ in range(size)]

    def validateById(self, field_id, update_fields):
        # print("update_fields!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!", update_fields)
        current_field = self.driver.find_element(By.ID, field_id)
        try:
            old_text = update_fields.get(field_id,None)
            # print("old_text@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" , old_text)
            if current_field.tag_name == 'select' and  self.isElementDisplayed_check(By.ID,
                                             field_id) and old_text is not None:  # old value is none during add new action
                current_text = current_field.get_attribute("value")
                # check if the element if a dropdown
                drop_down = Select(current_field)
                if drop_down is not None and current_field.tag_name == "select":
                    self.logger.info(f"This element is a select element {field_id}")
                    current_text = drop_down.all_selected_options.__getitem__(1)
                # validate match
                if current_text != old_text:
                    self.logger.error(f"Data loss for field {field_id}  old value  {old_text} current value{current_text}")
                    print(f"Data loss for field {field_id}  old value  {old_text} current value {current_text}")
                    return False
        except Exception as e:
            print(e)
        return True

    def readData(self, col, PatientName, templateName, update_fields):
        ynu_random = "Yes"
        ynu_random_option = "Y"
        addButtonId = ""
        repeatTimes = 0
        repeatRow = 0

        try:
            self.con = get_db_connection()
            self.stmt = self.con.cursor()
        except Exception as e:
            print(e)

        if templateName == 'INV_FORM_VAR':
            query = f"select question_label,question_identifier,nbs_ui_component_uid,data_type,question_group_seq_nbr, unit_type_cd,code_set_group_id, tab_order_id from nbs_odse..nbs_ui_metadata where investigation_form_cd='{templateName}' order by tab_order_id,parent_uid, nbs_ui_metadata_uid"
        elif templateName == 'INV_FORM_RVCT':
            query = f"select question_label,question_identifier,nbs_ui_component_uid,data_type,question_group_seq_nbr, unit_type_cd,code_set_group_id,tab_order_id from nbs_odse..nbs_ui_metadata where investigation_form_cd='{templateName}' order by tab_order_id"
        else:
            query = f"select question_label,question_identifier,nbs_ui_component_uid,mask,question_group_seq_nbr, unit_type_cd,code_set_group_id,block_nm from wa_ui_metadata where wa_template_uid = (Select wa_template_uid from wa_template where template_nm = '{templateName}' and publish_ind_cd= 'T') and order_nbr>0 order by order_nbr"

        try:
            self.rs = self.stmt.execute(query)  # fetch_all()
            print("DB Record", self.rs)

        except Exception as e:
            print(e)

        count = 0
        for row in self.rs:
            myArray = list(row)
            self.metadata.append(myArray)
            count += 1


        myArray = [None, None, None, None, None, None, None, None]

        self.metadata.append(myArray)


        self.stmt.close()
        print("DB read complete with data", count)
        tab = 0
        iselementdisplayed = False
        text = ""
        length = 0

        for itr in range(count):
            print("Starting itr ", itr)
            try:
                myArray = self.metadata[itr]
                Field_Label, Field_Id, Component_ID, Mask, Repeating_Group, Unit_Type_CD, Code_Set_Group_Id, Tab_Order_Id = myArray
                repeatCount = 2  # Set the value of repeatCount
                msg = "Element is read-only"  # Set the value of msg


                if Component_ID == 1010:
                    if templateName != 'INV_FORM_RVCT':
                        try:
                            self.driver.find_element(By.ID, f"tabs0head{tab}").click()
                        except Exception as e:
                            self.logger.info(str(e))
                        tab += 1

                elif Component_ID == 1009:
                    text = ''
                    if (templateName == 'INV_FORM_RVCT' or templateName == 'INV_FORM_VAR') and (
                            Mask.casefold() == "text"):
                        iselementdisplayed = self.isElementDisplayed_check(By.NAME, f"pamClientVO.answer({Field_Id})")
                    else:
                        iselementdisplayed = self.isElementDisplayed_check(By.ID, Field_Id)

                    if iselementdisplayed:
                        if (templateName == 'INV_FORM_RVCT' or templateName == 'INV_FORM_VAR') and (
                                Mask.casefold == "text"):
                            text = self.driver.find_element(By.NAME, f"pamClientVO.answer({Field_Id})").get_attribute(
                                "value")
                        else:
                            text = self.driver.find_element(By.ID, Field_Id).get_attribute("value")
                        length = len(text)

                        if length == 0:
                            if Field_Id == "DEM196":

                                self.driver.find_element(By.NAME, f"pamClientVO.answer({Field_Id})").clear()
                                self.driver.find_element(By.NAME, f"pamClientVO.answer({Field_Id})").send_keys(
                                    Field_Label + str(repeatCount))
                                update_fields[Field_Id] = Field_Label + str(repeatCount)
                            else:
                                try:
                                    assert self.validateById(Field_Id, update_fields)
                                    self.driver.find_element(By.ID, Field_Id).clear()
                                    self.driver.find_element(By.ID, Field_Id).send_keys(Field_Label + str(repeatCount))
                                    update_fields[Field_Id] = Field_Label + str(repeatCount)
                                except Exception as e:
                                    if msg in str(e):
                                        pass

                elif Component_ID == 1008:
                    text = ''
                    if (
                        templateName == 'INV_FORM_RVCT' or templateName == 'INV_FORM_VAR') and Mask.casefold() == 'text':
                        iselementdisplayed = self.driver.find_element(By.NAME, "pamClientVO.answer(" + Field_Id + ")")
                    else:
                        iselementdisplayed = self.driver.find_element(By.ID, Field_Id)

                    if iselementdisplayed:
                        if (
                                templateName == 'INV_FORM_RVCT' or templateName == 'INV_FORM_VAR') and Mask.casefold() == 'text':
                            text = self.driver.find_element(By.NAME,
                                                            "pamClientVO.answer(" + Field_Id + ")").get_attribute(
                                "value")

                    else:
                        text = self.driver.find_element(By.ID, Field_Id).get_attribute("value")

                    length = len(text)
                    print("Mask & Field ID!!!!", text, Mask, Field_Id)
                    assert self.validateById(Field_Id, update_fields)
                    if length == 0:
                        if Field_Id == "NBS_LAB364":
                            assert self.validateById(Field_Id, update_fields)
                            self.driver.find_element(By.ID, Field_Id).clear()
                            self.driver.find_element(By.ID, Field_Id).send_keys("1")
                            update_fields[Field_Id] = "1"
                            # continue
                        elif Field_Id == "NBS452":
                            assert self.validateById(Field_Id, update_fields)
                            self.driver.find_element(By.ID, Field_Id).clear()
                            self.driver.find_element(By.ID, Field_Id).send_keys("10/20/2019")
                            update_fields[Field_Id] = "10/20/2019"
                            # continue
                        elif Field_Id == "VAR151":
                            assert self.validateById(Field_Id, update_fields)
                            self.driver.find_element(By.ID, Field_Id).clear()
                            self.driver.find_element(By.ID, Field_Id).send_keys("10")
                            update_fields[Field_Id] = "10"
                            # continue
                        elif Field_Id == "VAR163":
                            assert self.validateById(Field_Id, update_fields)
                            num_of_lesions = self.num_of_macules + self.num_of_papules + self.num_of_vesicles
                            self.driver.find_element(By.ID, Field_Id).clear()
                            self.driver.find_element(By.ID, Field_Id).send_keys(str(num_of_lesions))
                            update_fields[Field_Id] = str(num_of_lesions)
                            # continue
                        elif Field_Id == "VAR108":
                            print("VAR 108 Data ", self.num_of_macules)
                            assert self.validateById(Field_Id, update_fields)
                            self.driver.find_element(By.ID, Field_Id).clear()
                            self.driver.find_element(By.ID, Field_Id).send_keys(str(self.num_of_macules))
                            update_fields[Field_Id] = str(self.num_of_macules)
                            # continue;
                        elif Field_Id == "VAR110":
                            print("VAR 110 data", self.num_of_papules)
                            assert self.validateById(Field_Id, update_fields)
                            self.driver.find_element(By.ID, Field_Id).clear()
                            self.driver.find_element(By.ID, Field_Id).send_keys(str(self.num_of_papules))
                            update_fields[Field_Id] = str(self.num_of_papules)
                            # continue
                        elif Field_Id == "VAR112":
                            assert self.validateById(Field_Id, update_fields)
                            self.driver.find_element(By.ID, Field_Id).clear()
                            self.driver.find_element(By.ID, Field_Id).send_keys(str(self.num_of_vesicles))
                            update_fields[Field_Id] = str(self.num_of_vesicles)
                            # continue;
                        elif Field_Id == "VAR124":
                            assert self.validateById(Field_Id, update_fields)
                            self.driver.find_element(By.ID, Field_Id).clear()
                            print("Check field VAR124")
                            self.driver.find_element(By.ID, Field_Id).send_keys("40.0")
                            update_fields[Field_Id] = "40.0"
                        #  break

                        elif Field_Id == "INV173" or Field_Id == "INV198":
                            assert self.validateById(Field_Id, update_fields)
                            rand = random.randint(11111, 99999)
                            self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").clear()
                            field_val = "2023-GA-ABCD" + str(rand)
                            self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").send_keys(
                                field_val)
                            update_fields[Field_Id] = field_val
                            # continue

                        if Mask is not None and Mask.casefold() == 'date':
                            if Field_Id == "INV137" or Field_Id == "VAR102":
                                assert self.validateById(Field_Id, update_fields)
                                self.driver.find_element(By.ID, Field_Id).clear()
                                self.driver.find_element(By.ID, Field_Id).send_keys(str(self.var_illness_onset_dt))
                                update_fields[Field_Id] = str(self.var_illness_onset_dt)
                            elif Field_Id == "INV146":
                                assert self.validateById(Field_Id, update_fields)
                                now = datetime.now().strftime("%m/%d/%Y")
                                self.driver.find_element(By.ID, Field_Id).clear()
                                self.driver.find_element(By.ID, Field_Id).send_keys(str(now))
                                update_fields[Field_Id] = str(now)
                            elif Field_Id == "DEM115":
                                assert self.validateById(Field_Id, update_fields)
                                self.driver.find_element(By.ID, Field_Id).clear()
                                self.driver.find_element(By.ID, Field_Id).send_keys(self.DOB)
                                self.driver.find_element(By.ID, Field_Id).send_keys(Keys.TAB)
                                update_fields[Field_Id] = str(self.DOB)
                            elif Field_Id == "TUB195":
                                assert self.validateById(Field_Id, update_fields)
                                self.driver.find_element(By.ID, Field_Id).clear()
                                self.driver.find_element(By.ID, Field_Id).send_keys(self.tub195)
                                self.driver.find_element(By.ID, Field_Id).send_keys(Keys.TAB)
                                update_fields[Field_Id] = str(self.tub195)
                            elif Field_Id == "TUB273":
                                assert self.validateById(Field_Id, update_fields)
                                self.driver.find_element(By.ID, Field_Id).clear()
                                self.driver.find_element(By.ID, Field_Id).send_keys(self.investigation_date_of_report)
                                update_fields[Field_Id] = self.investigation_date_of_report
                            else:
                                try:
                                    if '2023' in self.dt:
                                        dt = "10/01/1951"
                                    # TODO: convertedDate may require to add extra day
                                    assert self.validateById(Field_Id, update_fields)
                                    convertedDate = datetime.now().strftime("%m/%d/%Y")
                                    self.driver.find_element(By.ID, Field_Id).clear()
                                    self.driver.find_element(By.ID, Field_Id).send_keys(convertedDate)
                                    update_fields[Field_Id] = convertedDate
                                    dt = convertedDate
                                    if Field_Id == "INV111":
                                        investigation_date_of_report = dt
                                except Exception as e:
                                    if hasattr(e, 'message'):
                                        if 'Element is read-only' in e.message:
                                            self.logger.info("Element is read-only")
                                            # break
                                    self.logger.info(str(e))

                        elif Mask is not None and Mask.casefold() == 'text':
                            if Field_Id == "DEM163":
                                assert self.validateById(Field_Id, update_fields)
                                self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").clear()
                                self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").send_keys(
                                    "12345")
                                update_fields[Field_Id] = "12345"
                            elif Field_Id == "DEM238" or Field_Id == "DEM240":
                                assert self.validateById(Field_Id, update_fields)
                                self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").clear()
                                self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").send_keys(
                                    "1234567890")
                                update_fields[Field_Id] = "1234567890"
                            elif Field_Id == "DEM240":
                                assert self.validateById(Field_Id, update_fields)
                                self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").clear()
                                self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").send_keys(
                                    "1231231231")
                                update_fields[Field_Id] = "1231231231"
                            elif Field_Id == "DEM239":
                                assert self.validateById(Field_Id, update_fields)
                                self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").clear()
                                self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").send_keys(
                                    "55555")
                                update_fields[Field_Id] = "55555"
                            elif Field_Id == "DEM241":
                                self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").clear()
                                self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").send_keys(
                                    "66666")
                                update_fields[Field_Id] = "66666"
                            elif Field_Id == "DEM133":
                                assert self.validateById(Field_Id, update_fields)
                                self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").clear()
                                self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").send_keys(
                                    "111-11-1111")
                                update_fields[Field_Id] = "111-11-1111"
                            elif Field_Id == "INV173" or Field_Id == "INV198":
                                assert self.validateById(Field_Id, update_fields)
                                rand = random.randint(11111, 99999)
                                self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").clear()
                                field_val = "2023-GA-ABCD" + str(rand)
                                self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").send_keys(
                                    field_val)
                                update_fields[Field_Id] = field_val
                            # elif Field_Id == 'INV143' or Field_Id == 'INV144':
                            #     pass
                            else:
                                try:
                                    assert self.validateById(Field_Id, update_fields)
                                    self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").clear()
                                    self.driver.find_element(By.NAME,
                                                             "pageClientVO.answer(" + Field_Id + ")").send_keys(
                                        "Field_Id" + str(self.repeatCount))
                                    update_fields[Field_Id] = "Field_Id" + str(self.repeatCount)
                                except Exception as e:
                                    if hasattr(e, 'message'):
                                        if 'Element is read-only' in e.message:
                                            self.logger.info("Element is read-only")
                                            # break
                                    self.logger.info(str(e))

                        elif Mask == 'TXT':
                            try:
                                assert self.validateById(Field_Id, update_fields)
                                text = self.driver.find_element(By.ID, Field_Id).get_attribute("value")
                                length = len(text)
                                if length == 0:
                                    self.driver.find_element(By.ID, Field_Id).clear()
                                    print("FIELD_ID", Field_Id)
                                    self.driver.find_element(By.ID, Field_Id).send_keys(
                                        Field_Label + str(self.repeatCount))
                                    update_fields[Field_Id] = Field_Label + str(self.repeatCount)
                            except Exception as e:
                                if hasattr(e, 'message'):
                                    if 'Element is read-only' in e.message:
                                        self.logger.info("Element is read-only")
                                        # break
                                self.logger.info(str(e))

                        elif Mask == "NUM" or Mask == "Numeric":
                            if Field_Id == "VAR124":
                                assert self.validateById(Field_Id, update_fields)
                                rand = random.randint(1, 2)
                                if rand == 1:
                                    rNum = random.randint(30, 50)
                                    self.driver.find_element(By.ID, Field_Id).clear()
                                    self.driver.find_element(By.ID, Field_Id).send_keys(str(rNum))
                                    update_fields[Field_Id] = str(rNum)
                                else:
                                    rNum = random.randint(90, 110)
                                    self.driver.find_element(By.ID, Field_Id).clear()
                                    self.driver.find_element(By.ID, Field_Id).send_keys(str(rNum))
                                    update_fields[Field_Id] = str(rNum)

                            elif Field_Id == "TUB112":
                                self.driver.find_element(By.ID, Field_Id).clear()
                                self.driver.find_element(By.ID, Field_Id).send_keys("2018")
                                update_fields[Field_Id] = "2018"
                            elif Field_Id == "TUB239":
                                self.driver.find_element(By.ID, Field_Id).clear()
                                self.driver.find_element(By.ID, Field_Id).send_keys("1")
                                update_fields[Field_Id] = "1"
                            elif Field_Id == "TUB149":
                                rNumber = random.randint(4, 99)
                                self.driver.find_element(By.ID, Field_Id).clear()
                                self.driver.find_element(By.ID, Field_Id).send_keys(rNumber)
                                update_fields[Field_Id] = str(rNumber)
                            elif Field_Id == "VAR147":
                                rNumber = random.randint(1, 5)
                                self.driver.find_element(By.ID, Field_Id).clear()
                                self.driver.find_element(By.ID, Field_Id).send_keys(rNumber)
                                update_fields[Field_Id] = str(rNumber)
                            elif Field_Id == "INV2001" or Field_Id =='INV143':
                                update_fields[Field_Id] = self.driver.find_element(By.ID, Field_Id).get_attribute('value')
                            else:
                                try:
                                    numeric_value = self.numeric_value + 1
                                    self.driver.find_element(By.ID, Field_Id).clear()
                                    self.driver.find_element(By.ID, Field_Id).send_keys(str(numeric_value))
                                    update_fields[Field_Id] = str(numeric_value)
                                except Exception as e:
                                    if hasattr(e, 'message'):
                                        if 'Element is read-only' in e.message:
                                            self.logger.info("Element is read-only")
                                            # break
                                    self.logger.info(str(e))

                            if Unit_Type_CD == "CODED":
                                field_Id_updated = Field_Id + "UNIT_textbox"
                                iselementdisplayed = self.isElementDisplayed_check(By.NAME, field_Id_updated)
                                if iselementdisplayed:
                                    text = self.driver.find_element(By.NAME, field_Id_updated).get_attribute("value")
                                    length = len(text)
                                    print("UNIT Text", text, field_Id_updated)
                                    if length == 0:
                                        self.driver.find_element(By.NAME, field_Id_updated).clear()
                                        button = Field_Id + "UNIT_button"
                                        # JavascriptExecutor jse
                                        self.driver.find_element(By.NAME, button).click()
                                        countySize = len(self.driver.find_elements(By.XPATH,
                                                                                   "//*[@id='" + Field_Id + "UNIT" + "']/option"))
                                        rNum = str(random.randint(2, countySize))
                                        element = self.driver.find_element(By.XPATH,
                                                                           "//*[@id='" + Field_Id + "UNIT" + "']/option[" + rNum + "]")
                                        self.driver.execute_script("arguments[0].scrollIntoView()", element)
                                        # element.get_attribute("innerHTML")
                                        text1 = self.driver.find_element(By.XPATH,
                                                                         "//*[@id='" + Field_Id + "UNIT" + "']/option[" + rNum + "]").text
                                        print("UNIT Text", text1, field_Id_updated)
                                        self.driver.find_element(By.NAME, field_Id_updated).send_keys(text1)
                                        update_fields[Field_Id] = str(text1)
                                        pyautogui.press('tab')
                                        iselementdisplayed = self.isElementDisplayed_check(By.ID, Field_Id + "Oth")
                                        if iselementdisplayed:
                                            self.driver.find_element(By.ID, Field_Id + "Oth").send_keys(
                                                text + str(self.repeatCount))
                                            update_fields[Field_Id] = text + str(self.repeatCount)

                        elif Mask == "NUM_TEMP":
                            text1 = ""
                            if Unit_Type_CD == "CODED":
                                field_Id_updated = Field_Id + "UNIT_textbox"
                                iselementdisplayed = self.isElementDisplayed_check(By.NAME, field_Id_updated)
                                if iselementdisplayed:
                                    text = self.driver.find_element(By.NAME, field_Id_updated).get_attribute("value")

                                    length = len(text)
                                    if length == 0:
                                        self.driver.find_element(By.NAME, field_Id_updated).clear()
                                        button = Field_Id + "UNIT_button"
                                        self.driver.find_element(By.NAME, button).click()
                                        countySize = len(self.driver.find_elements(By.XPATH,
                                                                                   "//*[@id='" + Field_Id + "UNIT" + "']/option"))
                                        rNum = str(random.randint(2, countySize))

                                        element = self.driver.find_element(
                                            By.XPATH("//*[@id='" + Field_Id + "UNIT" + "']/option[" + rNum + "]"))
                                        self.driver.execute_script("arguments[0].scrollIntoView()", element)
                                        text1 = self.driver.find_element(By.XPATH,
                                                                         "//*[@id='" + Field_Id + "UNIT" + "']/option[" + rNum + "]").text
                                        self.driver.find_element(By.NAME(field_Id_updated)).send_keys(text1)

                                        update_fields[Field_Id] = str(text1)
                                        pyautogui.press('tab')
                                        iselementdisplayed = self.isElementDisplayed_check(By.ID, Field_Id + "Oth")
                                        if iselementdisplayed:
                                            self.driver.find_element(By.ID(Field_Id + "Oth")).send_keys(
                                                text + str(self.repeatCount))
                                            update_fields[Field_Id] = text + str(self.repeatCount)

                            if text1 == "Celsius":
                                try:
                                    rNum = random.randint(30, 50)
                                    self.driver.find_element(By.ID, Field_Id).clear()
                                    self.driver.find_element(By.ID, Field_Id).send_keys(str(rNum))
                                    update_fields[Field_Id] = str(rNum)
                                except Exception as e:
                                    if hasattr(e, 'message'):
                                        if 'Element is read-only' in e.message:
                                            self.logger.info("Element is read-only")
                                        # break
                                    self.logger.info(str(e))

                            elif text1 == "Fahrenheit":
                                try:
                                    rNum = random.randint(90, 110)
                                    self.driver.find_element(By.ID, Field_Id).clear()
                                    self.driver.find_element(By.ID, Field_Id).send_keys(str(rNum))
                                    update_fields[Field_Id] = str(rNum)
                                except Exception as e:
                                    if hasattr(e, 'message'):
                                        if 'Element is read-only' in e.message:
                                            self.logger.info("Element is read-only")
                                        # break
                                    self.logger.info(str(e))

                    elif Mask == "TXT_ZIP":
                        try:
                            self.driver.find_element(By.ID, Field_Id).clear()
                            self.driver.find_element(By.ID, Field_Id).send_keys("12345")
                            update_fields[Field_Id] = str("12345")
                        except Exception as e:
                            if hasattr(e, 'message'):
                                if 'Element is read-only' in e.message:
                                    self.logger.info("Element is read-only")
                                    # break
                            self.logger.info(str(e))

                    elif Mask == "TXT_PHONE":
                        try:
                            self.driver.find_element(By.ID, Field_Id).clear()
                            self.driver.find_element(By.ID, Field_Id).send_keys("1234567890")
                            update_fields[Field_Id] = str("1234567890")
                        except Exception as e:
                            if hasattr(e, 'message'):
                                if 'Element is read-only' in e.message:
                                    self.logger.info("Element is read-only")
                                    # break
                            self.logger.info(str(e))

                    elif Mask == "NUM_EXT":
                        try:
                            self.driver.find_element(By.ID, Field_Id).clear()
                            self.driver.find_element(By.ID, Field_Id).send_keys("11111")
                            update_fields[Field_Id] = str("11111")
                        except Exception as e:
                            if hasattr(e, 'message'):
                                if 'Element is read-only' in e.message:
                                    self.logger.info("Element is read-only")
                                    # break
                            self.logger.info(str(e))

                    elif Mask == "TXT_EMAIL":
                        try:
                            self.driver.find_element(By.ID, Field_Id).clear()
                            self.driver.find_element(By.ID, Field_Id).send_keys("email@email.com")
                            update_fields[Field_Id] = str("email@email.com")
                        except Exception as e:
                            if hasattr(e, 'message'):
                                if 'Element is read-only' in e.message:
                                    self.logger.info("Element is read-only")
                                    # break
                            self.logger.info(str(e))

                    elif Mask == "TXT_ID15":
                        try:
                            self.driver.find_element(By.ID, Field_Id).clear()
                            self.driver.find_element(By.ID, Field_Id).send_keys("1234556799467")
                            update_fields[Field_Id] = str("1234556799467")
                        except Exception as e:
                            if hasattr(e, 'message'):
                                if 'Element is read-only' in e.message:
                                    self.logger.info("Element is read-only")
                                    # break
                            self.logger.info(str(e))

                    # --------------------------------- verify--------------------------
                    elif Mask == "NUM_YYYY":
                        try:
                            self.driver.find_element(By.ID, Field_Id).clear()
                            #  seeing UI validation error when passing value 2022, So removed the value
                            self.driver.find_element(By.ID, Field_Id).send_keys()
                        except Exception as e:
                            if hasattr(e, 'message'):
                                if 'Element is read-only' in e.message:
                                    self.logger.info("Element is read-only")
                                    # break
                            self.logger.info(str(e))

                    elif Mask == "NUM_TEMP":
                        try:
                            self.driver.find_element(By.ID, Field_Id).clear()
                            self.driver.find_element(By.ID, Field_Id).send_keys("56")
                            update_fields[Field_Id] = str("56")
                        except Exception as e:
                            if hasattr(e, 'message'):
                                if 'Element is read-only' in e.message:
                                    self.logger.info("Element is read-only")
                                    # break
                            self.logger.info(str(e))

                    if Field_Id == "INV138" or Field_Id == "INV136" or Field_Id == "INV147":
                        # set current  date
                        self.driver.find_element(By.ID, Field_Id).clear()
                        self.driver.find_element(By.ID, Field_Id).send_keys(datetime.now().strftime("%m/%d/%Y"))
                        # --------------------- verify----------------
                        update_fields[Field_Id] = str(datetime.now().strftime("%m/%d/%Y"))
                    if Field_Id == "INV137":
                        self.driver.find_element(By.ID, Field_Id).clear()
                        self.driver.find_element(By.ID, Field_Id).send_keys(self.var_illness_onset_dt)
                        update_fields[Field_Id] = str(self.var_illness_onset_dt)

                elif Component_ID == 1007:
                    assert self.validateById(Field_Id, update_fields)
                    field_Id_updated = Field_Id + "_textbox"
                    iselementdisplayed = self.isElementDisplayed_check(By.NAME, field_Id_updated)
                    print("Select field ", field_Id_updated, iselementdisplayed, Code_Set_Group_Id)
                    if iselementdisplayed is True:
                        if str(Code_Set_Group_Id) == "4150":
                            print("ynu_random!!!!!!!!!!!!!!!!!!", ynu_random)
                            select_element = Select(self.driver.find_element(By.ID, Field_Id))
                            self.driver.execute_script("arguments[0].scrollIntoView()", element)
                            select_element.select_by_value(ynu_random_option)
                            #self.driver.find_element(By.NAME, field_Id_updated).send_keys(ynu_random)
                            #self.driver.find_element(By.NAME, field_Id_updated).send_keys(Keys.TAB)
                            # ----------------- verify ---------------------------
                            update_fields[Field_Id] = str(ynu_random_option)
                        elif field_Id_updated == "TUB130_textbox":
                            self.driver.find_element(By.NAME, field_Id_updated).clear()

                            self.driver.find_element(By.NAME, field_Id_updated).send_keys("Positive")
                            self.driver.find_element(By.NAME, field_Id_updated).send_keys(Keys.TAB)
                            update_fields[Field_Id] = str("Positive")
                        elif field_Id_updated == "TUB233_textbox":
                            self.driver.find_element(By.NAME, field_Id_updated).clear()
                            self.driver.find_element(By.NAME, field_Id_updated).send_keys("Adverse Treatment Event")
                            update_fields[Field_Id] = str("Adverse Treatment Event")
                            self.driver.find_element(By.NAME, field_Id_updated).send_keys(Keys.TAB)
                        elif Field_Id == "VAR211":
                            if self.rNumber == 1:
                                self.driver.find_element(By.NAME, field_Id_updated).send_keys("Celsius")
                                update_fields[Field_Id] = str("Celsius")
                            elif self.rNumber == 2:
                                self.driver.find_element(By.NAME, field_Id_updated).send_keys("Fahrenheit")
                                update_fields[Field_Id] = str("Fahrenheit")
                        else:
                            text = self.driver.find_element(By.NAME, field_Id_updated).get_attribute("value")
                            length = len(text)
                            if length == 0:
                                self.driver.find_element(By.NAME, field_Id_updated).clear()
                                button = Field_Id + "_button"
                                self.driver.find_element(By.NAME, button).click()
                                dropdown = self.driver.find_element(By.XPATH, "//select[@id='" + Field_Id + "']")
                                select = Select(dropdown)
                                option_size = len(select.options)
                                randnum = random.randint(2, option_size)
                                select.select_by_index(randnum - 1)
                                update_fields[Field_Id] = self.driver.find_element(By.NAME,
                                                                                   field_Id_updated).get_attribute(
                                    "value")
                                pyautogui.press('tab')
                                try:
                                    iselementdisplayed = self.driver.find_element(By.ID, Field_Id + "Oth")
                                    if iselementdisplayed:
                                        self.driver.find_element(By.ID, Field_Id + "Oth").send_keys(
                                            Field_Id + "Oth" + str(self.repeatCount))
                                except Exception as e:
                                    print("ERROR !!!!!!!!! ", str(e))
                                    self.logger.info(str(e))

                elif Component_ID == 1031:
                    assert self.validateById(Field_Id, update_fields)
                    field_Id_updated = Field_Id + "_textbox"
                    iselementdisplayed = self.isElementDisplayed_check(By.NAME, field_Id_updated)
                    if iselementdisplayed:
                        text = self.driver.find_element(By.NAME, field_Id_updated).get_attribute("value")
                        length = len(text)
                        if length == 0:
                            self.driver.find_element(By.NAME, field_Id_updated).clear()
                            button = Field_Id + "_button"
                            self.driver.find_element(By.NAME, button).click()
                            dropdown = self.driver.find_element(By.XPATH, "//select[@id='" + Field_Id + "']")
                            select = Select(dropdown)
                            option_size = len(select.options)
                            randnum = random.randint(2, option_size)
                            select.select_by_index(randnum - 1)
                            update_fields[Field_Id] = self.driver.find_element(By.NAME, field_Id_updated).get_attribute(
                                "value")
                            pyautogui.press('tab')
                            iselementdisplayed = self.isElementDisplayed_check(By.ID, Field_Id + "Oth")
                            if iselementdisplayed:
                                self.driver.find_element(By.ID, Field_Id + "Oth").send_keys(
                                    Field_Id + "Oth" + str(self.repeatCount))

                        if Field_Id == "TUB167":
                            pass

                    # break

                elif Component_ID == 1017:
                    assert self.validateById(Field_Id, update_fields)
                    field_Id_updated = Field_Id + "Text"
                    iselementdisplayed = self.isElementDisplayed_check(By.ID, field_Id_updated)
                    if iselementdisplayed:
                        text = self.driver.find_element(By.ID, field_Id_updated).get_attribute("value")
                        length = len(text)
                        if length == 0:
                            self.driver.find_element(By.ID, field_Id_updated).clear()
                            self.driver.find_element(By.ID, field_Id_updated).send_keys("1")
                            update_fields[Field_Id] = "1"
                            field_ID_Button = Field_Id + "CodeLookupButton"
                            self.driver.find_element(By.ID, field_ID_Button).click()

                    # break

                elif Component_ID == 1013:
                    assert self.validateById(Field_Id, update_fields)
                    option = Field_Id
                    confirmSize = len(self.driver.find_elements(By.XPATH, "//*[@id='" + option + "']/option"))
                    rNum = 0
                    rNumRandom = 0
                    if Field_Id == "TUB119":
                        continue
                    if confirmSize <= 2:
                        rNum = 1
                        self.driver.find_element(By.XPATH,
                                                 "//*[@id='" + option + "']/option[" + str(rNum) + "]").click()
                    else:
                        if confirmSize > 10:
                            rNum = 10
                        else:
                            rNum = random.randint(2, confirmSize - 1)
                            randNums = self.randIntMultiple(2, confirmSize, rNum)
                            for i in range(len(randNums)):
                                rNumRandom = randNums[i]
                                if Field_Id == "TUB167" and rNumRandom == 2:
                                    continue
                                element = self.driver.find_element(
                                    By.XPATH, "//*[@id='" + option + "']/option[" + str(rNumRandom) + "]")
                                # jse = (JavascriptExecutor)driver;
                                # jse.executeScript("arguments[0].scrollIntoView()", element);
                                self.driver.execute_script("arguments[0].scrollIntoView()", element)
                                self.driver.find_element(
                                    By.XPATH, "//*[@id='" + option + "']/option[" + str(rNumRandom) + "]").click()
                        iselementdisplayed = self.isElementDisplayed_check(By.ID, Field_Id + "Oth")
                        if iselementdisplayed:
                            field_value = Field_Id + "Oth" + repeatCount
                            self.driver.find_element(By.ID, Field_Id + "Oth").send_keys(field_value)
                            update_fields[Field_Id] = field_value

                        # break

                elif Component_ID == 1001:
                    if "Race" in Field_Label:
                        Race_Value = "Native Hawaiian or Other Pacific Islander, Black or African American, Asian, American Indian or Alaska Native, Unknown, White"
                        race = Race_Value.split(",")
                        numberOfCkBoxes = len(race)
                        for i in range(numberOfCkBoxes):
                            race_txt = race[i].strip()
                            if race_txt == "American Indian or Alaska Native":
                                if self.isElementDisplayed_check(By.NAME, "pageClientVO.americanIndianAlskanRace"):
                                    if self.driver.find_element(By.NAME,
                                                                "pageClientVO.americanIndianAlskanRace").is_selected():
                                        self.driver.find_element(By.NAME,
                                                                 "pageClientVO.americanIndianAlskanRace").click()
                                elif self.isElementDisplayed_check(By.NAME,
                                                                   "cTContactClientVO.americanIndianAlskanRace"):
                                    self.driver.find_element(By.NAME,
                                                             "cTContactClientVO.americanIndianAlskanRace").click()
                                elif self.isElementDisplayed_check(By.ID, "DEM153"):
                                    self.driver.find_element(By.ID, "DEM153").click()
                                elif self.isElementDisplayed_check(By.ID, "americanIndianRace"):
                                    self.driver.find_element(By.ID, "americanIndianRace").click()
                                elif self.isElementDisplayed_check(By.NAME, "pamClientVO.americanIndianRace"):
                                    self.driver.find_element(By.NAME, "pamClientVO.americanIndianRace").click()
                                elif self.isElementDisplayed_check(By.NAME, "pamClientVO.americanIndianAlskanRace"):
                                    self.driver.find_element(By.NAME, "pamClientVO.americanIndianAlskanRace").click()
                                # break;
                            elif race_txt == "Asian":
                                if self.isElementDisplayed_check(By.NAME, "pageClientVO.asianRace"):
                                    if self.driver.find_element(By.NAME, "pageClientVO.asianRace").is_selected():
                                        self.driver.find_element(By.NAME, "pageClientVO.asianRace").click()
                                    self.driver.find_element(By.NAME, "pageClientVO.asianRace").click()
                                elif self.isElementDisplayed_check(By.NAME, "cTContactClientVO.asianRace"):
                                    self.driver.findElement(By.NAME, "cTContactClientVO.asianRace").click()
                                elif self.isElementDisplayed_check(By.ID, "DEM154"):
                                    self.driver.findElement(By.ID, "DEM154").click()
                                elif self.isElementDisplayed_check(By.ID, "asianRace"):
                                    self.driver.find_element(By.ID, "asianRace").click()
                                elif self.isElementDisplayed_check(By.NAME, "pamClientVO.asianRace"):
                                    self.driver.findElement(By.NAME, "pamClientVO.asianRace").click()

                            elif race_txt == "Black or African American":
                                if self.isElementDisplayed_check(By.NAME, "pageClientVO.africanAmericanRace"):
                                    if self.driver.find_element(By.NAME,
                                                                "pageClientVO.africanAmericanRace").is_selected():
                                        self.driver.find_element(By.NAME, "pageClientVO.africanAmericanRace").click()
                                    self.driver.find_element(By.NAME, "pageClientVO.africanAmericanRace").click()
                                elif self.isElementDisplayed_check(By.NAME, "cTContactClientVO.africanAmericanRace"):
                                    self.driver.find_element(By.NAME, "cTContactClientVO.africanAmericanRace").click()
                                elif self.isElementDisplayed_check(By.ID, "DEM155"):
                                    self.driver.find_element(By.ID, "DEM155").click()
                                elif self.isElementDisplayed_check(By.ID, "africanRace"):
                                    self.driver.find_element(By.ID, "africanRace").click()
                                elif self.isElementDisplayed_check(By.NAME, "pamClientVO.africanAmericanRace"):
                                    self.driver.findElement(By.NAME, "pamClientVO.africanAmericanRace").click()

                                # break;
                            elif race_txt == "Native Hawaiian or Other Pacific Islander":
                                if self.isElementDisplayed_check(By.NAME, "pageClientVO.hawaiianRace"):
                                    if self.driver.find_element(By.NAME, "pageClientVO.hawaiianRace").is_selected():
                                        self.driver.find_element(By.NAME, "pageClientVO.hawaiianRace").click()
                                    self.driver.find_element(By.NAME, "pageClientVO.hawaiianRace").click()
                                elif self.isElementDisplayed_check(By.NAME, "cTContactClientVO.hawaiianRace"):
                                    self.driver.find_element(By.NAME, "cTContactClientVO.hawaiianRace").click()
                                elif self.isElementDisplayed_check(By.ID, "DEM156"):
                                    self.driver.find_element(By.ID, "DEM156").click()
                                elif self.isElementDisplayed_check(By.ID, "hawaiianRace"):
                                    self.driver.findElement(By.ID, "hawaiianRace").click()

                                # break
                            elif race_txt == "White":
                                if self.isElementDisplayed_check(By.NAME, "pageClientVO.whiteRace"):
                                    if self.driver.find_element(By.NAME, "pageClientVO.whiteRace").is_selected():
                                        self.driver.find_element(By.NAME, "pageClientVO.whiteRace").click()
                                    self.driver.find_element(By.NAME, "pageClientVO.whiteRace").click()
                                elif self.isElementDisplayed_check(By.NAME, "cTContactClientVO.whiteRace"):
                                    self.driver.find_element(By.NAME, "cTContactClientVO.whiteRace").click()
                                elif self.isElementDisplayed_check(By.ID, "DEM157"):
                                    self.driver.find_element(By.ID, "DEM157").click()
                                elif self.isElementDisplayed_check(By.ID, "whiteRace"):
                                    self.driver.find_element(By.ID, "whiteRace").click()
                                elif self.isElementDisplayed_check(By.NAME, "pamClientVO.whiteRace"):
                                    self.driver.find_element(By.NAME, "pamClientVO.whiteRace").click()
                                # break;

                            elif race_txt == "Other":
                                if self.isElementDisplayed_check(By.NAME, "pageClientVO.whiteRace"):
                                    if self.driver.find_element(By.NAME, "pageClientVO.whiteRace").is_selected():
                                        self.driver.find_element(By.NAME, "pageClientVO.whiteRace").click()
                                    self.driver.find_element(By.NAME, "pageClientVO.otherRace").click()
                                elif self.isElementDisplayed_check(By.NAME, "cTContactClientVO.otherRace"):
                                    self.driver.find_element(By.NAME, "cTContactClientVO.otherRace").click()
                                elif self.isElementDisplayed_check(By.ID, "otherRace"):
                                    self.driver.find_element(By.ID, "otherRace").click()
                                # break;
                            elif race_txt == "Refused to answer":
                                if self.isElementDisplayed_check(By.NAME, "pageClientVO.refusedToAnswer"):
                                    if self.driver.find_element(By.NAME, "pageClientVO.refusedToAnswer").is_selected():
                                        self.driver.find_element(By.NAME, "pageClientVO.refusedToAnswer").click()
                                    self.driver.find_element(By.NAME, "pageClientVO.refusedToAnswer").click()
                                elif self.isElementDisplayed_check(By.NAME, "cTContactClientVO.refusedToAnswer"):
                                    self.driver.find_element(By.NAME, "cTContactClientVO.refusedToAnswer").click()
                                elif self.isElementDisplayed_check(By.ID, "refusedToAnswer"):
                                    self.driver.find_element(By.ID, "refusedToAnswer").click()
                                # break;
                            elif race_txt == "Not Asked":
                                if self.isElementDisplayed_check(By.NAME, "pageClientVO.notAsked"):
                                    if self.driver.find_element(By.NAME, "pageClientVO.notAsked").is_selected():
                                        self.driver.find_element(By.NAME, "pageClientVO.notAsked").click()
                                    self.driver.find_element(By.NAME, "pageClientVO.notAsked").click()
                                elif self.isElementDisplayed_check(By.NAME, "cTContactClientVO.notAsked"):
                                    self.driver.find_element(By.NAME, "cTContactClientVO.notAsked").click()
                                elif self.isElementDisplayed_check(By.ID, "notAsked"):
                                    self.driver.find_element(By.ID, "notAsked").click()
                                # break;
                            elif race_txt == "Unknown":
                                if self.isElementDisplayed_check(By.NAME, "pageClientVO.unKnownRace"):
                                    if self.driver.find_element(By.NAME, "pageClientVO.unKnownRace").is_selected():
                                        # if self.driver.find_element(By.NAME, "pageClientVO.unKnownRace").is_selected():
                                        self.driver.find_element(By.NAME("pageClientVO.unKnownRace")).click()
                                    self.driver.find_element(By.NAME, "pageClientVO.unKnownRace").click()
                                elif self.isElementDisplayed_check(By.NAME, "cTContactClientVO.unKnownRace"):
                                    self.driver.find_element(By.NAME, "cTContactClientVO.unKnownRace").click()
                                elif self.isElementDisplayed_check(By.ID, "DEM152"):
                                    self.driver.find_element(By.ID, "DEM152").click()
                                elif self.isElementDisplayed_check(By.ID, "unknownRace"):
                                    self.driver.find_element(By.ID, "unknownRace").click()
                                elif self.isElementDisplayed_check(By.NAME, "pamClientVO.unKnownRace"):
                                    self.driver.find_element(By.NAME, "pamClientVO.unKnownRace").click()
                                # break;

                            else:
                                if self.isElementDisplayed_check(By.NAME, "pageClientVO.unKnownRace"):
                                    if self.driver.find_element(By.NAME, "pageClientVO.unKnownRace").is_selected():
                                        self.driver.find_element(By.NAME, "pageClientVO.unKnownRace").click()
                                    self.driver.find_element(By.NAME, "pageClientVO.unKnownRace").click()
                                elif self.isElementDisplayed_check(By.NAME, "cTContactClientVO.unKnownRace"):
                                    self.driver.find_element(By.NAME, "cTContactClientVO.unKnownRace").click()
                                elif self.isElementDisplayed_check(By.ID, "DEM152"):
                                    self.driver.find_element(By.ID, "DEM152").click()
                                elif self.isElementDisplayed_check(By.ID, "unknownRace"):
                                    self.driver.find_element(By.ID, "unknownRace").click()

                                # break;

                myArray1 = self.metadata[itr - 1]
                myArray2 = self.metadata[itr + 1]
                Repeating_Group_Prev = myArray1[4]
                Repeating_Group_Next = myArray2[4]
                next_field_id = myArray2[1]
                repeatTimes = 0

                print("myArray", self.metadata[itr], myArray1, myArray2, Repeating_Group, Repeating_Group_Prev,
                      Repeating_Group_Next)
                print("ITR", itr)

                if Repeating_Group is not None:
                    if Repeating_Group_Prev != Repeating_Group:
                        addButtonId = Field_Id
                        repeatTimes = random.randint(1, 3)
                        repeatRow = itr
                        self.repeatCountAddBtn = 1

                print("ADDn Button ID ", addButtonId, Repeating_Group, Repeating_Group_Next)
                # without the condition or only Repeating_Group is not None
                #                   # Check if next feld is disabled
                click_action = "false"
                if Repeating_Group is not None and Repeating_Group_Next == Repeating_Group:
                    print("Checking for field ", Field_Id)
                    next_element_disabled = "true"
                    idx = itr + 1
                    next_enabled_repeating_group = self.metadata[idx][4]
                    while next_element_disabled == "true":
                        metadata_entry = self.metadata[idx]
                        row_field_id = metadata_entry[1]
                        next_element_disabled = self.driver.find_element(By.ID, row_field_id).get_attribute('disabled')
                        next_enabled_repeating_group = metadata_entry[4]
                        print(" idx , nextelement  disable state ", idx, next_element_disabled)
                        idx = idx + 1

                    click_action = Repeating_Group != next_enabled_repeating_group
                    print("Next active entry in group ", next_enabled_repeating_group, click_action)

                if Repeating_Group is not None and (
                        Repeating_Group_Next != Repeating_Group or click_action) and Component_ID != 1016:
                    # // *[ @ id = "AddButtonToggleNBS_INV_GENV2_UI_4"] / td / input
                    # //*[@id="AddButtonToggleNBS_UI_GA35002"]/td/input
                    # //*[@id="AddButtonToggleNBS_UI_NBS_INV_GENV2_UI_4"]/td/input
                    # //*[@id="AddButtonToggleNBS_UI_GA35002"]/td/input
                    # // *[ @ id = "AddButtonToggleNBS_UI_GA21011"] / td / input
                    # //*[@id="AddButtonToggleNBS_UI_GA23001"]/td/input
                    # //*[@id="AddButtonToggleNBS_UI_GA23000"]/td/input
                    # //*[@id="AddButtonToggleNBS_UI_NBS_INV_GENV2_UI_4"]
                    # find the TXT field
                    # //*[@id="INV504"]
                    #  find the id of the element with class batchSubSection and has a child element with id Field-Id
                    button_id = ''
                    iselementdisplayed1 = False
                    Field_Id_updated1 = ''
                    try:
                        batch_sub_section = f"//*[@id = '{Field_Id}']//ancestor::*[contains(@class, 'batchSubSection')]"
                        batch_element = self.driver.find_element(By.XPATH, batch_sub_section)
                        button_id = batch_element.get_attribute("id")
                        #  AddButtonToggleNBS_UI_NBS_INV_GENV2_UI_4
                        print(f"{Field_Id} Add button identified ", f'AddButtonToggle{button_id}')
                        Field_Id_updated1 = f"//tr[@id='AddButtonToggle{button_id}']//td//input"
                        iselementdisplayed1 = self.isElementDisplayed_check(By.XPATH, Field_Id_updated1)
                    except Exception as e:
                        print("ERROR !!!!!!!!! ", str(e))
                        self.logger.info(str(e))

                    # "//*[@id="AddButtonToggleNBS_UI_75"]/td/input"
                    Field_Id_updated = f"//tr[@id='AddButtonToggle{addButtonId}']//td//input"
                    iselementdisplayed = self.isElementDisplayed_check(By.XPATH, Field_Id_updated)

                    #  CLICKING ON ADD BUTTON
                    if iselementdisplayed or iselementdisplayed1:
                        if iselementdisplayed:
                            try:
                                self.driver.find_element(By.XPATH, Field_Id_updated).click()
                                print("Button clicked", Field_Id_updated)
                            except Exception as e:
                                self.logger.info(str(e))
                        elif iselementdisplayed1:
                            try:
                                self.driver.find_element(By.XPATH, Field_Id_updated1).click()
                                print("Button clicked ", Field_Id_updated)
                            except Exception as e:
                                self.logger.info(str(e))

                        if self.repeatCountAddBtn < repeatTimes:
                            itr = repeatRow
                            self.repeatCountAddBtn += 1
                    else:
                        print(" ERROR! Not found ")
            except Exception as e:
                print("ERROR final !!!!!!!! ", str(e))
                self.logger.error(str(e))
                #  raise e
        time.sleep(2)
        self.driver.find_element(By.ID, "SubmitTop").click()
