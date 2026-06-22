import logging

import pytest

from src.execute_report import execute_report
from src.models import ReportSpec

faker_schema = 'pa_01.yaml'

logging.basicConfig(level='INFO')


#@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationPa01Library:
    """Integration tests for the pa_01 library."""

    def test_execute_report_check_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': (
                    'PA01 Case Management Report (Interview Assign Date) - HIV'
                ),
                'library_name': 'pa_01',
                'data_source_name': '[RDB].[dbo].[STD_HIV_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART]',
            }
        )

        result = execute_report(report_spec)

        assert result is not None

        for row in result.content.data:
            logging.info(row)
