import pytest

from src.execute_report import execute_report
from src.models import ReportSpec

faker_schema = 'qa_06.yaml'


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationQa06Library:
    """Integration tests for the qa_06 library."""

    def test_execute_report_check_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'QA 6',
                'library_name': 'qa_06',
                'data_source_name': '[RDB].[dbo].[STD_HIV_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART]'
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'
        data = result.content.data
        breakpoint()
