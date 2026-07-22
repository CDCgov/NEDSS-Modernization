import pytest
import yaml

from src.execute_report import execute_report
from src.libraries import tb_case_ver
from src.models import ReportSpec, Table

faker_schema = 'dm_inv_tb.yaml'


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationTbCaseVerLibrary:
    """Integration tests for the tb_case_ver library."""

    column_names = [
        'DISEASE_CODE',
        'CASE_VERIFICATION_CODE',
        'CASE_VERIFICATION_DESC',
        'DISEASE_SITE_DESC',
        'DISEASE_SITE_IND',
        'INVESTIGATION_KEY',
        'DISEASE_SITE_VALUE',
    ]

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
        error_re = r'Column name metadata missing from initial query\. Values found: disease_site_desc_colname "\w*", case_verification_desc_colname "\w*", inv_rpt_dt_colname "\w*"'  # noqa: E501
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

        with pytest.raises(ValueError, match=error_re):
            tb_case_ver.execute(trx, 'subset query SQL')

        # INV1115 missing
        metadata = self.get_metadata_table(
            [
                ('INV1133', None, None, 'DISEASE_SITE', None),
                ('INV111', None, None, 'INV_RPT_DT', None),
            ]
        )
        trx.query.return_value = metadata

        with pytest.raises(ValueError, match=error_re):
            tb_case_ver.execute(trx, 'subset query SQL')

        # INV1133 missing
        metadata = self.get_metadata_table(
            [
                ('INV1115', None, None, 'CASE_VERIFCTON_CAT', None),
                ('INV111', None, None, 'INV_RPT_DT', None),
            ]
        )
        trx.query.return_value = metadata

        with pytest.raises(ValueError, match=error_re):
            tb_case_ver.execute(trx, 'subset query SQL')

    def test_main_query_no_colnames(self):
        with pytest.raises(
            ValueError, match='One or more of the passed in column names is missing.'
        ):
            tb_case_ver._tb_case_ver_query('subset_query', None, None, None)

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
        assert result.content.columns == self.column_names
        assert len(result.content.data) > 0

        for row in result.content.data:
            assert row[0] is None or isinstance(row[0], str)
            assert row[1] is None or isinstance(row[1], str)
            assert isinstance(row[2], str)
            assert isinstance(row[3], str)
            assert isinstance(row[4], str)
            assert isinstance(row[5], int)
            assert isinstance(row[6], str)

        snapshot.assert_match(yaml.dump(result.content.data), 'snapshot.yml')

    def test_execute_report_check_no_data(self):
        report_spec = ReportSpec.model_validate(
            {
                'is_export': True,
                'is_builtin': True,
                'report_title': 'TB Case Verification',
                'library_name': 'tb_case_ver',
                'data_source_name': '[RDB].[dbo].[DM_INV_TB]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[DM_INV_TB] WHERE 1 = 2',
            }
        )

        result = execute_report(report_spec)

        assert result is not None
        assert result.content.columns == self.column_names
        assert len(result.content.data) == 0
