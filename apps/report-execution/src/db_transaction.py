from contextlib import contextmanager

import mssql_python

from .models import Table


class Transaction:
    def __init__(self, cursor):
        self.cursor = cursor

    def execute(self, query: str) -> Table:
        data = self.cursor.execute(query).fetchall()
        columns = self.column_names()
        return Table(columns=columns, data=data)

    def column_names(self) -> list[str]:
        return [c[0] for c in self.cursor.description]


@contextmanager
def db_transaction(connection_string):
    """Set up a database transaction"""
    with mssql_python.connect(connection_string) as connection:
        # Turn off auto commit, so all of the queries are in one transaction
        connection.setautocommit(False)

        with connection.cursor() as cursor:
            trx = Transaction(cursor)
            yield trx
