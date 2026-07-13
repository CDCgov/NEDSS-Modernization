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

    def test_execute_std_structure_and_columns(self):
        """Test that the STD report has correct columns and structure."""
        spec = self.create_spec(library_params='{"report_type": "STD"}')
        result = execute_report(spec)
        assert result.content_type == 'table'
        table = result.content
        
        # Check columns match
        expected_columns = ['PROVIDER_QUICK_CODE_new', 'colname', 'colval', 'colval2', 'colval3', 'colval4', 'pname_l', 'i']
        assert table.columns == expected_columns
        
        # Check there is data
        assert len(table.data) > 0
        
        # Check i column is always 5
        for row in table.data:
            assert row[7] == 5  # i column
        
        # Check pname_l is lowercase of PROVIDER_QUICK_CODE_new
        for row in table.data:
            assert row[6] == row[0].lower()  # pname_l == lowercase(provider)

    def test_execute_std_metric_labels_sas_order(self):
        """Verify metric labels for STD."""
        spec = self.create_spec(library_params='{"report_type": "STD"}')
        result = execute_report(spec)
        table = result.content
        
        # Expected STD metric labels
        expected_labels = [
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
            "Not Examined:",
            "Dispo G:",
            "Dispo H:",
            "Dispo J:",
            "Dispo K:",
            "Dispo L:",
            "Dispo V:",
            "Dispo X:",
            "Dispo Z:",
            "Dispo E:",
            "Open:",
            "Non-assigned Dispos:"
        ]
        
        # Get all unique metrics from the table
        metrics = {row[1] for row in table.data}  # colname is at index 1
        
        # Check all expected labels are present
        for label in expected_labels:
            assert label in metrics, f"Expected label '{label}' not found in STD report"
        
        # Check no extra labels
        assert len(metrics) == len(expected_labels), "STD report has extra metrics"

    def test_execute_hiv_metric_labels_sas_order(self):
        """Verify metric labels for HIV."""
        spec = self.create_spec(library_params='{"report_type": "HIV"}')
        result = execute_report(spec)
        table = result.content
        
        # Expected HIV metric labels
        expected_labels = [
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
        
        metrics = {row[1] for row in table.data}
        for label in expected_labels:
            assert label in metrics, f"Expected label '{label}' not found in HIV report"
        
        assert len(metrics) == len(expected_labels), "HIV report has extra metrics"

    def test_execute_std_no_hiv_labels(self):
        """Check that STD report does not contain HIV-specific labels."""
        spec = self.create_spec(library_params='{"report_type": "STD"}')
        result = execute_report(spec)
        table = result.content
        metrics = {row[1] for row in table.data}
        
        hiv_specific = {"Dispo 2:", "Dispo 3:", "Dispo 4:", "Dispo 5:", "Dispo 6:", "Dispo 7:"}
        for label in hiv_specific:
            assert label not in metrics, f"HIV label '{label}' found in STD report"

    def test_execute_hiv_no_std_labels(self):
        """Check that HIV report does not contain STD-specific labels."""
        spec = self.create_spec(library_params='{"report_type": "HIV"}')
        result = execute_report(spec)
        table = result.content
        metrics = {row[1] for row in table.data}
        
        std_specific = {"Dispo A:", "Dispo B:", "Dispo C:", "Dispo D:", "Dispo F:", "Dispo E:"}
        for label in std_specific:
            assert label not in metrics, f"STD label '{label}' found in HIV report"

    def test_execute_std_row_level_consistency(self):
        """Test row-level consistency for STD report."""
        spec = self.create_spec(library_params='{"report_type": "STD"}')
        result = execute_report(spec)
        table = result.content
        
        # For each row, verify the pattern matches SAS
        for row in table.data:
            provider, colname, colval, colval2, colval3, colval4, pname_l, i = row
            
            # colval, colval2, colval3, colval4 should be non-negative integers
            assert colval >= 0
            assert colval2 >= 0
            assert colval3 >= 0
            assert colval4 >= 0

    def test_execute_std_sort_order(self):
        """Test that rows are sorted by pname_l then metric order."""
        spec = self.create_spec(library_params='{"report_type": "STD"}')
        result = execute_report(spec)
        table = result.content
        
        # Expected metric order for STD
        expected_metric_order = [
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
            "Not Examined:",
            "Dispo G:",
            "Dispo H:",
            "Dispo J:",
            "Dispo K:",
            "Dispo L:",
            "Dispo V:",
            "Dispo X:",
            "Dispo Z:",
            "Dispo E:",
            "Open:",
            "Non-assigned Dispos:"
        ]
        
        # Check grouping by provider
        current_provider = None
        provider_metrics = []
        
        for row in table.data:
            provider = row[0]  # PROVIDER_QUICK_CODE_new
            metric = row[1]    # colname
            
            if provider != current_provider:
                # Check previous provider's metrics are in correct order
                if current_provider is not None:
                    assert provider_metrics == expected_metric_order, \
                        f"Provider {current_provider} has metrics in wrong order"
                current_provider = provider
                provider_metrics = []
            
            provider_metrics.append(metric)
        
        # Check last provider
        if provider_metrics:
            assert provider_metrics == expected_metric_order, \
                f"Provider {current_provider} has metrics in wrong order"
        
        # Check providers are sorted alphabetically by pname_l
        providers = [row[0] for row in table.data if row[1] == "Assigned:"]  # First metric for each provider
        pnames_lower = [p.lower() if p else "" for p in providers]
        assert pnames_lower == sorted(pnames_lower), "Providers not sorted alphabetically by pname_l"

    def test_execute_std_provider_counts(self):
        """Verify that each provider has all metrics."""
        spec = self.create_spec(library_params='{"report_type": "STD"}')
        result = execute_report(spec)
        table = result.content
        
        # Group rows by provider
        provider_metrics = {}
        for row in table.data:
            provider = row[0]
            metric = row[1]
            if provider not in provider_metrics:
                provider_metrics[provider] = set()
            provider_metrics[provider].add(metric)
        
        # Expected number of metrics per provider
        expected_metric_count = 24  # STD has 24 metrics
        
        # Each provider should have exactly the expected number of metrics
        for provider, metrics in provider_metrics.items():
            assert len(metrics) == expected_metric_count, \
                f"Provider {provider} has {len(metrics)} metrics, expected {expected_metric_count}"

    def test_execute_hiv_structure(self):
        """Test HIV report structure."""
        spec = self.create_spec(library_params='{"report_type": "HIV"}')
        result = execute_report(spec)
        assert result.content_type == 'table'
        table = result.content
        
        # Check columns
        expected_columns = ['PROVIDER_QUICK_CODE_new', 'colname', 'colval', 'colval2', 'colval3', 'colval4', 'pname_l', 'i']
        assert table.columns == expected_columns
        
        # Check i column is always 5
        for row in table.data:
            assert row[7] == 5
        
        # Check pname_l is lowercase
        for row in table.data:
            if row[6] == '':
                assert row[0] is None
            else:
                assert row[6] == row[0].lower()

        
        # HIV should have 24 metrics (without Dispo E)
        expected_metrics = 24  # HIV has 24 metrics
        metrics_per_provider = {}
        for row in table.data:
            provider = row[0]
            if provider not in metrics_per_provider:
                metrics_per_provider[provider] = 0
            metrics_per_provider[provider] += 1
        
        for provider, count in metrics_per_provider.items():
            assert count == expected_metrics, \
                f"Provider {provider} has {count} metrics, expected {expected_metrics}"

    def test_execute_report_check_data(self, snapshot):
        """Snapshot test for STD report."""
        report_spec = self.create_spec()
        result = execute_report(report_spec)
        assert result.content_type == 'table'
        
        # Convert to CSV-like format for snapshot comparison
        data = []
        for row in result.content.data:
            data.append({
                'PROVIDER_QUICK_CODE_new': row[0],
                'colname': row[1],
                'colval': row[2],
                'colval2': row[3],
                'colval3': row[4],
                'colval4': row[5],
                'pname_l': row[6],
                'i': row[7]
            })
        
        snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

    def test_execute_report_no_data(self):
        """Test with no data returns empty table with correct columns."""
        report_spec = self.create_spec()
        report_spec.subset_query = """
        SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART] WHERE 1=0
        """

        result = execute_report(report_spec)
        assert result.content_type == 'table'
        
        # Should return empty data but with correct columns
        expected_columns = ['PROVIDER_QUICK_CODE_new', 'colname', 'colval', 'colval2', 'colval3', 'colval4', 'pname_l', 'i']
        assert result.content.columns == expected_columns
        assert len(result.content.data) == 0

    def test_execute_report_different_hiv_report_type(self):
        """Test HIV report type works."""
        spec_hiv = self.create_spec(library_params='{"report_type": "HIV"}')
        result = execute_report(spec_hiv)
        assert result.content_type == 'table'
        
        # Basic structure checks
        table = result.content
        assert table.columns == ['PROVIDER_QUICK_CODE_new', 'colname', 'colval', 'colval2', 'colval3', 'colval4', 'pname_l', 'i']
        
        # Check that HIV labels are present
        hiv_labels = {"Dispo 2:", "Dispo 3:", "Dispo 4:", "Dispo 5:", "Dispo 6:", "Dispo 7:"}
        metrics = {row[1] for row in table.data}
        assert hiv_labels.issubset(metrics), "HIV-specific labels not found"

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