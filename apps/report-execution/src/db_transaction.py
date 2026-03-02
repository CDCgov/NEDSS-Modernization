
from typing import List, tuple, Any
import mssql_python
from contextlib import asynccontextmanager


class Transaction:
    def __init__(self, cursor):
        self.cursor = cursor

    def execute(self, query: str) -> List[tuple[Any, ...]]:
        return self.cursor.execute(query).fetchall()

# TODO: make this actually async?
@asynccontextmanager
async def db_transaction(connection_string, subset_sql):
    """Set up a database transaction and seed with the subset_sql as the
    `#work` table"""
    with mssql_python.connect(connection_string) as connection:
        # Turn off auto commit, so all of the queries are in one transaction
        connection.setautocommit(False)

        with Transaction(connection.cursor()) as trx:
            trx.execute(f"insert into #work {subset_sql}")
            yield trx
