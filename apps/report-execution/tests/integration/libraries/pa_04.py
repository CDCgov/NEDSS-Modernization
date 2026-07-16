import pytest
import yaml

from src.errors import InvalidLibraryParamsError
from src.execute_report import execute_report
from src.models import ReportSpec

faker_schema = 'std_hiv_datamart.yaml'

EXPECTED_COLUMNS = ['Scope', 'Category 1', 'Category 2', 'Count', 'Percentage', 'Index']
SCOPES = ('Initial/Original', 'Re-Interview', 'Combined')
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
        # 10 case-level rows + 2 buckets * 3 scopes * 19 rows/block
        assert len(data) == 10 + 2 * 3 * 19

        snapshot.assert_match(yaml.dump(data), 'snapshot.yml')

        row_map = {(row[0], row[1], row[2]): row for row in data}

        cases_closed = row_map[(None, None, 'Cases Closed')]
        cases_interviewed = row_map[(None, None, 'Cases Interviewed')]
        assert cases_closed[3] > 0
        # KNOWN SAS QUIRK: Val_A and Val_B are the same query in
        # PA04_HIV.sas, so these are always equal.
        assert cases_closed[3] == cases_interviewed[3]

        for bucket in BUCKETS:
            for scope in SCOPES:
                notification = row_map[(scope, bucket, 'Notification Index')]
                testing = row_map[(scope, bucket, 'Testing Index')]
                # KNOWN SAS QUIRK: pix/testindex are identical datasets in
                # HIV, so these are always equal.
                assert notification[5] == testing[5]

                initiated = row_map[(scope, bucket, 'Initiated')]
                examined = row_map[(scope, bucket, 'Examined')]
                not_examined = row_map[(scope, bucket, 'Not Examined')]
                assert initiated[3] >= 0
                assert examined[3] <= initiated[3] or initiated[3] == 0
                assert not_examined[3] <= initiated[3] or initiated[3] == 0

    def test_execute_report_no_data(self):
        report_spec = _spec('SELECT * FROM [RDB].[dbo].[STD_HIV_DATAMART] WHERE 1 = 2')

        result = execute_report(report_spec)
        assert result.content_type == 'table'

        data = result.content.data
        assert len(data) == 10 + 2 * 3 * 19

        row_map = {(row[0], row[1], row[2]): row for row in data}
        assert row_map[(None, None, 'Cases Closed')][3] == 0
        assert row_map[(None, None, 'Cases Interviewed')][3] == 0
        for bucket in BUCKETS:
            for scope in SCOPES:
                assert row_map[(scope, bucket, 'Initiated')][3] == 0
                assert row_map[(scope, bucket, 'Notification Index')][5] is None

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
