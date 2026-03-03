from typing import Annotated, Literal

from pandas import DataFrame
from pydantic import BaseModel, ConfigDict, PlainSerializer


def serialize_dataframe(df: DataFrame) -> str:
    """Turn a dataframe into a CSV for returning to the user."""
    return df.to_csv(index=False)


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


# TODO: add other return types  # noqa: FIX002
class ReportResult(BaseModel):
    """Report execution result."""

    model_config = ConfigDict(arbitrary_types_allowed=True)

    content_type: Literal['table']
    content: Annotated[DataFrame, PlainSerializer(serialize_dataframe)]
    description: str | None
