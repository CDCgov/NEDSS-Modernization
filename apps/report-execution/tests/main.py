"""Unit tests for the entrypoint of the Report Execution service."""

import pytest
from fastapi.testclient import TestClient

from src.main import app


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

    def test_execute_report_with_valid_spec(self, client):
        """Test executing a report with a valid ReportSpec."""
        report_spec = {
            "version": 1,
            "is_export": True,
            "report_title": "Test Report",
            "library_name": "test_library",
            "data_source_name": "random_db_table_0",
            "subset_query": "SELECT * FROM test",
        }
        response = client.post("/report/execute", json=report_spec)

        assert response.status_code == 200
        assert response.json() == {
            "report_title": "Test Report",
            "library_name": "test_library",
        }

    def test_execute_report_with_time_range(self, client):
        """Test executing a report with an optional time range."""
        report_spec = {
            "version": 1,
            "is_export": False,
            "report_title": "Time-based Report",
            "library_name": "analytics",
            "data_source_name": "random_db_table_1",
            "subset_query": "SELECT * FROM events WHERE date > ?",
            "time_range": {"start": "2024-01-01", "end": "2024-12-31"},
        }
        response = client.post("/report/execute", json=report_spec)

        assert response.status_code == 200
        assert response.json() == {
            "report_title": "Time-based Report",
            "library_name": "analytics",
        }

    def test_execute_report_without_time_range(self, client):
        """Test executing a report without providing time_range."""
        report_spec = {
            "version": 2,
            "is_export": True,
            "report_title": "Simple Report",
            "library_name": "basic_lib",
            "data_source_name": "random_db_table_2",
            "subset_query": "SELECT COUNT(*) FROM users",
        }
        response = client.post("/report/execute", json=report_spec)

        assert response.status_code == 200
        assert response.json() == {
            "report_title": "Simple Report",
            "library_name": "basic_lib",
        }

    def test_execute_report_missing_required_fields(self, client):
        """Test that missing required fields return a validation error."""
        incomplete_spec = {
            "version": 1,
            "report_title": "Incomplete Report",
        }
        response = client.post("/report/execute", json=incomplete_spec)

        assert response.status_code == 422  # Unprocessable Entity

    def test_execute_report_invalid_field_types(self, client):
        """Test that invalid field types return a validation error."""
        invalid_spec = {
            "version": "not_an_int",
            "is_export": True,
            "report_title": "Test Report",
            "library_name": "test_library",
            "data_source_name": "random_db_table_3",
            "subset_query": "SELECT * FROM test",
        }
        response = client.post("/report/execute", json=invalid_spec)

        assert response.status_code == 422  # Unprocessable Entity
