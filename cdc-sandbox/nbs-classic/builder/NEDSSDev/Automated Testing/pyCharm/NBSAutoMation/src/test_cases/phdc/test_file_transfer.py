import os
import configparser
from src.utilities import nbs_utilities


def load_config():
    config = configparser.ConfigParser()
    config.read('config.ini')
    return config


def test_transfer_file():
    config = load_config()

    hostname = config.get('RemoteServer', 'hostname')
    username = config.get('RemoteServer', 'username')
    password = config.get('RemoteServer', 'password')

    local_file_path = 'c:/temp/test_file.txt'
    remote_file_path = 'C:/TEMP/test_file.txt'

    # Create a test file locally
    with open(local_file_path, 'w') as file:
        file.write("This is a test file for file transfer.")

    # Transfer the file using the net use method
    try:
        print(f"Transferring file {local_file_path} to {remote_file_path}...")
        nbs_utilities.transfer_file_net_use(local_file_path, os.path.basename(remote_file_path))
        print(f"File successfully transferred to {remote_file_path}")
    except Exception as e:
        print(f"An error occurred during file transfer: {e}")


if __name__ == "__main__":
    test_transfer_file()
