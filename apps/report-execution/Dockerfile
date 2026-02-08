FROM python:3.14-slim

# Specify FastAPI application port, defaulting to 8001
ENV UVICORN_PORT=8001

# Install uv
COPY --from=ghcr.io/astral-sh/uv:latest /uv /uvx /bin/

WORKDIR /usr/report-execution

# Copy relevant application code
COPY apps/report-execution/pyproject.toml pyproject.toml
COPY apps/report-execution/pytest.toml  pytest.toml
COPY apps/report-execution/uv.toml uv.toml
COPY apps/report-execution/uv.lock uv.lock
COPY apps/report-execution/src src

# Install dependencies
RUN uv sync --frozen

# Run the FastAPI application using uvicorn
CMD ["uvicorn", "src.main:app"]
