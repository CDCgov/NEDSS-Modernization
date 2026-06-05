import pytest
import yaml

from src.execute_report import execute_report
from src.models import ReportSpec

faker_schema = 'dm_inv_std.yaml'


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
                'data_source_name': '[NBS_RDB].[dbo].[DM_INV_STD]',
                'subset_query': """
                        SELECT PROGRAM_JURISDICTION_OID,
                               PATIENT_LOCAL_ID,
                               EVENT_DATE
                        FROM rdb.dbo.DM_INV_STD
                        ORDER BY PROGRAM_JURISDICTION_OID,
                                 PATIENT_LOCAL_ID,
                                 EVENT_DATE
                """,
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data
        assert len(data) == 500
        assert len(data[0]) == len(result.content.columns)
        assert result.content.columns == [
            'PROGRAM_JURISDICTION_OID',
            'PATIENT_LOCAL_ID',
            'EVENT_DATE'
        ]

        snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

    def test_execute_report_no_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'NBS Custom',
                'library_name': 'nbs_custom',
                'data_source_name': '[NBS_RDB].[dbo].[DM_INV_STD]',
                'subset_query': """
                        SELECT PROGRAM_JURISDICTION_OID,
                               PATIENT_LOCAL_ID,
                               EVENT_DATE
                        FROM rdb.dbo.DM_INV_STD
                        WHERE 1 = 2
                """,
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        assert len(result.content.data) == 0
        assert result.content.columns == [
            'PROGRAM_JURISDICTION_OID',
            'PATIENT_LOCAL_ID',
            'EVENT_DATE'
        ]
