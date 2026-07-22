import datetime
import decimal
import re

import pytest
import yaml

from src.execute_report import execute_report
from src.models import ReportSpec

faker_schema = 'phc_demographic.yaml'


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationNbsSr05Library:
    """Integration tests for the nbs_sr_05 library.

    This library looks at the past five years of data and the date on the sql server
    is not readily hardcoded, so the tests here are largely probabilistic.
    """

    @pytest.fixture(autouse=True)
    def set_time(self, time_machine):
        time_machine.move_to(datetime.datetime(2024, 6, 24))

    def test_execute_report_check_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'NBS Custom',
                'library_name': 'nbs_sr_05',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic]',
            }
        )

        result = execute_report(report_spec)

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

            self._verify_percentage(record)
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
                'is_export': True,
                'is_builtin': True,
                'report_title': 'NBS Custom',
                'library_name': 'nbs_sr_05',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': (
                    'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic] '
                    'WHERE YEAR(event_date) < (2024 - 5)'
                ),
            }
        )

        result = execute_report(report_spec)

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
            self._verify_percentage(record)
            assert record[1] == 0
            assert record[2] == 0
            assert record[4] == 2024
            assert record[5] == 0
            assert record[6] == 0
            assert record[7] == 0

    def test_execute_report_no_current_year(self):
        report_spec = ReportSpec.model_validate(
            {
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
            self._verify_percentage(record)
            assert record[1] >= 100
            assert record[2] == 0
            assert record[4] == 2024
            assert record[5] == 0
            assert record[6] >= 100
            assert record[7] == 0

    def test_execute_report_only_current_year(self):
        report_spec = ReportSpec.model_validate(
            {
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

        assert len(result.content.data) == 0
        assert len(result.content.columns) == 8

    def test_execute_report_check_metadata(self):
        """Check the metadata and column names are correct with a frozen date."""
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'SR5: Cases of Reportable Diseases by State',
                'library_name': 'nbs_sr_05',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic]',
            }
        )

        result = execute_report(report_spec)
        assert result.description is not None and len(result.description) > 100

        assert result.content.columns[0] == 'Percent Change 2024 vs 5 Year Median'
        assert result.content.columns[1] == 'Cumulative for 2023 to Date'
        assert result.content.columns[2] == 'Cumulative for 2024 to Date'
        assert result.content.columns[3] == 'Disease'
        assert result.content.columns[4] == 'Year'
        assert result.content.columns[5] == 'Cases'
        assert result.content.columns[6] == '5 Year Median Year to Date'
        assert result.content.columns[7] == 'JUN2024'

    def _verify_percentage(self, record):
        """The query results from SR05 have percentages represented as strings
        with '%' appended, rounded to zero decimal places, and wrapped in
        parentheses when negative (e.g. -15% would be (15%)).  This is because
        the query is mimicking the SAS `percent9.0` format which has all the
        aforementioned features.

        Verify the found percentage by unpacking the percentage value from the
        result string and ensuring it is correct by re-calculating the percentage
        from the other provided numbers.
        """
        # ensure it's in the proper format (e.g. "15%" or "(15%)")
        pattern = r'\(?(\d+)%\)?'
        match = re.match(pattern, record[0])

        assert match is not None, f'regex failed to match for: {record[0]}'

        percentage_from_query = decimal.Decimal(match.group(1))

        # query result percentage is negative if wrapped in parens
        if record[0].find('(') != -1:
            percentage_from_query = percentage_from_query * -1

        cases_from_query = record[5]
        five_year_median_to_date_from_query = decimal.Decimal(record[6])

        # should be 0%
        if (
            five_year_median_to_date_from_query == 0
            or cases_from_query - five_year_median_to_date_from_query == 0
        ):
            assert percentage_from_query == 0
            return

        # re-calculate the percentage to compare against the query's results
        calc_percentage = (
            cases_from_query - five_year_median_to_date_from_query
        ) / five_year_median_to_date_from_query
        calc_percentage = round(calc_percentage * 100, 0)

        assert percentage_from_query == calc_percentage
