from contextlib import contextmanager

import models

import libraries


async def execute_report(report_spec: models.ReportSpec):

    if not is_valid_spec(report_spec):
        raise "TODO: validation handling"

    # TODO: set up database connection as read only and start a transaction
    with db_transaction() as trx:
        result = libraries[report_spec.library_name].execute(
            trx, report_spec.data_source_name, report_spec.time_range
        )

    if not is_valid_result(result):
        raise "TODO: validation handling"

    return result


def is_valid_spec(report_spec: models.ReportSpec):
    return True


def is_valid_result(report_result: models.ReportResult):
    return True


# TODO: make this actually async?
@contextmanager
async def db_transaction():
    yield "TODO"
