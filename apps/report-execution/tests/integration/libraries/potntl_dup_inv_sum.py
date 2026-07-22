from collections import Counter

import pytest
import yaml

from src.errors import MissingColumnError
from src.execute_report import execute_report
from src.models import ReportSpec

db_table = '[RDB].[dbo].[INV_SUMM_DATAMART]'
db_fk_tables = []
faker_schema = 'inv_summ_datamart.yaml'


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationNbsSrDupInvLibrary:
    """Integration tests for the Potential Duplicate Investigations library.

    This report identifies potential duplicate investigations for the same patient
    with the same disease within a user-specified number of days.
    """

    # Class property for default column map
    default_column_map = [
        ['EVENT_DATE', 'Event Date'],
        ['PATIENT_LOCAL_ID', 'Patient Local Id'],
        ['DISEASE_CD', 'Disease Code'],
        ['INVESTIGATION_LOCAL_ID', 'Investigation Local Id'],
    ]

    # Helper to generate common spec base
    def create_spec(self, **overrides):
        """Create a report specification with defaults from the class properties."""
        base = {
            'version': 1,
            'is_export': True,
            'is_builtin': True,
            'library_name': 'potntl_dup_inv_sum',
            'subset_query': '',
            'days_value': None,
            'column_map': self.default_column_map,
        }
        base.update(overrides)
        if base['subset_query'] == '':
            base['subset_query'] = f"""
            SELECT {
                ', '.join(f'{item[0]} as [{item[1]}]' for item in base['column_map'])
            }
            FROM [RDB].[dbo].[INV_SUMM_DATAMART]
            """
        return ReportSpec.model_validate(base)

    def test_execute_report_check_data(self, snapshot):
        """Test with no days_value - should default to 3650."""
        report_spec = self.create_spec()

        result = execute_report(report_spec)
        assert (
            result.context_header is not None
            and 'Duplicate Investigations Time Frame: 3650 Days'
            in result.context_header
        )
        assert result.context_header is not None

        data = result.content
        assert len(data.data) >= 0
        snapshot.assert_match(yaml.dump(data.data), 'snapshot.yml')

    def test_execute_report_check_data_with_required_string_col_order_by(
        self, snapshot
    ):
        """Test with sort by of a string required column."""
        report_spec = self.create_spec(sort_by='UPPER([Disease Code]) ASC')

        result = execute_report(report_spec)

        data = result.content.data
        assert len(data) >= 0
        for i in range(1, len(data)):
            prior = data[i - 1][2]
            cur = data[i][2]
            assert (prior is None and cur is None) or (prior is None) or prior <= cur

        snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

    def test_execute_report_check_data_with_non_required_string_col_order_by(
        self, snapshot
    ):
        """Test with sort by of a string non-required column."""
        report_spec = self.create_spec(sort_by='UPPER([Investigation Local Id]) ASC')

        result = execute_report(report_spec)

        data = result.content.data
        assert len(data) >= 0
        for i in range(1, len(data)):
            prior = data[i - 1][3]
            cur = data[i][3]
            assert (prior is None and cur is None) or (prior is None) or prior <= cur

        snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

    def test_execute_report_check_data_with_required_non_string_col_order_by(
        self, snapshot
    ):
        """Test with sort by of a non-string required column."""
        report_spec = self.create_spec(sort_by='[Event Date] ASC')

        result = execute_report(report_spec)

        data = result.content.data
        assert len(data) >= 0
        for i in range(1, len(data)):
            prior = data[i - 1][0]
            cur = data[i][0]
            assert (prior is None and cur is None) or (prior is None) or prior <= cur

        snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

    def test_execute_report_with_days_value(self):
        """Test with a specific days value (e.g., 365 days)."""
        report_spec = self.create_spec(days_value=365)

        result = execute_report(report_spec)
        assert result.context_header == 'Duplicate Investigations Time Frame: 365 Days'

        data = result.content
        assert len(data.data) >= 0
        assert len(data.data[0]) == len(data.columns) if data.data else True

    def test_execute_report_with_large_days_value(self):
        """Test with a large days value (e.g., 3650 days / 10 years)."""
        report_spec = self.create_spec(days_value=3650)

        result = execute_report(report_spec)
        assert result.context_header == 'Duplicate Investigations Time Frame: 3650 Days'

        # Should return more results than the 30-day test
        spec_30 = self.create_spec(days_value=30)
        result_30 = execute_report(spec_30)

        # Our data should have more results with 3650 days_value than 30, but
        # with a new snapshot where all potential duplicates are within 30 days,
        # this may not be the case.
        assert len(result.content.data) > len(result_30.content.data)

    def test_execute_report_with_negative_days_value(self):
        """Test with a negative days value."""
        report_spec = self.create_spec(days_value=-3650)

        result = execute_report(report_spec)
        assert (
            result.context_header == 'Duplicate Investigations Time Frame: -3650 Days'
        )

        # Based on current implementation, this should not return any results.
        assert len(result.content.data) == 0

    def test_execute_report_empty_subset(self):
        """Test handling of empty result set."""
        # Take the base subset_query and add WHERE 1=0 to force empty results
        report_spec = self.create_spec(days_value=365)
        report_spec.subset_query += ' WHERE 1 = 0'

        result = execute_report(report_spec)
        assert len(result.content.data) == 0

    def test_execute_report_check_metadata(self):
        """Check the metadata is correctly formatted."""
        report_spec = self.create_spec(days_value=365)

        result = execute_report(report_spec)
        assert result.context_header == 'Duplicate Investigations Time Frame: 365 Days'

    def test_execute_report_verify_no_single_events(self):
        """Verify that patients with only one event are filtered out."""
        report_spec = self.create_spec()

        result = execute_report(report_spec)

        # Count occurrences per patient/disease pair
        patient_ids = result.content.get_column('Patient Local Id')
        disease_cds = result.content.get_column('Disease Code')

        pairs = list(zip(patient_ids, disease_cds, strict=False))
        counts = Counter(pairs)

        # Each pair should appear at least twice
        for count in counts.values():
            assert count >= 2, f'Patient/disease pair appears only {count} times'

    def test_execute_report_missing_column_error(self):
        """Verify that an error is raised if required columns are missing from
        column_map.
        """
        report_spec = self.create_spec(
            days_value=365,
            column_map=[
                ['EVENT_DATE', 'Event Date'],
                ['PATIENT_LOCAL_ID', 'Patient Local Id'],
                # Missing DISEASE_CD
            ],
        )

        with pytest.raises(MissingColumnError) as exc_info:
            execute_report(report_spec)

        # Verify the error contains the missing column
        assert 'DISEASE_CD' in str(exc_info.value)

    def test_execute_report_column_ordering(self):
        """Verify that the expected columns are present and ordered as expected."""
        report_spec = self.create_spec()

        result = execute_report(report_spec)

        expected_columns = [
            'Event Date',
            'Patient Local Id',
            'Disease Code',
            'Investigation Local Id',
        ]
        missing_columns = set(expected_columns) - set(result.content.columns)
        assert not missing_columns, f'Missing expected columns: {missing_columns}'
        assert result.content.columns == expected_columns, (
            'Column order does not match expected'
        )
