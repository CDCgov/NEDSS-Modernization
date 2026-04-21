import mssql_python
import pytest
from pydantic import ValidationError

from src.errors import InvalidResultError, ResultTooBigError
from src.execute_report import execute_report
from src.models import ReportSpec


@pytest.mark.usefixtures('setup_containers')
@pytest.mark.integration
class TestIntegrationExecuteReport:
    """Integration tests for generic report execution."""

    def test_execute_report_valid(self):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Test Report',
                'library_name': 'nbs_custom',
                # Filter operator is used here as it is a stable, small table
                'data_source_name': '[NBS_ODSE].[dbo].[Filter_operator]',
                'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[Filter_operator]',
            }
        )
        result = execute_report(report_spec)
        assert result.content_type == 'table'
        assert (
            result.header
            == 'Custom Report For Table: [NBS_ODSE].[dbo].[Filter_operator]'
        )
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
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Test Report',
                'library_name': 'nbs_custom',
                # Filter operator is used here as it is a stable, small table
                'data_source_name': '[NBS_ODSE].[dbo].[Filter_operator]',
                'subset_query': 'SELECT *; FROM [NBS_ODSE].[dbo].[Filter_operator]',
            }
        )

        # TODO: error handling for sql issues  # noqa: FIX002
        with pytest.raises(mssql_python.exceptions.ProgrammingError):
            execute_report(report_spec)
            assert True

    def test_execute_report_too_big_run(self, monkeypatch):
        with monkeypatch.context() as m:
            m.setenv('REPORT_MAX_ROW_LIMIT_RUN', '10')
            report_spec = ReportSpec.model_validate(
                {
                    'is_export': False,
                    'is_builtin': True,
                    'report_title': 'Test Report',
                    'library_name': 'nbs_custom',
                    # Filter operator is used here as it is a stable, small table
                    'data_source_name': '[NBS_ODSE].[dbo].[Filter_operator]',
                    'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[Filter_operator]',
                }
            )
            with pytest.raises(ResultTooBigError) as exc_info:
                execute_report(report_spec)

            assert exc_info.value.message == (
                'Report request resulted in 11 rows. The limit for running reports'
                ' is 10 rows. Please refine your filter criteria.'
            )

    def test_execute_report_too_big_export(self, monkeypatch):
        with monkeypatch.context() as m:
            m.setenv('REPORT_MAX_ROW_LIMIT_EXPORT', '10')
            report_spec = ReportSpec.model_validate(
                {
                    'is_export': True,
                    'is_builtin': True,
                    'report_title': 'Test Report',
                    'library_name': 'nbs_custom',
                    # Filter operator is used here as it is a stable, small table
                    'data_source_name': '[NBS_ODSE].[dbo].[Filter_operator]',
                    'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[Filter_operator]',
                }
            )
            with pytest.raises(ResultTooBigError) as exc_info:
                execute_report(report_spec)

            assert exc_info.value.message == (
                'Report request resulted in 11 rows. The limit for exporting reports'
                ' is 10 rows. Please refine your filter criteria.'
            )

    def test_execute_report_result_missing_content(self, monkeypatch):
        def get_mock_library(library_name: str, is_builtin: bool):
            return type(
                'MockLibrary',
                (),
                {
                    'execute': lambda self, trx, subset_query, data_source_name, time_range: {
                        'content_type': 'table',
                        'header': 'Custom Report For Table: [NBS_ODSE].[dbo].[Filter_operator]',
                        'content': {},
                    }
                },
            )()

        with monkeypatch.context() as m:
            m.setattr('src.execute_report.get_library', get_mock_library)
            report_spec = ReportSpec.model_validate(
                {
                    'is_export': False,
                    'is_builtin': True,
                    'report_title': 'Test Report',
                    'library_name': 'nbs_custom',
                    'data_source_name': '[NBS_ODSE].[dbo].[Filter_operator]',
                    'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[Filter_operator]',
                }
            )
            with pytest.raises(InvalidResultError) as exc_info:
                execute_report(report_spec)

            assert exc_info.value.message == (
                 "Invalid report result from library `nbs_custom`"
            )

            root_error: ValidationError = exc_info.value.__cause__
            assert root_error is not None
            assert root_error.error_count() == 2

            columns_error = root_error.errors()[0]
            data_error = root_error.errors()[1]

            assert columns_error['type'] == 'missing'
            assert columns_error['loc'] == ('content', 'columns')
            assert columns_error['msg'] == 'Field required'

            assert data_error['type'] == 'missing'
            assert data_error['loc'] == ('content', 'data')
            assert data_error['msg'] == 'Field required'
