from . import models
from .execute_report import execute_report
from fastapi import FastAPI

app = FastAPI()

@app.get("/status")
async def health_check():
    """
    Check service health status.

    Returns: Status text
    """
    return "Report Execution Service is up and running!"


@app.post("/report/execute")
async def execute_report_api(report_spec: models.ReportSpec):
    return await execute_report(report_spec)
