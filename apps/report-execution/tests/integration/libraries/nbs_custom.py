import datetime
import decimal

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
                'library_name': 'nbs_custom',
                'subset_query': """
                    SELECT PROGRAM_JURISDICTION_OID,
                        PATIENT_LOCAL_ID,
                        EVENT_DATE,
                        INVESTIGATION_KEY
                    FROM rdb.dbo.DM_INV_STD
                """,
                'sort_by': '[INVESTIGATION_KEY] ASC',
            }
        )

        result = execute_report(report_spec)

        data = result.content.data
        assert len(data) == 500
        assert len(data[0]) == len(result.content.columns)
        assert result.content.columns == [
            'PROGRAM_JURISDICTION_OID',
            'PATIENT_LOCAL_ID',
            'EVENT_DATE',
            'INVESTIGATION_KEY',
        ]

        snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

        for data in result.content.data:
            assert isinstance(data[0], decimal.Decimal)
            assert isinstance(data[1], str)
            assert isinstance(data[2], datetime.datetime)
            assert isinstance(data[3], int)

    def test_execute_report_no_sort(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'library_name': 'nbs_custom',
                'subset_query': """
                        SELECT PROGRAM_JURISDICTION_OID,
                               PATIENT_LOCAL_ID,
                               EVENT_DATE
                        FROM rdb.dbo.DM_INV_STD
                """,
                'sort_by': None,
            }
        )

        result = execute_report(report_spec)

        data = result.content.data
        assert len(data) == 500
        assert len(data[0]) == len(result.content.columns)
        assert result.content.columns == [
            'PROGRAM_JURISDICTION_OID',
            'PATIENT_LOCAL_ID',
            'EVENT_DATE',
        ]

    def test_execute_report_no_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'library_name': 'nbs_custom',
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

        assert len(result.content.data) == 0
        assert result.content.columns == [
            'PROGRAM_JURISDICTION_OID',
            'PATIENT_LOCAL_ID',
            'EVENT_DATE',
        ]
