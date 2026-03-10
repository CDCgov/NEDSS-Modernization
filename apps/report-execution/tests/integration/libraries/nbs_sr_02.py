import pytest

from src.execute_report import execute_report
from src.models import ReportSpec


@pytest.mark.usefixtures('setup_containers')
@pytest.mark.integration
class TestIntegrationNbsSr02Library:
    """Integration tests for the nbs_custom library."""

    def test_execute_report_with_time_range(self):
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'NBS Custom',
                'library_name': 'nbs_sr_02',
                # Filter operator is used here as it is a stable, small table
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic]',
                'time_range': {'start': '2024-01-01', 'end': '2024-12-31'},
            }
        )

        result = execute_report(report_spec)
        assert result.description == (
            'SR2: Counts of Reportable Diseases by County for Selected Time frame\n'
            + '2024-01-01 - 2024-12-31'
        )
        assert result.content_type == 'table'

        assert len(result.content.data) >= 1
        assert len(result.content.data[0]) == 4
        assert len(result.content.data[0]) == len(result.content.columns)

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

    def test_execute_report_without_time_range(self):
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'NBS Custom',
                'library_name': 'nbs_custom',
                # Filter operator is used here as it is a stable, small table
                'data_source_name': '[NBS_ODSE].[dbo].[Filter_operator]',
                'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[Filter_operator]',
            }
        )

        result = execute_report(report_spec)
        assert result.description == (
            'Custom Report For Table: [NBS_ODSE].[dbo].[Filter_operator]'
        )
        assert result.content_type == 'table'

        assert len(result.content.data) == 11
        assert len(result.content.data[0]) == len(result.content.columns)
