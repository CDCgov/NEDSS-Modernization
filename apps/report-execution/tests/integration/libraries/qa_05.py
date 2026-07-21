import pytest
import yaml

from src.execute_report import execute_report
from src.models import ReportSpec

faker_schema = 'event_metric_hiv_std.yaml'


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationQa05Library:
    """Integration tests for the qa_05 library."""

    def test_execute_report_check_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'QA 5',
                'library_name': 'qa_05',
                'data_source_name': '[RDB].[dbo].[V_EVENT_METRIC]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[V_EVENT_METRIC]',
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data
        assert len(data) == 94
        assert len(data[0]) == 8
        assert len(data[0]) == len(result.content.columns)

        snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

        # Sanity check the result's shape beyond the snapshot
        for row in data:
            assert row[0] is not None
            assert row[0] != ''
            assert row[1] >= 0
            assert row[5] >= 0
            assert row[6] >= 0
            assert row[7] >= 0

        # all user_qc are unique
        unique_qc = set(row[0] for row in data)
        assert len(unique_qc) == len(data)

    def test_execute_report_no_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'QA 5',
                'library_name': 'qa_05',
                'data_source_name': '[RDB].[dbo].[V_EVENT_METRIC]',
                'subset_query': (
                    'SELECT * FROM [RDB].[dbo].[V_EVENT_METRIC] WHERE 1 = 2'
                ),
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data
        assert len(data) == 0
        assert len(result.content.columns) == 8

    def test_execute_report_check_metadata(self):
        """Check the metadata and column names are correct."""
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'QA05 Number of Records Entered by User ID',
                'library_name': 'qa_05',
                'data_source_name': '[RDB].[dbo].[V_EVENT_METRIC]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[V_EVENT_METRIC]',
            }
        )

        result = execute_report(report_spec)
        assert result.subheader is None
        assert result.description is None
        assert result.content_type == 'table'

        assert result.content.columns[0] == 'user_qc'
        assert result.content.columns[1] == 'OOJ_REFF'
        assert result.content.columns[2] == 'FIRST_NM'
        assert result.content.columns[3] == 'LAST_NM'
        assert result.content.columns[4] == 'PROVIDER_QUICK_CODE'
        assert result.content.columns[5] == 'ADD_USER_ID'
        assert result.content.columns[6] == 'REACTOR'
        assert result.content.columns[7] == 'PART_CLUS'
