import pytest
import yaml

from src.execute_report import execute_report
from src.models import ReportSpec

faker_schema = 'tb_datamart.yaml'


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationNbsSr18Library:
    """Integration tests for the SR18 TB Case Verification library."""

    def test_execute_report_check_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'SR 18',
                'library_name': 'nbs_sr_18',
                'data_source_name': '[RDB].[dbo].[TB_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[TB_DATAMART]',
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data

        snapshot.assert_match(yaml.dump(data), 'snapshot.yaml')

        assert len(data) == 7

        # ensure the columns are correct
        assert result.content.columns == [
            'Case Verification',
            'Pulmonary Count',
            'Pulmonary %',
            'Extrapulmonary Count',
            'Extrapulmonary %',
            'Both Count',
            'Both %',
            'Unknown Count',
            'Unknown %',
            'All Cases Count',
            'All Cases %',
        ]

        # ensure proper data types for each column
        for d in data:
            assert isinstance(d[0], str)
            assert isinstance(d[1], int)
            assert isinstance(d[2], float)
            assert isinstance(d[3], int)
            assert isinstance(d[4], float)
            assert isinstance(d[5], int)
            assert isinstance(d[6], float)
            assert isinstance(d[7], int)
            assert isinstance(d[8], float)
            assert isinstance(d[9], int)
            assert isinstance(d[10], float)

        # ensure the Case Verification strings match the SAS report
        assert data[0][0] == '0 - Not a Verified Case'
        assert data[1][0] == '1 - Positive Culture'
        assert data[2][0] == '1A - Positive NAA'
        assert data[3][0] == '2 - Positive Smear'
        assert data[4][0] == '3 - Clinical Case Definition'
        assert data[5][0] == '4 - Provider Diagnosis'
        assert data[6][0] == '5 - Suspect Case'

    def test_execute_report_no_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'SR 18',
                'library_name': 'nbs_sr_18',
                'data_source_name': '[RDB].[dbo].[TB_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[TB_DATAMART] WHERE 1 = 2',
            }
        )

        result = execute_report(report_spec)

        assert len(result.content.data) == 0
        assert result.content.columns == [
            'Case Verification',
            'Pulmonary Count',
            'Pulmonary %',
            'Extrapulmonary Count',
            'Extrapulmonary %',
            'Both Count',
            'Both %',
            'Unknown Count',
            'Unknown %',
            'All Cases Count',
            'All Cases %',
        ]
