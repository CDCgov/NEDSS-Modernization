import logging
from contextlib import contextmanager

import mssql_python

from . import errors
from .config import get_cached_config_value
from .models import Table


class Transaction:
    """A database transaction abstraction for use in libraries."""

    def __init__(self, cursor, is_export):
        self._cursor = cursor
        self.is_export = is_export

    def query(self, query: str, parameters: tuple = ()) -> Table:
        """Execute a query and have the data returned as a Table.

        DO NOT EXECUTE ANY PERMANENT CREATE, UPDATE, OR DELETE STATEMENTS

        Positional `?` placeholders can be used in the query and values passed as
        parameters in a tuple.
        """
        logging.debug(f'Querying: {query}')
        res = self._cursor.execute(query, parameters)

        # This could be -1 if the driver doesn't know, in which case the re-check
        # after library processing is necessary suspenders for this belt
        num_rows = self._cursor.rowcount
        check_row_limits(num_rows, self.is_export)

        data = res.fetchall()
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
def db_transaction(connection_string, is_export: bool):
    """Set up a database transaction."""
    with mssql_python.connect(connection_string) as connection:
        # Turn off auto commit, so all of the queries are in one transaction
        connection.setautocommit(False)

        with connection.cursor() as cursor:
            trx = Transaction(cursor, is_export)
            yield trx

        # not sure why this is needed - it shouldn't be per docs:
        # https://github.com/microsoft/mssql-python/wiki/Connection#context-manager
        connection.commit()


def check_row_limits(num_rows: int, is_export: bool):
    """Ensure the number of rows in the result set does not exceed limits.

    When the is_export/run status is not available, assume export, which has a large.
    """
    if is_export:
        config_key = 'REPORT_MAX_ROW_LIMIT_EXPORT'
    else:
        config_key = 'REPORT_MAX_ROW_LIMIT_RUN'

    row_limit = get_cached_config_value(config_key)

    if not row_limit:
        raise errors.InvalidConfigurationError(config_key)

    try:
        row_limit_int = int(row_limit)
    except ValueError:
        raise errors.IntConfigurationConversionError(config_key) from None

    if num_rows > row_limit_int:
        raise errors.ResultTooBigError(is_export, row_limit_int, num_rows)

    return None
