from typing import Annotated, Literal, Optional, List, Tuple, Any

from pandas import DataFrame
from pydantic import BaseModel, ConfigDict, PlainSerializer

# column names and values
Table = Tuple[List[str], List[Tuple[Any, ...]]]


def serialize_table(table: Table) -> str:
    # Short cut to valid CSV - can swap out later if performance dictates
    # or serialize to CSV at a different location
    df = DataFrame.from_records(table[1], columns=table[0])
    return df.to_csv(index=False)


class TimeRange(BaseModel):
    start: str  # Date in ISO format
    end: str  # Date in ISO format


class ReportSpec(BaseModel):
    version: int
    is_export: bool
    is_builtin: bool
    report_title: str
    library_name: str
    data_source_name: str
    subset_query: str
    time_range: Optional[TimeRange] = None


# TODO: add other return types
class ReportResult(BaseModel):
    model_config = ConfigDict(arbitrary_types_allowed=True)

    content_type: Literal["table"]
    content: Annotated[Table, PlainSerializer(serialize_table)]
    description: Optional[str]
