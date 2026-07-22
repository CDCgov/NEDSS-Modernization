import pytest
import yaml

from src.execute_report import execute_report
from src.models import ReportSpec

faker_schema = 'std_hiv_datamart.yaml'


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationNbsQa01Library:
    """Integration tests for the qa_01 library."""

    # Helper to generate common spec base
    @staticmethod
    def create_spec(**overrides):
        base = {
            'is_export': True,
            'is_builtin': True,
            'report_title': 'QA 01',
            'library_name': 'qa_01',
            'subset_query': 'SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART]',
        }
        base.update(overrides)
        return ReportSpec.model_validate(base)

    def test_execute_report_check_data(self, snapshot):
        report_spec = self.create_spec()

        result = execute_report(report_spec)

        data = result.content.data
        assert len(data) == 534
        assert len(data[0]) == 18
        assert len(data[0]) == len(result.content.columns)

        snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

        # Sanity check the result's shape beyond the snapshot
        record = None
        for row in result.content.data:
            if row[3] is not None and row[4] is not None and row[6] is not None:
                record = row
                break

        assert record is not None
        assert record[3].lower() == record[13]
        assert record[4] == record[17]

    def test_execute_report_no_data(self, snapshot):
        report_spec = self.create_spec(
            subset_query="""
                         SELECT * 
                         FROM [RDB].[dbo].[STD_HIV_DATAMART] 
                         WHERE patient_name = 'Russell, Lee'
                        """
        )

        result = execute_report(report_spec)

        data = result.content.data
        assert len(data) == 0
        assert len(result.content.columns) == 18

    def test_execute_report_check_metadata(self):
        """Check the metadata and column names are correct."""
        expected_columns = [
            'INVESTIGATION_KEY',
            'ADD_USER_ID',
            'PROVIDER_QUICK_CODE',
            'PATIENT_NAME',
            'PATIENT_AGE_REPORTED',
            'PATIENT_SEX',
            'PATIENT_RACE',
            'DIAGNOSIS_CD',
            'FIELD_RECORD_NUMBER',
            'INVESTIGATOR_INTERVIEW_QC',
            'Open_Status',
            'ASSIGNED_DT',
            'CLOSED_DT',
            'name_l',
            'race',
            'sex',
            'i',
            'age',
        ]

        report_spec = self.create_spec(report_title='QA01 Interview Record List')

        result = execute_report(report_spec)

        assert result.content.columns == expected_columns
