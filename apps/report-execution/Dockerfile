FROM python:3.14-slim

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

# Expose the port FastAPI will run on
EXPOSE 8000

# Run the FastAPI application using uvicorn
CMD ["uv", "run", "fastapi", "dev", "--host", "0.0.0.0", "--port", "8000", "main.py"]
