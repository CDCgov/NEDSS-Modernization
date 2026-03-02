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
