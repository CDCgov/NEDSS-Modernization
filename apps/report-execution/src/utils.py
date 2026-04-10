import logging
import os
from datetime import date, datetime
from typing import List, Optional, Union

from src.models import TimeRange

from . import errors

# Date format constant
US_DATE_FORMAT = "%m/%d/%Y"

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


def parse_date(date_str: str) -> datetime | None:
    """Parse a date string, trying ISO format first, then US format."""
    try:
        return datetime.fromisoformat(date_str)
    except ValueError:
        try:
            return datetime.strptime(date_str, US_DATE_FORMAT)
        except ValueError:
            return None


def gen_subheader(
    states: Optional[List[Optional[str]]] = None,
    time_range: Optional[TimeRange] = None,
    date_obj: Optional[Union[date, str]] = None,
    diseases: Optional[List[str]] = None,
) -> str:
    """Generate a subheader for reports from various optional components.
    
    Note: Caller is responsible for sorting/deduplicating states and diseases.
    
    Args:
        states: Optional list of state strings (already sorted and deduplicated)
        time_range: Optional TimeRange object with start/end dates
        date_obj: Optional date object or year string (e.g., '2024')
        diseases: Optional list of disease strings (already sorted and deduplicated)
    
    Returns:
        Formatted subheader string
    """
    parts = []
    
    # Add states if provided - replace None with 'N/A', filter out '<FILLER>'
    if states:
        clean_states = ['N/A' if s is None else s for s in states if s != '<FILLER>']
        if clean_states:
            parts.append(', '.join(clean_states))
    
    # Add diseases if provided - filter out None/empty
    if diseases:
        clean_diseases = [d for d in diseases if d]
        if clean_diseases:
            parts.append(', '.join(clean_diseases))
    
    # Add date range if time_range provided
    if time_range:
        # Check if year-only format
        if len(time_range.start) == 4 and len(time_range.end) == 4:
            parts.append(f'{time_range.start} to {time_range.end}')
        else:
            start_dt = parse_date(time_range.start)
            end_dt = parse_date(time_range.end)
            if start_dt and end_dt:
                parts.append(f'{start_dt.strftime(US_DATE_FORMAT)} to {end_dt.strftime(US_DATE_FORMAT)}')
            else:
                # Fallback to original strings if parsing failed
                parts.append(f'{time_range.start} to {time_range.end}')
    
    # Add single date if date_obj provided
    elif date_obj:
        # Check if date_obj is a year string (e.g., '2024')
        if isinstance(date_obj, str) and len(date_obj) == 4 and date_obj.isdigit():
            parts.append(date_obj)
        elif isinstance(date_obj, date):
            parts.append(date_obj.strftime(US_DATE_FORMAT))
        else:
            # Try to parse as string
            parts.append(str(date_obj))
    
    return ' | '.join(parts)