# Report Execution Service

## Prerequisites

1. Install [uv](https://docs.astral.sh/uv/) (a Python package and project manager), following [the instructions outlined on their website](https://docs.astral.sh/uv/getting-started/installation/).

1. Ensure you have Python 3.14 installed.  If you don't already have a means of managing Python versions, this can be accomplished directly with `uv`:
    ```bash
    uv python install 3.14
    ```

## Getting Started

1. Install all project dependencies, as outlined in `pyproject.toml`:
    ```bash
    uv sync --frozen
    ```

1. (Optional) Create a `.env` file from the `sample.env`, if you'd like to configure the application's port or host during local development (particularly helpful if you're running outside of Docker).  _NOTE: You'll need to manually load the env (`export $(xargs <.env)`), add `--env-file .env` to `uv` commands, or install and configure [direnv](https://direnv.net/) (or an equivalent shell extension) in order to make these environment variables available to the application._

    ```sh
    cp sample.env .env
    ```

1. Have the NBS MSSQL database running and make sure the env vars in this workspace are correct for that DB (`sample.env` values should match). Using the `cdc-sandbox` docker compose is a good/opinionated way to do this

    ```sh
    # from a dedicated terminal at the repo root
    cd cdc-sandbox
    # create .env if it doesn't exist
    ./check_env.sh
    # run the database
    docker compose up nbs-mssql
    ```

1. Start the FastAPI development server with [Uvicorn](https://uvicorn.dev/) (the default ASGI server program shipped with FastAPI):

    ```bash
    uv run uvicorn src.main:app
    ```

The application will be available at:
- API: http://localhost:8001
- Interactive API docs (Swagger UI): http://localhost:8001/docs
- Alternative API docs (ReDoc): http://localhost:8001/redoc

Sample curl:
```sh
curl -X POST 'http://localhost:8001/report/execute' -H "accept: application/json" -H "Content-Type: application/json" -d '{
            "version": 1,
            "is_export": true,
            "is_builtin": true,
            "report_title": "Test Report",
            "library_name": "nbs_custom",
            "data_source_name": "random_db_table_0",
            "subset_query": "SELECT * FROM test"
}'
```

## Testing

Unit testing for this project is currently handled with [pytest](https://docs.pytest.org/en/stable/#). To invoke the unit test suite, simply run the following:

```bash
uv run pytest -m "not integration"
```

Or something similar to the following, if you're looking to run a specific test suite:
```bash
uv run pytest src/main_test.py
```

To run integration tests, ensure the `.env` is loaded or passed to `uv` and run
```sh
uv run pytest # all tests
uv run pytest -m integration # just integration tests
uv run --env-file .env pytest # pass env file to uv
```

## Code Linting and Formatting

Additionally, this project uses [Ruff](https://docs.astral.sh/ruff/) (also created by Astral, the makers of uv) for linting and formatting and [Pyrefly](https://pyrefly.org/) for type checking.

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

Run the type checker with the following:
```bash
uv run pyrefly check
```

## Additional Resources
- [FastAPI Documentation](https://fastapi.tiangolo.com/)
- [uv Documentation](https://docs.astral.sh/uv/)
- [pytest Documentation](https://docs.pytest.org/)
