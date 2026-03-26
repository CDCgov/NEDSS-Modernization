import pytest

from src.execute_report import execute_report
from src.models import ReportSpec


@pytest.mark.usefixtures('setup_containers')
@pytest.mark.integration
class TestIntegrationNbsCustomLibrary:
    """Integration tests for the nbs_custom library."""

    def test_execute_report_with_time_range(self):
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'NBS Custom',
                'library_name': 'nbs_custom',
                # Filter operator is used here as it is a stable, small table
                'data_source_name': '[NBS_ODSE].[dbo].[Filter_operator]',
                'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[Filter_operator]',
                'time_range': {'start': '2024-01-01', 'end': '2024-12-31'},
            }
        )

        result = execute_report(report_spec)
        assert result.header == (
            'Custom Report For Table: [NBS_ODSE].[dbo].[Filter_operator]'
        )
        assert result.subheader == '2024-01-01 - 2024-12-31'
        assert result.description is None
        assert result.content_type == 'table'

        assert len(result.content.data) == 11
        assert len(result.content.data[0]) == len(result.content.columns)

    def test_execute_report_without_time_range(self):
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'NBS Custom',
                'library_name': 'nbs_custom',
                # Filter operator is used here as it is a stable, small table
                'data_source_name': '[NBS_ODSE].[dbo].[Filter_operator]',
                'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[Filter_operator]',
            }
        )

        result = execute_report(report_spec)
        assert result.header == (
            'Custom Report For Table: [NBS_ODSE].[dbo].[Filter_operator]'
        )
        assert result.subheader is None
        assert result.description is None
        assert result.content_type == 'table'

        assert len(result.content.data) == 11
        assert len(result.content.data[0]) == len(result.content.columns)
