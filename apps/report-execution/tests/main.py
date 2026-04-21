"""Unit tests for the entrypoint of the Report Execution service."""

import io

import pandas as pd
import pytest
from fastapi.testclient import TestClient

from src.main import app


@pytest.fixture
def client():
    """Create a test client for the FastAPI application.

    This fixture provides a TestClient instance that can be used to make requests to
    the application without running a server.
    """
    return TestClient(app)


class TestHealthCheckEndpoint:
    """Tests for the health check endpoint (/status)."""

    def test_health_check_returns_success(self, client):
        """Test that the health check endpoint returns a success message."""
        response = client.get('/status')

        assert response.status_code == 200
        assert response.json() == 'Report Execution Service is up and running!'


class TestReportExecuteEndpoint:
    """Tests for the report execution endpoint (/report/execute)."""

    def test_execute_report_api_with_valid_spec(self, client, mock_db_transaction):
        """Test executing a report with a valid ReportSpec."""
        report_spec = {
            'is_export': True,
            'is_builtin': True,
            'report_title': 'Test Report',
            'library_name': 'nbs_custom',
            'data_source_name': 'random_db_table_0',
            'subset_query': 'SELECT * FROM test',
        }
        response = client.post('/report/execute', json=report_spec)

        assert response.status_code == 200
        result = response.json()
        assert result

        # check we can round trip back to DF
        content = result['content']
        assert '1,a' in content
        str_io = io.StringIO(content)
        df = pd.read_csv(str_io)
        # check numbers kept precision, but not overly so
        assert df['id'][0] == 1
        assert df['id'][1] == 2.5
        assert df['id'][2] == 3
        assert df['id'][3] == 3.5
        assert df.shape == (4, 2)

    def test_execute_report_api(self, client, mock_db_transaction):
        """Test executing a report."""
        report_spec = {
            'is_export': False,
            'is_builtin': True,
            'report_title': 'Time-based Report',
            'library_name': 'nbs_custom',
            'data_source_name': 'random_db_table_1',
            'subset_query': 'SELECT * FROM events WHERE date > ?',
        }
        response = client.post('/report/execute', json=report_spec)

        assert response.status_code == 200
        assert response.json()

    def test_execute_report_api_missing_required_fields(self, client):
        """Test that missing required fields return a validation error."""
        incomplete_spec = {
            'report_title': 'Incomplete Report',
        }
        response = client.post('/report/execute', json=incomplete_spec)

        assert response.status_code == 422  # Unprocessable Entity

    def test_execute_report_api_invalid_field_types(self, client):
        """Test that invalid field types return a validation error."""
        invalid_spec = {
            'is_export': 'not_a_boolean',
            'is_builtin': True,
            'report_title': 'Test Report',
            'library_name': 'nbs_custom',
            'data_source_name': 'random_db_table_3',
            'subset_query': 'SELECT * FROM test',
        }
        response = client.post('/report/execute', json=invalid_spec)

        assert response.status_code == 422  # Unprocessable Entity

    def test_execute_report_api_invalid_library_name(self, client):
        """Test that invalid name returns a validation error."""
        invalid_spec = {
            'is_export': True,
            'is_builtin': True,
            'report_title': 'Test Report',
            'library_name': 'missing_library',
            'data_source_name': 'random_db_table_3',
            'subset_query': 'SELECT * FROM test',
        }
        response = client.post('/report/execute', json=invalid_spec)

        assert response.status_code == 422  # Unprocessable Entity
        assert response.json() == {
            'message': 'Library `missing_library` (is_builtin: True) not found'
        }
