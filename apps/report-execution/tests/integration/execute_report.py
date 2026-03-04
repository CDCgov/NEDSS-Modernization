import mssql_python
import pytest

from src.execute_report import execute_report
from src.models import ReportSpec


@pytest.mark.usefixtures('setup_containers')
@pytest.mark.integration
class TestIntegrationExecuteReport:
    """Integration tests for generic report execution."""

    def test_execute_report_valid(self):
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Test Report',
                'library_name': 'hello_world',
                # Filter operator is used here as it is a stable, small table
                'data_source_name': '[NBS_ODSE].[dbo].[Filter_operator]',
                'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[Filter_operator]',
            }
        )
        result = execute_report(report_spec)
        assert result.content_type == 'table'
        assert result.description == 'Pass through query'
        assert result.content.columns == [
            'filter_operator_uid',
            'filter_operator_code',
            'filter_operator_desc',
            'status_cd',
            'status_time',
        ]

        assert len(result.content.data) == 11
        assert len(result.content.data[0]) == len(result.content.columns)

    def test_execute_report_invalid_query_syntax(self):
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Test Report',
                'library_name': 'hello_world',
                # Filter operator is used here as it is a stable, small table
                'data_source_name': '[NBS_ODSE].[dbo].[Filter_operator]',
                'subset_query': 'SELECT *; FROM [NBS_ODSE].[dbo].[Filter_operator]',
            }
        )

        # TODO: error handling for sql issues  # noqa: FIX002
        with pytest.raises(mssql_python.exceptions.ProgrammingError):
            execute_report(report_spec)
            assert True
