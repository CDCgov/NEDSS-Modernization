import json
import os
from http import client
from typing import cast

import mssql_python
import pytest
from pydantic import ValidationError

from src import utils
from src.config import clear_config_cache
from src.db_transaction import db_transaction
from src.errors import (
    IntConfigurationConversionError,
    InvalidConfigurationError,
    InvalidResultError,
    MissingDbObjectError,
    ResultTooBigError,
)
from src.execute_report import execute_report
from src.models import ReportSpec


@pytest.mark.usefixtures('setup_containers')
@pytest.mark.integration
class TestIntegrationExecuteReport:
    """Integration tests for generic report execution."""

    @pytest.fixture(autouse=True)
    def clear_cache_between_tests(self):
        """Automatically wipes the configuration cache before each test executes."""
        clear_config_cache()

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
                'sort_by': 'UPPER([filter_operator_code]) ASC',
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

        data = result.content.data
        assert len(data) == 11
        assert len(data[0]) == len(result.content.columns)
        for i in range(1, len(data)):
            assert data[i - 1][1] <= data[i][1]

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

    def test_execute_report_too_big_run(self):
        """Should catch execution dataset overflows using limits managed in the DB."""
        conn_string = utils.get_env_or_error('DATABASE_CONN_STRING')

        with db_transaction(conn_string, True) as trx:
            trx.execute(
                "UPDATE NBS_ODSE..NBS_configuration SET config_value = '10' WHERE "
                + "config_key = 'REPORT_MAX_ROW_LIMIT_RUN'"
            )

        try:
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
            with pytest.raises(ResultTooBigError) as exc_info:
                execute_report(report_spec)

            assert exc_info.value.message == (
                'Report request resulted in 11 rows. The limit for running reports'
                ' is 10 rows. Please refine your filter criteria.'
            )
        finally:
            with db_transaction(conn_string, True) as trx:
                trx.execute(
                    "UPDATE NBS_ODSE..NBS_configuration SET config_value = '10000' "
                    + "WHERE config_key = 'REPORT_MAX_ROW_LIMIT_RUN'"
                )

    def test_execute_report_too_big_export(self):
        """Should catch export dataset overflows using limits managed in the DB."""
        conn_string = utils.get_env_or_error('DATABASE_CONN_STRING')

        with db_transaction(conn_string, True) as trx:
            trx.execute(
                "UPDATE NBS_ODSE..NBS_configuration SET config_value = '10' WHERE "
                + "config_key = 'REPORT_MAX_ROW_LIMIT_EXPORT'"
            )

        try:
            report_spec = ReportSpec.model_validate(
                {
                    'is_export': True,
                    'is_builtin': True,
                    'report_title': 'Test Report',
                    'library_name': 'nbs_custom',
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
        finally:
            with db_transaction(conn_string, True) as trx:
                trx.execute(
                    "UPDATE NBS_ODSE..NBS_configuration SET config_value = '100000' "
                    + "WHERE config_key = 'REPORT_MAX_ROW_LIMIT_EXPORT'"
                )

    def test_execute_report_should_raise_invalid_config_error_when_limit_missing(self):
        """Should cleanly present an InvalidConfigurationError if a row
        configuration vanishes.
        """
        conn_string = utils.get_env_or_error('DATABASE_CONN_STRING')

        with db_transaction(conn_string, True) as trx:
            trx.execute(
                'DELETE FROM NBS_ODSE..NBS_configuration WHERE '
                + "config_key = 'REPORT_MAX_ROW_LIMIT_RUN'"
            )

        try:
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
            with pytest.raises(InvalidConfigurationError) as exc_info:
                execute_report(report_spec)

            assert (
                'No qualified mapping found in NBS_Configuration'
                in exc_info.value.message
            )
        finally:
            with db_transaction(conn_string, True) as trx:
                trx.execute("""
                    INSERT INTO NBS_ODSE..NBS_configuration (
                        config_key, config_value, default_value, version_ctrl_nbr,
                        add_user_id, add_time, last_chg_user_id, last_chg_time, 
                        status_cd, status_time
                    ) VALUES ('REPORT_MAX_ROW_LIMIT_RUN', '10000', '10000', 1, 1,
                              GETDATE(), 1, GETDATE(), 'A', GETDATE())
                    """)

    def test_execute_report_should_raise_conversion_error_on_corrupt_limit_type(self):
        """Should cleanly present an IntConfigurationConversionError if value
        payload becomes non-numeric.
        """
        conn_string = utils.get_env_or_error('DATABASE_CONN_STRING')

        with db_transaction(conn_string, True) as trx:
            trx.execute(
                "UPDATE NBS_ODSE..NBS_configuration SET config_value = 'NOT_A_NUMBER' "
                + "WHERE config_key = 'REPORT_MAX_ROW_LIMIT_RUN'"
            )

        try:
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
            with pytest.raises(IntConfigurationConversionError) as exc_info:
                execute_report(report_spec)

            assert (
                'Unable to convert NBS configuration value to number'
                in exc_info.value.message
            )
        finally:
            with db_transaction(conn_string, True) as trx:
                trx.execute(
                    "UPDATE NBS_ODSE..NBS_configuration SET config_value = '10000' "
                    + "WHERE config_key = 'REPORT_MAX_ROW_LIMIT_RUN'"
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

        connection = client.HTTPConnection(
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

    @pytest.mark.parametrize(
        'empty_string_prop',
        [
            'report_title',
            'library_name',
            'data_source_name',
            'subset_query',
        ],
    )
    def test_execute_report_empty_string_prop(self, empty_string_prop):
        invalid_spec = {
            'is_export': True,
            'is_builtin': True,
            'report_title': 'Test Report',
            'library_name': 'nbs_custom',
            'data_source_name': '[NBS_ODSE].[dbo].[Filter_code]',
            'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[Filter_code]',
        }
        invalid_spec.pop(empty_string_prop)
        invalid_spec[empty_string_prop] = ''

        connection = client.HTTPConnection(
            f'localhost:{os.getenv("UVICORN_PORT", "8001")}'
        )

        headers = {'Content-type': 'application/json'}
        body = json.dumps(invalid_spec)

        connection.request('POST', '/report/execute', body, headers)

        response = connection.getresponse()
        assert response.status == 422

        result = json.loads(response.read())

        assert result['detail'][0]['loc'] == ['body', empty_string_prop]
        assert result['detail'][0]['msg'] == 'String should have at least 1 character'

    def test_execute_report_missing_result(self, monkeypatch):
        def get_lib_returning_none(library_name: str, is_builtin: bool, **kwargs):
            def execute_method(self, trx, subset_query, data_source_name, **kwargs):
                return None

            mock_library = type(
                'MockLibrary',
                (),
                {'execute': execute_method},
            )
            return mock_library()

        with monkeypatch.context() as m:
            m.setattr('src.execute_report.get_library', get_lib_returning_none)
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
                'Invalid report result from library `nbs_custom`: No result returned'
            )

    def test_execute_report_result_missing_content_data(self, monkeypatch):
        def get_lib_without_data(library_name: str, is_builtin: bool):
            def execute_method(self, trx, subset_query, data_source_name, **kwargs):
                return {
                    'content_type': 'table',
                    'header': 'Custom Report: [NBS_ODSE].[dbo].[Filter_operator]',
                    'content': {
                        'columns': ['filter_operator_uid', 'filter_operator_code'],
                    },
                }

            mock_library = type(
                'MockLibrary',
                (),
                {'execute': execute_method},
            )
            return mock_library()

        with monkeypatch.context() as m:
            m.setattr('src.execute_report.get_library', get_lib_without_data)
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
                'Invalid report result from library `nbs_custom`'
            )

            root_error = cast(ValidationError, exc_info.value.__cause__)
            assert root_error is not None
            assert root_error.error_count() == 1

            assert root_error.errors()[0]['type'] == 'missing'
            assert root_error.errors()[0]['loc'] == ('content', 'data')
            assert root_error.errors()[0]['msg'] == 'Field required'

    def test_execute_report_result_missing_content_columns(self, monkeypatch):
        def get_lib_without_columns(library_name: str, is_builtin: bool):
            def execute_method(self, trx, subset_query, data_source_name, **kwargs):
                return {
                    'content_type': 'table',
                    'header': 'Custom Report: [NBS_ODSE].[dbo].[Filter_operator]',
                    'content': {
                        'data': [
                            (1, 'Code1'),
                            (2, 'Code2'),
                        ]
                    },
                }

            mock_library = type(
                'MockLibrary',
                (),
                {'execute': execute_method},
            )
            return mock_library()

        with monkeypatch.context() as m:
            m.setattr('src.execute_report.get_library', get_lib_without_columns)
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
                'Invalid report result from library `nbs_custom`'
            )

            root_error = cast(ValidationError, exc_info.value.__cause__)
            assert root_error is not None
            assert root_error.error_count() == 1

            assert root_error.errors()[0]['type'] == 'missing'
            assert root_error.errors()[0]['loc'] == ('content', 'columns')
            assert root_error.errors()[0]['msg'] == 'Field required'

    def test_execute_report_invalid_datasource(self):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Test Report',
                'library_name': 'nbs_custom',
                'data_source_name': '[NBS_ODSE].[dbo].[I_do_not_exist]',
                'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[I_do_not_exist]',
            }
        )

        with pytest.raises(MissingDbObjectError) as exc_info:
            execute_report(report_spec)
            assert exc_info.value.message == (
                'Datasource `[NBS_ODSE].[dbo].[I_do_not_exist]`'
                ' not found in the reporting database'
            )

    def test_execute_report_invalid_column(self):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Test Report',
                'library_name': 'nbs_custom',
                'data_source_name': '[NBS_ODSE].[dbo].[Filter_operator]',
                'subset_query': 'SELECT nope FROM [NBS_ODSE].[dbo].[Filter_operator]',
            }
        )

        # TODO: error handling for sql issues  # noqa: FIX002
        with pytest.raises(MissingDbObjectError) as exc_info:
            execute_report(report_spec)
            assert (
                exc_info.value.message
                == 'Column `nope` not found in the reporting database'
            )
