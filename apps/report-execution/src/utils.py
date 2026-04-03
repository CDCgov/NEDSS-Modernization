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
        logging.warning(
            f'Failed to use `{env_var}` as it is not an integer. Received `{res}`'
        )
        return default


def gen_subheader(start_date, end_date, content):
    """Generate a subheader string based on the content and time range."""
    # Parse states and diseases from the content
    col_index = {col: idx for idx, col in enumerate(content.columns)}

    state_set = set()

    for row in content.data:
        state = row[col_index['State']]
        if state is not None:
            state_set.add(state)

    state_list = sorted(state_set)

    # Format the time period string
    time_period_str = f'{start_date} to {end_date}'

    return f'For {", ".join(state_list)} | {time_period_str}'
