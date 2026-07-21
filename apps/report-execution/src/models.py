from datetime import date, datetime
from typing import Annotated, Any

import pandas as pd
from pydantic import BaseModel, ConfigDict, Field, Json, PlainSerializer

from src.config import get_cached_config_value


class ReportSpec(BaseModel):
    """Report request specification."""

    is_export: bool
    is_builtin: bool
    report_title: str = Field(min_length=1)
    library_name: str = Field(min_length=1)
    data_source_name: str = Field(min_length=1)
    subset_query: str = Field(min_length=1)
    sort_by: str | None = None
    days_value: int | None = None  # Specific to potntl_dup_inv_sum
    column_map: list[list[str]] | None = None
    library_params: Json[Any] | None = Field(default_factory=dict)


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

    def data_as_dicts(self) -> list[dict]:
        """Return data as a list of dicts where the keys are the column names
        and the values are the data values.

        Returns:
            Table data in the form of dicts where the column names are the keys
        """

        def row_to_dict(row: tuple) -> dict:
            d = dict()

            for i, col in enumerate(self.columns):
                d[col] = row[i]

            return d

        return list(map(row_to_dict, self.data))


def serialize_table(table: Table) -> str:
    """Turn a Table into a CSV for returning to the user.

    Standardizes Python date and datetime instances to the provided strftime constants,
    defaulting to:
     - datetime: mm/dd/yyyy hh:mm:ss
     - date: mm/dd/yyyy
    """

    # properly format a given value if it's a date or datetime
    def convert_dates(val: Any) -> Any:
        if type(val) is date:
            csv_date_strftime = get_cached_config_value('REPORT_EXPORT_DATE_FORMAT')
            return pd.to_datetime(val).strftime(csv_date_strftime)
        elif type(val) is datetime:
            csv_datetime_strftime = get_cached_config_value(
                'REPORT_EXPORT_DATETIME_FORMAT'
            )
            return pd.to_datetime(val).strftime(csv_datetime_strftime)

        return val

    # update table data to have properly formatted dates and datetimes
    data = [list(map(convert_dates, tpl)) for tpl in table.data]

    # Short cut to valid CSV - can swap out later if performance dictates
    # or serialize to CSV at a different location
    df = pd.DataFrame.from_records(data, columns=table.columns, coerce_float=True)

    csv_str = df.to_csv(
        index=False,
        # everything left of the decimal, up to 2 decimal places, no trailing 0s
        float_format=lambda x: f'{x:.2f}'.rstrip('0').rstrip('.'),
        lineterminator='\r\n',
    )

    # Remove trailing new line
    return csv_str[:-2]


# TODO: add other return types  # noqa: FIX002
class ReportResult(BaseModel):
    """Report execution result."""

    model_config = ConfigDict(arbitrary_types_allowed=True)

    content: Annotated[Table, PlainSerializer(serialize_table)]
    subheader: str | None = None
    description: str | None = None
