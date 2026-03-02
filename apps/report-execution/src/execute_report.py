from importlib import import_module

from . import models
from . import errors
from .db_transaction import db_transaction
from . import utils


def execute_report(report_spec: models.ReportSpec):
    """Execute a report spec by validating inputs, loading library, handling DB connection
    and transaction,and validating/processing results"""

    if not is_valid_spec(report_spec):
        raise "TODO: validation handling"

    # get the library defined in the spec as a python module
    library = get_library(report_spec.library_name, report_spec.is_builtin)
    if not is_valid_library(library):
        raise "TODO: validation handling"

    # set up database connection as read only and start a transaction
    conn_string = utils.get_env_or_error("DATABASE_CONN_STRING")
    with db_transaction(conn_string, report_spec.subset_query) as trx:
        result = library.execute(
            trx, report_spec.data_source_name, report_spec.time_range
        )

    if not is_valid_result(result):
        raise "TODO: validation handling"

    return result


def is_valid_spec(report_spec: models.ReportSpec):
    """Check if the report spec is valid"""
    return True


# TODO: what is the type that should go here? Part of spike
def is_valid_library(library):
    """Check if the library is valid"""
    return True


def is_valid_result(report_result: models.ReportResult):
    """Check if the returned result is valid"""
    return True


def get_library(library_name: str, is_builtin: bool):
    """Given a library name and whether it is builtin, fetch the python library module"""
    try:
        if is_builtin:
            return import_module(f"src.libraries.{library_name}")
        else:
            raise "TODO: support custom libraries"
    except ModuleNotFoundError:
        raise errors.MissingLibraryError(library_name, is_builtin)
