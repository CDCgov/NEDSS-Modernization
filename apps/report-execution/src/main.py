import logging

from fastapi import FastAPI, Request
from fastapi.responses import JSONResponse

from . import errors, models
from .execute_report import execute_report
from .utils import get_str_env_or_default

# default the logging to INFO
level = get_str_env_or_default('LOG_LEVEL', 'INFO')
logging.basicConfig(level=level)

app = FastAPI()


# ======= ROUTES ========


@app.get('/status')
async def health_check():
    """Check service health status.

    Returns: Status text
    """
    return 'Report Execution Service is up and running!'


@app.post('/report/execute')
def execute_report_api(report_spec: models.ReportSpec):
    """Primary api route for report execution."""
    return execute_report(report_spec)


# ======= ERROR MAPPING ========


@app.exception_handler(errors.BaseReportExecutionError)
async def api_exception_handler(request: Request, exc: errors.BaseReportExecutionError):
    """Handle application errors."""
    return JSONResponse(
        status_code=exc.http_code,
        content={'message': exc.message},
    )
