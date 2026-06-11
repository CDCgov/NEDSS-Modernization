import pytest

from src.execute_report import execute_report
from src.models import ReportSpec

faker_schema = 'pa03.yaml'


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationPa03Library:
    """Integration tests for the pa_03 library."""

    def test_execute_report_check_data(self):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'PA03',
                'library_name': 'pa_03',
                'data_source_name': '[RDB].[dbo].[STD_HIV_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART]',
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data
        assert len(data) == 36
        assert len(data[0]) == 2
        assert len(data[0]) == len(result.content.columns)

        labels = [row[0] for row in data]
        values = [row[1] for row in data]

        assert labels == [
            'Total Number of Cases:',
            'No. Cases w/Internet Follow-up:',
            "Total No. Partners Init'd:",
            'Total No. Partners:',
            "Total No. Social Contacts Init'd:",
            'Total No. Social Contacts:',
            "Total No. Associates Init'd:",
            'Total No. Associates:',
            'Contact Index:',
            'IPS Contact Index:',
            'Cluster Index:',
            'IPS Cluster Index:',
            'Total No. IPS Partners:',
            'Total No. IPS Social Contacts:',
            'Total No. IPS Associates:',
            'Sexual Contact: I1',
            'Sexual Contact: I2',
            'Sexual Contact: I3',
            'Sexual Contact: I4',
            'Sexual Contact: I5',
            'Sexual Contact: I6',
            'Sexual Contact: I7',
            'Social Contact: I1',
            'Social Contact: I2',
            'Social Contact: I3',
            'Social Contact: I4',
            'Social Contact: I5',
            'Social Contact: I6',
            'Social Contact: I7',
            'Associate: I1',
            'Associate: I2',
            'Associate: I3',
            'Associate: I4',
            'Associate: I5',
            'Associate: I6',
            'Associate: I7',
        ]

        for value in values[:8]:
            assert isinstance(value, int)
            assert value >= 0

        for value in values[8:12]:
            assert value is None or isinstance(value, float)

        for value in values[12:]:
            assert isinstance(value, int)
            assert value >= 0

        row_map = {label: value for label, value in data}
        assert row_map['Total No. IPS Partners:'] <= row_map['Total No. Partners:']
        assert (
            row_map['Total No. IPS Social Contacts:']
            <= row_map['Total No. Social Contacts:']
        )
        assert row_map['Total No. IPS Associates:'] <= row_map['Total No. Associates:']

        sexual_outcome_total = sum(
            value for label, value in data if label.startswith('Sexual Contact:')
        )
        social_outcome_total = sum(
            value for label, value in data if label.startswith('Social Contact:')
        )
        associate_outcome_total = sum(
            value for label, value in data if label.startswith('Associate:')
        )

        assert sexual_outcome_total <= row_map['Total No. IPS Partners:']
        assert social_outcome_total <= row_map['Total No. IPS Social Contacts:']
        assert associate_outcome_total <= row_map['Total No. IPS Associates:']

    def test_execute_report_no_data(self):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'PA03',
                'library_name': 'pa_03',
                'data_source_name': '[RDB].[dbo].[STD_HIV_DATAMART]',
                'subset_query': (
                    'SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART] WHERE 1 = 2'
                ),
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data
        assert len(data) == 36
        assert len(result.content.columns) == 2

        row_map = {label: value for label, value in data}
        assert row_map['Total Number of Cases:'] == 0
        assert row_map['No. Cases w/Internet Follow-up:'] == 0
        assert row_map["Total No. Partners Init'd:"] == 0
        assert row_map['Total No. Partners:'] == 0
        assert row_map["Total No. Social Contacts Init'd:"] == 0
        assert row_map['Total No. Social Contacts:'] == 0
        assert row_map["Total No. Associates Init'd:"] == 0
        assert row_map['Total No. Associates:'] == 0
        assert row_map['Contact Index:'] is None
        assert row_map['IPS Contact Index:'] is None
        assert row_map['Cluster Index:'] is None
        assert row_map['IPS Cluster Index:'] is None
        assert row_map['Total No. IPS Partners:'] == 0
        assert row_map['Total No. IPS Social Contacts:'] == 0
        assert row_map['Total No. IPS Associates:'] == 0

        for label, value in data[15:]:
            assert label.startswith(
                ('Sexual Contact:', 'Social Contact:', 'Associate:')
            )
            assert value == 0

    def test_execute_report_check_metadata(self):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'PA03',
                'library_name': 'pa_03',
                'data_source_name': '[RDB].[dbo].[STD_HIV_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART]',
            }
        )

        result = execute_report(report_spec)
        assert result.header == 'PA03'
        assert result.subheader is None
        assert result.description is None
        assert result.content_type == 'table'

        assert result.content.columns[0] == 'LABEL'
        assert result.content.columns[1] == 'VALUE'
