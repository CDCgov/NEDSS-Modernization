FROM registry.access.redhat.com/ubi10/ubi-minimal:latest

# Specify FastAPI application port and host, with defaults
ENV UVICORN_PORT=8001
ENV UVICORN_HOST="0.0.0.0"

# Install uv
COPY --from=ghcr.io/astral-sh/uv:latest /uv /uvx /bin/

# uv settings for production
ENV UV_NO_DEV=1
ENV UV_FROZEN=1

WORKDIR /usr/report-execution

# Install mssql driver deps
RUN microdnf update && microdnf install -y libtool-ltdl krb5-libs

# Copy relevant dependency code
COPY pyproject.toml pyproject.toml
COPY uv.toml uv.toml
COPY uv.lock uv.lock

# Install dependencies
RUN uv sync

# Copy application code
COPY src src

# Run the FastAPI application using uvicorn
CMD ["uv", "run", "uvicorn", "src.main:app"]
