from contextlib import asynccontextmanager
from importlib import import_module

from . import models
from . import errors


async def execute_report(report_spec: models.ReportSpec):

    if not is_valid_spec(report_spec):
        raise "TODO: validation handling"

    # TODO: set up database connection as read only and start a transaction
    async with db_transaction() as trx:
        library = get_library(report_spec.library_name, report_spec.is_builtin)
        result = await library.execute(
            trx, report_spec.data_source_name, report_spec.time_range
        )

    if not is_valid_result(result):
        raise "TODO: validation handling"

    return result


def is_valid_spec(report_spec: models.ReportSpec):
    return True


def is_valid_result(report_result: models.ReportResult):
    return True


def get_library(library_name: str, is_builtin: bool):
    try:
        if is_builtin:
            return import_module(f"src.libraries.{library_name}")
        else:
            raise "TODO: support custom libraries"
    except ModuleNotFoundError:
        raise errors.MissingLibraryError(library_name, is_builtin)


# TODO: make this actually async?
@asynccontextmanager
async def db_transaction():
    yield "TODO"
