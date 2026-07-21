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

        data = result.content.data

        snapshot.assert_match(yaml.dump(data), 'snapshot.yaml')

        assert len(data) > 0

        assert result.content.columns == [
            'CASE_VERIFICATION',
            'CALC_DISEASE_SITE',
        ]

        for d in data:
            assert len(d) == 2
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

        assert len(result.content.data) == 0
        assert result.content.columns == [
            'CASE_VERIFICATION',
            'CALC_DISEASE_SITE',
        ]
