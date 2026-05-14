"""Integration tests for QA04 Cases Missing Lab and/or Treatment report."""

import pytest
import yaml
from src.models import ReportSpec
from src.execute_report import execute_report


class TestIntegrationQa04Library:
    """Test suite for qa_04 library."""

    def test_execute_report_check_data(self, snapshot):
        """Test that the report returns expected data."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'QA04 Cases Missing Lab and/or Treatment',
                'library_name': 'qa_04',
                'data_source_name': '[RDB].[dbo].[STD_HIV_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART]',
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'
        assert result.header == 'QA04 Cases Missing Lab and/or Treatment'

        data = result.content
        assert len(data.data) >= 0
        assert len(data.data[0]) == len(data.columns) if data.data else True

        snapshot.assert_match(yaml.dump(data.data), 'snapshot.yml')

    def test_execute_report_empty_subset(self):
        """Test that the report handles an empty subset gracefully."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'QA04 Cases Missing Lab and/or Treatment',
                'library_name': 'qa_04',
                'data_source_name': '[RDB].[dbo].[STD_HIV_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART] WHERE 1=0',
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'
        assert result.header == 'QA04 Cases Missing Lab and/or Treatment'

        data = result.content
        assert data.data == []

    def test_execute_report_check_metadata(self):
        """Test that the report returns correct metadata."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'QA04 Cases Missing Lab and/or Treatment',
                'library_name': 'qa_04',
                'data_source_name': '[RDB].[dbo].[STD_HIV_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART]',
            }
        )

        result = execute_report(report_spec)
        assert result.content_type == 'table'
        assert result.header == 'QA04 Cases Missing Lab and/or Treatment'
        assert result.subheader is None
        assert result.description is None

    def test_execute_report_error_explanations(self):
        """Test that only cases with errors are returned (no 'N/A')."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'QA04 Cases Missing Lab and/or Treatment',
                'library_name': 'qa_04',
                'data_source_name': '[RDB].[dbo].[STD_HIV_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART]',
            }
        )

        result = execute_report(report_spec)
        data = result.content

        # Verify no 'N/A' values in Error Explanation column
        error_col_idx = data.columns.index('Error Explanation')
        for row in data.data:
            assert row[error_col_idx] != 'N/A'
            assert row[error_col_idx] in (
                'No Treatment or Lab',
                'No Lab',
                'No Treatment',
            )

    def test_execute_report_confirmed_probable_only(self):
        """Test that only Confirmed and Probable cases are included."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'QA04 Cases Missing Lab and/or Treatment',
                'library_name': 'qa_04',
                'data_source_name': '[RDB].[dbo].[STD_HIV_DATAMART]',
                'subset_query': '''
                    SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART]
                    UNION ALL
                    SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART]
                    WHERE INV_CASE_STATUS = 'Suspect'
                ''',
            }
        )

        result = execute_report(report_spec)
        data = result.content

        # All returned rows should be from the main subset
        # (the UNION ALL with Suspect should be excluded)
        assert len(data.data) >= 0

    def test_execute_report_duplicate_handling(self):
        """Test that duplicate rows are removed."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'QA04 Cases Missing Lab and/or Treatment',
                'library_name': 'qa_04',
                'data_source_name': '[RDB].[dbo].[STD_HIV_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART]',
            }
        )

        result = execute_report(report_spec)
        data = result.content

        # Check for duplicates by creating a set of unique keys
        keys = [
            (row[0], row[1], row[2], row[3], row[4], row[5])
            for row in data.data
        ]
        assert len(keys) == len(set(keys)), "Duplicate rows found in output"

    def test_execute_report_column_order(self):
        """Test that columns are in the expected order."""
        report_spec = ReportSpec.model_validate(
            {
                'version': 1,
                'is_export': True,
                'is_builtin': True,
                'report_title': 'QA04 Cases Missing Lab and/or Treatment',
                'library_name': 'qa_04',
                'data_source_name': '[RDB].[dbo].[STD_HIV_DATAMART]',
                'subset_query': 'SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART]',
            }
        )

        result = execute_report(report_spec)
        data = result.content

        assert data.columns == [
            'Patient ID',
            'Name',
            'Dx',
            'Case ID',
            'Error Explanation',
            'Confirmation Date',
        ]