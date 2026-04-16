import http
import json
import os

import mssql_python
import pytest

from src.errors import ResultTooBigError
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

    @pytest.mark.parametrize(
        'missing_prop',
        [
            'is_export',
            'is_builtin',
            'report_title',
            'library_name',
            'data_source_name',
            'subset_query',
        ],
    )
    def test_execute_report_missing_required_prop(self, missing_prop):
        invalid_spec = {
            'is_export': True,
            'is_builtin': True,
            'report_title': 'Test Report',
            'library_name': 'nbs_custom',
            'data_source_name': '[NBS_ODSE].[dbo].[Filter_code]',
            'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[Filter_code]',
        }
        invalid_spec.pop(missing_prop)

        connection = http.client.HTTPConnection(
            f'localhost:{os.getenv("UVICORN_PORT", "8001")}'
        )

        headers = {'Content-type': 'application/json'}
        body = json.dumps(invalid_spec)

        connection.request('POST', '/report/execute', body, headers)

        response = connection.getresponse()
        assert response.status == 422

        result = json.loads(response.read())

        assert result['detail'][0]['loc'] == ['body', missing_prop]
        assert result['detail'][0]['msg'] == 'Field required'
