import datetime
import decimal

import pytest
import yaml

from src.execute_report import execute_report
from src.models import ReportSpec

faker_schema = 'std_hiv_datamart.yaml'


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationQa07Library:
    """Integration tests for the QA07 library."""
    def create_spec(self, **overrides):
        """Create a report specification with defaults from the class properties."""
        base = {
            'version': 1,
            'is_export': True,
            'is_builtin': True,
            'report_title': 'QA07',
            'library_name': 'qa_07',
            'data_source_name': '[RDB].[dbo].[STD_HIV_DATAMART]',
            'subset_query': 'SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART]',
            'library_params': '{"report_days": 30}'
        }
        base.update(overrides)
        return ReportSpec.model_validate(base)
    
    def test_execute_report_check_data(self, snapshot):
        report_spec = self.create_spec()
        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data
        # assert len(data) == 500
        # assert len(data[0]) == len(result.content.columns)
        # assert result.content.columns == [
        #     'PROGRAM_JURISDICTION_OID',
        #     'PATIENT_LOCAL_ID',
        #     'EVENT_DATE',
        # ]

        snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

        # for data in result.content.data:
        #     assert isinstance(data[0], decimal.Decimal)
        #     assert isinstance(data[1], str)
        #     assert isinstance(data[2], datetime.datetime)

    def test_execute_report_no_data(self, snapshot):
        report_spec = self.create_spec()
        report_spec.subset_query = 'SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART] WHERE 1=0'

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        assert len(result.content.data) == 0
