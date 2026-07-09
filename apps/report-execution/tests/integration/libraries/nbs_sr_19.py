import pytest
import yaml

from src.errors import InvalidLibraryParamsError
from src.execute_report import execute_report
from src.models import ReportSpec

faker_schema = 'tb_datamart.yaml'


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationNbsSr19Library:
    """Integration tests for the SR19 TB Record Count library."""

    def test_execute_report_check_data_with_count_date(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'SR 19',
                'library_name': 'nbs_sr_19',
                'data_source_name': '[RDB].[dbo].[TB_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[TB_DATAMART]',
                'library_params': '{"count_column": "COUNT_DATE"}',
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data

        snapshot.assert_match(yaml.dump(data), 'snapshot.yaml')

        assert len(data) > 0

        assert result.content.columns == [
            'monthYear',
            'sasdate',
            'counted_cases',
            'non_counted_cases',
            'total_cases',
        ]

        for d in data:
            assert len(d) == 5
            assert isinstance(d[0], str)
            assert isinstance(d[1], int)
            assert isinstance(d[2], int)
            assert isinstance(d[3], int)
            assert isinstance(d[4], int)

    def test_execute_report_check_data_with_date_reported(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'SR 19',
                'library_name': 'nbs_sr_19',
                'data_source_name': '[RDB].[dbo].[TB_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[TB_DATAMART]',
                'library_params': '{"count_column": "DATE_REPORTED"}',
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data

        snapshot.assert_match(yaml.dump(data), 'snapshot.yaml')

        assert len(data) > 0

        assert result.content.columns == [
            'monthYear',
            'sasdate',
            'counted_cases',
            'non_counted_cases',
            'total_cases',
        ]

        for d in data:
            assert len(d) == 5
            assert isinstance(d[0], str)
            assert isinstance(d[1], int)
            assert isinstance(d[2], int)
            assert isinstance(d[3], int)
            assert isinstance(d[4], int)

    def test_execute_report_no_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'SR 19',
                'library_name': 'nbs_sr_19',
                'data_source_name': '[RDB].[dbo].[TB_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[TB_DATAMART] WHERE 1 = 2',
                'library_params': '{"count_column": "COUNT_DATE"}',
            }
        )

        result = execute_report(report_spec)

        assert len(result.content.data) == 0
        assert result.content.columns == [
            'monthYear',
            'sasdate',
            'counted_cases',
            'non_counted_cases',
            'total_cases',
        ]

    def test_execute_report_invalid_params_raises_error(self):
        """Verifies malformed library_params throws a InvalidLibraryParamsError."""
        # Scenario A: Missing 'count_column' key entirely
        report_spec_missing = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'SR 19',
                'library_name': 'nbs_sr_19',
                'data_source_name': '[RDB].[dbo].[TB_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[TB_DATAMART]',
                'library_params': '{}',
            }
        )

        with pytest.raises(
            InvalidLibraryParamsError,
            match="Invalid library parameters: "
                  "'count_column' is required but was absent",
        ):
            execute_report(report_spec_missing)

        # Scenario B: library_params isn't an object/dict structure
        report_spec_bad_type = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'SR 19',
                'library_name': 'nbs_sr_19',
                'data_source_name': '[RDB].[dbo].[TB_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[TB_DATAMART]',
                'library_params': '"not_a_dict"',
            }
        )

        with pytest.raises(
            InvalidLibraryParamsError,
            match="Invalid library parameters: "
                  "'count_column' is required but was absent",
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
                'report_title': 'SR 19',
                'library_name': 'nbs_sr_19',
                'data_source_name': '[RDB].[dbo].[TB_DATAMART]',
                'subset_query': status_query,
                'library_params': '{"count_column": "COUNT_DATE"}',
            }
        )

        result = execute_report(report_spec)
        row = result.content.data[0]

        assert row[2] == 1  # counted_cases ('Count as a TB Case')
        assert row[3] == 2  # non_counted_cases (NULL + 'Suspected Case')
        assert row[4] == 3
