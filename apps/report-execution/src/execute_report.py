from importlib import import_module

from . import errors, models, utils
from .db_transaction import db_transaction


def execute_report(report_spec: models.ReportSpec):
    """Execute a report spec by validating inputs, loading library, handling DB
    connection and transaction,and validating/processing results.
    """
    if not is_valid_spec(report_spec):
        raise errors.ToDoError('validation handling')

    # get the library defined in the spec as a python module
    library = get_library(report_spec.library_name, report_spec.is_builtin)
    if not is_valid_library(library):
        raise errors.ToDoError('validation handling')

    # set up database connection as read only and start a transaction
    conn_string = utils.get_env_or_error('DATABASE_CONN_STRING')
    with db_transaction(conn_string) as trx:
        result = library.execute(
            trx,
            subset_query=report_spec.subset_query,
            data_source_name=report_spec.data_source_name,
            time_range=report_spec.time_range,
        )

    check_valid_result(result, report_spec.is_export)

    return result


def is_valid_spec(report_spec: models.ReportSpec):
    """Check if the report spec is valid."""
    return True


# TODO: what is the type that should go here? Part of spike  # noqa: FIX002
def is_valid_library(library):
    """Check if the library is valid."""
    return True


def check_valid_result(report_result: models.ReportResult, is_export: bool):
    """Check if the returned result is valid."""
    row_limit = (
        utils.get_int_env_or_default('REPORT_MAX_ROW_LIMIT_EXPORT', 100000)
        if is_export
        else utils.get_int_env_or_default('REPORT_MAX_ROW_LIMIT_RUN', 10000)
    )
    num_rows = len(report_result.content.data)
    if num_rows > row_limit:
        raise errors.ResultTooBigError(is_export, row_limit, num_rows)

    return None


def get_library(library_name: str, is_builtin: bool):
    """Given a library name and whether it is builtin, fetch the library module."""
    try:
        if is_builtin:
            return import_module(f'src.libraries.{library_name}')
        else:
            return import_module(f'src.libraries.custom.{library_name}')
    except ModuleNotFoundError:
        # Initial error not helpful for debugging
        raise errors.MissingLibraryError(library_name, is_builtin) from None
