from typing import Annotated, Literal, Optional
import io

from pandas import DataFrame
from pydantic import BaseModel, ConfigDict, PlainSerializer


def serialize_dataframe(df: DataFrame):
    str_io = io.StringIO()
    df.to_csv(str_io)
    return str_io.getvalue()


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
    content: Annotated[DataFrame, PlainSerializer(serialize_dataframe)]
    description: Optional[str]
