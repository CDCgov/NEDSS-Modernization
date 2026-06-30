import re
from datetime import date

import pytest
import yaml

from src.execute_report import execute_report
from src.models import ReportSpec

faker_schema = 'qa_06.yaml'


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationQa06Library:
    """Integration tests for the qa_06 library."""

    def test_execute_report_check_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'QA06',
                'library_name': 'qa_06',
                'data_source_name': '[RDB].[dbo].[STD_HIV_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART]',
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data
        assert len(data[0]) == 13
        assert len(data[0]) == len(result.content.columns)

        snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

        # Sanity check for data shape
        patient_local_id_re = re.compile(r'PSN\d{8}GA01')
        inv_local_id_re = re.compile(r'CAS\d{8}GA01')
        referral_basis_re = re.compile(r'[A-Z]{1}\d\s?-\s[A-Za-z ,0-9]+')
        diagnosis_re = re.compile(r'\d+\s-\s[A-Za-z ,]')

        for row in data:
            assert row[0] is None or type(row[0]) is str
            assert patient_local_id_re.match(row[1]) is not None
            assert inv_local_id_re.match(row[2]) is not None
            assert referral_basis_re.match(row[3]) is not None
            assert row[4] is None or type(row[4]) is str
            assert diagnosis_re.match(row[5]) is not None
            assert row[6] is None or row[6] in ['Yes', 'No']
            assert row[7] is None or type(row[7]) is date
            assert row[8] is None or type(row[8]) is date
            assert row[9] is None or type(row[9]) is date
            assert row[10] is None or type(row[10]) is date
            assert row[11] > 1
            assert type(row[12]) is int

    def test_execute_report_no_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'QA06',
                'library_name': 'qa_06',
                'data_source_name': '[RDB].[dbo].[STD_HIV_DATAMART]',
                'subset_query': (
                    'SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART] WHERE 1 = 2'
                ),
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data
        assert len(data) == 0
        assert len(result.content.columns) == 13

    def test_execute_report_check_metadata(self):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'QA06',
                'library_name': 'qa_06',
                'data_source_name': '[RDB].[dbo].[STD_HIV_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART]',
            }
        )

        result = execute_report(report_spec)
        assert result.header == 'QA06'
        assert result.subheader is None
        assert result.description is None
        assert result.content_type == 'table'

        assert result.content.columns[0] == 'PATIENT_NAME'
        assert result.content.columns[1] == 'PATIENT_LOCAL_ID'
        assert result.content.columns[2] == 'INV_LOCAL_ID'
        assert result.content.columns[3] == 'REFERRAL_BASIS'
        assert result.content.columns[4] == 'PROVIDER_QUICK_CODE'
        assert result.content.columns[5] == 'DIAGNOSIS'
        assert result.content.columns[6] == 'CMP_PID_IND'
        assert result.content.columns[7] == 'CONFIRMATION_DT'
        assert result.content.columns[8] == 'FL_FUP_EXAM_DT'
        assert result.content.columns[9] == 'SPECIMEN_COLLECTION_DT'
        assert result.content.columns[10] == 'DIAGNOSIS_DT'
        assert result.content.columns[11] == 'COUNT'
        assert result.content.columns[12] == 'PATIENTID'
