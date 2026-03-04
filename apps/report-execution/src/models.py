from typing import Annotated, Any, Literal

from pandas import DataFrame
from pydantic import BaseModel, ConfigDict, PlainSerializer


class TimeRange(BaseModel):
    """Start and end time for a report."""

    start: str  # Date in ISO format
    end: str  # Date in ISO format


class ReportSpec(BaseModel):
    """Report request specification."""

    version: int
    is_export: bool
    is_builtin: bool
    report_title: str
    library_name: str
    data_source_name: str
    subset_query: str
    time_range: TimeRange | None = None


class LibraryMetadata(BaseModel):
    """Metadata for a library program."""

    version: int
    name: str
    description: str
    owner_email: str | None = None


# column names and values
class Table(BaseModel):
    """Basic tabular data format."""

    columns: list[str]
    data: list[tuple[Any, ...]]


def serialize_table(table: Table) -> str:
    """Turn a Table into a CSV for returning to the user."""
    # Short cut to valid CSV - can swap out later if performance dictates
    # or serialize to CSV at a different location
    df = DataFrame.from_records(table.data, columns=table.columns)
    return df.to_csv(index=False)


# TODO: add other return types  # noqa: FIX002
class ReportResult(BaseModel):
    """Report execution result."""

    model_config = ConfigDict(arbitrary_types_allowed=True)

    content_type: Literal['table']
    content: Annotated[Table, PlainSerializer(serialize_table)]
    description: str | None
