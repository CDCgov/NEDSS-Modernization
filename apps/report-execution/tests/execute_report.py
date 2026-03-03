import pytest

from src.errors import MissingLibraryError
from src.execute_report import execute_report
from src.models import ReportSpec


class TestExecuteReport:
    """Tests for the execute_report function"""

    def test_execute_report_hello_world(self, mock_db_transaction):
        report_spec = ReportSpec.model_validate(
            {
                "version": 1,
                "is_export": True,
                "is_builtin": True,
                "report_title": "Test Report",
                "library_name": "hello_world",
                "data_source_name": "random_db_table_0",
                "subset_query": "SELECT * FROM test",
            }
        )
        result = execute_report(report_spec)
        assert result.content_type == "table"
        assert result.description == "Pass through query"
        assert result.content.columns == ["id", "name"]

        assert len(result.content.data) == 4
        assert len(result.content.data[0]) == 2

    def test_execute_report_missing_library(self):
        report_spec = ReportSpec.model_validate(
            {
                "version": 1,
                "is_export": True,
                "is_builtin": True,
                "report_title": "Test Report",
                "library_name": "missing_library",
                "data_source_name": "random_db_table_0",
                "subset_query": "SELECT * FROM test",
            }
        )
        with pytest.raises(MissingLibraryError) as exc_info:
            execute_report(report_spec)

        assert (
            exc_info.value.message
            == "Library `missing_library` (is_builtin: True) not found"
        )
