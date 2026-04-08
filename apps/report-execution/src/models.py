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


# column names and values
class Table(BaseModel):
    """Basic tabular data format."""

    columns: list[str]
    data: list[tuple[Any, ...]]
    
    def get_column(self, col_name: str) -> list[Any]:
        """Extract a column by name. Raises an error if the column doesn't exist."""

        if col_name not in self.columns:
            raise ValueError(f"Column '{col_name}' not found. Available columns: {self.columns}")
        idx = self.columns.index(col_name)
        return [row[idx] for row in self.data]
    
    def get_unique_column(self, col_name: str) -> list[Any]:
        """Extract unique values from a column."""
        
        return list(set(self.get_column(col_name)))


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
    header: str | None = None
    subheader: str | None = None
    description: str | None = None
