from datetime import date, datetime
from re import sub
from typing import Annotated, Any, Literal

from pandas import DataFrame, to_datetime
from pydantic import BaseModel, ConfigDict, Field, PlainSerializer


class ReportSpec(BaseModel):
    """Report request specification."""

    is_export: bool
    is_builtin: bool
    report_title: str = Field(min_length=1)
    library_name: str = Field(min_length=1)
    data_source_name: str = Field(min_length=1)
    subset_query: str = Field(min_length=1)
    days_value: int | None = None  # Specific to potntl_dup_inv_sum


# column names and values
class Table(BaseModel):
    """Basic tabular data format."""

    columns: list[str]
    data: list[tuple[Any, ...]]

    def get_column(self, col_name: str) -> list[Any]:
        """Extract a column by name. Raises an error if the column doesn't exist."""
        if col_name not in self.columns:
            raise ValueError(
                f"Column '{col_name}' not found. Available columns: {self.columns}"
            )
        idx = self.columns.index(col_name)
        return [row[idx] for row in self.data]

    def get_unique_column(self, col_name: str) -> list[Any]:
        """Extract unique values from a column, sorted with None at the beginning.

        Args:
            col_name: Name of the column to extract

        Returns:
            Sorted list of unique values with None placed first
        """
        values = set(self.get_column(col_name))
        # Sort with None first (False < True, so None comes before non-None)
        return sorted(values, key=lambda x: (x is not None, x))


def serialize_table(table: Table) -> str:
    """Turn a Table into a CSV for returning to the user.

    Standardizes Python date and datetime instances to the following format:
     - datetime: mm/dd/yyyy hh:mm:ss
     - date: mm/dd/yyyy
    """
    def convert_dates(val: Any) -> Any:
        if type(val) not in [date, datetime]:
            return val

        return to_datetime(val)

    updated_data = []
    for tpl in table.data:
        updated_data.append([v for v in map(convert_dates, tpl)])

    table.data = updated_data

    # Short cut to valid CSV - can swap out later if performance dictates
    # or serialize to CSV at a different location
    df = DataFrame.from_records(table.data, columns=table.columns, coerce_float=True)

    csv_str = df.to_csv(index=False, date_format="%m/%d/%Y %H:%M:%S", float_format='{:.20g}', lineterminator='\r\n')

    # Remove the %H:%M:%S where no time is given (dates) while leaving datetimes intact
    date_re = r"(?P<date>\d{2}/\d{2}/\d{4}) 00:00:00"
    csv_str = sub(date_re, r"\g<date>", csv_str)

    # Remove trailing new line
    return csv_str[:-2]


# TODO: add other return types  # noqa: FIX002
class ReportResult(BaseModel):
    """Report execution result."""

    model_config = ConfigDict(arbitrary_types_allowed=True)

    content_type: Literal['table']
    content: Annotated[Table, PlainSerializer(serialize_table)]
    header: str | None = None
    subheader: str | None = None
    description: str | None = None
