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

    def test_get_str_env_or_default_valid(self, monkeypatch):
        with monkeypatch.context() as m:
            m.setenv('CSV_DATE_STRFTIME', '%m/%d/%Y')

            res = utils.get_str_env_or_default('CSV_DATE_STRFTIME', 'foobar')
            assert res == '%m/%d/%Y'

    def test_get_str_env_or_default_invalid(self):
        res = utils.get_str_env_or_default('CSV_DATE_STRFTIME', 'foobar')
        assert res == 'foobar'

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
