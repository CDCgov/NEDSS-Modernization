import csv
import decimal
import io
from datetime import date, datetime
from typing import Annotated, Any

from pydantic import BaseModel, ConfigDict, Field, Json, PlainSerializer

from src.config import get_cached_config_value


class ReportSpec(BaseModel):
    """Report request specification."""

    is_export: bool
    is_builtin: bool
    library_name: str = Field(min_length=1)
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
    """Turn a Table into a CSV for returning to the user."""
    date_format = get_cached_config_value('REPORT_EXPORT_DATE_FORMAT')
    datetime_format = get_cached_config_value('REPORT_EXPORT_DATETIME_FORMAT')

    def convert(value: Any) -> Any:
        if type(value) is date:
            return value.strftime(date_format)

        if type(value) is datetime:
            return value.strftime(datetime_format)

        if isinstance(value, (float, decimal.Decimal)):
            return f'{value:.2f}'.rstrip('0').rstrip('.')

        return value

    output = io.StringIO(newline='')
    writer = csv.writer(output, lineterminator='\r\n')

    writer.writerow(table.columns)
    writer.writerows((convert(value) for value in row) for row in table.data)

    return output.getvalue().removesuffix('\r\n')

def yield_table_csv(table: Table):
    """Turn a Table into a CSV for returning to the user."""
    date_format = get_cached_config_value('REPORT_EXPORT_DATE_FORMAT')
    datetime_format = get_cached_config_value('REPORT_EXPORT_DATETIME_FORMAT')

    def convert(value: Any) -> Any:
        if type(value) is date:
            return value.strftime(date_format)

        if type(value) is datetime:
            return value.strftime(datetime_format)

        if isinstance(value, (float, decimal.Decimal)):
            return f'{value:.2f}'.rstrip('0').rstrip('.')

        return value

    # only big files actually get chunked
    chunk_size=10000
    output = io.StringIO(newline='')
    writer = csv.writer(output, lineterminator='\r\n')

    writer.writerow(table.columns)

    for i, row in enumerate(table.data, start=1):
        writer.writerow(convert(value) for value in row)

        if i % chunk_size == 0:
            yield output.getvalue()

            output.seek(0)
            output.truncate(0)

    # Yield whatever remains
    if output.tell():
        yield output.getvalue()


# TODO: add other return types  # noqa: FIX002
class ReportResult(BaseModel):
    """Report execution result."""

    model_config = ConfigDict(arbitrary_types_allowed=True)

    content: Annotated[Table, PlainSerializer(serialize_table)]
    context_header: str | None = None
    description: str | None = None
