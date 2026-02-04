FROM python:3.14-slim

# Install uv
COPY --from=ghcr.io/astral-sh/uv:latest /uv /uvx /bin/

WORKDIR /app

# Install dependencies
COPY pyproject.toml pytest.toml uv.toml uv.lock ./
RUN uv sync --frozen

# Copy application code
COPY src ./src

# Expose the port FastAPI will run on
EXPOSE 8000

# Run the FastAPI application using uvicorn
CMD ["uv", "run", "fastapi", "dev", "--host", "0.0.0.0", "--port", "8000", "main.py"]
