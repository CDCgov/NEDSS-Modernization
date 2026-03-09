from contextlib import contextmanager

import mssql_python

from .models import Table


class Transaction:
    """A database transaction abstraction for use in libraries."""

    def __init__(self, cursor):
        self.cursor = cursor

    def execute(self, query: str, parameters: tuple = ()) -> Table:
        """Execute a query and have the data returned as a Table.

        DO NOT EXECUTE ANY PERMANENT CREATE, UPDATE, OR DELETE STATEMENTS

        Positional `?` placeholders can be used in the query and values passed as
        parameters in a tuple.

        If the query inserts or updates a temporary table, then the returned table
        will be empty.
        """
        data = self.cursor.execute(query, parameters).fetchall()
        columns = self._column_names()
        return Table(columns=columns, data=data)

    def _column_names(self) -> list[str]:
        return [c[0] for c in self.cursor.description]


@contextmanager
def db_transaction(connection_string):
    """Set up a database transaction."""
    with mssql_python.connect(connection_string) as connection:
        # Turn off auto commit, so all of the queries are in one transaction
        connection.setautocommit(False)

        with connection.cursor() as cursor:
            trx = Transaction(cursor)
            yield trx
