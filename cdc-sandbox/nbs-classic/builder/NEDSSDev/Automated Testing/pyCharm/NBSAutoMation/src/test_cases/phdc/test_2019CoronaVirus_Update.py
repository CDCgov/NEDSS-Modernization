###########################################################################################################################################
# Test Case Name: test_2019CoronaVirus_Update
# User Story:34191 - Automate PHDC  with 2019 Novel Coronavirus condition (Part 2)
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
import pytest
from src.page_objects.HomePage.homepage_search import HomePageSearch
from src.utilities.properties import Properties
from src.utilities.nbs_utilities import NBSUtilities

class TestCoronaVirusPHDCUpdate:
    # Initialize class attributes
    duplicated_file_path = None
    edited_file_path = None
    document_id = None
    updated_document_id = None

    @classmethod
    def setup_class(cls):
        # Step 1: Copy XML file
        original_file_path = 'src/files/Template_PHDC_2019NovelCoronaVirus.xml'
        destination_folder = r'C:\TEMP'
        cls.duplicated_file_path = NBSUtilities.copy_xml_file(original_file_path, destination_folder)
        print(f"Copied file path: {cls.duplicated_file_path}")

        # Step 2: Edit XML file
        _, _, _, cls.edited_file_path = NBSUtilities.edit_xml_file(cls.duplicated_file_path)
        print(f"Edited XML file. New File Name: {cls.edited_file_path}")

        # Step 3: Transfer the file
        NBSUtilities.transfer_file_net_use(cls.duplicated_file_path, cls.edited_file_path)

        # Step 4: Execute SQL statements
        nbs_interface_uid = NBSUtilities.execute_sql_statements(cls.edited_file_path)
        print(f"Retrieved nbs_interface_uid: {nbs_interface_uid}")

        # Step 5: Run Jenkins batch to get the initial document ID
        server = NBSUtilities.get_jenkins_server()
        job_name = Properties.getJenkinsJobPHCRImporter()
        node_name = Properties.get_active_environment()
        result, logs, cls.document_id, errors = NBSUtilities.run_jenkins_pipeline(server, job_name, node_name)
        assert result == 'SUCCESS', f"Pipeline failed with errors: {errors}"
        print(f"Document ID {cls.document_id} found.")

    def test_login_and_verify_document(self, setup):
        # Verify login and initial document
        assert NBSUtilities.performLoginValidation(setup), "Login failed"
        home_page = HomePageSearch(setup)
        home_page.select_event_id('Document ID', self.document_id)

    def test_modify_and_verify_xml(self, setup):
        # Step 6: Modify XML with new values
        modifications = NBSUtilities.modify_xml_with_new_values(self.duplicated_file_path)
        print(f"Modifications made: {modifications}")

        # Step 7: Transfer modified file to remote folder
        NBSUtilities.transfer_file_net_use(self.duplicated_file_path, self.edited_file_path)
        
        # **Execute SQL statements again after Step 7 and before Step 8**
        nbs_interface_uid = NBSUtilities.execute_sql_statements(self.edited_file_path)
        print(f"Retrieved nbs_interface_uid again: {nbs_interface_uid}")
        
        # Step 8: Execute Jenkins batch and get updated document ID
        server = NBSUtilities.get_jenkins_server()
        job_name = Properties.getJenkinsJobPHCRImporter()
        node_name = Properties.get_active_environment()
        result, logs, self.updated_document_id, errors = NBSUtilities.run_jenkins_pipeline_updated_docid(server, job_name, node_name)
        
        assert result == 'SUCCESS', f"Jenkins pipeline failed with errors: {errors}"
        print(f"Updated Document ID {self.updated_document_id} found.")

        # Step 9: Re-run login and verification
        assert NBSUtilities.performLoginValidation(setup), "Login failed"
        home_page = HomePageSearch(setup)
        home_page.verify_update_phdc('Document ID', self.updated_document_id)


if __name__ == "__main__":
    TestCoronaVirusPHDCUpdate.setup_class()
    pytest.main([__file__, '-v'])

