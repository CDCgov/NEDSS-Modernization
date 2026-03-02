"""Unit tests for the entrypoint of the Report Execution service."""

import io
import pytest
from fastapi.testclient import TestClient
from src.main import app
import pandas as pd


@pytest.fixture
def client():
    """Create a test client for the FastAPI application.

    This fixture provides a TestClient instance that can be used
    to make requests to the application without running a server.
    """
    return TestClient(app)


class TestHealthCheckEndpoint:
    """Tests for the health check endpoint (/status)."""

    def test_health_check_returns_success(self, client):
        """Test that the health check endpoint returns a success message."""
        response = client.get("/status")

        assert response.status_code == 200
        assert response.json() == "Report Execution Service is up and running!"


class TestReportExecuteEndpoint:
    """Tests for the report execution endpoint (/report/execute)."""

    def test_execute_report_api_with_valid_spec(self, client, mock_db_transaction):
        """Test executing a report with a valid ReportSpec."""
        report_spec = {
            "version": 1,
            "is_export": True,
            "is_builtin": True,
            "report_title": "Test Report",
            "library_name": "hello_world",
            "data_source_name": "random_db_table_0",
            "subset_query": "SELECT * FROM test",
        }
        response = client.post("/report/execute", json=report_spec)

        assert response.status_code == 200
        result = response.json()
        assert result

        # check we can round trip back to DF
        str_io = io.StringIO(result["content"])
        df = pd.read_csv(str_io)
        assert df.shape == (4, 2)

    def test_execute_report_api_with_time_range(self, client, mock_db_transaction):
        """Test executing a report with an optional time range."""
        report_spec = {
            "version": 1,
            "is_export": False,
            "is_builtin": True,
            "report_title": "Time-based Report",
            "library_name": "hello_world",
            "data_source_name": "random_db_table_1",
            "subset_query": "SELECT * FROM events WHERE date > ?",
            "time_range": {"start": "2024-01-01", "end": "2024-12-31"},
        }
        response = client.post("/report/execute", json=report_spec)

        assert response.status_code == 200
        assert response.json()

    def test_execute_report_api_without_time_range(self, client, mock_db_transaction):
        """Test executing a report without providing time_range."""
        report_spec = {
            "version": 2,
            "is_export": True,
            "is_builtin": True,
            "report_title": "Simple Report",
            "library_name": "hello_world",
            "data_source_name": "random_db_table_2",
            "subset_query": "SELECT COUNT(*) FROM users",
        }
        response = client.post("/report/execute", json=report_spec)

        assert response.status_code == 200
        assert response.json()

    def test_execute_report_api_missing_required_fields(self, client):
        """Test that missing required fields return a validation error."""
        incomplete_spec = {
            "version": 1,
            "report_title": "Incomplete Report",
        }
        response = client.post("/report/execute", json=incomplete_spec)

        assert response.status_code == 422  # Unprocessable Entity

    def test_execute_report_api_invalid_field_types(self, client):
        """Test that invalid field types return a validation error."""
        invalid_spec = {
            "version": "not_an_int",
            "is_export": True,
            "is_builtin": True,
            "report_title": "Test Report",
            "library_name": "hello_world",
            "data_source_name": "random_db_table_3",
            "subset_query": "SELECT * FROM test",
        }
        response = client.post("/report/execute", json=invalid_spec)

        assert response.status_code == 422  # Unprocessable Entity

    def test_execute_report_api_invalid_library_name(self, client):
        """Test that invalid name returns a validation error."""
        invalid_spec = {
            "version": 1,
            "is_export": True,
            "is_builtin": True,
            "report_title": "Test Report",
            "library_name": "missing_library",
            "data_source_name": "random_db_table_3",
            "subset_query": "SELECT * FROM test",
        }
        response = client.post("/report/execute", json=invalid_spec)

        assert response.status_code == 422  # Unprocessable Entity
        assert response.json() == {
            "message": "Library `missing_library` (is_builtin: True) not found"
        }
