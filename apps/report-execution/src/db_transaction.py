import logging
from contextlib import contextmanager

import mssql_python

from .models import Table


class Transaction:
    """A database transaction abstraction for use in libraries."""

    def __init__(self, cursor):
        self._cursor = cursor

    def query(self, query: str, parameters: tuple = ()) -> Table:
        """Execute a query and have the data returned as a Table.

        DO NOT EXECUTE ANY PERMANENT CREATE, UPDATE, OR DELETE STATEMENTS

        Positional `?` placeholders can be used in the query and values passed as
        parameters in a tuple.
        """
        logging.debug(f'Querying: {query}')
        data = self._cursor.execute(query, parameters).fetchall()
        columns = self._column_names()
        return Table(columns=columns, data=data)

    def execute(self, query: str, parameters: tuple = ()) -> None:
        """Execute a SQL statement and do not return any result.

        DO NOT EXECUTE ANY PERMANENT CREATE, UPDATE, OR DELETE STATEMENTS

        Positional `?` placeholders can be used in the query and values passed as
        parameters in a tuple.
        """
        logging.debug(f'Executing: {query}')
        self._cursor.execute(query, parameters)
        return None

    def _column_names(self) -> list[str]:
        return [c[0] for c in self._cursor.description]


@contextmanager
def db_transaction(connection_string):
    """Set up a database transaction."""
    with mssql_python.connect(connection_string) as connection:
        # Turn off auto commit, so all of the queries are in one transaction
        connection.setautocommit(False)

        with connection.cursor() as cursor:
            trx = Transaction(cursor)
            yield trx

        # not sure why this is needed - it shouldn't be per docs:
        # https://github.com/microsoft/mssql-python/wiki/Connection#context-manager
        connection.commit()
