"""Models and constants for pa_01."""

from dataclasses import dataclass

# CSV columns
CSV_COLUMNS = [
    'Worker',
    'Category 1',
    'Category 2',
    'Category 3',
    'Count',
    'Percentage',
    'Index',
]

# CSV row data shape
Pa01Row = tuple[
    str,  # Worker
    str,  # Category 1
    str,  # Category 2
    str | None,  # Category 3
    int | None,  # Count
    str | None,  # Percentage
    str | None,  # Index
]


@dataclass(frozen=True)
class Pa01Worker:
    """Individual worker within the context of this report."""

    investigator_interview_key: int
    provider_quick_code: str
