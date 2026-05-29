import pytest
import yaml

from src.execute_report import execute_report
from src.models import ReportSpec

faker_schema = 'std_hiv_datamart.yaml'


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationQa10Library:
    """Integration tests for the qa_10 library."""

    def test_execute_report_check_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'QA10',
                'library_name': 'qa_10',
                'data_source_name': '[RDB].[dbo].[STD_HIV_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART]',
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data
        assert len(data) == 81
        assert len(data[0]) == 20
        assert len(data[0]) == len(result.content.columns)

        snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

        # Sanity check the result's shape beyond the snapshot
        for row in data:
            assert row[0] is not None
            assert row[0] > 0
            assert row[2].startswith('PSN')
            assert len(row[5]) >= 0

    def test_execute_report_no_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'QA10',
                'library_name': 'qa_10',
                'data_source_name': '[RDB].[dbo].[STD_HIV_DATAMART]',
                'subset_query': (
                    'SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART] WHERE 1 = 2'
                ),
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data
        assert len(data) == 0
        assert len(result.content.columns) == 20

    def test_execute_report_check_metadata(self):
        """Check the metadata and column names are correct."""
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'QA10',
                'library_name': 'qa_10',
                'data_source_name': '[RDB].[dbo].[STD_HIV_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART]',
            }
        )

        result = execute_report(report_spec)
        assert result.header == 'QA10'
        assert result.subheader is None
        assert result.description is None
        assert result.content_type == 'table'

        assert result.content.columns[0] == 'INVESTIGATION_KEY'
        assert result.content.columns[1] == 'PATIENT_NAME'
        assert result.content.columns[2] == 'PATIENT_LOCAL_ID'
        assert result.content.columns[3] == 'PATIENT_AGE_REPORTED'
        assert result.content.columns[4] == 'PATIENT_SEX'
        assert result.content.columns[5] == 'DIAGNOSIS_CD'
        assert result.content.columns[6] == 'JURISDICTION_NM'
        assert result.content.columns[7] == 'INVESTIGATOR_INTERVIEW_QC'
        assert result.content.columns[8] == 'CLOSED_DT'
        assert result.content.columns[9] == 'Open_Status'
        assert result.content.columns[10] == 'PATIENT_PREGNANT_IND'
        assert result.content.columns[11] == 'PBI_PREG_IN_LAST_12MO_IND'
        assert result.content.columns[12] == 'PBI_PREG_AT_EXAM_IND'
        assert result.content.columns[13] == 'PBI_PREG_AT_IX_IND'
        assert result.content.columns[14] == 'PBI_PREG_IN_LAST_12MO_IND'
        assert result.content.columns[15] == 'ASSIGNED_DT'
        assert result.content.columns[16] == 'i'
        assert result.content.columns[17] == 'name_l'
        assert result.content.columns[18] == 'patient_id'
        assert result.content.columns[19] == 'age'
