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
        assert len(data) > 0
        assert len(data[0]) == len(result.content.columns)

        snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

        # Verify data structure and basic sanity checks
        for row in data:
            # Find the row index for each column (don't assume order)
            col_index = {col: idx for idx, col in enumerate(result.content.columns)}

            # Basic column presence checks
            assert 'State Code' in col_index
            assert 'State' in col_index
            assert 'County' in col_index
            assert 'Condition' in col_index
            assert 'monyr' in col_index
            assert 'ord' in col_index
            assert 'Cases' in col_index

            # Data type and value checks
            assert isinstance(row[col_index['State Code']], (str, type(None)))
            assert isinstance(row[col_index['State']], (str, type(None)))
            assert isinstance(row[col_index['County']], (str, type(None)))
            assert isinstance(row[col_index['Condition']], str)
            assert isinstance(row[col_index['monyr']], str)
            assert isinstance(row[col_index['ord']], str)
            assert isinstance(row[col_index['Cases']], Decimal)

            # Cases should be non-negative
            assert row[col_index['Cases']] >= 0

            # Month code should be 6-digit YYYYMM format
            ord = row[col_index['ord']]
            assert len(ord) == 6
            assert ord.isdigit()

            # Month name should be 3 letters
            monyr = row[col_index['monyr']]
            assert len(monyr) in [3, 4]  # Some months like 'Sept' might be 4 chars
            assert monyr.isalpha()

    def test_execute_report_with_time_range_filter(self):
        """Test that time range filtering works correctly."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'NBS Custom',
                'library_name': 'nbs_sr_09',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic]',
                'time_range': {'start': '2024-03-01', 'end': '2024-05-31'},
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        # Verify only months in the specified range appear
        data = result.content.data
        col_index = {col: idx for idx, col in enumerate(result.content.columns)}

        valid_months = {'202403', '202404', '202405'}
        for row in data:
            ord = row[col_index['ord']]
            assert ord in valid_months

            # Verify month names match the codes
            month_map = {
                '202403': 'Mar',
                '202404': 'Apr',
                '202405': 'May',
            }
            expected_monyr = month_map[ord]
            assert row[col_index['monyr']] == expected_monyr

    def test_execute_report_with_State_filter(self):
        """Test filtering by specific State."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'NBS Custom',
                'library_name': 'nbs_sr_09',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': (
                    'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic] '
                    "WHERE State = 'Georgia'"
                ),
                'time_range': {'start': '2024-01-01', 'end': '2024-06-30'},
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        # Verify all records are for Georgia
        data = result.content.data
        col_index = {col: idx for idx, col in enumerate(result.content.columns)}

        for row in data:
            assert row[col_index['State']] == 'Georgia'

        # Subheader should include Georgia
        assert 'Georgia' in result.subheader

    def test_execute_report_with_Condition_filter(self):
        """Test filtering by specific Condition."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'NBS Custom',
                'library_name': 'nbs_sr_09',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': (
                    'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic] '
                    "WHERE phc_code_short_desc = 'Pertussis'"
                ),
                'time_range': {'start': '2024-01-01', 'end': '2024-06-30'},
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        # Verify all records are for Pertussis
        data = result.content.data
        col_index = {col: idx for idx, col in enumerate(result.content.columns)}

        for row in data:
            assert row[col_index['Condition']] == 'Pertussis'

        # Subheader should include Pertussis
        assert 'Pertussis' in result.subheader

    def test_execute_report_with_multiple_filters(self):
        """Test combining multiple filters (State, Condition, time range)."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'NBS Custom',
                'library_name': 'nbs_sr_09',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': (
                    'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic] '
                    "WHERE State = 'Tennessee' "
                    "AND phc_code_short_desc = 'Measles'"
                ),
                'time_range': {'start': '2024-04-01', 'end': '2024-04-30'},
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        # Verify all filters applied correctly
        data = result.content.data
        col_index = {col: idx for idx, col in enumerate(result.content.columns)}

        for row in data:
            assert row[col_index['State']] == 'Tennessee'
            assert row[col_index['Condition']] == 'Measles'
            assert row[col_index['ord']] == '202404'

        # Subheader should include the filtered values
        assert 'Tennessee' in result.subheader
        assert 'Measles' in result.subheader

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
        assert result.header == 'SR9: Monthly Cases by Disease, County, and State'

        # Check subheader contains expected elements
        assert 'State(s):' in result.subheader
        assert 'Condition(s):' in result.subheader
        assert 'Time Period:' in result.subheader

        # Check description contains required sections
        assert len(result.description) > 100
        assert 'Report Content' in result.description
        assert 'Data Source:' in result.description
        assert 'Total Monthly Cases' in result.description
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
        """Verify months are ordered correctly using ord."""
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

        # Group by Condition and check month ordering
        col_index = {col: idx for idx, col in enumerate(result.content.columns)}

        # Group data by Condition
        Condition_data = {}
        for row in result.content.data:
            Condition = row[col_index['Condition']]
            if Condition not in Condition_data:
                Condition_data[Condition] = []
            Condition_data[Condition].append(row)

        # For each Condition, verify months are in order
        for _Condition, rows in Condition_data.items():
            ords = [row[col_index['ord']] for row in rows]
            # Should be in ascending order
            assert ords == sorted(ords)
