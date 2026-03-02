import pytest

from src.execute_report import execute_report
from src.models import ReportSpec


class TestExecuteReport:
    """Tests for the execute_report function"""

    @pytest.mark.asyncio
    async def test_execute_report_hello_world(self):
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
        result = await execute_report(report_spec)
        assert result.content_type == "table"
        assert result.description == "Some hard coded data"

        assert result.content.shape == (4, 2)
