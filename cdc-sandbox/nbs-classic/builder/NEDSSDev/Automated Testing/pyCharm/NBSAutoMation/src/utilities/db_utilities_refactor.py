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
        try:
            cur = self.get_db_connection().cursor()
            cur.execute(query)
            first_record = cur.fetchone()
            cur.close()
            self.get_db_connection().close()
            organization_uid = first_record[0]
        except Exception as e:
            self.logger.error(str(e))
        return organization_uid

    def getPlaceUniqueId(self, last_name):
        """ Method to  get org unique Id
        last_name : str, last name
        organization_uid : str, organisation uid
        """
        query = "select place_uid from Place where nm='" + last_name + "'  "
        place_uid = 0000
        try:
            cur = self.get_db_connection().cursor()
            cur.execute(query)
            first_record = cur.fetchone()
            cur.close()
            self.get_db_connection().close()
            place_uid = first_record[0]
        except Exception as e:
            self.logger.error(str(e))
        return place_uid

    def getProviderUniqueId(self, last_name, first_name):
        query = "select person_uid from person where last_nm='" + last_name + "' and first_nm='" + first_name + "'"
        provider_uid = 0000
        try:
            cur = self.get_db_connection().cursor()
            cur.execute(query)
            first_record = cur.fetchone()
            cur.close()
            self.get_db_connection().close()
            provider_uid = first_record[0]
        except:
            self.logger.info("Error While connecting sql server")
        return provider_uid

    def getProviderCityStatePostal(self, provider_uid):
        city_code = {}
        fields = "pl.city_desc_txt,pl.state_cd,pl.zip_cd,pl.street_addr1"
        table = "person p, entity_locator_participation elp1, postal_locator pl"
        conditions = ("p.person_uid = elp1.entity_uid and elp1.class_cd = 'PST' and elp1.status_cd = 'A'  and "
                      "elp1.locator_uid = pl.postal_locator_uid and p.person_uid =").__add__(str(provider_uid))

        query = (f"SELECT {fields} "
                 f"FROM {table} "
                 f"WHERE {conditions};")
        try:
            cur = self.get_db_connection().cursor()
            rows = cur.execute(query)
            if rows:
                first_record = cur.fetchone()
                city_code["city"] = first_record[0]
                city_code["state"] = first_record[1]
                city_code["postal"] = first_record[2]
                city_code["address"] = first_record[3]
            cur.close()
            self.get_db_connection().close()
        except:
            self.logger.info("Error While connecting sql server")
        return city_code

    def getProviderContactTelephone(self, provider_uid):
        provider_telephone = {}
        fields = "tl.phone_nbr_txt"
        table = "person p, entity_locator_participation elp2,tele_locator tl"
        conditions = ("p.person_uid = elp2.entity_uid and elp2.class_cd = 'TELE' and elp2.status_cd= 'A' and "
                      "elp2.locator_uid  = tl.tele_locator_uid and p.person_uid =".__add__(str(provider_uid)))

        query = (f"SELECT {fields} "
                 f"FROM {table} "
                 f"WHERE {conditions};")
        try:
            cur = self.get_db_connection().cursor()
            rows = cur.execute(query)
            if rows:
                first_record = cur.fetchone()
                # cls.logger.info(first_record)
                provider_telephone["telephone"] = first_record[0]
            cur.close()
            self.get_db_connection().close()
        except:
            self.logger.info("Error While connecting sql server")
        return provider_telephone

    def getProviderIdentificationRecord(self, provider_uid):
        provider_identification = {}
        fields = "ei.type_cd,ei.root_extension_txt"
        table = "person p,  Entity_id ei "
        conditions = (" p.person_uid = ei.entity_uid and ei.status_cd = 'A' and p.record_status_cd='ACTIVE'  "
                      "and p.person_uid =".__add__(str(provider_uid)))

        query = (f"SELECT {fields} "
                 f"FROM {table} "
                 f"WHERE {conditions};")
        try:
            cur = self.get_db_connection().cursor()
            rows = cur.execute(query)
            if rows:
                first_record = cur.fetchone()
                # cls.logger.info(first_record)
                provider_identification["identi_select"] = first_record[0]
                provider_identification["identi_value"] = first_record[1]
            cur.close()
            self.get_db_connection().close()
        except:
            self.logger.info("Error While connecting sql server")

        return provider_identification

    def close_db_connection(self):
        self.get_db_connection().close()

