import logging
import os
from contextlib import contextmanager

import pytest
import tablefaker
from testcontainers.compose import ContainerIsNotRunning, DockerCompose

from src import utils
from src.db_transaction import db_transaction
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
    services = ['report-execution', 'nbs-mssql']
    compose_file_names = [
        'docker-compose.yml',
        '../apps/report-execution/tests/integration/docker-compose.yml',
    ]
    containers = DockerCompose(
        compose_path,
        compose_file_name=compose_file_names,
        services=services,
        env_file=['../sample.env', '../apps/report-execution/sample.env'],
        build=True,
    )
    report_exec_url = 'http://0.0.0.0:8001/status'

    def maybe_get_container(name):
        try:
            containers.get_container(name)
        except ContainerIsNotRunning:
            return None

    containers_to_stop = [
        maybe_get_container(service)
        for service in services
        if maybe_get_container(service) is not None
    ]

    containers.start()
    containers.wait_for(report_exec_url)
    logging.info('Ingestion ready to test!')

    def teardown():
        logging.info('Service logs...\n')
        logging.info('Tests finished! Tearing down.')
        for container in containers_to_stop:
            container.stop()

    request.addfinalizer(teardown)


def get_faker_sql(schema_name: str) -> str:
    """Process a fakertable schema and return the sql as a string."""
    faker_path = os.path.join(
        os.path.dirname(__file__),
        'integration',
        'assets',
        'tablefaker_schema',
        schema_name,
    )
    target_file_path = os.path.join(os.path.dirname(__file__), 'fake.sql')
    tablefaker.to_sql(faker_path, target_file_path=target_file_path)
    with open(target_file_path) as f:
        result = f.read()

    os.remove(target_file_path)

    # KLUDGE: NULL writing is not always correct
    result = result.replace(' nan,', ' NULL,')
    result = result.replace(' nan)', ' NULL)')
    result = result.replace(' <NA>,', ' NULL,')
    result = result.replace(' <NA>)', ' NULL)')
    return result


def temp_name(table_name: str) -> str:
    """Assumes `[schema].[dbo].[table name]` format.

    Not using temp tables as the usage spans connections.
    """
    return table_name[0:-1] + '_temp]'


@pytest.fixture(scope='class')
def fake_db_table(request):
    """Replace a DB table with fake table per the tablefaker schema."""
    db_table = request.module.db_table
    fk_tables = getattr(request.module, 'db_fk_tables', [])
    faker_schema = request.module.faker_schema
    faker_sql = get_faker_sql(faker_schema)

    conn_string = utils.get_env_or_error('DATABASE_CONN_STRING')

    # swap out original data for fake data
    with db_transaction(conn_string) as trx:
        # Tables with foreign keys pointing to the table we want to replace need to
        # be backed up and cleared out to avoid FK constraint violations
        for fk_table in fk_tables:
            temp_fk_table = temp_name(fk_table)
            trx.execute(
                f"IF OBJECT_ID('{temp_fk_table}') IS NOT NULL "
                f'DROP TABLE {temp_fk_table}'
            )
            trx.execute(f'SELECT * INTO {temp_fk_table} FROM {fk_table}')
            trx.execute(f'DELETE {fk_table}')
            logging.info(f'cleared FK table: {fk_table}')

        temp_db_table = temp_name(db_table)
        trx.execute(
            f"IF OBJECT_ID('{temp_db_table}') IS NOT NULL DROP TABLE {temp_db_table}"
        )
        trx.execute(f'SELECT * INTO {temp_db_table} FROM {db_table}')
        trx.execute(f'DELETE {db_table}')
        logging.info(f'cleared table: {db_table}')
        trx.execute(faker_sql)
        logging.info(f'Inserted fake data: {db_table}')

    # avoid connection inside connection
    yield

    # restore the original data
    with db_transaction(conn_string) as trx:
        trx.execute(f'DELETE {db_table}')
        trx.execute(f'INSERT INTO {db_table} SELECT * FROM {temp_db_table}')
        logging.info(f'Restored table: {db_table}')

        for fk_table in fk_tables:
            trx.execute(f'INSERT INTO {fk_table} SELECT * FROM {temp_name(fk_table)}')
            logging.info(f'Restored FK table: {fk_table}')
