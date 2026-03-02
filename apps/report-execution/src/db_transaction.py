
import mssql_python
from contextlib import asynccontextmanager


class Transaction:
    def __init__(self, cursor):
        self.cursor = cursor

    def execute(self, query: str):
        result = self.cursor.execute(query)
        return result

# TODO: make this actually async?
@asynccontextmanager
async def db_transaction(connection_string, subset_sql):
    """Set up a read only database transaction and seed with the subset_sql as the
    `#work` table"""
    with mssql_python.connect(connection_string) as connection:
        with Transaction(connection.cursor()) as cursor:
            cursor.execute(f"insert into #work {subset_sql}")
            yield cursor
