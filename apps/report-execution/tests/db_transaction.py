from src.db_transaction import db_transaction

from .conftest import MockTransaction


class TestDbTransaction:
    """Test the db_transaction module."""

    def test_db_conn_autocommit_false(self, mocker, mock_db_connection):
        mocker.patch('src.db_transaction.Transaction', MockTransaction)
        with db_transaction('test connection string') as trx:
            assert trx._cursor.autocommit is False
