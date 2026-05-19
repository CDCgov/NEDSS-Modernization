import pytest
import yaml

from src.execute_report import execute_report
from src.models import ReportSpec

faker_schema = 'std_hiv_datamart.yaml'


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationNbsQa03Library:
    """Integration tests for the qa_03 library."""

    # Helper to generate common spec base
    @staticmethod
    def create_spec(**overrides):
        base = {
            'is_export': True,
            'is_builtin': True,
            'report_title': 'QA 03',
            'library_name': 'qa_03',
            'data_source_name': '[RDB].[dbo].[STD_HIV_DATAMART]',
            'subset_query': 'SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART]',
        }
        base.update(overrides)
        return ReportSpec.model_validate(base)

    """Integration tests for the qa_03 library."""

    def test_execute_report_check_data(self, snapshot):
        report_spec = self.create_spec()

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data
        assert len(data) == 514
        assert len(data[0]) == 10
        assert len(data[0]) == len(result.content.columns)

        snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

        # Sanity check the result's shape beyond the snapshot
        record = None
        for row in result.content.data:
            if (
                row[0] == 'CAS10015820GA01'  # INV_LOCAL_ID
                and row[2] == 'PSN10089835GA01'  # PATIENT_LOCAL_ID
            ):
                record = row
                break

        assert record is not None
        assert record[3] == '710'  # DIAGNOSIS_CD
        assert record[4] == '36'  # PATIENT_AGE_REPORTED
        assert record[5] == '06/09/2020'  # CONFIRMATION_DT
        assert record[6] == 'Middlesex County'  # JURISDICTION_NM
        assert record[7] == 'Torres Medical Center'  # ORGANIZATION_NAME
        assert record[8] == 'Don'  # PROVIDER
        assert record[9] == '89835'  # PATIENTID

    def test_execute_report_no_data(self, snapshot):
        report_spec = self.create_spec(
            subset_query='SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART] '
            "WHERE patient_name = 'Russell, Lee'"
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data
        assert len(data) == 0
        assert len(result.content.columns) == 10

    def test_execute_report_check_metadata(self):
        """Check the metadata and column names are correct."""
        expected_columns = [
            'INV_LOCAL_ID',
            'PATIENT_NAME',
            'PATIENT_LOCAL_ID',
            'DIAGNOSIS_CD',
            'PATIENT_AGE_REPORTED',
            'CONFIRMATION_DT',
            'JURISDICTION_NM',
            'ORGANIZATION_NAME',
            'PROVIDER',
            'PATIENTID',
        ]

        report_spec = self.create_spec(report_title='QA03 Interview Record List')

        result = execute_report(report_spec)
        assert result.header == 'QA03 Interview Record List'
        assert result.content_type == 'table'

        assert result.content.columns == expected_columns
