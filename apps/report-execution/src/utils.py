import os
from . import errors


def get_env_or_error(env_var: str):
    res = os.getenv(env_var)
    if res is None:
        raise errors.InternalServerError(
            f"Missing required environment variable: `{env_var}`"
        )
