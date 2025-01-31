# This is for the database connection
import pyodbc
from src.utilities.properties import Properties
from src.utilities.log_config import LogUtils
logger = LogUtils.loggen(__name__)


def get_db_connection():
    """ methods to get the database cursor"""
    connection = ''
    try:
        connection = pyodbc.connect("DRIVER={" + Properties.getDbDriverName()
                                    + "}; SERVER=" + Properties.getApplicationAddress()
                                    + "; DATABASE=" + Properties.getDataBaseName()
                                    + "; UID=" + Properties.getDbUserName()
                                    + "; PWD=" + Properties.getDbUserPassword()
                                    + ";")
    except Exception as e:
        logger.error(str(e))

    return connection


def close_db_connection():
    connection = get_db_connection()
    connection.close()

def close_db_connection_phdc(connection):
    """Method to close the database connection"""
    try:
        connection.close()
    except Exception as e:
        logger.error(f"Error closing connection: {e}")


