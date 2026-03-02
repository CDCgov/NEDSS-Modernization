import os
from . import errors


def get_env_or_error(env_var: str):
    """Gets an environment variable, if it isn't present throws an internal service error"""

    res = os.getenv(env_var)
    if res is None:
        raise errors.InternalServerError(
            f"Missing required environment variable: `{env_var}`"
        )

    return res
