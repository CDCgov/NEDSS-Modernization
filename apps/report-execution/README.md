# Report Execution Service

A FastAPI-based service for report execution built with Python 3.14.

## Prerequisites

- Python 3.14 or higher
- [uv](https://docs.astral.sh/uv/) - Fast Python package installer and resolver

## Getting Started

### 1. Install uv

uv is a fast Python package installer and resolver. Install it using one of the following methods:

**macOS and Linux:**
```bash
curl -LsSf https://astral.sh/uv/install.sh | sh
```

**Windows:**
```powershell
powershell -ExecutionPolicy ByPass -c "irm https://astral.sh/uv/install.ps1 | iex"
```

**With pip:**
```bash
pip install uv
```

**With Homebrew (macOS):**
```bash
brew install uv
```

For more installation options, see the [official uv documentation](https://docs.astral.sh/uv/getting-started/installation/).

### 2. Install Dependencies

Once uv is installed, install all project dependencies:

```bash
uv sync
```

This will:
- Create a virtual environment (`.venv`) if it doesn't exist
- Install all dependencies from `pyproject.toml`

### 3. Run the Application

Start the FastAPI development server:

```bash
uv run fastapi dev src/main.py
```

The application will be available at:
- API: http://localhost:8000
- Interactive API docs (Swagger UI): http://localhost:8000/docs
- Alternative API docs (ReDoc): http://localhost:8000/redoc

**Production mode:**
```bash
uv run fastapi run src/main.py
```

### 4. Run Tests

Run the test suite using pytest:

```bash
uv run pytest
```

**Run specific test file:**
```bash
uv run pytest src/main_test.py
```

## Development

### Code Formatting and Linting

This project uses [Ruff](https://docs.astral.sh/ruff/) (also created by Astral, the makers of uv) for linting and formatting.

**Check for issues:**
```bash
uv run ruff check .
```

**Auto-fix issues:**
```bash
uv run ruff check --fix .
```

**Format code:**
```bash
uv run ruff format .
```

## Docker

Build and run the service using Docker:

```bash
# Build the Docker image
docker build -t report-execution .

# Run the container
docker run -p 8000:8000 report-execution
```

## Additional Resources

- [FastAPI Documentation](https://fastapi.tiangolo.com/)
- [uv Documentation](https://docs.astral.sh/uv/)
- [pytest Documentation](https://docs.pytest.org/)
