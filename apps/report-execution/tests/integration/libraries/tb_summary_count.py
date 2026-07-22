import pytest
import yaml

from src.errors import InvalidLibraryParamsError
from src.execute_report import execute_report
from src.models import ReportSpec

faker_schema = 'dm_inv_tb.yaml'


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationTBSummaryCountLibrary:
    """Integration tests for the TB Summary Count library."""

    def test_execute_report_check_data_with_inv_rpt_date(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'TB Summary Count',
                'library_name': 'tb_summary_count',
                'data_source_name': '[RDB].[dbo].[DM_INV_TB]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[DM_INV_TB]',
                'library_params': '{"date_column": "INV_RPT_DT"}',
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data

        snapshot.assert_match(yaml.dump(data), 'snapshot.yaml')

        assert len(data) > 0

        assert result.content.columns == [
            'monthYearTxt',
            'monthYear',
            'sasdate',
            'counted_cases',
            'non_counted_cases',
            'total_cases',
        ]

        for d in data:
            assert len(d) == 6
            assert isinstance(d[0], str)
            assert isinstance(d[1], str)
            assert isinstance(d[2], int)
            assert isinstance(d[3], int)
            assert isinstance(d[4], int)
            assert isinstance(d[5], int)

    def test_execute_report_check_data_with_case_count_date(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'TB Summary Count',
                'library_name': 'tb_summary_count',
                'data_source_name': '[RDB].[dbo].[DM_INV_TB]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[DM_INV_TB]',
                'library_params': '{"date_column": "CASE_COUNT_DT"}',
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data

        snapshot.assert_match(yaml.dump(data), 'snapshot.yaml')

        assert len(data) > 0

        assert result.content.columns == [
            'monthYearTxt',
            'monthYear',
            'sasdate',
            'counted_cases',
            'non_counted_cases',
            'total_cases',
        ]

        for d in data:
            assert len(d) == 6
            assert isinstance(d[0], str)
            assert isinstance(d[1], str)
            assert isinstance(d[2], int)
            assert isinstance(d[3], int)
            assert isinstance(d[4], int)
            assert isinstance(d[5], int)

    def test_execute_report_no_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'TB Summary Count',
                'library_name': 'tb_summary_count',
                'data_source_name': '[RDB].[dbo].[DM_INV_TB]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[DM_INV_TB] WHERE 1 = 2',
                'library_params': '{"date_column": "INV_RPT_DT"}',
            }
        )

        result = execute_report(report_spec)

        assert len(result.content.data) == 0
        assert result.content.columns == [
            'monthYearTxt',
            'monthYear',
            'sasdate',
            'counted_cases',
            'non_counted_cases',
            'total_cases',
        ]

    def test_execute_report_invalid_params_raises_error(self):
        """Verifies malformed library_params throws a InvalidLibraryParamsError."""
        # Scenario A: Missing 'date_column' key entirely
        report_spec_missing = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'TB Summary Count',
                'library_name': 'tb_summary_count',
                'data_source_name': '[RDB].[dbo].[DM_INV_TB]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[TB_DATAMART]',
                'library_params': '{}',
            }
        )

        with pytest.raises(
            InvalidLibraryParamsError,
            match='Invalid library parameters: '
            "'date_column' is required but was absent",
        ):
            execute_report(report_spec_missing)

        # Scenario B: library_params isn't an object/dict structure
        report_spec_bad_type = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'TB Summary Count',
                'library_name': 'tb_summary_count',
                'data_source_name': '[RDB].[dbo].[DM_INV_TB]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[TB_DATAMART]',
                'library_params': '"not_a_dict"',
            }
        )

        with pytest.raises(
            InvalidLibraryParamsError,
            match='Invalid library parameters: '
            "'date_column' is required but was absent",
        ):
            execute_report(report_spec_bad_type)
