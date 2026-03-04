import logging
import os

from . import errors


def get_env_or_error(env_var: str):
    """Gets an environment variable, if it isn't present throws an
    internal service error.
    """
    res = os.getenv(env_var)
    if res is None:
        raise errors.InternalServerError(
            f'Missing required environment variable: `{env_var}`'
        )

    return res


def get_int_env_or_default(env_var: str, default: int):
    """Gets an environment variable, if it isn't present or not an int uses the default.
    Logs a warning if int parsing fails.
    """
    res = os.getenv(env_var)
    if res is None:
        return default

    try:
        return int(res)
    except ValueError:
        logging.warning(f'Failed to use `{env_var}` as it is not an integer')
        return default
