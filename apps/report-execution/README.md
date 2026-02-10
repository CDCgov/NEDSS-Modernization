# Report Execution Service

## Prerequisites

- Python 3.14 or higher
- [uv](https://docs.astral.sh/uv/) - Fast Python package and project manager

## Getting Started

1. Install `uv`, following [the instructions outlined on their website](https://docs.astral.sh/uv/getting-started/installation/).

1. Ensure you have Python 3.14 installed.  If you don't already have a means of managing Python versions, this can be accomplished directly with `uv`:
    ```bash
    uv python install 3.14
    ```

1. Install all project dependencies, as outlined in `pyproject.toml`:
    ```bash
    uv sync --frozen
    ```

1. Start the FastAPI development server with [Uvicorn](https://uvicorn.dev/) (the default ASGI server program shipped with FastAPI):

    ```bash
    uv run uvicorn src.main:app
    ```

The application will be available at:
- API: http://localhost:8001
- Interactive API docs (Swagger UI): http://localhost:8001/docs
- Alternative API docs (ReDoc): http://localhost:8001/redoc


## Testing

Unit testing for this project is currently handled with [pytest](https://docs.pytest.org/en/stable/#). To invoke the unit test suite, simply run the following:

```bash
uv run pytest
```

Or something similar to the following, if you're looking to run a specific test suite:
```bash
uv run pytest src/main_test.py
```

## Code Linting and Formatting

Additionally, this project uses [Ruff](https://docs.astral.sh/ruff/) (also created by Astral, the makers of uv) for linting and formatting.

In order to check for existing linter issues, run:
```bash
uv run ruff check
```

To automatically address any auto-fixable linter issues, run:
```bash
uv run ruff check --fix
```

Run the formatter with the following:
```bash
uv run ruff format
```

## Additional Resources
- [FastAPI Documentation](https://fastapi.tiangolo.com/)
- [uv Documentation](https://docs.astral.sh/uv/)
- [pytest Documentation](https://docs.pytest.org/)
