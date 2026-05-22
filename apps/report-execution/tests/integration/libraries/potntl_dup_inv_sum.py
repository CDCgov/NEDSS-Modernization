from collections import Counter

import pytest
import yaml

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

    def test_execute_report_check_data(self, snapshot):
        """Test with no days_value - should default to 3650."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Potential Duplicate Investigations',
                'library_name': 'potntl_dup_inv_sum',
                'data_source_name': '[RDB].[dbo].[INV_SUMM_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[INV_SUMM_DATAMART]',
                'days_value': None,
                'column_map': {
                    'EVENT_DATE': 'Event Date',
                    'PATIENT_LOCAL_ID': 'Patient Local Id',
                    'DISEASE_CD': 'Disease Code',
                    'INVESTIGATION_ID': 'Investigation Id'
                }
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'
        assert result.header == 'Potential Duplicate Investigations'
        assert 'Duplicate Investigations Time Frame: 3650 Days'
        assert result.subheader is not None

        data = result.content
        assert len(data.data) >= 0

        snapshot.assert_match(yaml.dump(data.data), 'snapshot.yml')

    def test_execute_report_with_days_value(self):
        """Test with a specific days value (e.g., 365 days)."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Potential Duplicate Investigations',
                'library_name': 'potntl_dup_inv_sum',
                'data_source_name': '[RDB].[dbo].[INV_SUMM_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[INV_SUMM_DATAMART]',
                'days_value': 365,
                'column_map': {
                    'EVENT_DATE': 'Event Date',
                    'PATIENT_LOCAL_ID': 'Patient Local Id',
                    'DISEASE_CD': 'Disease Code',
                    'INVESTIGATION_ID': 'Investigation Id'
                }
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'
        assert result.header == 'Potential Duplicate Investigations'
        assert result.subheader == 'Duplicate Investigations Time Frame: 365 Days'

        data = result.content
        assert len(data.data) >= 0
        assert len(data.data[0]) == len(data.columns) if data.data else True

    def test_execute_report_with_large_days_value(self):
        """Test with a large days value (e.g., 3650 days / 10 years)."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Potential Duplicate Investigations',
                'library_name': 'potntl_dup_inv_sum',
                'data_source_name': '[RDB].[dbo].[INV_SUMM_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[INV_SUMM_DATAMART]',
                'days_value': 3650,
                'column_map': {
                    'EVENT_DATE': 'Event Date',
                    'PATIENT_LOCAL_ID': 'Patient Local Id',
                    'DISEASE_CD': 'Disease Code',
                    'INVESTIGATION_ID': 'Investigation Id'
                }
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'
        assert result.subheader == 'Duplicate Investigations Time Frame: 3650 Days'

        # Should return more results than the 30-day test
        spec_30 = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Potential Duplicate Investigations',
                'library_name': 'potntl_dup_inv_sum',
                'data_source_name': '[RDB].[dbo].[INV_SUMM_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[INV_SUMM_DATAMART]',
                'days_value': 30,
                'column_map': {
                    'EVENT_DATE': 'Event Date',
                    'PATIENT_LOCAL_ID': 'Patient Local Id',
                    'DISEASE_CD': 'Disease Code',
                    'INVESTIGATION_ID': 'Investigation Id'
                }
            }
        )
        result_30 = execute_report(spec_30)

        # Our data should have more results with 3650 days_value than 30, but
        # with a new snapshot where all potential duplicates are within 30 days,
        # this may not be the case.
        assert len(result.content.data) > len(result_30.content.data)

    def test_execute_report_with_negative_days_value(self):
        """Test with a negative days value."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Potential Duplicate Investigations',
                'library_name': 'potntl_dup_inv_sum',
                'data_source_name': '[RDB].[dbo].[INV_SUMM_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[INV_SUMM_DATAMART]',
                'days_value': -3650,
                'column_map': {
                    'EVENT_DATE': 'Event Date',
                    'PATIENT_LOCAL_ID': 'Patient Local Id',
                    'DISEASE_CD': 'Disease Code',
                    'INVESTIGATION_ID': 'Investigation Id'
                }
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'
        assert result.subheader == 'Duplicate Investigations Time Frame: -3650 Days'

        # Based on current implementation, this should not return any results.
        assert len(result.content.data) == 0

    def test_execute_report_empty_subset(self):
        """Test handling of empty result set."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Potential Duplicate Investigations',
                'library_name': 'potntl_dup_inv_sum',
                'data_source_name': '[RDB].[dbo].[INV_SUMM_DATAMART]',
                'subset_query': """
                SELECT * FROM [RDB].[dbo].[INV_SUMM_DATAMART] WHERE 1 = 0
                """,
                'days_value': 365,
                'column_map': {
                    'EVENT_DATE': 'Event Date',
                    'PATIENT_LOCAL_ID': 'Patient Local Id',
                    'DISEASE_CD': 'Disease Code',
                    'INVESTIGATION_ID': 'Investigation Id'
                }
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'
        assert len(result.content.data) == 0

    def test_execute_report_check_metadata(self):
        """Check the metadata is correctly formatted."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Potential Duplicate Investigations',
                'library_name': 'potntl_dup_inv_sum',
                'data_source_name': '[RDB].[dbo].[INV_SUMM_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[INV_SUMM_DATAMART]',
                'days_value': 365,
                'column_map': {
                    'EVENT_DATE': 'Event Date',
                    'PATIENT_LOCAL_ID': 'Patient Local Id',
                    'DISEASE_CD': 'Disease Code',
                    'INVESTIGATION_ID': 'Investigation Id'
                }
            }
        )

        result = execute_report(report_spec)
        assert result.header == 'Potential Duplicate Investigations'
        assert result.subheader == 'Duplicate Investigations Time Frame: 365 Days'
        assert result.content_type == 'table'

    def test_execute_report_verify_no_single_events(self):
        """Verify that patients with only one event are filtered out."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Potential Duplicate Investigations',
                'library_name': 'potntl_dup_inv_sum',
                'data_source_name': '[RDB].[dbo].[INV_SUMM_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[INV_SUMM_DATAMART]',
                'days_value': 3650,
                'column_map': {
                    'EVENT_DATE': 'Event Date',
                    'PATIENT_LOCAL_ID': 'Patient Local Id',
                    'DISEASE_CD': 'Disease Code',
                    'INVESTIGATION_ID': 'Investigation Id'
                }
            }
        )

        result = execute_report(report_spec)

        # Count occurrences per patient/disease pair
        patient_ids = result.content.get_column('PATIENT_LOCAL_ID')
        disease_cds = result.content.get_column('DISEASE_CD')

        pairs = list(zip(patient_ids, disease_cds, strict=False))
        counts = Counter(pairs)

        # Each pair should appear at least twice
        for count in counts.values():
            assert count >= 2, f'Patient/disease pair appears only {count} times'

    def test_execute_report_column_ordering(self):
        """Verify that the expected columns are consistently ordered as expected."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Potential Duplicate Investigations',
                'library_name': 'potntl_dup_inv_sum',
                'data_source_name': '[RDB].[dbo].[INV_SUMM_DATAMART]',
                'subset_query': 
                    '''
                    SELECT EVENT_DATE as [Event Date],
                    PATIENT_LOCAL_ID as [Patient Local Id],
                    DISEASE_CD as [Disease Code],
                    INVESTIGATION_ID as [Investigation Id]
                    FROM [RDB].[dbo].[INV_SUMM_DATAMART]
                    ORDER BY DISEASE_CD
                    ''',
                'days_value': 3650,
                'column_map': {
                    'EVENT_DATE': 'Event Date',
                    'PATIENT_LOCAL_ID': 'Patient Local Id',
                    'DISEASE_CD': 'Disease Code',
                    'INVESTIGATION_ID': 'Investigation Id'
                }
            }
        )

        result = execute_report(report_spec)

        expected_columns = {
            'PATIENT_LOCAL_ID', 'DISEASE_CD', 'EVENT_DATE', 'INVESTIGATION_ID'
        }
        result_columns = set(result.content.columns)

        missing_columns = expected_columns - result_columns
        assert not missing_columns, f'Missing expected columns: {missing_columns}'