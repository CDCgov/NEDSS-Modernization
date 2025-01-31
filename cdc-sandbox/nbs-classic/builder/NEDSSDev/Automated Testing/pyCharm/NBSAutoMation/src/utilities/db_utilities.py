import pyodbc
from src.utilities.properties import Properties
from src.utilities.log_config import LogUtils



class DBUtilities:
    logger = LogUtils.loggen(__name__)

    def get_db_connection(self):
        """ methods to get the database cursor"""
        connection = None
        try:
            connection = pyodbc.connect("DRIVER={" + Properties.getDbDriverName()
                                        + "}; SERVER=" + Properties.getApplicationAddress()
                                        + "; DATABASE=" + Properties.getDataBaseName()
                                        + "; UID=" + Properties.getDbUserName()
                                        + "; PWD=" + Properties.getDbUserPassword()
                                        + ";")
        except Exception as e:
            self.logger.error(str(e))
        return connection

    def getOrgUniqueId(self, last_name):
        """ Method to  get org unique Id
        last_name : str, last name
        organization_uid : str, organisation uid
        """
        query = "select organization_uid from Organization where display_nm='" + last_name + "'  "
        organization_uid = 0000
        conn = None
        cur = None
        try:
            conn = self.get_db_connection()
            cur = conn.cursor()
            cur.execute(query)
            first_record = cur.fetchone()
            organization_uid = first_record[0]
        except Exception as e:
            self.logger.error(str(e))
        finally:
            if cur is not None:
                cur.close()
            if conn is not None:
                conn.close()
        return organization_uid

    def getPlaceUniqueId(self, last_name):
        """ Method to  get org unique Id
           last_name : str, last name
           organization_uid : str, organisation uid
           """
        query = "select place_uid from Place where nm='" + last_name + "'  "
        place_uid = None
        conn = None
        cur = None
        try:
            conn = self.get_db_connection()
            cur = conn.cursor()
            cur.execute(query)
            first_record = cur.fetchone()
            place_uid = first_record[0]
        except Exception as e:
            self.logger.error(str(e))
        finally:
            if cur is not None:
                cur.close()
            if conn is not None:
                conn.close()
        return place_uid

    def getProviderUniqueId(self, last_name, first_name):
        """ Method to  get provider unique Id
           last_name : str, last name
           first_name: str, first name
           provider_uid : str, provider uid
           """
        query = "select person_uid from person where last_nm='" + last_name + "' and first_nm='" + first_name + "'"
        provider_uid = None
        connection = None
        cur = None
        try:
            connection = self.get_db_connection()
            # self.logger.info(query)
            cur = connection.cursor()
            cur.execute(query)
            first_record = cur.fetchone()
            # self.logger.info(first_record)
            provider_uid = first_record[0]
            # self.logger.info(provider_uid)
        except:
            self.logger.exception(msg="Error in getProviderUniqueId While connecting sql server")
        finally:
            if cur is not None:
                cur.close()
            if connection is not None:
                connection.close()
        self.logger.info(provider_uid)
        return provider_uid

    def getProviderCityStatePostal(self, provider_uid):
        """ Method to  get provider address
              provider_uid : str, person uid
              address : dict, address info
              """
        city_code = {}
        connection = None
        cur = None
        fields = "pl.city_desc_txt,pl.state_cd,pl.zip_cd,pl.street_addr1,p.birth_time,p.curr_sex_cd,p.ethnic_group_ind"
        table = "person p, entity_locator_participation elp1, postal_locator pl"
        conditions = ("p.person_uid = elp1.entity_uid and elp1.class_cd = 'PST' and elp1.status_cd = 'A'  and "
                      "elp1.locator_uid = pl.postal_locator_uid and p.person_uid =").__add__(str(provider_uid))

        query = (f"SELECT {fields} "
                 f"FROM {table} "
                 f"WHERE {conditions};")
        try:
            connection = self.get_db_connection()
            # self.logger.info(query)
            cur = connection.cursor()
            rows = cur.execute(query)
            if rows:
                first_record = cur.fetchone()
                city_code["city"] = first_record[0]
                city_code["state"] = first_record[1]
                city_code["postal"] = first_record[2]
                city_code["address"] = first_record[3]
                city_code["dob"] = first_record[4]
                city_code["gender"] = first_record[5]
                city_code["eth-ind"] = first_record[6]
        except Exception as ex:
            self.logger.info(f"Error in getProviderCityStatePostal While connecting sql server {str(ex)}" )
            # self.logger.info(f"Delete Investigation Test Failed with {str(e)}")
        finally:
            if cur is not None:
                cur.close()
            if connection is not None:
                connection.close()
        return city_code

    def getProviderContactTelephone(self, provider_uid):
        """ Method to  get provider phone details
            provider_uid : str, person uid
            phone : dict, phone info
            """
        provider_telephone = {}
        connection = None
        cur = None
        fields = "tl.phone_nbr_txt,tl.email_address"
        table = "person p, entity_locator_participation elp2,tele_locator tl"
        conditions = ("p.person_uid = elp2.entity_uid and elp2.class_cd = 'TELE' and elp2.status_cd= 'A' and "
                      "elp2.locator_uid  = tl.tele_locator_uid and p.person_uid =".__add__(str(provider_uid)))

        query = (f"SELECT {fields} "
                 f"FROM {table} "
                 f"WHERE {conditions};")
        try:
            connection = self.get_db_connection()

            # self.logger.info(query)
            cur = connection.cursor()
            rows = cur.execute(query)
            if rows:
                first_record = cur.fetchone()
                provider_telephone["telephone"] = first_record[0]
                provider_telephone["email"] = first_record[1]
        except Exception as ex:
            self.logger.info(f"Error in getProviderCityStatePostal While connecting sql server {str(ex)}" )
        finally:
            if cur is not None:
                cur.close()
            if connection is not None:
                connection.close()
        return provider_telephone

    def getProviderIdentificationRecord(self, provider_uid):
        """ Method to  get provider identification details
            provider_uid : str, person uid
            provider_identification : dict, identification details
            """
        provider_identification = {}
        connection = None
        cur = None
        fields = "ei.type_cd,ei.root_extension_txt"
        table = "person p,  Entity_id ei "
        conditions = (" p.person_uid = ei.entity_uid and ei.status_cd = 'A' and p.record_status_cd='ACTIVE'  "
                      "and p.person_uid =".__add__(str(provider_uid)))

        query = (f"SELECT {fields} "
                 f"FROM {table} "
                 f"WHERE {conditions};")
        try:
            connection = self.get_db_connection()

            # self.logger.info(query)
            cur = connection.cursor()
            rows = cur.execute(query)
            if rows:
                first_record = cur.fetchone()
                provider_identification["identi_select"] = first_record[0]
                provider_identification["identi_value"] = first_record[1]

        except Exception as ex:
            self.logger.info(f"Error in getProviderCityStatePostal While connecting sql server {str(ex)}" )
        finally:
            if cur is not None:
                cur.close()
            if connection is not None:
                connection.close()
        return provider_identification

    def getPersonEmailId(self, provider_uid):
        """ Method to  get provider email id
            provider_uid : str, person uid
            provider_email : dict, email details
            """
        provider_email = {}
        connection = None
        cur = None
        fields = "tl.email_address"
        table = "person p, entity_locator_participation elp2,tele_locator tl"
        conditions = ("p.person_uid = elp2.entity_uid and elp2.class_cd = 'TELE' and elp2.status_cd= 'A' and "
                      " elp2.cd = 'NET' and elp2.locator_uid  = tl.tele_locator_uid and p.person_uid ="
                      .__add__(str(provider_uid)))

        query = (f"SELECT {fields} "
                 f"FROM {table} "
                 f"WHERE {conditions};")
        try:
            connection = self.get_db_connection()
            # self.logger.info(query)
            cur = connection.cursor()
            rows = cur.execute(query)
            if rows:
                first_record = cur.fetchone()
                # self.logger.info(first_record)
                provider_email["email"] = first_record[0]
        except Exception as ex:
            self.logger.info(f"Error in getProviderCityStatePostal While connecting sql server {str(ex)}" )
        finally:
            if cur is not None:
                cur.close()
            if connection is not None:
                connection.close()
        return provider_email

    def getPersonSelectedRaces(self, provider_uid):
        """ Method to  get person selected races
            provider_uid : str, person uid
            result_list : list, race list
            """
        connection = None
        cur = None
        result_list = None
        fields = "race_cd"
        table = "Person_race"
        conditions = "person_uid =".__add__(str(provider_uid))

        query = (f"SELECT {fields} "
                 f"FROM {table} "
                 f"WHERE {conditions};")
        try:
            connection = self.get_db_connection()
            # self.logger.info(query)
            cur = connection.cursor()
            cur.execute(query)
            rows = cur.fetchall()
            result_list = [list(row) for row in rows]
        except Exception as ex:
            self.logger.info(f"Error in getProviderCityStatePostal While connecting sql server {str(ex)}" )
        finally:
            if cur is not None:
                cur.close()
            if connection is not None:
                connection.close()
        return result_list

    def getPropertyByConfigKey(self, config_key_value):
        """ Method to  get config value based on key
            config_key_value : str, config key
            config_value : str, config value based on key
            """
        connection = None
        cur = None
        config_value = None
        fields = "config_value"
        table = "NBS_configuration"
        conditions = "config_key=".__add__(str(config_key_value))

        query = (f"SELECT {fields} "
                 f"FROM {table} "
                 f"WHERE {conditions};")
        try:
            connection = self.get_db_connection()
            # self.logger.info(query)
            cur = connection.cursor()
            rows = cur.execute(query)
            if rows:
                first_record = cur.fetchone()
                config_value = first_record[0]

        except Exception as ex:
            self.logger.info(f"Error in getProviderCityStatePostal While connecting sql server {str(ex)}" )
        finally:
            if cur is not None:
                cur.close()
            if connection is not None:
                connection.close()
        return config_value

    def getSingleColumnValueByTable(self, column_name, table_name, compare_column, compare_value):
        """ Method to  get value for single record column
          column_name : str, table column name
          table_name : str, table name
          compare_column: str, compare where clause column
          compare_value: str, compare where clause value
          """
        connection = None
        cur = None
        value = None
        fields = str(column_name)
        table = str(table_name)
        conditions = str(compare_column).__add__(" = '").__add__(str(compare_value)).__add__("'")

        query = (f"SELECT {fields} "
                 f"FROM {table} "
                 f"WHERE {conditions};")
        try:
            connection = self.get_db_connection()
            # self.logger.info(query)
            cur = connection.cursor()
            cur.execute(query)
            first_record = cur.fetchone()
            if first_record is not None:
                value = first_record[0]

        except Exception as ex:
            self.logger.info(f"Error in getProviderCityStatePostal While connecting sql server {str(ex)}" )
        finally:
            if cur is not None:
                cur.close()
            if connection is not None:
                connection.close()
        return value

    def getObservationUid(self, last_name, first_name):
        """ Method to  get observation uid
         last_name : str, person last name
         first_name : str, person first name
         """
        connection = None
        cur = None
        value = None
        connection = None
        cur = None
        query = (" SELECT obs.local_id FROM observation obs"
                 " inner join act_relationship act  on obs.observation_uid = act.target_act_uid"
                 "  inner join observation obs1  on act.source_act_uid = obs1.observation_uid"
                 " inner join Participation part   on part.act_uid = obs.observation_uid"
                 " inner join person p on p.person_uid = part.subject_entity_uid"
                 " AND obs1.obs_domain_cd_st_1 = 'Result' AND (act.source_act_uid = obs1.observation_uid)"
                 " AND (act.target_class_cd = 'OBS') AND (act.type_cd = 'COMP')  AND (act.source_class_cd = 'OBS')"
                 " AND (act.record_status_cd = 'ACTIVE') and obs.ctrl_cd_display_form = 'LabReport'"
                 " where p.last_nm = '".__add__(last_name) + "'  and p.first_nm= '"
                 .__add__(first_name) + "' and cast(obs.add_time as date) >= cast(GETDATE() as date)")

        try:
            connection = self.get_db_connection()
            # self.logger.info(query)
            cur = connection.cursor()
            rows = cur.execute(query)
            if rows:
                first_record = cur.fetchone()
                if first_record is not None:
                    value = first_record[0]

        except Exception as ex:
            self.logger.info(f"Error in getProviderCityStatePostal While connecting sql server {str(ex)}" )
        finally:
            if cur is not None:
                cur.close()
            if connection is not None:
                connection.close()
        return value

    def getInvTempData(self, template_name):
        """ Method to  get org unique Id
        last_name : str, last name
        organization_uid : str, organisation uid
        """
        if template_name == 'INV_FORM_VAR':
            query = "select question_label,question_identifier,nbs_ui_component_uid,data_type,question_group_seq_nbr, " \
                    "unit_type_cd,code_set_group_id, tab_order_id from nbs_odse..nbs_ui_metadata where " \
                    "investigation_form_cd='" + template_name + "' order by tab_order_id,parent_uid, " \
                                                                "nbs_ui_metadata_uid"
        elif template_name == 'INV_FORM_RVCT':
            query = "select question_label,question_identifier,nbs_ui_component_uid,data_type,question_group_seq_nbr, " \
                    "unit_type_cd,code_set_group_id,tab_order_id from nbs_odse..nbs_ui_metadata where " \
                    "investigation_form_cd='" + template_name + "' order by tab_order_id"
        else:
            query = "select question_label,question_identifier,nbs_ui_component_uid,mask,question_group_seq_nbr, " \
                    "unit_type_cd,code_set_group_id,block_nm from wa_ui_metadata where wa_template_uid = " \
                    "" + "(Select wa_template_uid from wa_template where template_nm = " \
                         "'" + template_name + "' and publish_ind_cd= 'T') and order_nbr>0 order by order_nbr"

        conn = None
        cur = None
        rs = None
        try:
            conn = self.get_db_connection()
            cur = conn.cursor()
            cur.execute(query)
            rs = cur.fetchall()
        except Exception as e:
            self.logger.error(str(e))
        finally:
            if cur is not None:
                cur.close()
            if conn is not None:
                conn.close()
        return rs


    def getInvTempName(self, condition):
        """ Method to  get org unique Id
        last_name : str, last name
        organization_uid : str, organisation uid
        """

        query = "select question_label,question_identifier,nbs_ui_component_uid,mask,question_group_seq_nbr, " \
                "unit_type_cd,code_set_group_id,block_nm from wa_ui_metadata where wa_template_uid = " \
                "" + "(Select wa_template_uid from wa_template where template_nm = " \
                     "'" + condition + "' and publish_ind_cd= 'T') and order_nbr>0 order by order_nbr"

        conn = None
        cur = None
        rs = None
        template_name = None
        try:
            conn = self.get_db_connection()
            cur = conn.cursor()
            cur.execute(query)
            rs = cur.fetchone()
            template_name = rs[0]
        except Exception as e:
            self.logger.error(str(e))
        finally:
            if cur is not None:
                cur.close()
            if conn is not None:
                conn.close()
        return template_name
