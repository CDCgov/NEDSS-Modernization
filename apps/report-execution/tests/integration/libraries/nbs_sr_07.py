import datetime

import pytest
import yaml

from src.execute_report import execute_report
from src.models import ReportSpec

faker_schema = 'phc_demographic.yaml'


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationNbsSr07Library:
    """Integration tests for the nbs_sr_07 library."""

    @pytest.fixture(autouse=True)
    def set_time(self, time_machine):
        time_machine.move_to(datetime.datetime(2024, 6, 24))

    def test_execute_report_check_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'SR7',
                'library_name': 'nbs_sr_07',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic]',
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data
        assert len(data) == 6
        assert len(data[0]) == len(result.content.columns)

        snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

        for disease in ['Pertussis', 'Measles', 'Salmonellosis']:
            disease_rows = list()
            for row in result.content.data:
                if row[0] == disease:
                    disease_rows.append(row)
            # ensure there are 2 rows per disease
            assert len(disease_rows) == 2
            assert disease_rows[0][1] == 'Five Year Median YTD'
            assert disease_rows[0][2] >= 100
            assert disease_rows[1][1] == 'Current YTD'
            assert disease_rows[1][2] >= 100

    def test_execute_report_check_metadata(self):
        """Check the metadata and column names are correct with a frozen date."""
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'SR7: Cases of Selected Diseases vs. 5-Year Median for Selected '
                'Time Period',
                'library_name': 'nbs_sr_07',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic]',
            }
        )

        result = execute_report(report_spec)
        assert (
            result.header
            == 'SR7: Cases of Selected Diseases vs. 5-Year Median for Selected '
            'Time Period'
        )
        assert result.subheader == 'N/A, Georgia, Tennessee'
        assert len(result.description) > 100
        assert result.content_type == 'table'

        assert result.content.columns[0] == 'Disease'
        assert result.content.columns[1] == 'type'
        assert result.content.columns[2] == 'Number of Cases'
