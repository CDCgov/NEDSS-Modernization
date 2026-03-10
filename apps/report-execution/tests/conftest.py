import logging
import os
from contextlib import contextmanager

import pytest
from testcontainers.compose import DockerCompose

from src.models import Table


def pytest_configure(config):
    """Hook to extend pytest configuration."""
    config.addinivalue_line(
        'markers', 'integration: tests requiring database and/or service containers'
    )


@pytest.fixture(scope='function')
def db_credentials():
    """Mocked DB Credentials if none present."""
    if os.getenv('DATABASE_CONN_STRING') is None:
        os.environ['DATABASE_CONN_STRING'] = 'testing'


class MockTransaction:
    """Mock version of Transaction with public api."""

    # Pointer to the parent connection for testing purposes
    def __init__(self, cursor=None):
        self.cursor = cursor

    def execute(self, query):
        return Table(
            columns=['id', 'name'], data=[(1, 'a'), (2, 'b'), (3, 'c'), (4, 'd')]
        )


class MockConnection:
    """Mock version of db connetion."""

    def __init__(self):
        self.autocommit = None

    def setautocommit(self, val: bool):
        self.autocommit = val

    @contextmanager
    def cursor(self):
        # elide the need for a mock cursor
        yield self


@contextmanager
def mock_db_connection_ctxt(conn_string):
    """Mock context function for db_transaction."""
    yield MockConnection()


@contextmanager
def mock_db_transaction_ctxt(conn_string):
    """Mock context function for db_transaction."""
    yield MockTransaction()


@pytest.fixture(scope='function')
def mock_db_transaction(mocker, db_credentials):
    """Replace `db_transaction` calls with mock which returns static table."""
    mock = mocker.patch('src.execute_report.db_transaction', mock_db_transaction_ctxt)
    return mock


@pytest.fixture(scope='function')
def mock_db_connection(mocker):
    """Replace `mssql_python.connect` calls with mock which returns fake connection."""
    mock = mocker.patch('mssql_python.connect', mock_db_connection_ctxt)
    return mock


@pytest.fixture(scope='session')
def setup_containers(request):
    """Set up DB and report execution containers."""
    logging.info('Setting up containers tests...')
    compose_path = os.path.join(os.path.dirname(__file__), '../../../cdc-sandbox')
    compose_file_name = [
        'docker-compose.yml',
        '../apps/report-execution/tests/integration/docker-compose-custom-lib.yml',
    ]
    containers = DockerCompose(
        compose_path,
        compose_file_name=compose_file_name,
        services=['report-execution', 'nbs-mssql'],
        build=True,
        env_file=['../sample.env', '../apps/report-execution/sample.env'],
    )
    report_exec_url = 'http://0.0.0.0:8001/status'

    containers.start()
    containers.wait_for(report_exec_url)
    logging.info('Ingestion ready to test!')

    def teardown():
        logging.info('Service logs...\n')
        logging.info(containers.get_logs())
        logging.info('Tests finished! Tearing down.')
        # containers.stop()

    # request.addfinalizer(teardown)
