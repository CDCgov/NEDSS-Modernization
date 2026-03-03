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


class ToDoError(BaseReportExecutionError):
    """An error for a feature that hasn't been implemented yet."""

    def __init__(self, message, orig_exc=None):
        todo_message = f'TODO: {message}'
        if orig_exc is not None:
            logging.error(orig_exc)
        super().__init__(todo_message, 502)
