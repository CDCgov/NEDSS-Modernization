from datetime import date, timedelta

import pytest

from src.errors import InvalidReportSpecError, MissingLibraryError
from src.execute_report import execute_report
from src.models import ReportSpec


class TestExecuteReport:
    """Tests for the execute_report function."""

    def test_execute_report_nbs_custom(self, mock_db_transaction):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Test Report',
                'library_name': 'nbs_custom',
                'data_source_name': 'random_db_table_0',
                'subset_query': 'SELECT * FROM test',
            }
        )
        result = execute_report(report_spec)
        assert result.content_type == 'table'
        assert result.header == 'Custom Report For Table: random_db_table_0'
        assert result.subheader is None
        assert result.description is None
        assert result.content.columns == ['id', 'name']

        assert len(result.content.data) == 4
        assert len(result.content.data[0]) == 2

    def test_execute_report_missing_library(self):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Test Report',
                'library_name': 'missing_library',
                'data_source_name': 'random_db_table_0',
                'subset_query': 'SELECT * FROM test',
            }
        )
        with pytest.raises(MissingLibraryError) as exc_info:
            execute_report(report_spec)

        assert (
            exc_info.value.message
            == 'Library `missing_library` (is_builtin: True) not found'
        )

    def test_execute_report_start_after_end(self):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Test Report',
                'library_name': 'nbs_custom',
                'data_source_name': 'random_db_table_0',
                'subset_query': 'SELECT * FROM test',
                'time_range': {'start': '2024-06-30', 'end': '2024-06-01'},
            }
        )
        with pytest.raises(InvalidReportSpecError) as exc_info:
            execute_report(report_spec)

        assert exc_info.value.http_code == 422
        assert exc_info.value.message == (
            'Invalid report specification: Start date must be before '
            'end date in time range.'
        )

    def test_execute_report_start_in_future(self):
        future_start = date.today() + timedelta(days=7)
        end = future_start + timedelta(days=7)

        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Test Report',
                'library_name': 'nbs_custom',
                'data_source_name': 'random_db_table_0',
                'subset_query': 'SELECT * FROM test',
                'time_range': {
                    'start': future_start.isoformat(),
                    'end': end.isoformat(),
                },
            }
        )
        with pytest.raises(InvalidReportSpecError) as exc_info:
            execute_report(report_spec)

        assert exc_info.value.http_code == 422
        assert exc_info.value.message == (
            'Invalid report specification: Start date cannot take place in the future.'
        )

    def test_execute_report_invalid_start_date_format(self):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Test Report',
                'library_name': 'nbs_custom',
                'data_source_name': 'random_db_table_0',
                'subset_query': 'SELECT * FROM test',
                'time_range': {'start': 'June 30, 2024', 'end': '2024-07-07'},
            }
        )
        with pytest.raises(InvalidReportSpecError) as exc_info:
            execute_report(report_spec)

        assert exc_info.value.http_code == 422
        assert exc_info.value.message == (
            'Invalid report specification: Time range "start" and "end" '
            'must be valid ISO format dates.'
        )

    def test_execute_report_invalid_end_date_format(self):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Test Report',
                'library_name': 'nbs_custom',
                'data_source_name': 'random_db_table_0',
                'subset_query': 'SELECT * FROM test',
                'time_range': {'start': '2024-06-30', 'end': 'July 7, 2024'},
            }
        )
        with pytest.raises(InvalidReportSpecError) as exc_info:
            execute_report(report_spec)

        assert exc_info.value.http_code == 422
        assert exc_info.value.message == (
            'Invalid report specification: Time range "start" and "end" '
            'must be valid ISO format dates.'
        )
