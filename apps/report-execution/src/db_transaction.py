from typing import List, Tuple, Any
import mssql_python
from contextlib import contextmanager


class Transaction:
    def __init__(self, cursor):
        self.cursor = cursor

    def execute(self, query: str) -> List[Tuple[Any, ...]]:
        results = self.cursor.execute(query).fetchall()
        columns = self.column_names()
        return (columns, results)

    def column_names(self) -> List[str]:
        return [c[0] for c in self.cursor.description]


# TODO: make this actually async?
@contextmanager
def db_transaction(connection_string, subset_sql):
    """Set up a database transaction and seed with the subset_sql as the
    `#work` table"""
    with mssql_python.connect(connection_string) as connection:
        # Turn off auto commit, so all of the queries are in one transaction
        connection.setautocommit(False)

        with Transaction(connection.cursor()) as trx:
            trx.execute(f"insert into #work {subset_sql}")
            yield trx
