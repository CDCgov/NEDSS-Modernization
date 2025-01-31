import logging
import os


class LogUtils:
    @staticmethod
    def loggen(name):
        logger = logging.getLogger(name)
        # FileHandler class to set the location of log file
        fileHandler = logging.FileHandler(os.path.abspath(os.curdir) + '\\logs\\nedss.log')
        # Formatter class to set the format of log file
        formatter = logging.Formatter("%(asctime)s :%(levelname)s : %(name)s :%(lineno)d]:%(message)s")
        # object of FileHandler gets formatting info from setFormatter method
        fileHandler.setFormatter(formatter)
        # logger object gets formatting, path of log file info with addHandler #method
        logger.addHandler(fileHandler)
        # setting logging level to INFO
        logger.setLevel(logging.DEBUG)
        return logger
