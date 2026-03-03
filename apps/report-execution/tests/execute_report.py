import pytest

from src.errors import MissingLibraryError
from src.execute_report import execute_report
from src.models import ReportSpec


class TestExecuteReport:
    """Tests for the execute_report function."""

    @pytest.mark.asyncio
    async def test_execute_report_hello_world(self):
        """Check valid report runs."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Test Report',
                'library_name': 'hello_world',
                'data_source_name': 'random_db_table_0',
                'subset_query': 'SELECT * FROM test',
            }
        )
        result = await execute_report(report_spec)
        assert result.content_type == 'table'
        assert result.description == 'Some hard coded data'

        assert result.content.shape == (4, 2)

    @pytest.mark.asyncio
    async def test_execute_report_missing_library(self):
        """Check missing library throws error."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Test Report',
                'library_name': 'missing_library',
                'data_source_name': 'random_db_table_0',
                'subset_query': 'SELECT * FROM test',
            }
        )
        with pytest.raises(MissingLibraryError) as excinfo:
            await execute_report(report_spec)

        assert (
            excinfo.value.message
            == 'Library `missing_library` (is_builtin: True) not found'
        )
