import logging
import os
from datetime import date

from src.models import TimeRange

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


def gen_subheader(
    states: list[str] | None = None,
    time_range: TimeRange | None = None,
    date_obj: date | None = None,
    diseases: list[str] | None = None,
) -> str:
    """Generate a subheader for reports from various optional components.

    Args:
        states: Optional list of state strings (duplicates will be removed)
        time_range: Optional TimeRange object with start/end dates
        date_obj: Optional date object for single date display
        diseases: Optional list of disease strings (duplicates will be removed)

    Returns:
        Formatted subheader string
    """
    parts = []

    # Add states if provided and not empty. Exclude <FILLER> and make None values 'N/A'
    if states:
        clean_states = {s for s in states if s and s != '<FILLER>'}
        if clean_states:
            if None in states:
                sorted_states = ['N/A'] + sorted(clean_states)
            else:
                sorted_states = sorted(clean_states)
            parts.append(', '.join(sorted_states))

    # Add diseases if provided and not empty
    if diseases:
        clean_diseases = {d for d in diseases if d}
        if clean_diseases:
            parts.append(', '.join(sorted(clean_diseases)))

    # Add date range if time_range provided
    if time_range:
        parts.append(f'{time_range.start} to {time_range.end}')

    # Add single date if date_obj provided
    elif date_obj is not None:
        parts.append(date_obj.strftime('%m/%d/%Y'))

    return ' | '.join(parts)
