import pytest
import yaml

from src.execute_report import execute_report
from src.models import ReportSpec

faker_schema = 'tb_datamart.yaml'


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationNbsSr18Library:
    """Integration tests for the SR18 TB Case Verification library."""

    def test_execute_report_check_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'SR 18',
                'library_name': 'nbs_sr_18',
                'data_source_name': '[RDB].[dbo].[TB_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[TB_DATAMART]',
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data

        snapshot.assert_match(yaml.dump(data), 'snapshot.yaml')

        assert len(data) == 7

        # ensure the columns are correct
        assert result.content.columns == [
            'CASE_VERIFICATION',
            'CALC_DISEASE_SITE',
        ]

        # ensure proper data types for each column
        for d in data:
            assert isinstance(d[0], str)
            assert isinstance(d[1], str)

    def test_execute_report_no_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'SR 18',
                'library_name': 'nbs_sr_18',
                'data_source_name': '[RDB].[dbo].[TB_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[TB_DATAMART] WHERE 1 = 2',
            }
        )

        result = execute_report(report_spec)

        # should still have 7 rows of data and all columns
        assert len(result.content.data) == 7
        assert result.content.columns == [
            'CASE_VERIFICATION',
            'CALC_DISEASE_SITE',
        ]

        for data in result.content.data:
            assert isinstance(data[0], str)
            assert isinstance(data[1], str)
