"""Unit tests for the report-execution FastAPI application."""

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


class TestRootEndpoint:
    """Tests for the root endpoint (/)."""

    def test_read_root_returns_hello_world(self, client):
        """Test that the root endpoint returns the expected greeting."""
        response = client.get("/")

        assert response.status_code == 200
        assert response.json() == {"Hello": "World"}

    def test_read_root_content_type(self, client):
        """Test that the root endpoint returns JSON content type."""
        response = client.get("/")

        assert response.headers["content-type"] == "application/json"


class TestItemsEndpoint:
    """Tests for the items endpoint (/items/{item_id})."""

    def test_read_item_with_valid_id(self, client):
        """Test reading an item with a valid integer ID."""
        item_id = 42
        response = client.get(f"/items/{item_id}")

        assert response.status_code == 200
        assert response.json() == {"item_id": item_id, "q": None}

    def test_read_item_with_query_parameter(self, client):
        """Test reading an item with an optional query parameter."""
        item_id = 123
        query_value = "test-query"
        response = client.get(f"/items/{item_id}", params={"q": query_value})

        assert response.status_code == 200
        assert response.json() == {"item_id": item_id, "q": query_value}

    def test_read_item_with_zero_id(self, client):
        """Test reading an item with ID zero."""
        response = client.get("/items/0")

        assert response.status_code == 200
        assert response.json() == {"item_id": 0, "q": None}

    def test_read_item_with_negative_id(self, client):
        """Test reading an item with a negative ID."""
        response = client.get("/items/-1")

        assert response.status_code == 200
        assert response.json() == {"item_id": -1, "q": None}

    def test_read_item_with_invalid_id_type(self, client):
        """Test that non-integer IDs return a validation error."""
        response = client.get("/items/not-a-number")

        assert response.status_code == 422  # Unprocessable Entity
        assert "detail" in response.json()

    @pytest.mark.parametrize("item_id,query,expected", [
        (1, None, {"item_id": 1, "q": None}),
        (100, "search", {"item_id": 100, "q": "search"}),
        (999, "", {"item_id": 999, "q": ""}),
        (5, "complex query string", {"item_id": 5, "q": "complex query string"}),
    ])
    def test_read_item_parametrized(self, client, item_id, query, expected):
        """Test multiple combinations of item_id and query parameters."""
        params = {"q": query} if query is not None else {}
        response = client.get(f"/items/{item_id}", params=params)

        assert response.status_code == 200
        assert response.json() == expected

    def test_read_item_content_type(self, client):
        """Test that the items endpoint returns JSON content type."""
        response = client.get("/items/1")

        assert response.headers["content-type"] == "application/json"
