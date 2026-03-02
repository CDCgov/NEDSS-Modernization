import os
import pytest
from contextlib import contextmanager


@pytest.fixture(scope="function")
def db_credentials():
    """Mocked DB Credentials"""
    os.environ["DATABASE_CONN_STRING"] = "testing"


class MockTransaction:
    def execute(self, query):
        return (["id", "name"], [(1, "a"), (2, "b"), (3, "c"), (4, "d")])


@contextmanager
def mock_db_transaction_ctxt(conn_string, data_source_name):
    yield MockTransaction()


@pytest.fixture(scope="function")
def mock_db_transaction(mocker, db_credentials):
    mock = mocker.patch("src.execute_report.db_transaction", mock_db_transaction_ctxt)
    return mock
