from typing import Literal, Optional

from pandas import DataFrame
from pydantic import BaseModel


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

# TODO: add other types
class ReportResult(BaseModel):
    content_type: Literal['table']
    content: DataFrame
    description: Optional[str]
