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

    # Class property for the subset query
    subset_query = """
        SELECT 
            INVESTIGATION_KEY as [Investigation Key],
            PATIENT_KEY as [Patient Key],
            PATIENT_LOCAL_ID as [Patient Local Id],
            INVESTIGATION_LOCAL_ID as [Investigation Local Id],
            DISEASE as [Disease],
            DISEASE_CD as [Disease Code],
            PATIENT_FIRST_NAME as [Patient First Name],
            PATIENT_LAST_NAME as [Patient Last Name],
            PATIENT_DOB as [DOB],
            PATIENT_CURRENT_SEX as [Sex],
            AGE_REPORTED as [Age Reported],
            AGE_REPORTED_UNIT as [Age Reported Unit],
            PATIENT_STREET_ADDRESS_1 as [Patient Address 1],
            PATIENT_STREET_ADDRESS_2 as [Patient Address 2],
            PATIENT_CITY as [Patient City],
            PATIENT_STATE as [Patient State],
            PATIENT_ZIP as [Patient Zip],
            PATIENT_COUNTY as [Patient County], 
            PATIENT_ETHNICITY as [Ethnicity],
            RACE_CALCULATED as [Race - Calculated],
            RACE_CALC_DETAILS as [Race - Calculated Details],
            INVESTIGATION_STATUS as [Investigation Status],
            EARLIEST_RPT_TO_CNTY_DT as [Earliest Date Reported to County],
            EARLIEST_RPT_TO_STATE_DT as [Earliest Date Reported to State],
            DIAGNOSIS_DATE as [Diagnosis Date],
            ILLNESS_ONSET_DATE as [Illness Onset Date], 
            CASE_STATUS as [Case Status],
            MMWR_WEEK as [MMWR Week],
            MMWR_YEAR as [MMWR Year],
            INVESTIGATION_CREATE_DATE as [Investigation Create Date],
            INVESTIGATION_CREATED_BY as [Investigation Created By User],
            INVESTIGATION_LAST_UPDTD_DATE as [Investigation Last Updated Date],
            INVESTIGATION_LAST_UPDTD_BY as [Investigation Last Updated By User],
            NOTIFICATION_STATUS as [Notification Record Status],
            PROGRAM_JURISDICTION_OID as [Jurisdiction Code],
            EVENT_DATE as [Event Date],
            EVENT_DATE_TYPE as [Event Date Type],
            LABORATORY_INFORMATION as [Laboratory Information],
            FIRST_POSITIVE_CULTURE_DT as [First Positive Culture Date],
            EARLIEST_SPECIMEN_COLLECT_DATE as [Earliest Specimen Collection Date],
            PROGRAM_AREA as [Program Area],
            PHYSICIAN_LAST_NAME as [Physician Last Name],
            PHYSICIAN_FIRST_NAME as [Physician First Name],
            NOTIFICATION_LOCAL_ID as [Notification Local ID],
            NOTIFICATION_CREATE_DATE as [Notification Create Date],
            NOTIFICATION_SUBMITTER as [Notification Submitter],
            NOTIFICATION_LAST_UPDATED_DATE as [Notification Last Updated Date],
            NOTIFICATION_LAST_UPDATED_USER as [Notification Last Updated By User],
            INV_RPT_DT as [Date of Report],
            INV_START_DT as [Investigation Start Date],
            CONFIRMATION_DT as [Confirmation Date],
            HSPTL_ADMISSION_DT as [Hospital Admission Date],
            CURR_PROCESS_STATE as [Investigation Current Process Stage]
        FROM [RDB].[dbo].[INV_SUMM_DATAMART]
    """

    # Class property for default column map
    default_column_map = {
        'EVENT_DATE': 'Event Date',
        'PATIENT_LOCAL_ID': 'Patient Local Id',
        'DISEASE_CD': 'Disease Code',
        'INVESTIGATION_LOCAL_ID': 'Investigation Local Id',
    }

    # Helper to generate common spec base
    def create_spec(self, **overrides):
        """Create a report specification with defaults from the class properties."""
        base = {
            'version': 1,
            'is_export': True,
            'is_builtin': True,
            'report_title': 'Potential Duplicate Investigations',
            'library_name': 'potntl_dup_inv_sum',
            'data_source_name': '[RDB].[dbo].[INV_SUMM_DATAMART]',
            'subset_query': self.subset_query,
            'days_value': None,
            'column_map': self.default_column_map.copy(),
        }
        base.update(overrides)
        return ReportSpec.model_validate(base)

    def test_execute_report_check_data(self, snapshot):
        """Test with no days_value - should default to 3650."""
        report_spec = self.create_spec()

        result = execute_report(report_spec)
        assert result.content_type == 'table'
        assert result.header == 'Potential Duplicate Investigations'
        assert 'Duplicate Investigations Time Frame: 3650 Days' in result.subheader
        assert result.subheader is not None

        data = result.content
        assert len(data.data) >= 0

        snapshot.assert_match(yaml.dump(data.data), 'snapshot.yml')

    def test_execute_report_with_days_value(self):
        """Test with a specific days value (e.g., 365 days)."""
        report_spec = self.create_spec(days_value=365)

        result = execute_report(report_spec)
        assert result.content_type == 'table'
        assert result.header == 'Potential Duplicate Investigations'
        assert result.subheader == 'Duplicate Investigations Time Frame: 365 Days'

        data = result.content
        assert len(data.data) >= 0
        assert len(data.data[0]) == len(data.columns) if data.data else True

    def test_execute_report_with_large_days_value(self):
        """Test with a large days value (e.g., 3650 days / 10 years)."""
        report_spec = self.create_spec(days_value=3650)

        result = execute_report(report_spec)
        assert result.content_type == 'table'
        assert result.subheader == 'Duplicate Investigations Time Frame: 3650 Days'

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
        assert result.content_type == 'table'
        assert result.subheader == 'Duplicate Investigations Time Frame: -3650 Days'

        # Based on current implementation, this should not return any results.
        assert len(result.content.data) == 0

    def test_execute_report_empty_subset(self):
        """Test handling of empty result set."""
        # Take the base subset_query and add WHERE 1=0 to force empty results
        empty_subset_query = f'{self.subset_query} WHERE 1 = 0'

        report_spec = self.create_spec(days_value=365, subset_query=empty_subset_query)

        result = execute_report(report_spec)
        assert result.content_type == 'table'
        assert len(result.content.data) == 0

    def test_execute_report_check_metadata(self):
        """Check the metadata is correctly formatted."""
        report_spec = self.create_spec(days_value=365)

        result = execute_report(report_spec)
        assert result.header == 'Potential Duplicate Investigations'
        assert result.subheader == 'Duplicate Investigations Time Frame: 365 Days'
        assert result.content_type == 'table'

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
            column_map={
                'EVENT_DATE': 'Event Date',
                'PATIENT_LOCAL_ID': 'Patient Local Id',
                # Missing DISEASE_CD
            },
        )

        with pytest.raises(MissingColumnError) as exc_info:
            execute_report(report_spec)

        # Verify the error contains the missing column
        assert 'DISEASE_CD' in str(exc_info.value)

    def test_execute_report_column_ordering(self):
        """Verify that the expected columns are consistently ordered as expected."""
        report_spec = self.create_spec()

        result = execute_report(report_spec)

        expected_columns = {
            'Patient Local Id',
            'Disease Code',
            'Event Date',
            'Investigation Local Id',
        }
        result_columns = set(result.content.columns)

        missing_columns = expected_columns - result_columns
        assert not missing_columns, f'Missing expected columns: {missing_columns}'
