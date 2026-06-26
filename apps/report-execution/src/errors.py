import logging


class BaseReportExecutionError(Exception):
    """Base exception for report execution errors. Builds in the correct HTTP code."""

    def __init__(self, message: str, http_code: int):
        self.message = message
        self.http_code = http_code
        super().__init__(self.message)


class MissingLibraryError(BaseReportExecutionError):
    """The requested library is missing."""

    def __init__(self, library_name: str, is_builtin: bool):
        message = f'Library `{library_name}` (is_builtin: {is_builtin}) not found'
        super().__init__(message, 422)


class MissingColumnError(BaseReportExecutionError):
    """Required columns are missing."""

    def __init__(self, missing_columns: list[str]):
        message = f"""
        Required columns {', '.join(missing_columns)} are missing from column selection
        """
        super().__init__(message, 422)


class ResultTooBigError(BaseReportExecutionError):
    """The returned results are larger than allowed by configuration."""

    def __init__(self, is_export: bool, row_limit: int, num_rows: int):
        message = (
            f'Report request resulted in {num_rows} rows.'
            + f' The limit for {"exporting" if is_export else "running"} reports is'
            + f' {row_limit} rows. Please refine your filter criteria.'
        )
        super().__init__(message, 422)


class InternalServerError(BaseReportExecutionError):
    """An error with the server setup or execution. The message is logged for sys
    admins, but not passed to the end user.
    """

    def __init__(self, message, orig_exc=None):
        logging.error(message)
        if orig_exc is not None:
            logging.error(orig_exc)
        super().__init__('Internal Server Error', 500)


class InvalidReportSpecError(BaseReportExecutionError):
    """The provided report specification is invalid."""

    def __init__(self, message):
        super().__init__(f'Invalid report specification: {message}', 422)


class InvalidResultError(BaseReportExecutionError):
    """The report result from library execution is invalid."""

    def __init__(self, library_name: str, message=None):
        error_message = f'Invalid report result from library `{library_name}`'
        if message:
            error_message += f': {message}'
        super().__init__(error_message, 500)


class ToDoError(BaseReportExecutionError):
    """An error for a feature that hasn't been implemented yet."""

    def __init__(self, message, orig_exc=None):
        todo_message = f'TODO: {message}'
        if orig_exc is not None:
            logging.error(orig_exc)
        super().__init__(todo_message, 502)

class InvalidConfigurationError(BaseReportExecutionError):
    """The requested configuration key is missing or unmapped in the database."""

    def __init__(self, config_key: str):
        super().__init__(f'No qualified mapping found in NBS_Configuration '
                         + f'for config_key: {config_key}', 422)

class IntConfigurationConversionError(BaseReportExecutionError):
    """The configuration text payload cannot be evaluated as a valid integer."""

    def __init__(self, config_key: str):
        super().__init__(f'Unable to convert NBS configuration value to number '
                         + f'for config_key: {config_key}', 422)

class ConfigurationIntegrityError(BaseReportExecutionError):
    """A query constraint was broken due to duplicate keys or bad data layouts."""

    def __init__(self, message: str):
        super().__init__(f'NBS_Configuration Data Integrity Error: {message}', 422)
