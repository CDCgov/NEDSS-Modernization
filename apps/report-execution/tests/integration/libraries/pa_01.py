import pytest
import yaml

from src.execute_report import execute_report
from src.models import ReportSpec

faker_schema = 'pa_01.yaml'


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationPa01Library:
    """Integration tests for the pa_01 library."""

    def create_spec(self, **overrides):
        base = {
            'is_export': True,
            'is_builtin': True,
            'report_title': (
                'PA01 Case Management Report (Interview Assign Date) - HIV'
            ),
            'library_name': 'pa_01',
            'data_source_name': '[RDB].[dbo].[STD_HIV_DATAMART]',
            'subset_query': 'SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART]',
            'library_params': '{"report_variant": "HIV"}',
        }

        base.update(overrides)

        return ReportSpec.model_validate(base)

    def test_execute_report_bad_input(self):
        report_spec = self.create_spec(library_params=None)

        with pytest.raises(ValueError, match=r'.*is not a dict.*'):
            execute_report(report_spec)

        report_spec = self.create_spec(library_params='19')

        with pytest.raises(ValueError, match=r'.*is not a dict.*'):
            execute_report(report_spec)

        report_spec = self.create_spec(library_params='{"foo": 19}')

        with pytest.raises(ValueError, match=r'.*missing key.*'):
            execute_report(report_spec)

    def test_execute_report_check_data_hiv(self, snapshot):
        report_spec = self.create_spec()

        result = execute_report(report_spec)

        assert result is not None

        assert result.content.columns == [
            'Worker',
            'Category 1',
            'Category 2',
            'Category 3',
            'Count',
            'Percentage',
            'Index',
        ]

        data = result.content.data

        assert len(data) > 0

        for row in data:
            assert isinstance(row[0], str)
            assert isinstance(row[1], str)
            assert isinstance(row[2], str)
            assert row[3] is None or isinstance(row[3], str)
            assert row[4] is None or isinstance(row[4], int)
            assert row[5] is None or isinstance(row[5], str)
            assert row[6] is None or isinstance(row[6], str)

        snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

    def test_execute_report_no_data_hiv(self):
        report_spec = self.create_spec(
            subset_query='SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART] WHERE 1 = 2'
        )

        result = execute_report(report_spec)

        assert result is not None

        assert result.content.columns == [
            'Worker',
            'Category 1',
            'Category 2',
            'Category 3',
            'Count',
            'Percentage',
            'Index',
        ]

        assert len(result.content.data) == 0
