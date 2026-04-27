import pytest
import yaml

from src.execute_report import execute_report
from src.models import ReportSpec

faker_schema = 'phc_demographic.yaml'


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationNbsSr08Library:
    """Integration tests for the nbs_sr_08 library."""

    def test_execute_report_check_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'SR8: Report of Disease Cases Over Selected Time Period',
                'library_name': 'nbs_sr_08',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic]',
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data
        assert len(data) == 950  # two combinations with no data, zeros not filled
        assert len(data[0]) == 7
        assert len(data[0]) == len(result.content.columns)

        snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

        # Sanity check the result's shape beyond the snapshot
        record = None
        for row in result.content.data:
            if (
                row[1] == 'Georgia' # State
                and row[2] == 'Middlesex County' # County
                and row[3] == 'Measles'
                and row[4] != None
            ):
                record = row
                break

        assert record is not None
        assert record[0] == '13' # State Code
        assert record[5] == '13002' # County Code
        assert record[6] >= 1 # Case Count


    def test_execute_report_no_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'SR8: Report of Disease Cases Over Selected Time Period',
                'library_name': 'nbs_sr_08',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': (
                    'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic]'
                    "WHERE state = 'Rhode Island'"
                ),
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data
        assert len(data) == 0
        assert len(result.content.columns) == 7

        assert result.subheader == ''

    def test_execute_report_check_metadata_one_state(self):
        """Check the metadata and column names are correct."""
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'SR8: Report of Disease Cases Over Selected Time Period',
                'library_name': 'nbs_sr_08',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': (
                    'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic] '
                    " WHERE state_cd = '13'"
                ),
            }
        )

        result = execute_report(report_spec)
        assert (
            result.header
            == 'SR8: Report of Disease Cases Over Selected Time Period'
        )
        assert result.subheader == 'Georgia'
        assert len(result.description) > 100
        assert result.content_type == 'table'

        assert result.content.columns[0] == 'State Code'
        assert result.content.columns[1] == 'State'
        assert result.content.columns[2] == 'County'
        assert result.content.columns[3] == 'Condition'
        assert result.content.columns[4] == 'Event Date'
        assert result.content.columns[5] == 'County Code'
        assert result.content.columns[6] == 'Cases'
