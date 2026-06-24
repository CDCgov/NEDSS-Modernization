import pytest
import yaml

from src.execute_report import execute_report
from src.models import ReportSpec

faker_schema = 'pa05.yaml'


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationPa05Library:
    """Integration tests for the pa_05 library."""

    def test_execute_report_check_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'PA05',
                'library_name': 'pa_05',
                'data_source_name': '[RDB].[dbo].[STD_HIV_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART]',
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data
        assert len(result.content.columns) == 8
        assert len(data) >= 23
        assert len(data) % 23 == 0
        assert len(data[0]) == len(result.content.columns)

        snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

        overall_rows = [row for row in data if row[0] == 'Overall']
        worker_rows = [row for row in data if row[0] == 'Worker']

        assert len(overall_rows) == 23
        assert len(worker_rows) % 23 == 0

        worker_keys = {(row[1], row[2]) for row in worker_rows}
        for worker_key in worker_keys:
            rows_for_worker = [
                row for row in worker_rows if (row[1], row[2]) == worker_key
            ]
            assert len(rows_for_worker) == 23

        row_map = {(row[0], row[1], row[2], row[4]): row for row in data}
        assert row_map[('Overall', None, None, 'NUM. CASES ASSIGNED')][5] >= 0
        assert row_map[('Overall', None, None, "NUM. CASES IX'D")][5] >= 0
        assert row_map[('Overall', None, None, "NUM. OF OI'S")][5] >= 0
        assert row_map[('Overall', None, None, "NUM. OF RI'S")][5] >= 0

        for row in data:
            assert isinstance(row[5], int)
            assert row[5] >= 0
            assert row[7] in (None, 'percent', 'ratio')
            if row[7] is None:
                assert row[6] is None
            else:
                assert row[6] is None or isinstance(row[6], float)

    def test_execute_report_no_data(self):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'PA05',
                'library_name': 'pa_05',
                'data_source_name': '[RDB].[dbo].[STD_HIV_DATAMART]',
                'subset_query': (
                    'SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART] WHERE 1 = 2'
                ),
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data
        assert len(data) == 23
        assert len(result.content.columns) == 8
        assert all(row[0] == 'Overall' for row in data)
        assert all(row[5] == 0 for row in data)

        row_map = {(row[0], row[4]): row for row in data}
        assert row_map[('Overall', 'NUM. CASES ASSIGNED')] == (
            'Overall',
            None,
            None,
            'Case Activity',
            'NUM. CASES ASSIGNED',
            0,
            None,
            None,
        )
        assert row_map[('Overall', "NUM. OF OI'S")][5] == 0
        assert row_map[('Overall', "NUM. OF RI'S")][5] == 0

    def test_execute_report_check_metadata(self):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'PA05',
                'library_name': 'pa_05',
                'data_source_name': '[RDB].[dbo].[STD_HIV_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART]',
            }
        )

        result = execute_report(report_spec)
        assert result.header == 'PA05'
        assert result.subheader is None
        assert result.description is None
        assert result.content_type == 'table'
        assert result.content.columns == [
            'Scope',
            'Provider Quick Code',
            'Investigator Interview Key',
            'Metric Group',
            'Metric',
            'Count',
            'Rate',
            'Rate Type',
        ]
