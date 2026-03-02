import pytest

from src.execute_report import execute_report

class TestExecuteReport:
    """Tests for the execute_report function"""

    def test_execute_report_hello_world(self):
        report_spec = {
            "version": 1,
            "is_export": True,
            "report_title": "Test Report",
            "library_name": "hello_world",
            "data_source_name": "random_db_table_0",
            "subset_query": "SELECT * FROM test",
        }
        result = execute_report(report_spec)
        assert result.content_type == 'table'
