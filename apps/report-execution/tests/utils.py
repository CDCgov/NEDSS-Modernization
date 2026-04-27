import pytest

from src import utils
from src.errors import InternalServerError


class TestUtils:
    """Tests for common utility functions."""

    def test_get_env_or_error_valid(self, db_credentials):
        res = utils.get_env_or_error('DATABASE_CONN_STRING')
        assert res is not None

    def test_get_env_or_error_missing(self):

        with pytest.raises(InternalServerError) as exc_info:
            utils.get_env_or_error('NOT_A_REAL_ENV_VAR')

        assert exc_info.value.http_code == 500
        assert exc_info.value.message == 'Internal Server Error'

    def test_get_int_env_or_default_valid(self, monkeypatch):
        with monkeypatch.context() as m:
            m.setenv('REPORT_MAX_ROW_LIMIT_RUN', '10')

            res = utils.get_int_env_or_default('REPORT_MAX_ROW_LIMIT_RUN', 100)
            assert res == 10

    def test_get_int_env_or_default_valid_w_whitespace(self, monkeypatch):
        with monkeypatch.context() as m:
            m.setenv('REPORT_MAX_ROW_LIMIT_RUN', '10 ')

            res = utils.get_int_env_or_default('REPORT_MAX_ROW_LIMIT_RUN', 100)
            assert res == 10

    def test_get_int_env_or_default_invalid(self, monkeypatch, caplog):
        with monkeypatch.context() as m:
            m.setenv('REPORT_MAX_ROW_LIMIT_RUN', '1a')

            res = utils.get_int_env_or_default('REPORT_MAX_ROW_LIMIT_RUN', 100)
            assert res == 100
            assert len(caplog.records) == 1
            log = caplog.records[0]
            assert log.levelname == 'WARNING'
            assert log.msg == (
                'Failed to use `REPORT_MAX_ROW_LIMIT_RUN` as it is not an integer.'
                ' Received `1a`'
            )

    def test_get_int_env_or_default_not_set(self, monkeypatch, caplog):
        with monkeypatch.context() as m:
            m.delenv('REPORT_MAX_ROW_LIMIT_RUN', raising=False)

            res = utils.get_int_env_or_default('REPORT_MAX_ROW_LIMIT_RUN', 100)
            assert res == 100
            assert len(caplog.records) == 0

    def test_gen_subheader_with_us(self):
        result = utils.gen_subheader(states=['Georgia', 'Tennessee'])
        assert result == 'Georgia, Tennessee'

    def test_gen_subheader_with_states_and_diseases(self):
        result = utils.gen_subheader(
            states=[None, 'Alabama', 'Georgia'],
            diseases=['Measles'],
        )
        assert result == 'N/A, Alabama, Georgia | Measles'

    def test_gen_subheader_no_args(self):
        result = utils.gen_subheader()
        assert result == ''

    def test_build_case_count_query_with_empty_subset_query(self):
        with pytest.raises(ValueError, match='Subset query cannot be empty'):
            utils.build_case_count_query(
                column_mapping={
                    'state_cd': 'State Code',
                    'state': 'State',
                    'county': 'County',
                    'phc_code_short_desc': 'Condition',
                    'event_date': 'Event Date',
                    'cnty_cd': 'County Code',
                },
                subset_query='',
            )

    def test_build_case_count_query_with_invalid_column_mapping(self):
        with pytest.raises(ValueError, match='One or more columns are invalid'):
            subset_query = 'SELECT * FROM [NBS_ODSE].[dbo].[PublicHealthCaseFact]'
            utils.build_case_count_query(
                column_mapping={
                    'state_code': 'State Code',
                    'state': 'State',
                    'county': 'County',
                    'phc_code_short_desc': 'Condition',
                    'event_date': 'Event Date',
                    'county_cd': 'County Code',
                },
                subset_query=subset_query,
            )

    def test_build_case_count_query_with_subset_query(self):
        subset_query = 'SELECT * FROM [NBS_ODSE].[dbo].[PublicHealthCaseFact]'
        result = utils.build_case_count_query(
            column_mapping={
                'state_cd': 'State Code',
                'state': 'State',
                'county': 'County',
                'phc_code_short_desc': 'Condition',
                'event_date': 'Event Date',
                'cnty_cd': 'County Code',
            },
            subset_query=subset_query,
        )
        expected = (
            'WITH subset as (SELECT * FROM [NBS_ODSE].[dbo].[PublicHealthCaseFact])\n'
            'SELECT state_cd AS "State Code", state AS "State", county AS "County", phc_code_short_desc AS "Condition", event_date AS "Event Date", cnty_cd AS "County Code", sum(group_case_cnt) as Cases\n'
            'FROM subset\n'
            'GROUP BY state_cd, state, county, phc_code_short_desc, event_date, cnty_cd\n'
            'ORDER BY state_cd, state, county, phc_code_short_desc, event_date, cnty_cd'
        )
        assert result == expected