import logging
import os
from datetime import date, datetime

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


def parse_date(date_str: str) -> datetime:
    """Parse a date string, trying ISO format first, then US format."""
    try:
        # Try ISO format (YYYY-MM-DD)
        return datetime.fromisoformat(date_str)
    except ValueError:
        # Try US format (MM/DD/YYYY)
        return datetime.strptime(date_str, '%m/%d/%Y')


def gen_subheader(
    states: list[str | None] | None = None,
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

    # Add states if provided
    if states:
        has_none = any(s is None for s in states)
        clean_states = {s for s in states if s and s != '<FILLER>'}
        if clean_states:
            if has_none:
                sorted_states = ['N/A'] + sorted(clean_states)
            else:
                sorted_states = sorted(clean_states)
            parts.append(', '.join(sorted_states))

    # Add diseases if provided
    if diseases:
        clean_diseases = {d for d in diseases if d}
        if clean_diseases:
            parts.append(', '.join(sorted(clean_diseases)))

    # Add date range if time_range provided
    if time_range:
        # Parse dates (handles both ISO and US formats)
        start_dt = parse_date(time_range.start)
        end_dt = parse_date(time_range.end)
        # Format as MM/DD/YYYY
        parts.append(
            f'{start_dt.strftime("%m/%d/%Y")} to {end_dt.strftime("%m/%d/%Y")}'
        )

    # Add single date if date_obj provided
    elif date_obj is not None:
        parts.append(date_obj.strftime('%m/%d/%Y'))

    return ' | '.join(parts)
