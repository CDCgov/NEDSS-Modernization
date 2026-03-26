import pytest
import yaml

from src.execute_report import execute_report
from src.models import ReportSpec

db_table = '[NBS_ODSE].[dbo].[PublicHealthCaseFact]'
db_fk_tables = ['[NBS_ODSE].[dbo].[SubjectRaceInfo]']
faker_schema = 'phc_demographic.yaml'


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationNbsSr02Library:
    """Integration tests for the nbs_custom library."""

    def test_execute_report_check_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'SR 2',
                'library_name': 'nbs_sr_02',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic]',
                'time_range': {'start': '2020-01-01', 'end': '2024-12-31'},
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data
        assert len(data) == 12
        assert len(data[0]) == 4
        assert len(data[0]) == len(result.content.columns)

        snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

        record = None
        for row in result.content.data:
            if (
                row[0] == 'Georgia'
                and row[1] == 'Gwinnett County'
                and row[2] == 'Pertussis'
            ):
                record = row
                break

        assert record is not None
        assert record[3] >= 1

    def test_execute_report_check_metadata_with_time_range(self):
        """Check the metadata and column names are correct."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'SR 2',
                'library_name': 'nbs_sr_02',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic]',
                'time_range': {'start': '2020-01-01', 'end': '2024-12-31'},
            }
        )

        result = execute_report(report_spec)
        assert (
            result.header
            == 'SR2: Counts of Reportable Diseases by County for Selected Time frame'
        )
        assert (
            result.subheader
            == 'For N/A, Georgia, Tennessee and From 2020-01-01 To 2024-12-31'
        )
        assert len(result.description) > 100
        assert result.content_type == 'table'

        assert result.content.columns[0] == 'State'
        assert result.content.columns[1] == 'County'
        assert result.content.columns[2] == 'Condition'
        assert result.content.columns[3] == 'Cases'

    def test_execute_report_check_metadata_without_time_range_one_state(self):
        """Check the metadata and column names are correct."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'SR 2',
                'library_name': 'nbs_sr_02',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': (
                    'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic] '
                    ' WHERE state_cd = 13'
                ),
            }
        )

        result = execute_report(report_spec)
        assert (
            result.header
            == 'SR2: Counts of Reportable Diseases by County for Selected Time frame'
        )
        assert result.subheader == 'For Georgia'
        assert len(result.description) > 100
        assert result.content_type == 'table'

        assert result.content.columns[0] == 'State'
        assert result.content.columns[1] == 'County'
        assert result.content.columns[2] == 'Condition'
        assert result.content.columns[3] == 'Cases'
