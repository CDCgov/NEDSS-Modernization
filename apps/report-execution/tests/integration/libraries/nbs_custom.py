import pytest
import yaml

from src.execute_report import execute_report
from src.models import ReportSpec

faker_schema = 'phc_demographic.yaml'


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationNbsCustomLibrary:
    """Integration tests for the nbs_custom library."""

    def test_execute_report_check_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'NBS Custom',
                'library_name': 'nbs_custom',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': """
                        SELECT PHC_code_short_desc,
                               state,
                               county
                        FROM NBS_ODSE.dbo.PHCDemographic
                        ORDER BY PHC_code_short_desc, state, county
                """,
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data
        assert len(data) == 1000
        assert len(data[0]) == len(result.content.columns)
        assert result.content.columns == [
            'PHC_code_short_desc',
            'state',
            'county'
        ]

        snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

    def test_execute_report_no_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'NBS Custom',
                'library_name': 'nbs_custom',
                'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
                'subset_query': """
                        SELECT PHC_code_short_desc,
                               state,
                               county
                        FROM NBS_ODSE.dbo.PHCDemographic
                        WHERE 1 = 2
                """,
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        assert len(result.content.data) == 0
        assert result.content.columns == [
            'PHC_code_short_desc',
            'state',
            'county'
        ]
