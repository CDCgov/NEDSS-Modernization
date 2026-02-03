# Use Python 3.14 as the base image
FROM python:3.14-slim

# Install uv
COPY --from=ghcr.io/astral-sh/uv:latest /uv /uvx /bin/

# Set working directory
WORKDIR /app

# Install dependencies
COPY pyproject.toml uv.lock ./
RUN uv sync --frozen --no-cache

# Copy application code
COPY main.py ./
COPY report-libraries ./report-libraries

# Expose the port FastAPI will run on
EXPOSE 8000

# Run the FastAPI application using uvicorn
CMD ["uv", "run", "fastapi", "dev", "--host", "0.0.0.0", "--port", "8000", "main.py"]
