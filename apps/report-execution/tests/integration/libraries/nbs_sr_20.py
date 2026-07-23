import pytest
import yaml

from src.errors import InvalidLibraryParamsError
from src.execute_report import execute_report
from src.models import ReportSpec

faker_schema = 'tb_datamart.yaml'


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationNbsSr20Library:
    """Integration tests for the SR20 TB Record Count library."""

    def test_execute_report_check_data_with_count_date(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'library_name': 'nbs_sr_20',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[TB_DATAMART]',
                'library_params': '{"date_column": "COUNT_DATE"}',
            }
        )

        result = execute_report(report_spec)

        data = result.content.data

        snapshot.assert_match(yaml.dump(data), 'snapshot.yaml')

        assert len(data) > 0

        assert result.content.columns == [
            'monthYear',
            'sasdate',
            'counted_cases',
        ]

        for d in data:
            assert len(d) == 3
            assert isinstance(d[0], str)
            assert isinstance(d[1], int)
            assert isinstance(d[2], int)

    def test_execute_report_check_data_with_date_reported(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'library_name': 'nbs_sr_20',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[TB_DATAMART]',
                'library_params': '{"date_column": "DATE_REPORTED"}',
            }
        )

        result = execute_report(report_spec)

        data = result.content.data

        snapshot.assert_match(yaml.dump(data), 'snapshot.yaml')

        assert len(data) > 0

        assert result.content.columns == [
            'monthYear',
            'sasdate',
            'counted_cases',
        ]

        for d in data:
            assert len(d) == 3
            assert isinstance(d[0], str)
            assert isinstance(d[1], int)
            assert isinstance(d[2], int)

    def test_execute_report_no_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'library_name': 'nbs_sr_20',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[TB_DATAMART] WHERE 1 = 2',
                'library_params': '{"date_column": "COUNT_DATE"}',
            }
        )

        result = execute_report(report_spec)

        assert len(result.content.data) == 0
        assert result.content.columns == [
            'monthYear',
            'sasdate',
            'counted_cases',
        ]

    def test_execute_report_invalid_params_raises_error(self):
        """Verifies malformed library_params throws a InvalidLibraryParamsError."""
        # Scenario A: Missing 'date_column' key entirely
        report_spec_missing = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'library_name': 'nbs_sr_20',
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
                'library_name': 'nbs_sr_20',
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

    def test_execute_report_handles_null_and_diverse_statuses(self):
        """Verifies that null status and alternate text fall into non_counted_cases."""
        status_query = """
            SELECT 
                CAST('2025-06-01' AS DATE) AS COUNT_DATE, 
                'Count as a TB Case' AS count_status
            UNION ALL
            SELECT CAST('2025-06-02' AS DATE) AS COUNT_DATE, NULL AS count_status
            UNION ALL
            SELECT 
                CAST('2025-06-03' AS DATE) AS COUNT_DATE, 
                'Suspected Case' AS count_status
        """
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'library_name': 'nbs_sr_20',
                'subset_query': status_query,
                'library_params': '{"date_column": "COUNT_DATE"}',
            }
        )

        result = execute_report(report_spec)
        row = result.content.data[0]

        assert row[2] == 3
