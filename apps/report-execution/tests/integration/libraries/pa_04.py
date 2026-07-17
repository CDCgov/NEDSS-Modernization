import pytest
import yaml

from src.errors import InvalidLibraryParamsError
from src.execute_report import execute_report
from src.models import ReportSpec

faker_schema = 'std_hiv_datamart.yaml'

EXPECTED_COLUMNS = [
    'Category 1',
    'Category 2',
    'Category 3',
    'Count',
    'Percentage',
    'Index',
    'From OI Count',
    'From OI Percentage',
    'From OI Index',
    'From RI Count',
    'From RI Percentage',
    'From RI Index',
    'Total Count',
    'Total Percentage',
    'Total Index',
]
# (Count, Percentage, Index) column offsets for each scope, within a row.
SCOPE_COLUMN_OFFSETS = {
    'Initial/Original': (6, 7, 8),
    'Re-Interview': (9, 10, 11),
    'Combined': (12, 13, 14),
}
BUCKETS = ('Partners', 'Clusters')


def _spec(subset_query: str, library_params: str = '{"variant": "hiv"}'):
    return ReportSpec.model_validate(
        {
            'is_export': True,
            'is_builtin': True,
            'report_title': 'PA04',
            'library_name': 'pa_04',
            'data_source_name': '[RDB].[dbo].[STD_HIV_DATAMART]',
            'subset_query': subset_query,
            'library_params': library_params,
        }
    )


@pytest.mark.usefixtures('setup_containers', 'fake_db_table')
@pytest.mark.integration
class TestIntegrationPa04Library:
    """Integration tests for the pa_04 library (HIV variant)."""

    def test_execute_report_check_data(self, snapshot):
        report_spec = _spec('SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART]')

        result = execute_report(report_spec)
        assert result.content_type == 'table'
        assert result.content.columns == EXPECTED_COLUMNS

        data = result.content.data
        # 10 case-level rows + 2 buckets * 19 rows/block
        assert len(data) == 10 + 2 * 19

        snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

        row_map = {(row[0], row[1]): row for row in data}

        cases_closed = row_map[('Cases Closed', None)]
        cases_interviewed = row_map[('Cases Interviewed', None)]
        assert cases_closed[3] > 0
        # KNOWN SAS QUIRK: Val_A and Val_B are the same query in
        # PA04_HIV.sas, so these are always equal.
        assert cases_closed[3] == cases_interviewed[3]

        for bucket in BUCKETS:
            notification = row_map[(f'{bucket} Examined', 'Notification Index')]
            testing = row_map[(f'{bucket} Examined', 'Testing Index')]
            # KNOWN SAS QUIRK: pix/testindex are identical datasets in HIV,
            # so these are always equal in every scope column.
            assert notification[6:] == testing[6:]

            initiated = row_map[(f'{bucket} Initiated', None)]
            examined = row_map[(f'{bucket} Examined', None)]
            not_examined = row_map[(f'{bucket} Not Examined', None)]
            for count_col, percent_col, _index_col in SCOPE_COLUMN_OFFSETS.values():
                assert initiated[count_col] >= 0
                # KNOWN SAS QUIRK: PA04_HIV.sas's %fills macro never writes a
                # percentage for 'Partners/Clusters Initiated' (the
                # assignment is commented out in the source).
                assert initiated[percent_col] is None
                assert (
                    examined[count_col] <= initiated[count_col]
                    or initiated[count_col] == 0
                )
                assert (
                    not_examined[count_col] <= initiated[count_col]
                    or initiated[count_col] == 0
                )

    def test_execute_report_no_data(self):
        report_spec = _spec('SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART] WHERE 1 = 2')

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data
        assert len(data) == 10 + 2 * 19

        row_map = {(row[0], row[1]): row for row in data}
        assert row_map[('Cases Closed', None)][3] == 0
        assert row_map[('Cases Interviewed', None)][3] == 0
        for bucket in BUCKETS:
            initiated = row_map[(f'{bucket} Initiated', None)]
            notification = row_map[(f'{bucket} Examined', 'Notification Index')]
            for count_col, _percent_col, index_col in SCOPE_COLUMN_OFFSETS.values():
                assert initiated[count_col] == 0
                # KNOWN SAS QUIRK: %SYSEVALF's 0/0 division resolves to '0'
                # text (confirmed via a real SAS log run), not missing.
                assert notification[index_col] == 0.0

    def test_execute_report_check_metadata(self):
        report_spec = _spec('SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART]')

        result = execute_report(report_spec)
        assert result.header == 'PA04'
        assert result.subheader is None
        assert result.description is None
        assert result.content_type == 'table'
        assert result.content.columns == EXPECTED_COLUMNS

    def test_execute_report_missing_variant_raises_error(self):
        report_spec = _spec(
            'SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART]', library_params='{}'
        )

        with pytest.raises(
            InvalidLibraryParamsError,
            match="'variant' is required",
        ):
            execute_report(report_spec)

    def test_execute_report_unsupported_variant_raises_error(self):
        report_spec = _spec(
            'SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART]',
            library_params='{"variant": "std"}',
        )

        with pytest.raises(
            InvalidLibraryParamsError,
            match='Unsupported PA04 variant',
        ):
            execute_report(report_spec)
