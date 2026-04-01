import datetime

import pytest
import yaml

from src.execute_report import execute_report
from src.models import ReportSpec

db_table = '[NBS_ODSE].[dbo].[PublicHealthCaseFact]'
db_fk_tables = ['[NBS_ODSE].[dbo].[SubjectRaceInfo]']
faker_schema = 'phc_demographic.yaml'


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationNbsSr05Library:
    """Integration tests for the nbs_sr_05 library.

    This library looks at the past five years of data and the date on the sql server
    is not readily hardcoded, so the tests here are largely probabalistic.
    """

    @pytest.fixture(autouse=True)
    def set_time(self, time_machine):
        time_machine.move_to(datetime.datetime(2024, 6, 24))

    def test_execute_report_check_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'NBS Custom',
                'library_name': 'nbs_sr_05',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic]',
                'time_range': {'start': '2024-01-01', 'end': '2024-12-31'},
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data
        assert len(data) == 3
        assert len(data[0]) == len(result.content.columns)

        snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

        # probabilistic sanity check
        for disease in ['Pertussis', 'Measles', 'Salmonellosis']:
            record = None
            for row in result.content.data:
                if row[3] == disease:
                    record = row
                    break

            assert record is not None
            # -100% to 100%
            assert int(record[0][:-1]) <= 100
            assert int(record[0][:-1]) >= -100
            assert record[1] >= 100
            assert record[2] >= 100
            assert record[4] == 2024
            assert record[5] == record[2]
            assert record[6] >= 100
            assert record[7] >= 10
            assert record[2] >= record[7]  # ytd must be at least this month

    def test_execute_report_old_data_zeros(self):
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'NBS Custom',
                'library_name': 'nbs_sr_05',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': (
                    'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic] '
                    'WHERE YEAR(event_date) < (2024 - 5)'
                ),
                'time_range': {'start': '2024-01-01', 'end': '2024-12-31'},
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        assert len(result.content.data) >= 1
        assert len(result.content.data[0]) == len(result.content.columns)

        for disease in ['Pertussis', 'Measles', 'Salmonellosis']:
            record = None
            for row in result.content.data:
                if row[3] == disease:
                    record = row
                    break

            assert record is not None
            # No data returned should default to zero
            assert record[0] == '0%'
            assert record[1] == 0
            assert record[2] == 0
            assert record[4] == 2024
            assert record[5] == 0
            assert record[6] == 0
            assert record[7] == 0

    def test_execute_report_no_current_year(self):
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'NBS Custom',
                'library_name': 'nbs_sr_05',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': (
                    'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic] '
                    'WHERE YEAR(event_date) < 2024'
                ),
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        assert len(result.content.data) >= 1
        assert len(result.content.data[0]) == len(result.content.columns)

        for disease in ['Pertussis', 'Measles', 'Salmonellosis']:
            record = None
            for row in result.content.data:
                if row[3] == disease:
                    record = row
                    break

            assert record is not None
            # current year data should be zero, older not
            assert int(record[0][:-1]) < 0
            assert record[1] >= 100
            assert record[2] == 0
            assert record[4] == 2024
            assert record[5] == 0
            assert record[6] >= 100
            assert record[7] == 0

    def test_execute_report_only_current_year(self):
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'NBS Custom',
                'library_name': 'nbs_sr_05',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': (
                    'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic] '
                    'WHERE YEAR(event_date) = 2024'
                ),
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        assert len(result.content.data) >= 1
        assert len(result.content.data[0]) == len(result.content.columns)

        for disease in ['Pertussis', 'Measles', 'Salmonellosis']:
            record = None
            for row in result.content.data:
                if row[3] == disease:
                    record = row
                    break

            assert record is not None
            # current year data should be non-zero, older zero
            # default zero when median is zero to avoid error
            assert record[0] == '0%'
            assert record[1] == 0
            assert record[2] >= 100
            assert record[4] == 2024
            assert record[5] >= 100
            assert record[6] == 0
            assert record[7] >= 10

    def test_execute_report_empty_subset(self):
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'NBS Custom',
                'library_name': 'nbs_sr_05',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': (
                    'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic] WHERE 1 = 0'
                ),
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        assert len(result.content.data) == 0
        assert len(result.content.columns) == 8

    def test_execute_report_check_metadata(self):
        """Check the metadata and column names are correct with a frozen date."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'NBS Custom',
                'library_name': 'nbs_sr_05',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic]',
                'time_range': {'start': '2024-01-01', 'end': '2024-12-31'},
            }
        )

        result = execute_report(report_spec)
        assert result.header == 'SR5: Cases of Reportable Diseases by State'
        assert result.subheader == 'N/A, Georgia, Tennessee | 06/24/2024'
        assert len(result.description) > 100
        assert result.content_type == 'table'

        assert result.content.columns[0] == 'Percent Change 2024 vs 5 Year Median'
        assert result.content.columns[1] == 'Cumulative for 2023 to Date'
        assert result.content.columns[2] == 'Cumulative for 2024 to Date'
        assert result.content.columns[3] == 'Disease'
        assert result.content.columns[4] == 'Year'
        assert result.content.columns[5] == 'Cases'
        assert result.content.columns[6] == '5 Year Median Year to Date'
        assert result.content.columns[7] == 'JUN2024'
