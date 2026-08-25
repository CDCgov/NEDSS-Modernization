import pytest
import yaml

from src.execute_report import execute_report
from src.models import ReportSpec

faker_schema = 'std_hiv_datamart.yaml'


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationPa05Library:
    """Integration tests for the pa_05 library."""

    def test_execute_report_check_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'library_name': 'pa_05',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART]',
            }
        )

        result = execute_report(report_spec)

        data = result.content.data
        assert len(result.content.columns) == 7
        assert len(data) >= 23
        assert len(data) % 23 == 0
        assert len(data[0]) == len(result.content.columns)

        snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

        overall_rows = [row for row in data if row[0] == 'ALL']
        worker_rows = [row for row in data if row[0] != 'ALL']

        # Every worker-row block (whether or not two investigator keys share a
        # PROVIDER_QUICK_CODE -- see the `Worker` note in pa_05.py's execute()
        # docstring) still contains all 23 metrics, so the totals are exact
        # multiples of 23.
        assert len(overall_rows) == 23
        assert len(worker_rows) % 23 == 0

        row_map = {(row[0], row[1], row[2], row[3]): row for row in data}
        assert row_map[('ALL', 'Num. Cases Assigned', None, None)][4] >= 0
        assert row_map[('ALL', 'Num. Cases Assigned', "Num. Cases IX'D", None)][4] >= 0
        assert row_map[('ALL', "Num. of OI'S", None, None)][4] >= 0
        assert row_map[('ALL', "Num. of RI'S", None, None)][4] >= 0

        for row in data:
            count, percentage, index = row[4], row[5], row[6]
            assert isinstance(count, int)
            assert count >= 0
            assert percentage is None or (
                isinstance(percentage, str) and percentage.endswith('%')
            )
            assert index is None or isinstance(index, float)
            # A metric is percent-type, ratio-type, or neither -- never both.
            assert percentage is None or index is None

    def test_execute_report_no_data(self):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'library_name': 'pa_05',
                'subset_query': (
                    'SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART] WHERE 1 = 2'
                ),
            }
        )

        result = execute_report(report_spec)

        data = result.content.data
        assert len(data) == 23
        assert len(result.content.columns) == 7
        assert all(row[0] == 'ALL' for row in data)
        assert all(row[4] == 0 for row in data)
        assert all(row[5] is None and row[6] is None for row in data)

        row_map = {(row[0], row[1], row[2], row[3]): row for row in data}
        assert row_map[('ALL', 'Num. Cases Assigned', None, None)] == (
            'ALL',
            'Num. Cases Assigned',
            None,
            None,
            0,
            None,
            None,
        )
        assert row_map[('ALL', "Num. of OI'S", None, None)][4] == 0
        assert row_map[('ALL', "Num. of RI'S", None, None)][4] == 0

    def test_execute_report_check_metadata(self):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'library_name': 'pa_05',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART]',
            }
        )

        result = execute_report(report_spec)
        assert result.context_header is None
        assert result.description is None
        assert result.content.columns == [
            'Worker',
            'Category 1',
            'Category 2',
            'Category 3',
            'Count',
            'Percentage',
            'Index',
        ]
