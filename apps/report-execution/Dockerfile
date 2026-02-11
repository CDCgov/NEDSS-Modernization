FROM python:3.14-slim

# Specify FastAPI application port and host, with defaults
ENV UVICORN_PORT=8001
ENV UVICORN_HOST="0.0.0.0"

# Install uv
COPY --from=ghcr.io/astral-sh/uv:latest /uv /uvx /bin/

WORKDIR /usr/report-execution

# Copy relevant application code
COPY pyproject.toml pyproject.toml
COPY pytest.toml  pytest.toml
COPY uv.toml uv.toml
COPY uv.lock uv.lock
COPY src src

# Install dependencies
RUN uv sync --frozen

# Run the FastAPI application using uvicorn
CMD ["uv", "run", "uvicorn", "src.main:app"]
