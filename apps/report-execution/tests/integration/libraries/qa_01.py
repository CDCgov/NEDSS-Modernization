from decimal import Decimal

import pytest
import yaml

from src.execute_report import execute_report
from src.models import ReportSpec

db_table = '[NBS_RDB].[dbo].[STD_HIV_DATAMART]'
db_fk_tables = []
faker_schema = 'std_hiv_datamart.yaml'


@pytest.mark.usefixtures('setup_containers')
@pytest.mark.integration
class TestIntegrationNbsQa01Library:
    """Integration tests for the nbs_custom library."""

    def test_execute_report_check_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'QA 01',
                'library_name': 'qa_01',
                'data_source_name': '[NBS_RDB].[dbo].[STD_HIV_DATAMART]',
                'subset_query': 'SELECT * FROM [NBS_RDB].[dbo].[STD_HIV_DATAMART]',
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data
        assert len(data) == 6  # two combinations with no data, zeros not filled
        assert len(data[0]) == 3
        assert len(data[0]) == len(result.content.columns)

        snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

        # Sanity check the result's shape beyond the snapshot
        record = None
        for row in result.content.data:
            if (
                    row[0] == Decimal('4877.00000')
                    and row[1] == 'Measles'
                    and row[2] == 'Confirmed'
            ):
                record = row
                break

        assert record is not None

    def test_execute_report_no_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'SR 3',
                'library_name': 'nbs_sr_13',
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
        assert len(result.content.columns) == 3

    def test_execute_report_check_metadata(self):
        """Check the metadata and column names are correct."""
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'SR13: Counts of Selected Diseases By Case Status',
                'library_name': 'nbs_sr_13',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': (
                    'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic] '
                    " WHERE state_cd = '13'"
                ),
            }
        )

        result = execute_report(report_spec)
        assert result.header == 'SR13: Counts of Selected Diseases By Case Status'
        assert len(result.description) > 100
        assert result.content_type == 'table'

        assert result.content.columns[0] == 'Case Count'
        assert result.content.columns[1] == 'Condition'
        assert result.content.columns[2] == 'Case Status'
