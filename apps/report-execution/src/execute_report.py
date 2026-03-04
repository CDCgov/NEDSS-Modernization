from contextlib import asynccontextmanager
from importlib import import_module

from . import errors, models


async def execute_report(report_spec: models.ReportSpec):
    """Execute a report spec by validating inputs, loading library, handling DB
    connection and transaction,and validating/processing results.
    """
    if not is_valid_spec(report_spec):
        raise errors.ToDoError('validation handling')

    # get the library defined in the spec as a python module
    library = get_library(report_spec.library_name, report_spec.is_builtin)
    if not is_valid_library(library):
        raise errors.ToDoError('validation handling')

    # TODO: set up db connection as read only and start a transaction  # noqa: FIX002
    async with db_transaction() as trx:
        result = await library.execute(
            trx, report_spec.data_source_name, report_spec.time_range
        )

    if not is_valid_result(result):
        raise errors.ToDoError('validation handling')

    return result


def is_valid_spec(report_spec: models.ReportSpec):
    """Check if the report spec is valid."""
    return True


# TODO: what is the type that should go here? Part of spike  # noqa: FIX002
def is_valid_library(library):
    """Check if the library is valid."""
    return True


def is_valid_result(report_result: models.ReportResult):
    """Check if the returned result is valid."""
    return True


def get_library(library_name: str, is_builtin: bool):
    """Given a library name and whether it is builtin, fetch the library module."""
    try:
        if is_builtin:
            return import_module(f'src.libraries.{library_name}')
        else:
            raise errors.ToDoError('support custom libraries')
    except ModuleNotFoundError:
        # Initial error not helpful for debugging
        raise errors.MissingLibraryError(library_name, is_builtin) from None


@asynccontextmanager
async def db_transaction():
    """Set up a read only database transaction."""
    yield 'TODO'
