import pytest
import yaml

from src.execute_report import execute_report
from src.models import ReportSpec

faker_schema = 'pa_03.yaml'


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationPa03Library:
    """Integration tests for the pa_03 library."""

    def test_execute_report_check_data(self, snapshot):
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

        data = result.content.data
        assert len(data) == 36
        assert len(data[0]) == 5
        assert len(data[0]) == len(result.content.columns)

        snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

        assert data[:15] == [
            ('Total Number of Cases', None, None, data[0][3], None),
            (
                'Total Number of Cases',
                "Total No. Partners Init'd",
                None,
                data[1][3],
                None,
            ),
            (
                'Total Number of Cases',
                "Total No. Social Contacts Init'd",
                None,
                data[2][3],
                None,
            ),
            (
                'Total Number of Cases',
                "Total No. Associates Init'd",
                None,
                data[3][3],
                None,
            ),
            ('Total Number of Cases', 'Contact Index', None, None, data[4][4]),
            ('Total Number of Cases', 'Cluster Index', None, None, data[5][4]),
            ('No. Cases w/Internet Follow-up', None, None, data[6][3], None),
            (
                'No. Cases w/Internet Follow-up',
                'Total No. Partners',
                None,
                data[7][3],
                None,
            ),
            (
                'No. Cases w/Internet Follow-up',
                'Total No. Social Contacts',
                None,
                data[8][3],
                None,
            ),
            (
                'No. Cases w/Internet Follow-up',
                'Total No. Associates',
                None,
                data[9][3],
                None,
            ),
            (
                'No. Cases w/Internet Follow-up',
                'IPS Contact Index',
                None,
                None,
                data[10][4],
            ),
            (
                'No. Cases w/Internet Follow-up',
                'IPS Cluster Index',
                None,
                None,
                data[11][4],
            ),
            ('Total No. IPS Partners', None, None, data[12][3], None),
            ('Total No. IPS Social', None, None, data[13][3], None),
            ('Total No. IPS Associates', None, None, data[14][3], None),
        ]

        assert data[15:] == [
            ('Outcomes', 'Sexual Contact', 'I1', data[15][3], None),
            ('Outcomes', 'Sexual Contact', 'I2', data[16][3], None),
            ('Outcomes', 'Sexual Contact', 'I3', data[17][3], None),
            ('Outcomes', 'Sexual Contact', 'I4', data[18][3], None),
            ('Outcomes', 'Sexual Contact', 'I5', data[19][3], None),
            ('Outcomes', 'Sexual Contact', 'I6', data[20][3], None),
            ('Outcomes', 'Sexual Contact', 'I7', data[21][3], None),
            ('Outcomes', 'Social Contact', 'I1', data[22][3], None),
            ('Outcomes', 'Social Contact', 'I2', data[23][3], None),
            ('Outcomes', 'Social Contact', 'I3', data[24][3], None),
            ('Outcomes', 'Social Contact', 'I4', data[25][3], None),
            ('Outcomes', 'Social Contact', 'I5', data[26][3], None),
            ('Outcomes', 'Social Contact', 'I6', data[27][3], None),
            ('Outcomes', 'Social Contact', 'I7', data[28][3], None),
            ('Outcomes', 'Associate', 'I1', data[29][3], None),
            ('Outcomes', 'Associate', 'I2', data[30][3], None),
            ('Outcomes', 'Associate', 'I3', data[31][3], None),
            ('Outcomes', 'Associate', 'I4', data[32][3], None),
            ('Outcomes', 'Associate', 'I5', data[33][3], None),
            ('Outcomes', 'Associate', 'I6', data[34][3], None),
            ('Outcomes', 'Associate', 'I7', data[35][3], None),
        ]

        for row in data[:4]:
            assert isinstance(row[3], int)
            assert row[3] >= 0
            assert row[4] is None

        for row in data[4:6]:
            assert row[3] is None
            assert row[4] is None or isinstance(row[4], float)

        for row in data[6:10]:
            assert isinstance(row[3], int)
            assert row[3] >= 0
            assert row[4] is None

        for row in data[10:12]:
            assert row[3] is None
            assert row[4] is None or isinstance(row[4], float)

        for row in data[12:]:
            assert isinstance(row[3], int)
            assert row[3] >= 0
            assert row[4] is None

        row_map = {(row[0], row[1], row[2]): row for row in data}

        total_partners = row_map[
            ('No. Cases w/Internet Follow-up', 'Total No. Partners', None)
        ][3]
        total_social = row_map[
            ('No. Cases w/Internet Follow-up', 'Total No. Social Contacts', None)
        ][3]
        total_associates = row_map[
            ('No. Cases w/Internet Follow-up', 'Total No. Associates', None)
        ][3]
        total_ips_partners = row_map[('Total No. IPS Partners', None, None)][3]
        total_ips_social = row_map[('Total No. IPS Social', None, None)][3]
        total_ips_associates = row_map[('Total No. IPS Associates', None, None)][3]

        assert total_ips_partners <= total_partners
        assert total_ips_social <= total_social
        assert total_ips_associates <= total_associates

        sexual_outcome_total = sum(
            row[3]
            for row in data
            if row[0] == 'Outcomes' and row[1] == 'Sexual Contact'
        )
        social_outcome_total = sum(
            row[3]
            for row in data
            if row[0] == 'Outcomes' and row[1] == 'Social Contact'
        )
        associate_outcome_total = sum(
            row[3] for row in data if row[0] == 'Outcomes' and row[1] == 'Associate'
        )

        assert sexual_outcome_total <= total_ips_partners
        assert social_outcome_total <= total_ips_social
        assert associate_outcome_total <= total_ips_associates

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

        data = result.content.data
        assert len(data) == 36
        assert len(result.content.columns) == 5

        assert data[0] == ('Total Number of Cases', None, None, 0, None)
        assert data[6] == ('No. Cases w/Internet Follow-up', None, None, 0, None)
        assert data[12] == ('Total No. IPS Partners', None, None, 0, None)
        assert data[13] == ('Total No. IPS Social', None, None, 0, None)
        assert data[14] == ('Total No. IPS Associates', None, None, 0, None)

        for row in data[15:]:
            assert row[0] == 'Outcomes'
            assert row[3] == 0
            assert row[4] is None

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
        assert result.context_header is None
        assert result.description is None

        assert result.content.columns[0] == 'Category 1'
        assert result.content.columns[1] == 'Category 2'
        assert result.content.columns[2] == 'Category 3'
        assert result.content.columns[3] == 'Count'
        assert result.content.columns[4] == 'Index'
