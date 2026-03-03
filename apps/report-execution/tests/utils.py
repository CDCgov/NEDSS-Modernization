import pytest

from src import utils
from src.errors import InternalServerError


class TestUtils:
    """Tests for common utility functions"""

    def test_get_env_or_error_valid(self, db_credentials):
        res = utils.get_env_or_error("DATABASE_CONN_STRING")
        assert res is not None

    def test_get_env_or_error_missing(self):

        with pytest.raises(InternalServerError) as exc_info:
            utils.get_env_or_error("NOT_A_REAL_ENV_VAR")

        assert exc_info.value.http_code == 500
        assert exc_info.value.message == "Internal Server Error"
