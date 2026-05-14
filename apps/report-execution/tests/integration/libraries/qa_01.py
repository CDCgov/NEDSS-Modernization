import pytest
import yaml

from src.execute_report import execute_report
from src.models import ReportSpec

faker_schema = 'std_hiv_datamart.yaml'


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationNbsQa01Library:
    """Integration tests for the nbs_sr_02 library."""

    # Helper to generate common spec base
    @staticmethod
    def create_spec(**overrides):
        base = {
            'is_export': True,
            'is_builtin': True,
            'report_title': 'QA 01',
            'library_name': 'qa_01',
            'data_source_name': '[RDB].[dbo].[STD_HIV_DATAMART]',
            'subset_query': 'SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART]',
        }
        base.update(overrides)
        return ReportSpec.model_validate(base)

    """Integration tests for the nbs_custom library."""

    def test_execute_report_check_data(self, snapshot):
        report_spec = self.create_spec()

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data
        assert len(data) == 108  # two combinations with no data, zeros not filled
        assert len(data[0]) == 18
        assert len(data[0]) == len(result.content.columns)

        snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

        # Sanity check the result's shape beyond the snapshot
        record = None
        for row in result.content.data:
            if row[0] == 45481149 and row[3] == 'Van Dam, Venus' and row[4] == '43':
                record = row
                break

        assert record is not None

    def test_execute_report_no_data(self, snapshot):
        report_spec = self.create_spec(
            subset_query=t"""
                         SELECT * 
                         FROM [RDB].[dbo].[STD_HIV_DATAMART] 
                         WHERE patient_name = 'Russell, Lee'
                        """
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

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
        assert result.header == 'QA01 Interview Record List'
        assert len(result.description) > 30
        assert result.content_type == 'table'

        assert result.content.columns == expected_columns
