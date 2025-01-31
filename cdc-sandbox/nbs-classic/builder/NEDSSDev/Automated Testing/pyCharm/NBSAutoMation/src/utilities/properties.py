import configparser
import os

config = configparser.RawConfigParser()
config.read(os.path.abspath(os.curdir) + '\\configurations\\config.ini')


class Properties:
    @staticmethod
    def get_active_environment():
        environment = config.get('Environment', 'active_environment')
        return environment

    @staticmethod
    def getApplicationURL():
        env = Properties.get_active_environment()
        url = config.get(env, 'baseURL')
        return url

    @staticmethod
    def getApplicationAddress():
        env = Properties.get_active_environment()
        server_name = config.get(env, 'server_name')
        return server_name

    @staticmethod
    def getRemoteHostname():
        env = Properties.get_active_environment()
        hostname = config.get(env, 'hostname')
        return hostname

    # @staticmethod
    # def getApplicationURL():
    #     url = config.get('commonInfo', 'baseURL')
    #     return url

    @staticmethod
    def getUserName():
        username = config.get('commonInfo', 'username')
        return username

    @staticmethod
    def getUserPassword():
        password = config.get('commonInfo', 'password')
        return password

    # @staticmethod
    # def getApplicationAddress():
    #     url = config.get('DatabaseInfo', 'server_name')
    #     return url

    @staticmethod
    def getDbUserName():
        username = config.get('DatabaseInfo', 'db_username')
        return username

    @staticmethod
    def getDbUserPassword():
        password = config.get('DatabaseInfo', 'db_password')
        return password

    @staticmethod
    def getDbDriverName():
        driver = config.get('DatabaseInfo', 'db_driver')
        return driver

    @staticmethod
    def getDataBaseName():
        driver = config.get('DatabaseInfo', 'database_name')
        return driver

    @staticmethod
    def getRemotePath():
        path = config.get('RemoteServer', 'remote_path')
        return path

    @staticmethod
    def getBatchFilePath():
        path = config.get('RemoteServer', 'batch_file_path')
        return path

    # @staticmethod
    # def getRemoteHostname():
    #     hostname = config.get('RemoteServer', 'hostname')
    #     return hostname

    # @staticmethod
    # def getRemotePort():
    #     port = config.get('RemoteServer', 'port')
    #     return port

    @staticmethod
    def getRemoteUsername():
        username = config.get('RemoteServer', 'username')
        return username

    @staticmethod
    def getRemotePassword():
        password = config.get('RemoteServer', 'password')
        return password
    
    #Added extra code for phdc script to work
    @staticmethod
    def getLocalFilePath():
        localfilepath = config.get('RemoteServer', 'local_file_path')
        return localfilepath
    
    #Added extra code for phdc script to work
    @staticmethod
    def getRemoteFilePath():
        remotefilepath = config.get('RemoteServer', 'remote_file_path')
        return remotefilepath

    @staticmethod
    def getJenkinsURL():
        url = config.get('JenkinsInfo', 'jenkins_url')
        return url

    @staticmethod
    def getJenkinsUsername():
        username = config.get('JenkinsInfo', 'jenkins_username')
        return username

    @staticmethod
    def getJenkinsPassword():
        password = config.get('JenkinsInfo', 'jenkins_password')
        return password

    @staticmethod
    def getJenkinsJobPHCRImporter():
        job_name = config.get('JenkinsInfo', 'job_phcrimporter')
        return job_name
    
    @staticmethod
    def getJenkinsJobdedupsimilar():
        job_name = config.get('JenkinsInfo', 'job_dedupsimilar')
        return job_name
    
    
    @staticmethod
    def getJenkinsJobdedupsame():
        job_name = config.get('JenkinsInfo', 'job_dedupsame')
        return job_name