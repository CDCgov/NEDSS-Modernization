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
