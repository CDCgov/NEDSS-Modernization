from decimal import Decimal

import pytest
import yaml

from src.execute_report import execute_report
from src.models import ReportSpec

db_table = '[NBS_ODSE].[dbo].[PublicHealthCaseFact]'
db_fk_tables = ['[NBS_ODSE].[dbo].[SubjectRaceInfo]']
faker_schema = 'phc_demographic.yaml'


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationNbsSr09Library:
    """Integration tests for the nbs_sr_09 library.

    This library generates monthly case counts for bar graph data,
    filtering by time range, State, and Condition.
    """

    def test_execute_report_check_data(self, snapshot):
        """Test that the report returns correct monthly aggregated data."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'NBS Custom',
                'library_name': 'nbs_sr_09',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic]',
                'time_range': {'start': '2024-01-01', 'end': '2024-06-30'},
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data
        assert len(data) > 50
        assert len(data[0]) == len(result.content.columns)

        snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

        # Verify data structure and basic sanity checks
        for row in data:
            # Find the row index for each column (don't assume order)
            col_index = {col: idx for idx, col in enumerate(result.content.columns)}
            # Data type and value checks
            assert isinstance(row[col_index['State Code']], (str, type(None)))
            assert isinstance(row[col_index['State']], (str, type(None)))
            assert isinstance(row[col_index['County']], (str, type(None)))
            assert isinstance(row[col_index['Condition']], str)
            assert isinstance(row[col_index['monyr']], str)
            assert isinstance(row[col_index['ord']], str)
            assert isinstance(row[col_index['Cases']], Decimal)

            # Cases should be positive
            assert row[col_index['Cases']] > 0

            # Month code should be 6-digit YYYYMM format
            ord = row[col_index['ord']]
            assert len(ord) == 6
            assert ord.isdigit()

            # Month name should be 3 letters
            monyr = row[col_index['monyr']]
            assert len(monyr) == 3
            assert monyr.isalpha()

    def test_execute_report_with_filters(self):
        """Verify report works with filtered subset_query (filters are caller's responsibility)."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'NBS Custom',
                'library_name': 'nbs_sr_09',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': (
                    "SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic] "
                    "WHERE state = 'Georgia' "
                    "AND phc_code_short_desc = 'Pertussis'"
                ),
                'time_range': {'start': '2024-01-01', 'end': '2024-12-31'},
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'
        # Just verify it runs without error - the filtering is SQL's job
        assert len(result.content.data) >= 0

    def test_execute_report_empty_subset(self):
        """Test handling of empty result set."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'NBS Custom',
                'library_name': 'nbs_sr_09',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': (
                    'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic] WHERE 1 = 0'
                ),
                'time_range': {'start': '2024-01-01', 'end': '2024-06-30'},
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        # Should return empty dataset but with correct column structure
        assert len(result.content.data) == 0
        # State Code, State, County, Condition, monyr, ord, Cases
        assert len(result.content.columns) == 7

    def test_execute_report_check_column_order(self):
        """Verify column names and order match expected output."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'NBS Custom',
                'library_name': 'nbs_sr_09',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic]',
                'time_range': {'start': '2024-01-01', 'end': '2024-06-30'},
            }
        )

        result = execute_report(report_spec)

        # Expected column order (matching SQL query in the library)
        expected_columns = [
            'State Code',
            'State',
            'County',
            'Condition',
            'monyr',
            'ord',
            'Cases',
        ]

        assert result.content.columns == expected_columns

    def test_execute_report_check_metadata(self):
        """Check the metadata is correctly formatted."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'NBS Custom',
                'library_name': 'nbs_sr_09',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic]',
                'time_range': {'start': '2024-01-01', 'end': '2024-06-30'},
            }
        )

        result = execute_report(report_spec)

        # Check header
        assert (
            result.header
            == 'SR9: Monthly Cases of Selected Disease by County and State'
        )

        # Check subheader contains expected elements
        assert 'Georgia' in result.subheader and 'Tennessee' in result.subheader
        assert '01/01/2024 to 06/30/2024' in result.subheader

        # Check description contains required sections
        assert len(result.description) > 100
        assert 'Report Content' in result.description
        assert 'Data Source:' in result.description
        assert 'Cases' in result.description
        assert 'Event Date:' in result.description

        assert result.content_type == 'table'

    def test_execute_report_with_County_data(self):
        """Verify County-level data is preserved."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'NBS Custom',
                'library_name': 'nbs_sr_09',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic]',
                'time_range': {'start': '2024-01-01', 'end': '2024-06-30'},
            }
        )

        result = execute_report(report_spec)

        # Verify County column exists and has values
        col_index = {col: idx for idx, col in enumerate(result.content.columns)}
        assert 'County' in col_index

        # Check that counties are properly set
        counties = {row[col_index['County']] for row in result.content.data}
        assert len(counties) > 0
        # At least some counties should have real values (not all 'N/A')
        assert any(County != 'N/A' for County in counties)

    def test_execute_report_month_ordering(self):
        """Verify months are ordered correctly for a single state/county/disease."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'NBS Custom',
                'library_name': 'nbs_sr_09',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': (
                    "SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic] "
                    "WHERE state = 'Georgia' "
                    "AND county = 'Fulton County' "
                    "AND phc_code_short_desc = 'Pertussis'"
                ),
                'time_range': {'start': '2024-01-01', 'end': '2024-06-30'},
            }
        )

        result = execute_report(report_spec)
        col_index = {col: idx for idx, col in enumerate(result.content.columns)}
        
        # Extract month codes - they should already be in order
        ord_values = [row[col_index['ord']] for row in result.content.data]
        
        # Verify months are in chronological order
        assert ord_values == sorted(ord_values), \
            f"Months not in order: {ord_values}"

