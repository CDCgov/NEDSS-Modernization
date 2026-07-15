import pytest
import yaml

from src.execute_report import execute_report
from src.models import ReportSpec

faker_schema = 'dm_inv_tb.yaml'


#@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationTbCaseVerLibrary:
    """Integration tests for the tb_case_ver library."""

    def test_execute_report_check_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'TB Case Verification',
                'library_name': 'tb_case_ver',
                'data_source_name': '[RDB].[dbo].[DM_INV_TB]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[DM_INV_TB]',
            }
        )

        result = execute_report(report_spec)
