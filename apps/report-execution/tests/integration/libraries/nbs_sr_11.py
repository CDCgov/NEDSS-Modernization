import pytest
import yaml

from src.execute_report import execute_report
from src.models import ReportSpec

faker_schema = 'phc_demographic.yaml'


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationNbsSr11Library:
    """Integration tests for the nbs_sr_11 library."""

    def test_execute_report_check_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'SR 11',
                'library_name': 'nbs_sr_11',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic]',
                'time_range': {'start': '2020-01-01', 'end': '2024-12-31'},
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data
        assert len(data) == 159
        assert len(data[0]) == 6  # State Code, State, County, Condition, Year, Cases
        assert len(data[0]) == len(result.content.columns)

        snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

        # Sanity check the result's shape beyond the snapshot
        record = None
        for row in result.content.data:
            if row[2] == 'Polk County' and row[3] == 'Measles' and row[4] == 2021:
                record = row
                break

        assert record is not None
        # Case count for Polk County, Measles, 2021
        assert record[5] == 773

    def test_execute_report_no_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'SR 11',
                'library_name': 'nbs_sr_11',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': (
                    'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic]'
                    "WHERE state = 'Rhode Island'"
                ),
                'time_range': {'start': '2020-01-01', 'end': '2024-12-31'},
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data
        assert len(data) == 0
        assert len(result.content.columns) == 6

    def test_execute_report_check_metadata(self):
        """Check the metadata and column names are correct."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'SR 11',
                'library_name': 'nbs_sr_11',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': (
                    'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic]'
                    "WHERE state = 'Georgia' "
                    "AND phc_code_short_desc IN ('Pertussis', 'Measles')"
                ),
                'time_range': {'start': '2020-01-01', 'end': '2024-12-31'},
            }
        )

        result = execute_report(report_spec)
        assert result.header == 'SR11: Cases of Selected Diseases By Year Over Time'
        assert result.subheader == 'Georgia | Measles, Pertussis | 2020 to 2024'

        assert len(result.description) > 100
        assert result.content_type == 'table'

        assert result.content.columns[0] == 'State Code'
        assert result.content.columns[1] == 'State'
        assert result.content.columns[2] == 'County'
        assert result.content.columns[3] == 'Condition'
        assert result.content.columns[4] == 'year'
        assert result.content.columns[5] == 'Cases'
