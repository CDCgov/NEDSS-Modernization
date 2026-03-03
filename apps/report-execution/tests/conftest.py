import os
from contextlib import contextmanager

import pytest
from testcontainers.compose import DockerCompose

from src.models import Table


def pytest_configure(config):
    config.addinivalue_line(
        "markers", "integration: tests requiring database and/or service containers"
    )


@pytest.fixture(scope="function")
def db_credentials():
    """Mocked DB Credentials"""
    if os.getenv("DATABASE_CONN_STRING") is None:
        os.environ["DATABASE_CONN_STRING"] = "testing"


class MockTransaction:
    def execute(self, query):
        return Table(
            columns=["id", "name"], data=[(1, "a"), (2, "b"), (3, "c"), (4, "d")]
        )


@contextmanager
def mock_db_transaction_ctxt(conn_string):
    yield MockTransaction()


@pytest.fixture(scope="function")
def mock_db_transaction(mocker, db_credentials):
    mock = mocker.patch("src.execute_report.db_transaction", mock_db_transaction_ctxt)
    return mock


@pytest.fixture(scope="session")
def setup_containers(request):
    print("Setting up containers tests...")
    compose_path = os.path.join(os.path.dirname(__file__), "../../../cdc-sandbox")
    compose_file_name = "docker-compose.yml"
    containers = DockerCompose(
        compose_path,
        compose_file_name=compose_file_name,
        services=["report-execution", "nbs-mssql"],
    )
    report_exec_url = "http://0.0.0.0:8001/status"

    containers.start()
    containers.wait_for(report_exec_url)
    print("Ingestion ready to test!")

    def teardown():
        print("Service logs...\n")
        print(containers.get_logs())
        print("Tests finished! Tearing down.")
        containers.stop()

    request.addfinalizer(teardown)
