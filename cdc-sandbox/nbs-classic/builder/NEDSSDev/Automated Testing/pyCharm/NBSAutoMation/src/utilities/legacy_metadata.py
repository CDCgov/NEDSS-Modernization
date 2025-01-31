# import random
# import time
# from datetime import datetime
#
# import pyautogui
# from selenium.webdriver.common.by import By
# from selenium.webdriver.common.keys import Keys
# from selenium.webdriver.support.select import Select
#
# from src.utilities.constants import NBSConstants
# from src.utilities.dbconnections import get_db_connection
# from src.utilities.log_config import LogUtils
#
#
# class legacy_metadata:
#
#     def __init__(self, driver):
#         self.driver = driver
#         self.rowNum = 0
#         self.robot = None
#         self.iselementdisplayed = False
#         self.iselementpopulated = False
#         self.iselementdisplayed1 = False
#         self.log = None
#         self.df = None
#         self.comUtils = None
#         self.comPBUtils = None
#         self.element = None
#         self.jse = None
#         self.url = ""
#         self.con = None
#         self.stmt = None
#         self.rs = None
#         self.dbName = "nedss-dbsql"
#         self.instance = "test1"
#         self.text = ""
#         self.length = 0
#         self.Unit_Type_CD = "Dropdown value"
#         self.repeatCount = 1
#         self.repeatCountAddBtn = 1
#         self.rNumber = 1
#         self.tbTabOrder = 1
#         self.dt = "10/01/1951"  # start date
#         self.Var_Rash_onset_dt = "10/01/1951"  # start date;
#         self.tub195 = "09/01/1951"
#         self.DOB = "10/01/1950"  # start date;
#         self.num_of_lesions, self.num_of_macules, self.num_of_papules, self.num_of_vesicles = 1, 1, 1, 1
#         self.numeric_value = 1
#         self.specialChars = 'Deep^~&#~!@#$%^&*()_+{}:<>?`-=[],.\'\"\\'
#         self.investigation_date_of_report = "10/01/1959"
#         self.metadata = []
#         self.logger = LogUtils.loggen(__name__)
#         self.var_illness_onset_dt = "02/10/2024"
#         # self.ynu_random = 'Yes'
#         self.random_number_for_patient = datetime.now().strftime("%m%d%y")
#         self.patient_lastname = NBSConstants.APPEND_TEST_FOR_LAST_NAME.__add__("-").__add__(
#             str(self.random_number_for_patient))
#         self.patient_firstname = NBSConstants.APPEND_TEST_FOR_FIRST_NAME.__add__("-").__add__(
#             str(self.random_number_for_patient))
#         self.patient_middlename = NBSConstants.APPEND_TEST_FOR_MIDDLE_NAME.__add__("-").__add__(
#             str(self.random_number_for_patient))
#
#     def setUpDriver(self, driver):
#         self.driver = driver
#
#     def setUpDataFile(self, df):
#         self.df = df
#
#     def isElementDisplayed_check(self, elm_path, elm_val):
#         try:
#             elm = self.driver.find_element(elm_path, elm_val)
#             if elm:
#                 return True
#             else:
#                 return False
#         except Exception as e:
#             self.logger.info(str(e))
#             return False
#
#     def randIntMultiple(self, min_value, max_value, size):
#         return [random.randint(min_value, max_value) for _ in range(size)]
#
#     def readData(self, col, PatientName, templateName):
#         # self.comUtils.setUpDriver(self.driver)
#         # try:
#         #     self.robot = webdriver.Robot()
#         # except Exception as e:
#         #     print(e)
#         ynu_random = "Yes"
#         addButtonId = ""
#         repeatTimes = 0
#         repeatRow = 0
#
#         # self.url = f"jdbc:sqlserver://{database};SelectMethod=cursor;DatabaseName=nbs_odse"
#
#         try:
#             self.con = get_db_connection()
#             self.stmt = self.con.cursor()
#         except Exception as e:
#             print(e)
#
#         if templateName == 'INV_FORM_VAR':
#             query = f"select question_label,question_identifier,nbs_ui_component_uid,data_type,question_group_seq_nbr, unit_type_cd,code_set_group_id, tab_order_id from nbs_odse..nbs_ui_metadata where investigation_form_cd='{templateName}' order by tab_order_id,parent_uid, nbs_ui_metadata_uid"
#         elif templateName == 'INV_FORM_RVCT':
#             query = f"select question_label,question_identifier,nbs_ui_component_uid,data_type,question_group_seq_nbr, unit_type_cd,code_set_group_id,tab_order_id from nbs_odse..nbs_ui_metadata where investigation_form_cd='{templateName}' order by tab_order_id"
#         else:
#             query = f"select question_label,question_identifier,nbs_ui_component_uid,mask,question_group_seq_nbr, unit_type_cd,code_set_group_id,block_nm from wa_ui_metadata where wa_template_uid = (Select wa_template_uid from wa_template where template_nm = '{templateName}' and publish_ind_cd= 'T') and order_nbr>0 order by order_nbr"
#
#         try:
#             self.rs = self.stmt.execute(query)  # fetch_all()
#             print("DB Record", self.rs)
#
#         except Exception as e:
#             print(e)
#
#         count = 0
#         for row in self.rs:
#             myArray = list(row)
#             self.metadata.append(myArray)
#             count += 1
#
#         myArray = [None, None, None, None, None, None, None, None]
#         self.metadata.append(myArray)
#
#         self.stmt.close()
#
#         tab = 0
#         iselementdisplayed = False
#         text = ""
#         length = 0
#
#         for itr in range(count):
#             try:
#                 myArray = self.metadata[itr]
#                 Field_Label, Field_Id, Component_ID, Mask, Repeating_Group, Unit_Type_CD, Code_Set_Group_Id, Tab_Order_Id = myArray
#                 repeatCount = 2  # Set the value of repeatCount
#                 msg = "Element is read-only"  # Set the value of msg
#
#                 # if templateName == "INV_FORM_RVCT":
#                 #     if self.tbTabOrder == int(Tab_Order_Id):
#                 #         if self.tbTabOrder == 3:
#                 #             self.tbTabOrder += 1
#                 #             continue
#                 #         self.iselementdisplayed = self.comUtils.isElementDisplayed(
#                 #             By.XPATH, f"//*[@id='tabs0head{tab}']")
#                 #         if self.iselementdisplayed:
#                 #             self.driver.find_element(By.XPATH, f"//*[@id='tabs0head{tab}']").click()
#                 #         tab += 1
#                 #         self.tbTabOrder += 1
#
#                 if Component_ID == 1010:
#                     if templateName != 'INV_FORM_RVCT':
#                         try:
#                             self.driver.find_element(By.ID, f"tabs0head{tab}").click()
#                         except Exception as e:
#                             self.logger.info(str(e))
#                         tab += 1
#
#                 elif Component_ID == 1009:
#                     if (templateName == 'INV_FORM_RVCT' or templateName == 'INV_FORM_VAR') and (
#                             Mask.casefold() == "text"):
#                         iselementdisplayed = self.isElementDisplayed_check(By.NAME, f"pamClientVO.answer({Field_Id})")
#                     else:
#                         iselementdisplayed = self.isElementDisplayed_check(By.ID, Field_Id)
#
#                     if iselementdisplayed:
#                         if (templateName == 'INV_FORM_RVCT' or templateName == 'INV_FORM_VAR') and (
#                                 Mask.casefold == "text"):
#                             text = self.driver.find_element(By.NAME, f"pamClientVO.answer({Field_Id})").get_attribute(
#                                 "value")
#                         else:
#                             text = self.driver.find_element(By.ID, Field_Id).get_attribute("value")
#                         length = len(text)
#
#                         if length == 0:
#                             if Field_Id == "DEM196":
#                                 self.driver.find_element(By.NAME, f"pamClientVO.answer({Field_Id})").clear()
#                                 self.driver.find_element(By.NAME, f"pamClientVO.answer({Field_Id})").send_keys(
#                                     Field_Label + str(repeatCount))
#                             else:
#                                 try:
#                                     self.driver.find_element(By.ID, Field_Id).clear()
#                                     self.driver.find_element(By.ID, Field_Id).send_keys(Field_Label + str(repeatCount))
#                                 except Exception as e:
#                                     if msg in str(e):
#                                         pass
#
#                 elif Component_ID == 1008:
#                     if (
#                             templateName == 'INV_FORM_RVCT' or templateName == 'INV_FORM_VAR') and Mask.casefold() == 'text':
#                         iselementdisplayed = self.driver.find_element(By.NAME, "pamClientVO.answer(" + Field_Id + ")")
#                     else:
#                         iselementdisplayed = self.driver.find_element(By.ID, Field_Id)
#
#                     if iselementdisplayed:
#                         if (
#                                 templateName == 'INV_FORM_RVCT' or templateName == 'INV_FORM_VAR') and Mask.casefold() == 'text':
#                             text = self.driver.find_element(By.NAME,
#                                                             "pamClientVO.answer(" + Field_Id + ")").get_attribute(
#                                 "value")
#
#                     else:
#                         text = self.driver.find_element(By.ID, Field_Id).get_attribute("value")
#
#                     length = len(text)
#                     print("Mask & Field ID!!!!", Mask, Field_Id)
#                     if length == 0:
#                         if Field_Id == "NBS_LAB364":
#                             self.driver.find_element(By.ID, Field_Id).clear()
#                             self.driver.find_element(By.ID, Field_Id).send_keys("1")
#                             # continue
#                         elif Field_Id == "NBS452":
#                             self.driver.find_element(By.ID, Field_Id).clear()
#                             self.driver.find_element(By.ID, Field_Id).send_keys("10/20/2019")
#                             # continue
#                         elif Field_Id == "VAR151":
#                             self.driver.find_element(By.ID, Field_Id).clear()
#                             self.driver.find_element(By.ID, Field_Id).send_keys("10")
#                             # continue
#                         elif Field_Id == "VAR163":
#                             num_of_lesions = self.num_of_macules + self.num_of_papules + self.num_of_vesicles
#                             self.driver.find_element(By.ID, Field_Id).clear()
#                             self.driver.find_element(By.ID, Field_Id).send_keys(str(num_of_lesions))
#                             # continue
#                         elif Field_Id == "VAR108":
#                             self.driver.find_element(By.ID, Field_Id).clear()
#                             self.driver.find_element(By.ID, Field_Id).send_keys(str(self.num_of_macules))
#                             # continue;
#                         elif Field_Id == "VAR110":
#                             self.driver.find_element(By.ID, Field_Id).clear()
#                             self.driver.find_element(By.ID, Field_Id).send_keys(str(self.num_of_papules))
#                             # continue
#                         elif Field_Id == "VAR112":
#                             self.driver.find_element(By.ID, Field_Id).clear()
#                             self.driver.find_element(By.ID, Field_Id).send_keys(str(self.num_of_vesicles))
#                             # continue;
#                         elif Field_Id == "INV173" or Field_Id == "INV198":
#                             rand = random.randint(11111, 99999)
#                             self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").clear()
#                             self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").send_keys(
#                                 "2023-GA-ABCD" + str(rand))
#                             # continue
#
#                         if Mask.casefold() == 'date':
#                             if Field_Id == "INV137" or Field_Id == "VAR102":
#                                 self.driver.find_element(By.ID, Field_Id).clear()
#                                 self.driver.find_element(By.ID, Field_Id).send_keys(str(self.var_illness_onset_dt))
#                             elif Field_Id == "INV146":
#                                 now = datetime.now().strftime("%m/%d/%Y")
#                                 self.driver.find_element(By.ID, Field_Id).clear()
#                                 self.driver.find_element(By.ID, Field_Id).send_keys(str(now))
#                             elif Field_Id == "DEM115":
#                                 self.driver.find_element(By.ID, Field_Id).clear()
#                                 self.driver.find_element(By.ID, Field_Id).send_keys(self.DOB)
#                                 self.driver.find_element(By.ID, Field_Id).send_keys(Keys.TAB)
#                             elif Field_Id == "TUB195":
#                                 self.driver.find_element(By.ID, Field_Id).clear()
#                                 self.driver.find_element(By.ID, Field_Id).send_keys(self.tub195)
#                                 self.driver.find_element(By.ID, Field_Id).send_keys(Keys.TAB)
#                             elif Field_Id == "TUB273":
#                                 self.driver.find_element(By.ID, Field_Id).clear()
#                                 self.driver.find_element(By.ID, Field_Id).send_keys(self.investigation_date_of_report)
#                             else:
#                                 try:
#                                     if '2023' in self.dt:
#                                         dt = "10/01/1951"
#                                     # TODO: convertedDate may require to add extra day
#                                     convertedDate = datetime.now().strftime("%m/%d/%Y")
#                                     self.driver.find_element(By.ID, Field_Id).clear()
#                                     self.driver.find_element(By.ID, Field_Id).send_keys(convertedDate)
#                                     dt = convertedDate
#                                     if Field_Id == "INV111":
#                                         investigation_date_of_report = dt
#                                 except Exception as e:
#                                     if hasattr(e, 'message'):
#                                         if 'Element is read-only' in e.message:
#                                             self.logger.info("Element is read-only")
#                                             # break
#                                     self.logger.info(str(e))
#
#                         elif Mask.casefold() == 'text':
#                             if Field_Id == "DEM163":
#                                 self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").clear()
#                                 self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").send_keys(
#                                     "12345")
#                             elif Field_Id == "DEM238" or Field_Id == "DEM240":
#                                 self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").clear()
#                                 self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").send_keys(
#                                     "1234567890")
#                             elif Field_Id == "DEM240":
#                                 self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").clear()
#                                 self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").send_keys(
#                                     "1231231231")
#                             elif Field_Id == "DEM239":
#                                 self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").clear()
#                                 self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").send_keys(
#                                     "55555")
#                             elif Field_Id == "DEM241":
#                                 self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").clear()
#                                 self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").send_keys(
#                                     "66666")
#                             elif Field_Id == "DEM133":
#                                 self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").clear()
#                                 self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").send_keys(
#                                     "111-11-1111")
#                             elif Field_Id == "INV173" or Field_Id == "INV198":
#                                 rand = random.randint(11111, 99999)
#                                 self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").clear()
#                                 self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").send_keys(
#                                     "2023-GA-ABCD" + str(rand))
#                             # elif Field_Id == 'INV143' or Field_Id == 'INV144':
#                             #     pass
#                             else:
#                                 try:
#                                     self.driver.find_element(By.NAME, "pageClientVO.answer(" + Field_Id + ")").clear()
#                                     self.driver.find_element(By.NAME,
#                                                              "pageClientVO.answer(" + Field_Id + ")").send_keys(
#                                         "Field_Id" + str(self.repeatCount))
#                                 except Exception as e:
#                                     if hasattr(e, 'message'):
#                                         if 'Element is read-only' in e.message:
#                                             self.logger.info("Element is read-only")
#                                             # break
#                                     self.logger.info(str(e))
#
#                         elif Mask == 'TXT':
#                             try:
#                                 text = self.driver.find_element(By.ID, Field_Id).get_attribute("value")
#                                 length = len(text)
#                                 if length == 0:
#                                     self.driver.find_element(By.ID, Field_Id).clear()
#                                     print("FIELD_ID", Field_Id)
#                                     self.driver.find_element(By.ID, Field_Id).send_keys(
#                                         Field_Label + str(self.repeatCount))
#                             except Exception as e:
#                                 if hasattr(e, 'message'):
#                                     if 'Element is read-only' in e.message:
#                                         self.logger.info("Element is read-only")
#                                         # break
#                                 self.logger.info(str(e))
#
#                         elif Mask == "NUM" or Mask == "Numeric":
#                             if Field_Id == "VAR124":
#                                 rand = random.randint(1, 2)
#                                 if rand == 1:
#                                     rNum = random.randint(30, 50)
#                                     self.driver.find_element(By.ID, Field_Id).clear()
#                                     self.driver.find_element(By.ID, Field_Id).send_keys(str(rNum))
#                                 else:
#                                     rNum = random.randint(90, 110)
#                                     self.driver.find_element(By.ID, Field_Id).clear()
#                                     self.driver.find_element(By.ID, Field_Id).send_keys(str(rNum))
#
#                             elif Field_Id == "TUB112":
#                                 self.driver.find_element(By.ID, Field_Id).clear()
#                                 self.driver.find_element(By.ID, Field_Id).send_keys("2018")
#                             elif Field_Id == "TUB239":
#                                 self.driver.find_element(By.ID, Field_Id).clear()
#                                 self.driver.find_element(By.ID, Field_Id).send_keys("1")
#                             elif Field_Id == "TUB149":
#                                 rNumber = random.randint(4, 99)
#                                 self.driver.find_element(By.ID, Field_Id).clear()
#                                 self.driver.find_element(By.ID, Field_Id).send_keys(rNumber)
#                             elif Field_Id == "VAR147":
#                                 rNumber = random.randint(1, 5)
#                                 self.driver.find_element(By.ID, Field_Id).clear()
#                                 self.driver.find_element(By.ID, Field_Id).send_keys(rNumber)
#                             else:
#                                 try:
#                                     numeric_value = self.numeric_value + 1
#                                     self.driver.find_element(By.ID, Field_Id).clear()
#                                     self.driver.find_element(By.ID, Field_Id).send_keys(str(numeric_value))
#                                 except Exception as e:
#                                     if hasattr(e, 'message'):
#                                         if 'Element is read-only' in e.message:
#                                             self.logger.info("Element is read-only")
#                                             # break
#                                     self.logger.info(str(e))
#
#                             if Unit_Type_CD == "CODED":
#                                 field_Id_updated = Field_Id + "UNIT_textbox"
#                                 iselementdisplayed = self.isElementDisplayed_check(By.NAME, field_Id_updated)
#                                 if iselementdisplayed:
#                                     text = self.driver.find_element(By.NAME, field_Id_updated).get_attribute("value")
#                                     length = len(text)
#                                     if length == 0:
#                                         self.driver.find_element(By.NAME, field_Id_updated).clear()
#                                         button = Field_Id + "UNIT_button"
#                                         # JavascriptExecutor jse
#                                         self.driver.find_element(By.NAME, button).click()
#                                         countySize = len(self.driver.find_elements(By.XPATH,
#                                                                                    "//*[@id='" + Field_Id + "UNIT" + "']/option"))
#                                         rNum = str(random.randint(2, countySize))
#                                         element = self.driver.find_element(By.XPATH,
#                                                                            "//*[@id='" + Field_Id + "UNIT" + "']/option[" + rNum + "]")
#                                         self.driver.execute_script("arguments[0].scrollIntoView()", element)
#                                         # element.get_attribute("innerHTML")
#                                         text1 = self.driver.find_element(By.XPATH,
#                                                                          "//*[@id='" + Field_Id + "UNIT" + "']/option[" + rNum + "]").text
#                                         self.driver.find_element(By.NAME, field_Id_updated).send_keys(text1)
#                                         pyautogui.press('tab')
#                                         iselementdisplayed = self.isElementDisplayed_check(By.ID, Field_Id + "Oth")
#                                         if iselementdisplayed:
#                                             self.driver.find_element(By.ID, Field_Id + "Oth").send_keys(
#                                                 text + str(self.repeatCount))
#
#                         elif Mask == "NUM_TEMP":
#                             text1 = ""
#                             if Unit_Type_CD == "CODED":
#                                 field_Id_updated = Field_Id + "UNIT_textbox"
#                                 iselementdisplayed = self.isElementDisplayed_check(By.NAME, field_Id_updated)
#                                 if iselementdisplayed:
#                                     text = self.driver.find_element(By.NAME, field_Id_updated).get_attribute("value")
#                                     length = len(text)
#                                     if length == 0:
#                                         self.driver.find_element(By.NAME, field_Id_updated).clear()
#                                         button = Field_Id + "UNIT_button"
#                                         self.driver.find_element(By.NAME, button).click()
#                                         countySize = len(self.driver.find_elements(By.XPATH,
#                                                                                    "//*[@id='" + Field_Id + "UNIT" + "']/option"))
#                                         rNum = str(random.randint(2, countySize))
#                                         element = self.driver.find_element(
#                                             By.XPATH("//*[@id='" + Field_Id + "UNIT" + "']/option[" + rNum + "]"))
#                                         self.driver.execute_script("arguments[0].scrollIntoView()", element)
#                                         text1 = self.driver.find_element(By.XPATH,
#                                                                          "//*[@id='" + Field_Id + "UNIT" + "']/option[" + rNum + "]").text
#                                         self.driver.find_element(By.NAME(field_Id_updated)).send_keys(text1)
#                                         pyautogui.press('tab')
#                                         iselementdisplayed = self.isElementDisplayed_check(By.ID, Field_Id + "Oth")
#                                         if iselementdisplayed:
#                                             self.driver.find_element(By.ID(Field_Id + "Oth")).send_keys(
#                                                 text + str(self.repeatCount))
#
#                             if text1 == "Celsius":
#                                 try:
#                                     rNum = random.randint(30, 50)
#                                     self.driver.find_element(By.ID, Field_Id).clear()
#                                     self.driver.find_element(By.ID, Field_Id).send_keys(str(rNum))
#                                 except Exception as e:
#                                     if hasattr(e, 'message'):
#                                         if 'Element is read-only' in e.message:
#                                             self.logger.info("Element is read-only")
#                                         # break
#                                     self.logger.info(str(e))
#
#                             elif text1 == "Fahrenheit":
#                                 try:
#                                     rNum = random.randint(90, 110)
#                                     self.driver.find_element(By.ID, Field_Id).clear()
#                                     self.driver.find_element(By.ID, Field_Id).send_keys(str(rNum))
#                                 except Exception as e:
#                                     if hasattr(e, 'message'):
#                                         if 'Element is read-only' in e.message:
#                                             self.logger.info("Element is read-only")
#                                         # break
#                                     self.logger.info(str(e))
#
#                     elif Mask == "TXT_ZIP":
#                         try:
#                             self.driver.find_element(By.ID, Field_Id).clear()
#                             self.driver.find_element(By.ID, Field_Id).send_keys("12345")
#                         except Exception as e:
#                             if hasattr(e, 'message'):
#                                 if 'Element is read-only' in e.message:
#                                     self.logger.info("Element is read-only")
#                                     # break
#                             self.logger.info(str(e))
#
#                     elif Mask == "TXT_PHONE":
#                         try:
#                             self.driver.find_element(By.ID, Field_Id).clear()
#                             self.driver.find_element(By.ID, Field_Id).send_keys("1234567890")
#                         except Exception as e:
#                             if hasattr(e, 'message'):
#                                 if 'Element is read-only' in e.message:
#                                     self.logger.info("Element is read-only")
#                                     # break
#                             self.logger.info(str(e))
#
#                     elif Mask == "NUM_EXT":
#                         try:
#                             self.driver.find_element(By.ID, Field_Id).clear()
#                             self.driver.find_element(By.ID, Field_Id).send_keys("11111")
#                         except Exception as e:
#                             if hasattr(e, 'message'):
#                                 if 'Element is read-only' in e.message:
#                                     self.logger.info("Element is read-only")
#                                     # break
#                             self.logger.info(str(e))
#
#                     elif Mask == "TXT_EMAIL":
#                         try:
#                             self.driver.find_element(By.ID, Field_Id).clear()
#                             self.driver.find_element(By.ID, Field_Id).send_keys("email@email.com")
#                         except Exception as e:
#                             if hasattr(e, 'message'):
#                                 if 'Element is read-only' in e.message:
#                                     self.logger.info("Element is read-only")
#                                     # break
#                             self.logger.info(str(e))
#
#                     elif Mask == "TXT_ID15":
#                         try:
#                             self.driver.find_element(By.ID, Field_Id).clear()
#                             self.driver.find_element(By.ID, Field_Id).send_keys("1234556799467")
#                         except Exception as e:
#                             if hasattr(e, 'message'):
#                                 if 'Element is read-only' in e.message:
#                                     self.logger.info("Element is read-only")
#                                     # break
#                             self.logger.info(str(e))
#
#                     elif Mask == "NUM_YYYY":
#                         try:
#                             self.driver.find_element(By.ID, Field_Id).clear()
#                             #  seeing UI validation error when passing value 2022, So removed the value
#                             self.driver.find_element(By.ID, Field_Id).send_keys()
#                         except Exception as e:
#                             if hasattr(e, 'message'):
#                                 if 'Element is read-only' in e.message:
#                                     self.logger.info("Element is read-only")
#                                     # break
#                             self.logger.info(str(e))
#
#                     elif Mask == "NUM_TEMP":
#                         try:
#                             self.driver.find_element(By.ID, Field_Id).clear()
#                             self.driver.find_element(By.ID, Field_Id).send_keys("56")
#                         except Exception as e:
#                             if hasattr(e, 'message'):
#                                 if 'Element is read-only' in e.message:
#                                     self.logger.info("Element is read-only")
#                                     # break
#                             self.logger.info(str(e))
#                     #  break
#
#                 elif Component_ID == 1007:
#                     field_Id_updated = Field_Id + "_textbox"
#                     iselementdisplayed = self.isElementDisplayed_check(By.NAME, field_Id_updated)
#                     print("Select field ", field_Id_updated,  iselementdisplayed , Code_Set_Group_Id)
#                     if iselementdisplayed is True:
#                         if str(Code_Set_Group_Id) == "4150":
#                             print("ynu_random!!!!!!!!!!!!!!!!!!", ynu_random)
#                             # if ynu_random == "Yes" or ynu_random == "No" or ynu_random == "Unknown":
#                             self.driver.find_element(By.NAME, field_Id_updated).clear()
#                             self.driver.find_element(By.NAME, field_Id_updated).send_keys(ynu_random)
#                             self.driver.find_element(By.NAME, field_Id_updated).send_keys(Keys.TAB)
#                             # else:
#                             #     sel_option = random.randint(1, 3)
#                             #     if sel_option == 1:
#                             #         self.driver.find_element(By.NAME, field_Id_updated).send_keys("Yes")
#                             #         self.driver.find_element(By.NAME, field_Id_updated).send_keys(Keys.TAB)
#                             #     elif sel_option == 2:
#                             #         self.driver.find_element(By.NAME, field_Id_updated).send_keys("No")
#                             #         self.driver.find_element(By.NAME, field_Id_updated).send_keys(Keys.TAB)
#                             #     else:
#                             #         self.driver.find_element(By.NAME, field_Id_updated).send_keys("Unknown")
#                             #         self.driver.find_element(By.NAME, field_Id_updated).send_keys(Keys.TAB)
#                         elif field_Id_updated == "TUB130_textbox":
#                             self.driver.find_element(By.NAME, field_Id_updated).clear()
#                             self.driver.find_element(By.NAME, field_Id_updated).send_keys("Positive")
#                             self.driver.find_element(By.NAME, field_Id_updated).send_keys(Keys.TAB)
#                         elif field_Id_updated == "TUB233_textbox":
#                             self.driver.find_element(By.NAME, field_Id_updated).clear()
#                             self.driver.find_element(By.NAME, field_Id_updated).send_keys("Adverse Treatment Event")
#                             self.driver.find_element(By.NAME, field_Id_updated).send_keys(Keys.TAB)
#                         elif Field_Id == "VAR211":
#                             if self.rNumber == 1:
#                                 self.driver.find_element(By.NAME, field_Id_updated).send_keys("Celsius")
#                             elif self.rNumber == 2:
#                                 self.driver.find_element(By.NAME, field_Id_updated).send_keys("Fahrenheit")
#                         else:
#                             text = self.driver.find_element(By.NAME, field_Id_updated).get_attribute("value")
#                             length = len(text)
#                             if length == 0:
#                                 self.driver.find_element(By.NAME, field_Id_updated).clear()
#                                 button = Field_Id + "_button"
#                                 self.driver.find_element(By.NAME, button).click()
#                                 dropdown = self.driver.find_element(By.XPATH, "//select[@id='" + Field_Id + "']")
#                                 select = Select(dropdown)
#                                 option_size = len(select.options)
#                                 randnum = random.randint(2, option_size)
#                                 select.select_by_index(randnum - 1)
#                                 pyautogui.press('tab')
#                                 iselementdisplayed = self.driver.find_element(By.ID, Field_Id + "Oth")
#                                 if iselementdisplayed:
#                                     self.driver.find_element(By.ID, Field_Id + "Oth").send_keys(
#                                         Field_Id + "Oth" + str(self.repeatCount))
#
#                             # xp = '//*[@id="AddButtonToggleNBS_INV_GENV2_UI_4"]/td/input'
#                             # #   //tr[@id='AddButtonToggleNBS_UI_INV2002']//td//input
#                             # #  //tr[@id='AddButtonToggleNBS_UI_DEM113']//td//input
#                             #
#                             # Field_Id_updated = f"//tr[@id='AddButtonToggle{Field_Id}']//td//input"
#                             # Field_Id_updated1 = f"//tr[@id='AddButtonToggleNBS_UI_{Field_Id}']//td//input"
#                             # iselementdisplayed_elm = self.isElementDisplayed_check(By.XPATH, Field_Id_updated)
#                             # iselementdisplayed1_elm = self.isElementDisplayed_check(By.XPATH, Field_Id_updated1)
#                             # #  CLICKING ON ADD BUTTON
#                             #
#                             # print(" COMPARING VALUES !!!", xp == Field_Id_updated, xp == Field_Id_updated1)
#                             # print(" HEY !!!!!!!!!!!!!!!!!!!,", Field_Id_updated, Field_Id_updated1)
#                             # if iselementdisplayed_elm or iselementdisplayed1_elm:
#                             #     if iselementdisplayed_elm:
#                             #         self.driver.find_element(By.XPATH, Field_Id_updated).click()
#                             #     elif iselementdisplayed1_elm:
#                             #         self.driver.find_element(By.XPATH, Field_Id_updated1).click()
#                             # else:
#                             #     print(" I AM IN ELSE")
#                             #     try:
#                             #         self.driver.find_element(By.XPATH, xp).click()
#                             #     except Exception as e:
#                             #         print(str(e))
#
#                 #  break
#
#                 elif Component_ID == 1031:
#                     field_Id_updated = Field_Id + "_textbox"
#                     iselementdisplayed = self.isElementDisplayed_check(By.NAME, field_Id_updated)
#                     if iselementdisplayed:
#                         text = self.driver.find_element(By.NAME, field_Id_updated).get_attribute("value")
#                         length = len(text)
#                         if length == 0:
#                             self.driver.find_element(By.NAME, field_Id_updated).clear()
#                             button = Field_Id + "_button"
#                             self.driver.find_element(By.NAME, button).click()
#                             dropdown = self.driver.find_element(By.XPATH, "//select[@id='" + Field_Id + "']")
#                             select = Select(dropdown)
#                             option_size = len(select.options)
#                             randnum = random.randint(2, option_size)
#                             select.select_by_index(randnum - 1)
#                             pyautogui.press('tab')
#                             iselementdisplayed = self.isElementDisplayed_check(By.ID, Field_Id + "Oth")
#                             if iselementdisplayed:
#                                 self.driver.find_element(By.ID, Field_Id + "Oth").send_keys(
#                                     Field_Id + "Oth" + str(self.repeatCount))
#
#                         if Field_Id == "TUB167":
#                             pass
#
#                     # break
#
#                 elif Component_ID == 1017:
#                     field_Id_updated = Field_Id + "Text"
#                     iselementdisplayed = self.isElementDisplayed_check(By.ID, field_Id_updated)
#                     if iselementdisplayed:
#                         text = self.driver.find_element(By.ID, field_Id_updated).get_attribute("value")
#                         length = len(text)
#                         if length == 0:
#                             self.driver.find_element(By.ID, field_Id_updated).clear()
#                             self.driver.find_element(By.ID, field_Id_updated).send_keys("1")
#                             field_ID_Button = Field_Id + "CodeLookupButton"
#                             self.driver.find_element(By.ID, field_ID_Button).click()
#
#                     # break
#
#                 elif Component_ID == 1013:
#                     option = Field_Id
#                     confirmSize = len(self.driver.find_elements(By.XPATH, "//*[@id='" + option + "']/option"))
#                     rNum = 0
#                     rNumRandom = 0
#                     if Field_Id == "TUB119":
#                         continue
#                     if confirmSize <= 2:
#                         rNum = 1
#                         self.driver.find_element(By.XPATH,
#                                                  "//*[@id='" + option + "']/option[" + str(rNum) + "]").click()
#                     else:
#                         if confirmSize > 10:
#                             rNum = 10
#                         else:
#                             rNum = random.randint(2, confirmSize - 1)
#                             randNums = self.randIntMultiple(2, confirmSize, rNum)
#                             for i in range(len(randNums)):
#                                 rNumRandom = randNums[i]
#                                 if Field_Id == "TUB167" and rNumRandom == 2:
#                                     continue
#                                 element = self.driver.find_element(
#                                     By.XPATH, "//*[@id='" + option + "']/option[" + str(rNumRandom) + "]")
#                                 # jse = (JavascriptExecutor)driver;
#                                 # jse.executeScript("arguments[0].scrollIntoView()", element);
#                                 self.driver.execute_script("arguments[0].scrollIntoView()", element)
#                                 self.driver.find_element(
#                                     By.XPATH, "//*[@id='" + option + "']/option[" + str(rNumRandom) + "]").click()
#                         iselementdisplayed = self.isElementDisplayed_check(By.ID, Field_Id + "Oth")
#                         if iselementdisplayed:
#                             self.driver.find_element(By.ID, Field_Id + "Oth").send_keys(Field_Id + "Oth" + repeatCount)
#
#                         # break
#
#                 elif Component_ID == 1001:
#                     if "Race" in Field_Label:
#                         Race_Value = "Native Hawaiian or Other Pacific Islander, Black or African American, Asian, American Indian or Alaska Native, Unknown, White"
#                         race = Race_Value.split(",")
#                         numberOfCkBoxes = len(race)
#                         for i in range(numberOfCkBoxes):
#                             race_txt = race[i].strip()
#                             if race_txt == "American Indian or Alaska Native":
#                                 if self.isElementDisplayed_check(By.NAME, "pageClientVO.americanIndianAlskanRace"):
#                                     if self.driver.find_element(By.NAME,
#                                                                 "pageClientVO.americanIndianAlskanRace").is_selected():
#                                         self.driver.find_element(By.NAME,
#                                                                  "pageClientVO.americanIndianAlskanRace").click()
#                                 elif self.isElementDisplayed_check(By.NAME,
#                                                                    "cTContactClientVO.americanIndianAlskanRace"):
#                                     self.driver.find_element(By.NAME,
#                                                              "cTContactClientVO.americanIndianAlskanRace").click()
#                                 elif self.isElementDisplayed_check(By.ID, "DEM153"):
#                                     self.driver.find_element(By.ID, "DEM153").click()
#                                 elif self.isElementDisplayed_check(By.ID, "americanIndianRace"):
#                                     self.driver.find_element(By.ID, "americanIndianRace").click()
#                                 elif self.isElementDisplayed_check(By.NAME, "pamClientVO.americanIndianRace"):
#                                     self.driver.find_element(By.NAME, "pamClientVO.americanIndianRace").click()
#                                 elif self.isElementDisplayed_check(By.NAME, "pamClientVO.americanIndianAlskanRace"):
#                                     self.driver.find_element(By.NAME, "pamClientVO.americanIndianAlskanRace").click()
#                                 # break;
#                             elif race_txt == "Asian":
#                                 if self.isElementDisplayed_check(By.NAME, "pageClientVO.asianRace"):
#                                     if self.driver.find_element(By.NAME, "pageClientVO.asianRace").is_selected():
#                                         self.driver.find_element(By.NAME, "pageClientVO.asianRace").click()
#                                     self.driver.find_element(By.NAME, "pageClientVO.asianRace").click()
#                                 elif self.isElementDisplayed_check(By.NAME, "cTContactClientVO.asianRace"):
#                                     self.driver.findElement(By.NAME, "cTContactClientVO.asianRace").click()
#                                 elif self.isElementDisplayed_check(By.ID, "DEM154"):
#                                     self.driver.findElement(By.ID, "DEM154").click()
#                                 elif self.isElementDisplayed_check(By.ID, "asianRace"):
#                                     self.driver.find_element(By.ID, "asianRace").click()
#                                 elif self.isElementDisplayed_check(By.NAME, "pamClientVO.asianRace"):
#                                     self.driver.findElement(By.NAME, "pamClientVO.asianRace").click()
#
#                             elif race_txt == "Black or African American":
#                                 if self.isElementDisplayed_check(By.NAME, "pageClientVO.africanAmericanRace"):
#                                     if self.driver.find_element(By.NAME,
#                                                                 "pageClientVO.africanAmericanRace").is_selected():
#                                         self.driver.find_element(By.NAME, "pageClientVO.africanAmericanRace").click()
#                                     self.driver.find_element(By.NAME, "pageClientVO.africanAmericanRace").click()
#                                 elif self.isElementDisplayed_check(By.NAME, "cTContactClientVO.africanAmericanRace"):
#                                     self.driver.find_element(By.NAME, "cTContactClientVO.africanAmericanRace").click()
#                                 elif self.isElementDisplayed_check(By.ID, "DEM155"):
#                                     self.driver.find_element(By.ID, "DEM155").click()
#                                 elif self.isElementDisplayed_check(By.ID, "africanRace"):
#                                     self.driver.find_element(By.ID, "africanRace").click()
#                                 elif self.isElementDisplayed_check(By.NAME, "pamClientVO.africanAmericanRace"):
#                                     self.driver.findElement(By.NAME, "pamClientVO.africanAmericanRace").click()
#
#                                 # break;
#                             elif race_txt == "Native Hawaiian or Other Pacific Islander":
#                                 if self.isElementDisplayed_check(By.NAME, "pageClientVO.hawaiianRace"):
#                                     if self.driver.find_element(By.NAME, "pageClientVO.hawaiianRace").is_selected():
#                                         self.driver.find_element(By.NAME, "pageClientVO.hawaiianRace").click()
#                                     self.driver.find_element(By.NAME, "pageClientVO.hawaiianRace").click()
#                                 elif self.isElementDisplayed_check(By.NAME, "cTContactClientVO.hawaiianRace"):
#                                     self.driver.find_element(By.NAME, "cTContactClientVO.hawaiianRace").click()
#                                 elif self.isElementDisplayed_check(By.ID, "DEM156"):
#                                     self.driver.find_element(By.ID, "DEM156").click()
#                                 elif self.isElementDisplayed_check(By.ID, "hawaiianRace"):
#                                     self.driver.findElement(By.ID, "hawaiianRace").click()
#
#                                 # break
#                             elif race_txt == "White":
#                                 if self.isElementDisplayed_check(By.NAME, "pageClientVO.whiteRace"):
#                                     if self.driver.find_element(By.NAME, "pageClientVO.whiteRace").is_selected():
#                                         self.driver.find_element(By.NAME, "pageClientVO.whiteRace").click()
#                                     self.driver.find_element(By.NAME, "pageClientVO.whiteRace").click()
#                                 elif self.isElementDisplayed_check(By.NAME, "cTContactClientVO.whiteRace"):
#                                     self.driver.find_element(By.NAME, "cTContactClientVO.whiteRace").click()
#                                 elif self.isElementDisplayed_check(By.ID, "DEM157"):
#                                     self.driver.find_element(By.ID, "DEM157").click()
#                                 elif self.isElementDisplayed_check(By.ID, "whiteRace"):
#                                     self.driver.find_element(By.ID, "whiteRace").click()
#                                 elif self.isElementDisplayed_check(By.NAME, "pamClientVO.whiteRace"):
#                                     self.driver.find_element(By.NAME, "pamClientVO.whiteRace").click()
#                                 # break;
#
#                             elif race_txt == "Other":
#                                 if self.isElementDisplayed_check(By.NAME, "pageClientVO.whiteRace"):
#                                     if self.driver.find_element(By.NAME, "pageClientVO.whiteRace").is_selected():
#                                         self.driver.find_element(By.NAME, "pageClientVO.whiteRace").click()
#                                     self.driver.find_element(By.NAME, "pageClientVO.otherRace").click()
#                                 elif self.isElementDisplayed_check(By.NAME, "cTContactClientVO.otherRace"):
#                                     self.driver.find_element(By.NAME, "cTContactClientVO.otherRace").click()
#                                 elif self.isElementDisplayed_check(By.ID, "otherRace"):
#                                     self.driver.find_element(By.ID, "otherRace").click()
#                                 # break;
#                             elif race_txt == "Refused to answer":
#                                 if self.isElementDisplayed_check(By.NAME, "pageClientVO.refusedToAnswer"):
#                                     if self.driver.find_element(By.NAME, "pageClientVO.refusedToAnswer").is_selected():
#                                         self.driver.find_element(By.NAME, "pageClientVO.refusedToAnswer").click()
#                                     self.driver.find_element(By.NAME, "pageClientVO.refusedToAnswer").click()
#                                 elif self.isElementDisplayed_check(By.NAME, "cTContactClientVO.refusedToAnswer"):
#                                     self.driver.find_element(By.NAME, "cTContactClientVO.refusedToAnswer").click()
#                                 elif self.isElementDisplayed_check(By.ID, "refusedToAnswer"):
#                                     self.driver.find_element(By.ID, "refusedToAnswer").click()
#                                 # break;
#                             elif race_txt == "Not Asked":
#                                 if self.isElementDisplayed_check(By.NAME, "pageClientVO.notAsked"):
#                                     if self.driver.find_element(By.NAME, "pageClientVO.notAsked").is_selected():
#                                         self.driver.find_element(By.NAME, "pageClientVO.notAsked").click()
#                                     self.driver.find_element(By.NAME, "pageClientVO.notAsked").click()
#                                 elif self.isElementDisplayed_check(By.NAME, "cTContactClientVO.notAsked"):
#                                     self.driver.find_element(By.NAME, "cTContactClientVO.notAsked").click()
#                                 elif self.isElementDisplayed_check(By.ID, "notAsked"):
#                                     self.driver.find_element(By.ID, "notAsked").click()
#                                 # break;
#                             elif race_txt == "Unknown":
#                                 if self.isElementDisplayed_check(By.NAME, "pageClientVO.unKnownRace"):
#                                     if self.driver.find_element(By.NAME, "pageClientVO.unKnownRace").is_selected():
#                                         # if self.driver.find_element(By.NAME, "pageClientVO.unKnownRace").is_selected():
#                                         self.driver.find_element(By.NAME("pageClientVO.unKnownRace")).click()
#                                     self.driver.find_element(By.NAME, "pageClientVO.unKnownRace").click()
#                                 elif self.isElementDisplayed_check(By.NAME, "cTContactClientVO.unKnownRace"):
#                                     self.driver.find_element(By.NAME, "cTContactClientVO.unKnownRace").click()
#                                 elif self.isElementDisplayed_check(By.ID, "DEM152"):
#                                     self.driver.find_element(By.ID, "DEM152").click()
#                                 elif self.isElementDisplayed_check(By.ID, "unknownRace"):
#                                     self.driver.find_element(By.ID, "unknownRace").click()
#                                 elif self.isElementDisplayed_check(By.NAME, "pamClientVO.unKnownRace"):
#                                     self.driver.find_element(By.NAME, "pamClientVO.unKnownRace").click()
#                                 # break;
#
#                             else:
#                                 if self.isElementDisplayed_check(By.NAME, "pageClientVO.unKnownRace"):
#                                     if self.driver.find_element(By.NAME, "pageClientVO.unKnownRace").is_selected():
#                                         self.driver.find_element(By.NAME, "pageClientVO.unKnownRace").click()
#                                     self.driver.find_element(By.NAME, "pageClientVO.unKnownRace").click()
#                                 elif self.isElementDisplayed_check(By.NAME, "cTContactClientVO.unKnownRace"):
#                                     self.driver.find_element(By.NAME, "cTContactClientVO.unKnownRace").click()
#                                 elif self.isElementDisplayed_check(By.ID, "DEM152"):
#                                     self.driver.find_element(By.ID, "DEM152").click()
#                                 elif self.isElementDisplayed_check(By.ID, "unknownRace"):
#                                     self.driver.find_element(By.ID, "unknownRace").click()
#
#                                 # break;
#
#                 myArray1 = self.metadata[itr - 1]
#                 myArray2 = self.metadata[itr + 1]
#                 Repeating_Group_Prev = myArray1[4]
#                 Repeating_Group_Next = myArray2[4]
#                 repeatTimes = 0
#
#                 print("myArray", myArray1, myArray2, Repeating_Group, Repeating_Group_Prev, Repeating_Group_Next)
#                 print("ITR", itr)
#
#                 if Repeating_Group is not None:
#                     if Repeating_Group_Prev != Repeating_Group:
#                         addButtonId = Field_Id
#                         repeatTimes = random.randint(1, 3)
#                         repeatRow = itr
#                         self.repeatCountAddBtn = 1
#
#                 print("ADDn Button ID ", addButtonId, Repeating_Group, Repeating_Group_Next)
#                 # without the condition or only Repeating_Group is not None
#
#                 if Repeating_Group is not None and Repeating_Group_Next != Repeating_Group:
#                     # // *[ @ id = "AddButtonToggleNBS_INV_GENV2_UI_4"] / td / input
#                     # //*[@id="AddButtonToggleNBS_UI_GA35002"]/td/input
#                     # //*[@id="AddButtonToggleNBS_UI_NBS_INV_GENV2_UI_4"]/td/input
#                     # //*[@id="AddButtonToggleNBS_UI_GA35002"]/td/input
#                     # // *[ @ id = "AddButtonToggleNBS_UI_GA21011"] / td / input
#                     # //*[@id="AddButtonToggleNBS_UI_GA23001"]/td/input
#                     # //*[@id="AddButtonToggleNBS_UI_GA23000"]/td/input
#                     # //*[@id="AddButtonToggleNBS_UI_NBS_INV_GENV2_UI_4"]
#                     # find the TXT field
#                     # //*[@id="INV504"]
#                     #  find the id of the element with class batchSubSection and has a child element with id Field-Id
#                     button_id = ''
#                     if addButtonId != Field_Id :
#                         batch_sub_section = f"//input[@id = '{Field_Id}']//ancestor::*[contains(@class, 'batchSubSection')]"
#                         batch_element = self.driver.find_element(By.XPATH, batch_sub_section)
#                         button_id = batch_element.get_attribute("id")
#                         #  AddButtonToggleNBS_UI_NBS_INV_GENV2_UI_4
#                         print(f"{Field_Id} Add button identified ", f'AddButtonToggle{button_id}')
#
#                     # "//*[@id="AddButtonToggleNBS_UI_75"]/td/input"
#                     Field_Id_updated = f"//tr[@id='AddButtonToggle{addButtonId}']//td//input"
#                     Field_Id_updated1 = f"//tr[@id='AddButtonToggle{button_id}']//td//input"
#
#                     iselementdisplayed = self.isElementDisplayed_check(By.XPATH, Field_Id_updated)
#                     iselementdisplayed1 = self.isElementDisplayed_check(By.XPATH, Field_Id_updated1)
#
#                     #  CLICKING ON ADD BUTTON
#                     if iselementdisplayed or iselementdisplayed1:
#                         if iselementdisplayed:
#                             try:
#                                 self.driver.find_element(By.XPATH, Field_Id_updated).click()
#                                 print("Button clicked ",Field_Id_updated)
#                             except Exception as e:
#                                 self.logger.info(str(e))
#                         elif iselementdisplayed1:
#                             try:
#                                 self.driver.find_element(By.XPATH, Field_Id_updated1).click()
#                                 print("Button clicked ", Field_Id_updated)
#                             except Exception as e:
#                                 self.logger.info(str(e))
#
#                         if self.repeatCountAddBtn < repeatTimes:
#                             itr = repeatRow
#                             self.repeatCountAddBtn += 1
#                     else :
#                         print(" ERROR! Not found ")
#             except Exception as e:
#                 print("ERROR !!!!!!!!! ", str(e))
#                 self.logger.info(str(e))
#         time.sleep(2)
#         self.driver.find_element(By.ID, "SubmitTop").click()
