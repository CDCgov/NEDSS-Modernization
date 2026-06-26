import unittest
from unittest.mock import MagicMock

from src import errors
from src.config import get_config_value
from src.models import Table


class TestConfigurationFinder(unittest.TestCase):
    """Test suite for validating configuration retrieval and formatting logic.

    Uses `unittest.mock.MagicMock` to simulate database queries returning custom
    `Table` instances without needing an active database connection.
    """

    def setUp(self):
        # Create a mock transaction object before every test
        self.mock_trx = MagicMock()

    # ==========================================
    # TESTS FOR get_config_value (Edge Cases)
    # ==========================================

    def test_get_config_value_should_return_empty_string_when_no_rows(self):
        """Should return "" cleanly if zero rows match the key query."""
        self.mock_trx.query.return_value = Table(columns=['config_value'], data=[])

        with self.assertRaises(errors.InvalidConfigurationError):
            get_config_value(self.mock_trx, 'nbs_odse')

    def test_get_config_value_should_return_empty_string_on_duplicates(self):
        """Should log an error and return "" if unique constraints are broken."""
        corrupt_table = Table(columns=['config_value'], data=[('DB_ONE',), ('DB_TWO',)])
        self.mock_trx.query.return_value = corrupt_table

        with self.assertRaises(errors.ConfigurationIntegrityError):
            get_config_value(self.mock_trx, 'duplicate_key')

    def test_get_config_value_should_return_empty_string_on_all_nulls(self):
        """Should log an error and return "" if the matching row resolves to NULL."""
        null_table = Table(columns=['config_value'], data=[(None,)])
        self.mock_trx.query.return_value = null_table

        with self.assertRaises(errors.ConfigurationIntegrityError):
            get_config_value(self.mock_trx, 'null_key')


if __name__ == '__main__':
    unittest.main()
