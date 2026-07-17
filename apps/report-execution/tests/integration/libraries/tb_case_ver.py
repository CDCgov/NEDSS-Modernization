import pytest

from src.execute_report import execute_report
from src.libraries import tb_case_ver
from src.models import ReportSpec, Table

faker_schema = 'dm_inv_tb.yaml'


# @pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationTbCaseVerLibrary:
    """Integration tests for the tb_case_ver library."""

    def get_metadata_table(self, data):
        return Table(
            columns=[
                'QUESTION_IDENTIFIER',
                'RDB_COLUMN_NM',
                'RDB_TABLE_NM',
                'USER_DEFINED_COLUMN_NM',
                'DATAMART_NM',
            ],
            data=data,
        )

    def test_missing_metadata_raises_valueerror(self, mocker):
        trx = mocker.Mock()
        mocker.patch.object(tb_case_ver, '_metadata_query', return_value='metadata SQL')

        # INV111 missing
        metadata = self.get_metadata_table(
            [
                ('INV1133', None, None, 'DISEASE_SITE', None),
                ('INV1115', None, None, 'CASE_VERIFCTON_CAT', None),
            ]
        )
        trx.query.return_value = metadata

        with pytest.raises(ValueError, match='Column name metadata missing'):
            tb_case_ver.execute(trx, 'subset query SQL')

        # INV1115 missing
        metadata = self.get_metadata_table(
            [
                ('INV1133', None, None, 'DISEASE_SITE', None),
                ('INV111', None, None, 'INV_RPT_DT', None),
            ]
        )
        trx.query.return_value = metadata

        with pytest.raises(ValueError, match='Column name metadata missing'):
            tb_case_ver.execute(trx, 'subset query SQL')

        # INV1133 missing
        metadata = self.get_metadata_table(
            [
                ('INV1115', None, None, 'CASE_VERIFCTON_CAT', None),
                ('INV111', None, None, 'INV_RPT_DT', None),
            ]
        )
        trx.query.return_value = metadata

        with pytest.raises(ValueError, match='Column name metadata missing'):
            tb_case_ver.execute(trx, 'subset query SQL')

    def test_execute_report_check_data(self, snapshot):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'TB Case Verification',
                'library_name': 'tb_case_ver',
                'data_source_name': '[RDB].[dbo].[DM_INV_TB]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[DM_INV_TB]',
            }
        )

        result = execute_report(report_spec)

        assert result is not None
