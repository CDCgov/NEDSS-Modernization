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
class TestIntegrationNbsSr09Library:
    """Integration tests for the nbs_sr_09 library.

    This library generates monthly case counts for bar graph data,
    filtering by time range, state, and disease.
    """

    @pytest.fixture(autouse=True)
    def set_time(self, time_machine):
        time_machine.move_to(datetime.datetime(2024, 6, 24))

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
            assert 'state_cd' in col_index
            assert 'state' in col_index
            assert 'county' in col_index
            assert 'disease' in col_index
            assert 'month_name' in col_index
            assert 'month_code' in col_index
            assert 'cases' in col_index
            
            # Data type and value checks
            assert isinstance(row[col_index['state_cd']], str)
            assert isinstance(row[col_index['state']], str)
            assert isinstance(row[col_index['county']], str)
            assert isinstance(row[col_index['disease']], str)
            assert isinstance(row[col_index['month_name']], str)
            assert isinstance(row[col_index['month_code']], str)
            assert isinstance(row[col_index['cases']], (int, float))
            
            # Cases should be non-negative
            assert row[col_index['cases']] >= 0
            
            # Month code should be 6-digit YYYYMM format
            month_code = row[col_index['month_code']]
            assert len(month_code) == 6
            assert month_code.isdigit()
            
            # Month name should be 3 letters
            month_name = row[col_index['month_name']]
            assert len(month_name) in [3, 4]  # Some months like 'Sept' might be 4 chars
            assert month_name.isalpha()

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
            month_code = row[col_index['month_code']]
            assert month_code in valid_months
            
            # Verify month names match the codes
            month_map = {
                '202403': 'Mar',
                '202404': 'Apr',
                '202405': 'May',
            }
            expected_month_name = month_map[month_code]
            assert row[col_index['month_name']] == expected_month_name

    def test_execute_report_with_state_filter(self):
        """Test filtering by specific state."""
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
                    "WHERE state = 'Georgia'"
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
            assert row[col_index['state']] == 'Georgia'
            
        # Subheader should include Georgia
        assert 'Georgia' in result.subheader

    def test_execute_report_with_disease_filter(self):
        """Test filtering by specific disease."""
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
            assert row[col_index['disease']] == 'Pertussis'
            
        # Subheader should include Pertussis
        assert 'Pertussis' in result.subheader

    def test_execute_report_with_multiple_filters(self):
        """Test combining multiple filters (state, disease, time range)."""
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
                    "WHERE state = 'Tennessee' "
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
            assert row[col_index['state']] == 'Tennessee'
            assert row[col_index['disease']] == 'Measles'
            assert row[col_index['month_code']] == '202404'
            
        # Subheader should include the filtered values
        assert 'Tennessee' in result.subheader
        assert 'Measles' in result.subheader

    def test_execute_report_no_time_range(self):
        """Test report works without explicit time range (defaults to last 12 months)."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'NBS Custom',
                'library_name': 'nbs_sr_09',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic]',
                # No time_range provided
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'
        
        # Should return data (may be empty if no data in last 12 months)
        # Check that subheader indicates last 12 months
        assert 'Last 12 Months' in result.subheader

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
        assert len(result.content.columns) == 7  # state_cd, state, county, disease, month_name, month_code, cases

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
            'state_cd',
            'state',
            'county',
            'disease',
            'month_name',
            'month_code',
            'cases'
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
        assert result.header == 'SR9: Monthly Cases by Disease and State'
        
        # Check subheader contains expected elements
        assert 'State(s):' in result.subheader
        assert 'Disease(s):' in result.subheader
        assert 'Time Period:' in result.subheader
        assert '2024-01-01 to 2024-06-30' in result.subheader
        assert '06/24/2024' in result.subheader
        
        # Check description contains required sections
        assert len(result.description) > 100
        assert 'Report Content' in result.description
        assert 'Data Source:' in result.description
        assert 'Total Monthly Cases' in result.description
        assert 'Event Date:' in result.description
        
        assert result.content_type == 'table'

    def test_execute_report_with_county_data(self):
        """Verify county-level data is preserved."""
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
        
        # Verify county column exists and has values
        col_index = {col: idx for idx, col in enumerate(result.content.columns)}
        assert 'county' in col_index
        
        # Check that counties are properly set (shouldn't be all 'N/A' unless data missing)
        counties = {row[col_index['county']] for row in result.content.data}
        assert len(counties) > 0
        # At least some counties should have real values (not all 'N/A')
        assert any(county != 'N/A' for county in counties)

    def test_execute_report_month_ordering(self):
        """Verify months are ordered correctly using month_code."""
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
        
        # Group by disease and check month ordering
        col_index = {col: idx for idx, col in enumerate(result.content.columns)}
        
        # Group data by disease
        disease_data = {}
        for row in result.content.data:
            disease = row[col_index['disease']]
            if disease not in disease_data:
                disease_data[disease] = []
            disease_data[disease].append(row)
        
        # For each disease, verify months are in order
        for disease, rows in disease_data.items():
            month_codes = [row[col_index['month_code']] for row in rows]
            # Should be in ascending order
            assert month_codes == sorted(month_codes)