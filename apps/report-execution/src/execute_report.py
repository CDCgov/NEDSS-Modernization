import typing
from importlib import import_module

from pydantic import ValidationError

from . import errors, models, utils
from .config import get_config_value
from .db_transaction import db_transaction


def execute_report(report_spec: models.ReportSpec):
    """Execute a report spec by validating inputs, loading library, handling DB
    connection and transaction,and validating/processing results.
    """
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
            days_value=report_spec.days_value,
            column_map=report_spec.column_map,
            library_params=report_spec.library_params,
        )

        check_valid_result(result, report_spec, trx)

    # Most libraries won't have a specific header, so the report title is used
    if result.header is None:
        result.header = report_spec.report_title

    return result


# TODO: what is the type that should go here? Part of spike  # noqa: FIX002
def is_valid_library(library):
    """Check if the library is valid."""
    return True


def check_valid_result(
    report_result: typing.Any, report_spec: models.ReportSpec, trx: db_transaction
):
    """Check if the returned result is valid."""
    if report_result is None:
        raise errors.InvalidResultError(report_spec.library_name, 'No result returned')

    try:
        result = models.ReportResult.model_validate(report_result)
    except ValidationError as e:
        raise errors.InvalidResultError(report_spec.library_name) from e

    if report_spec.is_export:
        config_key = 'REPORT_MAX_ROW_LIMIT_EXPORT'
    else:
        config_key = 'REPORT_MAX_ROW_LIMIT_RUN'

    row_limit = get_config_value(trx, config_key)

    if not row_limit:
        raise errors.InvalidConfigurationError(config_key)

    try:
        row_limit_int = int(row_limit)
    except ValueError:
        raise errors.IntConfigurationConversionError(config_key) from None

    num_rows = len(result.content.data)
    if num_rows > row_limit_int:
        raise errors.ResultTooBigError(report_spec.is_export, row_limit_int, num_rows)

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
