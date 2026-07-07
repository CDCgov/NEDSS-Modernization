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
            'report_title': 'PA02',
            'library_name': 'pa_02',
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
        with pytest.raises(ValueError, match="report_type must be"):
            execute_report(spec)

    def test_execute_std_structure_and_consistency(self):
        spec = self.create_spec(library_params='{"report_type": "STD"}')
        result = execute_report(spec)
        assert result.content_type == 'table'
        table = result.content
        assert table.columns == ['provider', 'metric', 'partners', 'clus', 'reac', 'cohort', 'total']

        assert len(table.data) > 0

        # Row-level sum consistency
        for row in table.data:
            provider, metric, partners, clus, reac, cohort, total = row
            assert total == partners + clus + reac + cohort, f"Row {row} has inconsistent total"

        # Verify ALL rows: one per metric
        all_rows = [row for row in table.data if row[0] == 'ALL']
        metrics = {row[1] for row in table.data if row[0] != 'ALL'}
        assert len(all_rows) == len(metrics), \
            f"Expected {len(metrics)} ALL rows, got {len(all_rows)}"
        # Each metric should have exactly one ALL row
        all_metrics = {row[1] for row in all_rows}
        assert all_metrics == metrics, "Mismatch between ALL metrics and provider metrics"

    def test_execute_std_non_negative_counts(self):
        """Ensure all numeric values in the STD report are non-negative."""
        spec = self.create_spec(library_params='{"report_type": "STD"}')
        result = execute_report(spec)
        table = result.content
        # Skip the first two columns (provider, metric), check the rest
        for row in table.data:
            for val in row[2:]:  # partners, clus, reac, cohort, total
                assert val >= 0, f"Negative value found in row {row}"

    def test_execute_std_metric_labels(self):
        """Verify that the metric labels for STD are correct and appear in expected order."""
        spec = self.create_spec(library_params='{"report_type": "STD"}')
        result = execute_report(spec)
        table = result.content
        # Expected metric labels in order (from the library definition)
        expected_std_labels = [
            "Assigned:",
            "Dispositioned:",
            "Exam'd:",
            "Exam'd w/in 3:",
            "Exam'd w/in 5:",
            "Exam'd w/in 7:",
            "Exam'd w/in 14:",
            "Dispo A:",
            "Dispo B:",
            "Dispo C:",
            "Dispo D:",
            "Dispo F:",
            "Dispo E:",
            "Not Examined:",
            "Dispo G:",
            "Dispo H:",
            "Dispo J:",
            "Dispo K:",
            "Dispo L:",
            "Dispo V:",
            "Dispo X:",
            "Dispo Z:",
            "Open:",
            "Non-assigned Dispos:"
        ]
        # Get all unique metrics from the table (excluding provider column)
        metrics = set(row[1] for row in table.data)
        # Check that expected labels are present (may not have data for all, but labels should exist)
        # At minimum, check that the labels appear in the table (some may have zero count)
        for label in expected_std_labels:
            assert label in metrics, f"Expected label '{label}' not found in STD report"

    def test_execute_hiv_structure_and_consistency(self):
        """Test HIV report structure, consistency, and HIV-specific disposition labels."""
        spec = self.create_spec(library_params='{"report_type": "HIV"}')
        result = execute_report(spec)
        assert result.content_type == 'table'
        table = result.content
        assert table.columns == ['provider', 'metric', 'partners', 'clus', 'reac', 'cohort', 'total']

        # Check consistency like STD
        for row in table.data:
            provider, metric, partners, clus, reac, cohort, total = row
            assert total == partners + clus + reac + cohort

        # Verify HIV-specific labels
        expected_hiv_labels = [
            "Assigned:",
            "Dispositioned:",
            "Exam'd:",
            "Exam'd w/in 3:",
            "Exam'd w/in 5:",
            "Exam'd w/in 7:",
            "Exam'd w/in 14:",
            "Dispo 2:",
            "Dispo 3:",
            "Dispo 4:",
            "Dispo 5:",
            "Dispo 6:",
            "Dispo 7:",
            "Not Examined:",
            "Dispo G:",
            "Dispo H:",
            "Dispo J:",
            "Dispo K:",
            "Dispo L:",
            "Dispo V:",
            "Dispo X:",
            "Dispo Z:",
            "Open:",
            "Non-assigned Dispos:"
        ]
        metrics = set(row[1] for row in table.data)
        for label in expected_hiv_labels:
            assert label in metrics, f"Expected label '{label}' not found in HIV report"

    def test_execute_provider_count(self):
        """Verify that the number of providers in the report matches distinct providers in the data."""
        # We can query the database to get distinct INVESTIGATOR_FL_FUP_KEY (excluding 1) and PROVIDER_QUICK_CODE
        # But that requires a separate database connection. Instead, we can test that
        # the report contains at least one provider and that the ALL row is correct.
        # We'll also check that each provider has rows for all metrics.
        spec = self.create_spec(library_params='{"report_type": "STD"}')
        result = execute_report(spec)
        table = result.content
        # Get distinct providers (excluding ALL)
        providers = {row[0] for row in table.data if row[0] != 'ALL'}
        assert len(providers) > 0, "No providers found in report"

        # Check that for each provider, there is a row for each metric
        # We can get the expected number of metrics from the table
        all_metrics = {row[1] for row in table.data if row[0] != 'ALL'}
        # For each provider, count rows
        for provider in providers:
            provider_rows = [row for row in table.data if row[0] == provider]
            # Should have one row per metric
            assert len(provider_rows) == len(all_metrics), f"Provider {provider} has {len(provider_rows)} rows, expected {len(all_metrics)}"

    def test_execute_non_assigned_dispositions_present(self):
        """Ensure the 'Non-assigned Dispos:' metric exists and is non-negative."""
        spec = self.create_spec(library_params='{"report_type": "STD"}')
        result = execute_report(spec)
        table = result.content
        non_assigned_rows = [row for row in table.data if row[1] == "Non-assigned Dispos:"]
        assert len(non_assigned_rows) > 0, "Non-assigned Dispos: metric not found"
        for row in non_assigned_rows:
            assert row[6] >= 0, "Non-assigned Dispos: total is negative"

    def test_execute_hiv_disposition_labels_only_hiv(self):
        """Check that HIV report does not contain STD-specific disposition labels."""
        spec = self.create_spec(library_params='{"report_type": "HIV"}')
        result = execute_report(spec)
        table = result.content
        metrics = {row[1] for row in table.data}
        # STD-specific labels
        std_specific = {"Dispo A:", "Dispo B:", "Dispo C:", "Dispo D:", "Dispo F:", "Dispo E:"}
        for label in std_specific:
            assert label not in metrics, f"STD label '{label}' found in HIV report"

    def test_execute_std_disposition_labels_only_std(self):
        """Check that STD report does not contain HIV-specific disposition labels."""
        spec = self.create_spec(library_params='{"report_type": "STD"}')
        result = execute_report(spec)
        table = result.content
        metrics = {row[1] for row in table.data}
        hiv_specific = {"Dispo 2:", "Dispo 3:", "Dispo 4:", "Dispo 5:", "Dispo 6:", "Dispo 7:"}
        for label in hiv_specific:
            assert label not in metrics, f"HIV label '{label}' found in STD report"
