import pytest

from src.errors import MissingLibraryError
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

    def test_execute_report_with_library_params(self, mock_db_transaction):
        """Test that library_params can be passed to the execute function."""
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Test Report with Params',
                'library_name': 'nbs_custom',
                'data_source_name': 'random_db_table_0',
                'subset_query': 'SELECT * FROM test',
                'library_params': '{"report_days": 30}',
            }
        )
        result = execute_report(report_spec)
        assert result.content.columns == ['id', 'name']
        assert len(result.content.data) == 4

    def test_execute_report_invalid_library_params_type(self):
        """Test that a non-JSON value for library_params raises validation error."""
        with pytest.raises(ValueError) as exc_info:
            ReportSpec.model_validate(
                {
                    'is_export': True,
                    'is_builtin': True,
                    'report_title': 'Invalid Params',
                    'library_name': 'nbs_custom',
                    'data_source_name': 'random_db_table_0',
                    'subset_query': 'SELECT * FROM test',
                    'library_params': 'not a json object',
                }
            )
        assert 'Invalid JSON' in str(exc_info.value)
