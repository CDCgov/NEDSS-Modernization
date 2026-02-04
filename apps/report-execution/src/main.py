from typing import Optional

from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI()


class TimeRange(BaseModel):
    start: str  # Date in ISO format
    end: str  # Date in ISO format


class ReportSpec(BaseModel):
    version: int
    is_export: bool
    report_title: str
    library_name: str
    data_source_name: str
    subset_query: str
    time_range: Optional[TimeRange] = None


@app.get("/")
async def read_root():
    return {"Hello": "World"}


@app.post("/report/execute")
async def read_item(report_spec: ReportSpec):
    return {
        "report_title": report_spec.report_title,
        "library_name": report_spec.library_name,
    }
