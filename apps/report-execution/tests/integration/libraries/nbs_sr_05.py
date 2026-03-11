import logging

import pytest

from src.execute_report import execute_report
from src.models import ReportSpec

db_table = '[NBS_ODSE].[dbo].[PublicHealthCaseFact]'
db_fk_tables = ['[NBS_ODSE].[dbo].[SubjectRaceInfo]']
faker_schema = 'phc_demographic.yaml'


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationNbsSr05Library:
    """Integration tests for the nbs_custom library."""

    def test_execute_report_with_time_range(self):
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'NBS Custom',
                'library_name': 'nbs_sr_05',
                # Filter operator is used here as it is a stable, small table
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic]',
                'time_range': {'start': '2024-01-01', 'end': '2024-12-31'},
            }
        )

        result = execute_report(report_spec)
        assert result.description == (
            'SR5: Cases of Reportable Diseases by State\n' + '2024-01-01 - 2024-12-31'
        )
        assert result.content_type == 'table'
        logging.info(result.content.data)

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
