import datetime

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


    def test_execute_report_with_days_value(self, snapshot):
        """Test with a specific days value (e.g., 365 days)."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Potential Duplicate Investigations',
                'library_name': 'nbs_sr_dup_inv',
                'data_source_name': '[RDB].[dbo].[INV_SUMM_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[INV_SUMM_DATAMART]',
                'days_value': 365,
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'
        assert result.header == 'Potential Duplicate Investigations'
        assert result.subheader == 'Duplicate Investigations Time Frame: 365 Days'

        data = result.content.data
        assert len(data) >= 0
        assert len(data[0]) == len(result.content.columns) if data else True

        snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

        # Verify column structure
        col_index = {col: idx for idx, col in enumerate(result.content.columns)}
        expected_columns = [
            'PATIENT_LOCAL_ID',
            'DISEASE_CD',
            'EVENT_DATE',
            'INVESTIGATION_LOCAL_ID',
            'CASE_STATUS',
            'days_since_prev',
            'days_until_next',
            'event_count'
        ]
        for col in expected_columns:
            assert col in col_index

        # Verify data types and basic sanity
        for row in data:
            # event_count should be > 1 (only duplicates)
            assert row[col_index['event_count']] > 1
            
            # days_since_prev or days_until_next should be <= 365 if not null
            days_prev = row[col_index['days_since_prev']]
            days_next = row[col_index['days_until_next']]
            if days_prev is not None:
                assert days_prev <= 365
            if days_next is not None:
                assert days_next <= 365

    def test_execute_report_no_days_value(self):
        """Test with no days_value (returns all data without date filtering)."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Potential Duplicate Investigations',
                'library_name': 'nbs_sr_dup_inv',
                'data_source_name': '[RDB].[dbo].[INV_SUMM_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[INV_SUMM_DATAMART]',
                # No days_value provided
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'
        assert result.header == 'Potential Duplicate Investigations'
        assert result.subheader is None

        data = result.content.data
        assert len(data) >= 0

        # Verify column structure
        col_index = {col: idx for idx, col in enumerate(result.content.columns)}
        expected_columns = [
            'PATIENT_LOCAL_ID',
            'DISEASE_CD',
            'EVENT_DATE',
            'INVESTIGATION_LOCAL_ID',
            'CASE_STATUS',
            'days_since_prev',
            'days_until_next',
            'event_count'
        ]
        for col in expected_columns:
            assert col in col_index

        # event_count should be > 1 for all rows (only duplicates)
        for row in data:
            assert row[col_index['event_count']] > 1

    def test_execute_report_with_small_days_value(self):
        """Test with a small days value (e.g., 30 days) to find close duplicates."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Potential Duplicate Investigations',
                'library_name': 'nbs_sr_dup_inv',
                'data_source_name': '[RDB].[dbo].[INV_SUMM_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[INV_SUMM_DATAMART]',
                'days_value': 30,
            }
        )

        result = execute(report_spec)
        assert result.content_type == 'table'
        assert result.subheader == 'Duplicate Investigations Time Frame: 30 Days'

        data = result.content.data
        col_index = {col: idx for idx, col in enumerate(result.content.columns)}

        # All returned rows should have event gaps <= 30 days
        for row in data:
            days_prev = row[col_index['days_since_prev']]
            days_next = row[col_index['days_until_next']]
            if days_prev is not None:
                assert days_prev <= 30
            if days_next is not None:
                assert days_next <= 30

    def test_execute_report_with_large_days_value(self):
        """Test with a large days value (e.g., 3650 days / 10 years)."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Potential Duplicate Investigations',
                'library_name': 'nbs_sr_dup_inv',
                'data_source_name': '[RDB].[dbo].[INV_SUMM_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[INV_SUMM_DATAMART]',
                'days_value': 3650,
            }
        )

        result = execute(report_spec)
        assert result.content_type == 'table'
        assert result.subheader == 'Duplicate Investigations Time Frame: 3650 Days'

        # Should return more results than the 30-day test
        data_30 = None
        spec_30 = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Potential Duplicate Investigations',
                'library_name': 'nbs_sr_dup_inv',
                'data_source_name': '[RDB].[dbo].[INV_SUMM_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[INV_SUMM_DATAMART]',
                'days_value': 30,
            }
        )
        result_30 = execute(spec_30)
        
        # 3650 days should potentially return more or equal rows than 30 days
        assert len(result.content.data) >= len(result_30.content.data)

    def test_execute_report_with_disease_filter(self):
        """Test filtering by specific diseases via subset_query."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Potential Duplicate Investigations',
                'library_name': 'nbs_sr_dup_inv',
                'data_source_name': '[RDB].[dbo].[INV_SUMM_DATAMART]',
                'subset_query': (
                    "SELECT * FROM [RDB].[dbo].[INV_SUMM_DATAMART] "
                    "WHERE DISEASE_CD IN ('10190', '10140')"
                ),
                'days_value': 365,
            }
        )

        result = execute(report_spec)
        assert result.content_type == 'table'

        # Verify only selected diseases appear
        col_index = {col: idx for idx, col in enumerate(result.content.columns)}
        for row in result.content.data:
            disease_cd = row[col_index['DISEASE_CD']]
            assert disease_cd in ('10190', '10140')

    def test_execute_report_with_patient_filter(self):
        """Test filtering by specific patient via subset_query."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Potential Duplicate Investigations',
                'library_name': 'nbs_sr_dup_inv',
                'data_source_name': '[RDB].[dbo].[INV_SUMM_DATAMART]',
                'subset_query': (
                    "SELECT * FROM [RDB].[dbo].[INV_SUMM_DATAMART] "
                    "WHERE PATIENT_LOCAL_ID = 'PSN10067002GA01'"
                ),
                'days_value': 365,
            }
        )

        result = execute(report_spec)
        assert result.content_type == 'table'

        # Verify only the specified patient appears
        col_index = {col: idx for idx, col in enumerate(result.content.columns)}
        for row in result.content.data:
            assert row[col_index['PATIENT_LOCAL_ID']] == 'PSN10067002GA01'

    def test_execute_report_empty_subset(self):
        """Test handling of empty result set."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Potential Duplicate Investigations',
                'library_name': 'nbs_sr_dup_inv',
                'data_source_name': '[RDB].[dbo].[INV_SUMM_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[INV_SUMM_DATAMART] WHERE 1 = 0',
                'days_value': 365,
            }
        )

        result = execute(report_spec)
        assert result.content_type == 'table'
        assert len(result.content.data) == 0
        assert len(result.content.columns) == 8  # 8 expected columns

    def test_execute_report_check_metadata(self):
        """Check the metadata is correctly formatted."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'Potential Duplicate Investigations',
                'library_name': 'nbs_sr_dup_inv',
                'data_source_name': '[RDB].[dbo].[INV_SUMM_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[INV_SUMM_DATAMART]',
                'days_value': 365,
            }
        )

        result = execute(report_spec)
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
                'library_name': 'nbs_sr_dup_inv',
                'data_source_name': '[RDB].[dbo].[INV_SUMM_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[INV_SUMM_DATAMART]',
                'days_value': 3650,
            }
        )

        result = execute(report_spec)
        col_index = {col: idx for idx, col in enumerate(result.content.columns)}
        
        # Group by patient and disease to verify event_count > 1
        patient_disease_counts = {}
        for row in result.content.data:
            key = (row[col_index['PATIENT_LOCAL_ID']], row[col_index['DISEASE_CD']])
            patient_disease_counts[key] = patient_disease_counts.get(key, 0) + 1
        
        # Each patient/disease pair should have at least 2 events
        for count in patient_disease_counts.values():
            assert count >= 2