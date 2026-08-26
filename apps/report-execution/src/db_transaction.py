import logging
import re
from contextlib import contextmanager

import mssql_python

from . import errors
from .config import get_cached_config_value
from .models import Table

INVALID_OBJECT_REGEX = re.compile("Invalid object name ('.*').")
INVALID_COLUMN_REGEX = re.compile("Invalid column name ('.*').")

BATCH_SIZE = 2**12 # 4096


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

        try:
            self._cursor.execute(query, parameters)
        except mssql_python.ProgrammingError as e:
            datasource_match = INVALID_OBJECT_REGEX.search(e.message)
            if datasource_match is not None:
                raise errors.MissingDbObjectError(
                    'Datasource', datasource_match.group(1)
                ) from None

            col_match = INVALID_COLUMN_REGEX.search(e.message)
            if col_match is not None:
                raise errors.MissingDbObjectError(
                    'Column', col_match.group(1)
                ) from None

            # re-raise
            raise e

        data = self._fetch_rows()
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

    def _fetch_rows(self):
        data = []
        row_limit = get_row_limit(self.is_export)
        while len(data) <= row_limit:
            batch = self._cursor.fetchmany(BATCH_SIZE)
            data.extend(batch)

            if len(batch) < BATCH_SIZE:
                break

        # If there are any more rows to fetch beyond the limit, the result is too big
        if len(data) > row_limit:
            raise errors.ResultTooBigError(
                self.is_export, row_limit, f'over {row_limit}'
            )

        return data

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


def get_row_limit(is_export: bool):
    """Get the max number of rows allowed in the report."""
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

    return row_limit_int
