import pytest

from src.models import ReportSpec
from src.execute_report import execute_report

faker_schema = 'tb_datamart.yaml'


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationNbsSr13Library:
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

    # def test_execute_report_check_data(self, snapshot):
    #     report_spec = ReportSpec.model_validate(
    #         {
    #             'is_export': True,
    #             'is_builtin': True,
    #             'report_title': 'SR 13',
    #             'library_name': 'nbs_sr_13',
    #             'data_source_name': '[NBS_ODSE].[dbo].[PHCDemographic]',
    #             'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic]',
    #         }
    #     )

    #     result = execute_report(report_spec)
    #     assert result.content_type == 'table'

    #     data = result.content.data
    #     assert len(data) == 6  # two combinations with no data, zeros not filled
    #     assert len(data[0]) == 3
    #     assert len(data[0]) == len(result.content.columns)

    #     snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

    #     # Sanity check the result's shape beyond the snapshot
    #     record = None
    #     for row in result.content.data:
    #         if (
    #             row[0] == Decimal('4877.00000')
    #             and row[1] == 'Measles'
    #             and row[2] == 'Confirmed'
    #         ):
    #             record = row
    #             break

    #     assert record is not None
