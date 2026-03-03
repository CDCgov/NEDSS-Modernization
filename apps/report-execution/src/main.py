from fastapi import FastAPI, Request
from fastapi.responses import JSONResponse

from . import errors, models
from .execute_report import execute_report

app = FastAPI()


# ======= ROUTES ========


@app.get("/status")
async def health_check():
    """
    Check service health status.

    Returns: Status text
    """
    return "Report Execution Service is up and running!"


@app.post("/report/execute")
async def execute_report_api(report_spec: models.ReportSpec):
    return execute_report(report_spec)


# ======= ERROR MAPPING ========


@app.exception_handler(errors.BaseReportExecutionError)
async def library_exception_handler(
    request: Request, exc: errors.BaseReportExecutionError
):
    return JSONResponse(
        status_code=exc.http_code,
        content={"message": exc.message},
    )
