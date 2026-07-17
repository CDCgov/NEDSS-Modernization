import pytest
import yaml

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

    @staticmethod
    def reconstruct_colname(row):
        """Helper to rebuild the original colname from Category 1 and Category 2."""
        cat1, cat2 = row[1], row[2]  # Category 1 and Category 2 columns
        if cat2:
            return cat2  # submetrics already include the full label
        # Main headers
        mapping = {
            'Assigned': 'Assigned:',
            'Dispositioned': 'Dispositioned:',
            "Exam'd": "Exam'd:",
            'Not Examined': 'Not Examined:',
            'Open': 'Open:',
            'Non-Assigned': 'Non-assigned Dispos:',
        }
        return mapping.get(cat1, cat1 + ':')  # fallback

    def test_execute_std_structure_and_columns(self):
        """Test that the STD report has correct columns and structure."""
        spec = self.create_spec(library_params='{"report_type": "STD"}')
        result = execute_report(spec)
        assert result.content_type == 'table'
        table = result.content

        expected_columns = [
            'Worker',
            'Category 1',
            'Category 2',
            'Part.',
            'Clus.',
            'Reac.',
            'Other',
            'Total',
        ]
        assert table.columns == expected_columns

        # Check there is data
        assert len(table.data) > 0

        # Check Total is sum of the four group columns
        for row in table.data:
            total = row[3] + row[4] + row[5] + row[6]  # Part. + Clus. + Reac. + Other
            assert row[7] == total

    def test_execute_std_metric_labels_sas_order(self):
        """Verify metric labels for STD."""
        spec = self.create_spec(library_params='{"report_type": "STD"}')
        result = execute_report(spec)
        table = result.content

        expected_labels = [
            'Assigned:',
            'Dispositioned:',
            "Exam'd:",
            "Exam'd w/in 3:",
            "Exam'd w/in 5:",
            "Exam'd w/in 7:",
            "Exam'd w/in 14:",
            'Dispo A:',
            'Dispo B:',
            'Dispo C:',
            'Dispo D:',
            'Dispo F:',
            'Not Examined:',
            'Dispo G:',
            'Dispo H:',
            'Dispo J:',
            'Dispo K:',
            'Dispo L:',
            'Dispo V:',
            'Dispo X:',
            'Dispo Z:',
            'Dispo E:',
            'Open:',
            'Non-assigned Dispos:',
        ]

        metrics = {self.reconstruct_colname(row) for row in table.data}
        for label in expected_labels:
            assert label in metrics, f"Expected label '{label}' not found in STD report"

        assert len(metrics) == len(expected_labels), 'STD report has extra metrics'

    def test_execute_hiv_metric_labels_sas_order(self):
        """Verify metric labels for HIV."""
        spec = self.create_spec(library_params='{"report_type": "HIV"}')
        result = execute_report(spec)
        table = result.content

        expected_labels = [
            'Assigned:',
            'Dispositioned:',
            "Exam'd:",
            "Exam'd w/in 3:",
            "Exam'd w/in 5:",
            "Exam'd w/in 7:",
            "Exam'd w/in 14:",
            'Dispo 2:',
            'Dispo 3:',
            'Dispo 4:',
            'Dispo 5:',
            'Dispo 6:',
            'Dispo 7:',
            'Not Examined:',
            'Dispo G:',
            'Dispo H:',
            'Dispo J:',
            'Dispo K:',
            'Dispo L:',
            'Dispo V:',
            'Dispo X:',
            'Dispo Z:',
            'Open:',
            'Non-assigned Dispos:',
        ]

        metrics = {self.reconstruct_colname(row) for row in table.data}
        for label in expected_labels:
            assert label in metrics, f"Expected label '{label}' not found in HIV report"

        assert len(metrics) == len(expected_labels), 'HIV report has extra metrics'

    def test_execute_std_no_hiv_labels(self):
        """Check that STD report does not contain HIV-specific labels."""
        spec = self.create_spec(library_params='{"report_type": "STD"}')
        result = execute_report(spec)
        table = result.content
        metrics = {self.reconstruct_colname(row) for row in table.data}

        hiv_specific = {
            'Dispo 2:',
            'Dispo 3:',
            'Dispo 4:',
            'Dispo 5:',
            'Dispo 6:',
            'Dispo 7:',
        }
        for label in hiv_specific:
            assert label not in metrics, f"HIV label '{label}' found in STD report"

    def test_execute_hiv_no_std_labels(self):
        """Check that HIV report does not contain STD-specific labels."""
        spec = self.create_spec(library_params='{"report_type": "HIV"}')
        result = execute_report(spec)
        table = result.content
        metrics = {self.reconstruct_colname(row) for row in table.data}

        std_specific = {
            'Dispo A:',
            'Dispo B:',
            'Dispo C:',
            'Dispo D:',
            'Dispo F:',
            'Dispo E:',
        }
        for label in std_specific:
            assert label not in metrics, f"STD label '{label}' found in HIV report"

    def test_execute_std_row_level_consistency(self):
        """Test row-level consistency for STD report."""
        spec = self.create_spec(library_params='{"report_type": "STD"}')
        result = execute_report(spec)
        table = result.content

        for row in table.data:
            # Part., Clus., Reac., Other should be non-negative integers
            assert row[3] >= 0
            assert row[4] >= 0
            assert row[5] >= 0
            assert row[6] >= 0

    def test_execute_std_sort_order(self):
        """Test that rows are sorted correctly: ALL first, then providers
        alphabetically, and within each provider metrics.
        """
        spec = self.create_spec(library_params='{"report_type": "STD"}')
        result = execute_report(spec)
        table = result.content

        # Expected metric order (colnames)
        expected_metric_order = [
            'Assigned:',
            'Dispositioned:',
            "Exam'd:",
            "Exam'd w/in 3:",
            "Exam'd w/in 5:",
            "Exam'd w/in 7:",
            "Exam'd w/in 14:",
            'Dispo A:',
            'Dispo B:',
            'Dispo C:',
            'Dispo D:',
            'Dispo F:',
            'Not Examined:',
            'Dispo G:',
            'Dispo H:',
            'Dispo J:',
            'Dispo K:',
            'Dispo L:',
            'Dispo V:',
            'Dispo X:',
            'Dispo Z:',
            'Dispo E:',
            'Open:',
            'Non-assigned Dispos:',
        ]

        # Extract rows excluding ALL
        provider_rows = [row for row in table.data if row[0] != 'ALL']

        # Check providers are sorted alphabetically (case-insensitive)
        # Use the first metric for each provider to get the list
        first_metric = 'Assigned:'
        providers = []
        for _i, row in enumerate(provider_rows):
            if self.reconstruct_colname(row) == first_metric:
                providers.append(row[0])
        assert providers == sorted(providers, key=lambda x: x.lower()), (
            'Providers not sorted alphabetically by worker code'
        )

        # Check that for each provider, metrics appear in expected order
        current_provider = None
        provider_metrics = []
        for row in provider_rows:
            worker = row[0]
            colname = self.reconstruct_colname(row)
            if worker != current_provider:
                if current_provider is not None:
                    assert provider_metrics == expected_metric_order, (
                        f'Provider {current_provider} has metrics in wrong order'
                    )
                current_provider = worker
                provider_metrics = []
            provider_metrics.append(colname)

        # Check last provider
        if provider_metrics:
            assert provider_metrics == expected_metric_order

        # Ensure ALL rows come first
        all_rows = [row for row in table.data if row[0] == 'ALL']
        assert len(all_rows) == len(expected_metric_order)
        all_metrics = [self.reconstruct_colname(row) for row in all_rows]
        assert all_metrics == expected_metric_order

    def test_execute_std_provider_counts(self):
        """Verify that each provider has all metrics (including ALL row count)."""
        spec = self.create_spec(library_params='{"report_type": "STD"}')
        result = execute_report(spec)
        table = result.content

        expected_metric_count = 24  # STD has 24 metrics

        # Group rows by worker
        worker_counts = {}
        for row in table.data:
            worker = row[0]
            worker_counts[worker] = worker_counts.get(worker, 0) + 1

        # ALL row should have 24 metrics
        assert worker_counts.get('ALL', 0) == expected_metric_count

        # Each provider should also have 24 metrics
        for worker, count in worker_counts.items():
            if worker != 'ALL':
                assert count == expected_metric_count, f'Provider {worker} has {
                    count
                } metrics, expected {expected_metric_count}'

    def test_execute_hiv_structure(self):
        """Test HIV report structure."""
        spec = self.create_spec(library_params='{"report_type": "HIV"}')
        result = execute_report(spec)
        assert result.content_type == 'table'
        table = result.content

        expected_columns = [
            'Worker',
            'Category 1',
            'Category 2',
            'Part.',
            'Clus.',
            'Reac.',
            'Other',
            'Total',
        ]
        assert table.columns == expected_columns

        # Check Total is sum of the four group columns
        for row in table.data:
            total = row[3] + row[4] + row[5] + row[6]
            assert row[7] == total

        # HIV should have 24 metrics per provider (including ALL)
        expected_metric_count = 24
        worker_counts = {}
        for row in table.data:
            worker = row[0]
            worker_counts[worker] = worker_counts.get(worker, 0) + 1

        assert worker_counts.get('ALL', 0) == expected_metric_count
        for worker, count in worker_counts.items():
            if worker != 'ALL':
                assert count == expected_metric_count, f'Provider {worker} has {
                    count
                } metrics, expected {expected_metric_count}'

    def test_execute_report_check_data_std(self, snapshot):
        """Snapshot test for STD report."""
        report_spec = self.create_spec()
        result = execute_report(report_spec)
        assert result.content_type == 'table'

        # Convert to list of dicts for snapshot
        data = []
        for row in result.content.data:
            data.append(
                {
                    'Worker': row[0],
                    'Category 1': row[1],
                    'Category 2': row[2],
                    'Part.': row[3],
                    'Clus.': row[4],
                    'Reac.': row[5],
                    'Other': row[6],
                    'Total': row[7],
                }
            )

        snapshot.assert_match(yaml.dump(data), 'std_snapshot.yml')

    def test_execute_report_check_data_hiv(self, snapshot):
        """Snapshot test for HIV report."""
        report_spec = self.create_spec(library_params='{"report_type": "HIV"}')
        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = []
        for row in result.content.data:
            data.append(
                {
                    'Worker': row[0],
                    'Category 1': row[1],
                    'Category 2': row[2],
                    'Part.': row[3],
                    'Clus.': row[4],
                    'Reac.': row[5],
                    'Other': row[6],
                    'Total': row[7],
                }
            )

        snapshot.assert_match(yaml.dump(data), 'hiv_snapshot.yml')

    def test_execute_report_no_data(self):
        """Test with no data returns empty table with correct columns."""
        report_spec = self.create_spec()
        report_spec.subset_query = """
        SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART] WHERE 1=0
        """

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        expected_columns = [
            'Worker',
            'Category 1',
            'Category 2',
            'Part.',
            'Clus.',
            'Reac.',
            'Other',
            'Total',
        ]
        assert result.content.columns == expected_columns
        assert len(result.content.data) == 0

    def test_execute_report_different_hiv_report_type(self):
        """Test HIV report type works."""
        spec_hiv = self.create_spec(library_params='{"report_type": "HIV"}')
        result = execute_report(spec_hiv)
        assert result.content_type == 'table'

        table = result.content
        assert table.columns == [
            'Worker',
            'Category 1',
            'Category 2',
            'Part.',
            'Clus.',
            'Reac.',
            'Other',
            'Total',
        ]

        # Check that HIV labels are present
        hiv_labels = {
            'Dispo 2:',
            'Dispo 3:',
            'Dispo 4:',
            'Dispo 5:',
            'Dispo 6:',
            'Dispo 7:',
        }
        metrics = {self.reconstruct_colname(row) for row in table.data}
        assert hiv_labels.issubset(metrics), 'HIV-specific labels not found'

    def test_execute_report_missing_report_type_parameter(self):
        """Test that missing 'report_type' in library_params raises an error."""
        spec = self.create_spec(library_params='{}')
        with pytest.raises(ValueError, match="must contain 'report_type'"):
            execute_report(spec)

    def test_execute_report_invalid_report_type_format(self):
        """Test that invalid report type raises an error."""
        spec = self.create_spec(library_params='{"report_type": "random"}')
        with pytest.raises(ValueError, match='report_type must be'):
            execute_report(spec)
