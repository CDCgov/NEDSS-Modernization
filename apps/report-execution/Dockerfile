FROM python:3.14-slim

# Specify FastAPI application port, defaulting to 8001
ENV UVICORN_PORT=8001

# Install uv
COPY --from=ghcr.io/astral-sh/uv:latest /uv /uvx /bin/

WORKDIR /app

# Install dependencies
COPY pyproject.toml pytest.toml uv.toml uv.lock ./
RUN uv sync --frozen

# Copy application code
COPY src ./src

# Run the FastAPI application using uvicorn
CMD ["uvicorn", "src.main:app"]
