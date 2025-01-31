###########################################################################################################################################
# Test Case Name: test_2019CoronaVirus
# User Story:34034 - Automate PHDC – PHDC with 2019 Novel Coronavirus condition
# Dependencies:
# History:
# Test Case Overview:
# 1. Copies XML template from project's files folder and place it in the local c:temp folder
# 2. Modifies the XML template (Middle 4 digits of first occurrence of CASXXXXX ID, middle 4 digits PSN ID, first name and last namesa)
# 3. Connect to the remote server and place the modified xml template in c:\temp folder
# 4. Run the database operations, inserting and updating a record 
# 5. Run the Jenkins Pipeline for PHCR Importer and get the logs including the Document ID
# 6. Login to NBS Application and Search for the Document ID
# 7. Open the patient File, click on the Events Tab and verify the Document ID 
# 8. Open the Document and Verify the Document ID
##########################################################################################################################################
import time
import pytest
from src.page_objects.HomePage.homepage_search import HomePageSearch
from src.utilities.properties import Properties
from src.utilities.nbs_utilities import NBSUtilities

def test_copy_xml():
    original_file_path = 'src/files/Template_PHDC_2019NovelCoronaVirus.xml'
    destination_folder = r'C:\TEMP'

    duplicated_file_path = NBSUtilities.copy_xml_file(original_file_path, destination_folder)
    print(f"Copied file path: {duplicated_file_path}")
    assert duplicated_file_path is not None, "Duplicated file path is None"
    return duplicated_file_path

def test_edit_xml():
    duplicated_file_path = test_copy_xml()

    new_number, first_name, last_name, new_file_name = NBSUtilities.edit_xml_file(duplicated_file_path)
    print(f"Edited XML file. New Number: {new_number}, First Name: {first_name}, Last Name: {last_name}, New File Name: {new_file_name}")
    assert new_file_name is not None, "New file name is None"
    return duplicated_file_path, new_file_name

def test_transfer_file():
    duplicated_file_path, new_file_name = test_edit_xml()
    print('Duplicated file path:', duplicated_file_path)
    print('New file name:', new_file_name)

    NBSUtilities.transfer_file_net_use(duplicated_file_path, new_file_name)
    print(f"File copied: {new_file_name}")
    assert new_file_name is not None, "File copy failed"
    return duplicated_file_path, new_file_name

def test_database_operations():
    duplicated_file_path, new_file_name = test_transfer_file()
    print('Executing SQL operations...')
    nbs_interface_uid = NBSUtilities.execute_sql_statements(new_file_name)
    print(f"Retrieved nbs_interface_uid: {nbs_interface_uid}")
    assert nbs_interface_uid is not None, "nbs_interface_uid is None"
    return nbs_interface_uid

def test_run_jenkins_batch():
    nbs_interface_uid = test_database_operations()
    server = NBSUtilities.get_jenkins_server()
    job_name = Properties.getJenkinsJobPHCRImporter()
    node_name = Properties.get_active_environment()
    result, logs, document_id, errors = NBSUtilities.run_jenkins_pipeline(server, job_name, node_name)
    assert result == 'SUCCESS', f"Pipeline failed with errors: {errors}"
    assert document_id is not None, "Document ID not found in the logs."
    print(f"Document ID {document_id} found.")
    return document_id


def test_login_and_verify_document(setup):
    document_id = test_run_jenkins_batch()
    assert NBSUtilities.performLoginValidation(setup), "Login failed"
    home_page = HomePageSearch(setup)
    home_page.select_event_id('Document ID',document_id) 


if __name__ == "__main__":
    pytest.main([__file__, '-v'])