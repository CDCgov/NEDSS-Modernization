import pytest
import yaml
from mssql_python.exceptions import ProgrammingError

from src.execute_report import execute_report
from src.models import ReportSpec

faker_schema = 'std_hiv_datamart.yaml'


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationPa02Library:
    """Integration tests for the PA02 library."""

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
            'library_params': '{"report_type": "STD"}',
        }
        base.update(overrides)
        return ReportSpec.model_validate(base)

    def test_execute_report_check_data(self, snapshot):
        report_spec = self.create_spec()
        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data
        # assert len(data) == 2
        # assert result.content.columns == [
        #     'PATIENT_NAME',
        #     'PATIENT_LOCAL_ID',
        #     'INV_LOCAL_ID',
        #     'DIAGNOSIS',
        #     'CONFIRMATION_DT',
        #     'FL_FUP_EXAM_DT',
        #     'PATIENTID',
        #     'DAYS',
        #     'DAYS1',
        #     'COUNT',
        # ]
        snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

    def test_execute_report_no_data(self, snapshot):
        report_spec = self.create_spec()
        report_spec.subset_query = """
        SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART] WHERE 1=0
        """

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        assert len(result.content.data) == 0

    def test_execute_report_different_hiv_report_type(self):
        spec_hiv = self.create_spec(library_params='{"report_type": "HIV"}')

    def test_execute_report_missing_report_type_parameter(self):
        """Test that missing 'report_type' in library_params raises an error."""
        spec = self.create_spec(library_params='{}')
        with pytest.raises(ValueError, match="must contain 'report_type'"):
            execute_report(spec)

    def test_execute_report_invalid_report_type_format(self):
        """Test that invalid report type raises an error."""
        spec = self.create_spec(library_params='{"report_type": "random"}')
        with pytest.raises(ProgrammingError):
            execute_report(spec)
