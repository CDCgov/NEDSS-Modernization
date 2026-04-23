import logging
import os
from datetime import datetime

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


def parse_date(date_str: str, date_format: str) -> datetime | None:
    """Parse a date string, trying ISO format first, then US format."""
    try:
        return datetime.fromisoformat(date_str)
    except ValueError:
        try:
            return datetime.strptime(date_str, date_format)
        except ValueError:
            return None


def gen_subheader(
    states: list[str | None] | None = None,
    diseases: list[str] | None = None,
) -> str:
    """Generate a subheader for reports from various optional components.

    Note: Caller is responsible for sorting/deduplicating states and diseases.

    Args:
        states: Optional list of state strings (already sorted and deduplicated)
        diseases: Optional list of disease strings (already sorted and deduplicated)

    Returns:
        Formatted subheader string
    """
    parts = []

    # Add states if provided - replace None with 'N/A'
    if states:
        clean_states = ['N/A' if s is None else s for s in states]
        if clean_states:
            parts.append(', '.join(clean_states))

    # Add diseases if provided - filter out None/empty
    if diseases:
        clean_diseases = [d for d in diseases if d]
        if clean_diseases:
            parts.append(', '.join(clean_diseases))

    return ' | '.join(parts)
