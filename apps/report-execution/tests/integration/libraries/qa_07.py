import pytest
import yaml
from mssql_python.exceptions import ProgrammingError

from src.errors import InvalidLibraryParamsError
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
            'library_params': '{"days_value": 30}',
        }
        base.update(overrides)
        return ReportSpec.model_validate(base)

    def test_execute_report_check_data(self, snapshot):
        report_spec = self.create_spec()
        result = execute_report(report_spec)

        data = result.content.data
        assert len(data) == 4
        assert result.content.columns == [
            'PATIENT_NAME',
            'PATIENT_LOCAL_ID',
            'INV_LOCAL_ID',
            'DIAGNOSIS',
            'CONFIRMATION_DT',
            'FL_FUP_EXAM_DT',
            'PATIENTID',
            'DAYS',
            'DAYS1',
            'COUNT',
        ]
        snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

    def test_execute_report_no_data(self, snapshot):
        report_spec = self.create_spec()
        report_spec.subset_query = """
        SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART] WHERE 1=0
        """

        result = execute_report(report_spec)

        assert len(result.content.data) == 0

    def test_execute_report_different_days(self):
        """Test that the number of duplicate cases changes with the days parameter."""
        # 30 days
        spec_30 = self.create_spec(library_params='{"days_value": 30}')
        result_30 = execute_report(spec_30)
        rows_30 = len(result_30.content.data)

        # 60 days
        spec_60 = self.create_spec(library_params='{"days_value": 60}')
        result_60 = execute_report(spec_60)
        rows_60 = len(result_60.content.data)

        # 90 days
        spec_90 = self.create_spec(library_params='{"days_value": 90}')
        result_90 = execute_report(spec_90)
        rows_90 = len(result_90.content.data)
        # With more days, more rows should be considered duplicates
        assert rows_30 <= rows_60 <= rows_90

    def test_execute_report_missing_days_parameter(self):
        """Test that missing 'report_days' in library_params raises an error."""
        spec = self.create_spec(library_params='{}')
        with pytest.raises(
            InvalidLibraryParamsError, match="must contain 'days_value'"
        ):
            execute_report(spec)

    def test_execute_report_invalid_days_format(self):
        """Test that non‑integer days value raises an error."""
        spec = self.create_spec(library_params='{"days_value": "thirty"}')
        with pytest.raises(ProgrammingError):
            execute_report(spec)
