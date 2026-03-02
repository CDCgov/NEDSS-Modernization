import logging


class BaseReportExecutionError(Exception):
    """Base exception for report execution errors. Builds in the correct HTTP code for the API"""

    def __init__(self, message: str, http_code: int):
        self.message = message
        self.http_code = http_code
        super().__init__(self.message)


class MissingLibraryError(BaseReportExecutionError):
    """The requested library is missing or invalid"""

    def __init__(self, library_name: str, is_builtin: bool):
        message = f"Library `{library_name}` (is_builtin: {is_builtin}) not found"
        # TODO (PR): Is this the right http code?
        super().__init__(message, 422)


class InternalServerError(BaseReportExecutionError):
    """An error with the server setup or execution. The message is logged for sys admins,
    but not passed to the end user"""

    def __init__(self, message):
        logging.error(message)
        super().__init__("Internal Server Error", 500)
