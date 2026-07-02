import unittest
from unittest.mock import MagicMock

from src import errors
from src.config import clear_config_cache, get_config_value
from src.models import Table


class TestConfigurationFinder(unittest.TestCase):
    """Test suite for validating configuration retrieval, formatting, and caching logic.

    Uses `unittest.mock.MagicMock` to simulate database queries returning custom
    `Table` instances without needing an active database connection.
    """

    def setUp(self):
        # Create a mock transaction object and wipe the memory cache before every test
        self.mock_trx = MagicMock()
        clear_config_cache()

    # ==========================================
    # TESTS FOR get_config_value (Caching Hooks)
    # ==========================================

    def test_get_config_value_caches_result_on_subsequent_calls(self):
        """Should query the database on first access then hit memory cache after."""
        valid_data = [('10000',)]
        self.mock_trx._cursor.execute.return_value.fetchall.return_value = valid_data

        # First Call: Database query executed
        first_result = get_config_value(self.mock_trx, 'TEST')
        self.assertEqual(first_result, '10000')
        self.assertEqual(self.mock_trx._cursor.execute.call_count, 1)

        # Alter the database mock return value. If the cache fails and hits the database
        # it will pull this new bad data.
        invalid_data = [('99999',)]
        self.mock_trx._cursor.execute.return_value.fetchall.return_value = invalid_data

        # Second Call: Should pull from the cache
        second_result = get_config_value(self.mock_trx, 'TEST')
        self.assertEqual(second_result, '10000')  # Returns original cached value
        self.assertEqual(
            self.mock_trx._cursor.execute.return_value.fetchall.call_count, 1
        )  # DB call count stays at 1

    def test_clear_config_cache_forces_database_re_read(self):
        """Should re-query the database if cache is cleared."""
        valid_data = [('10000',)]
        self.mock_trx._cursor.execute.return_value.fetchall.return_value = valid_data

        # Initial lookup to populate the cache
        get_config_value(self.mock_trx, 'TEST')
        self.assertEqual(
            self.mock_trx._cursor.execute.return_value.fetchall.call_count, 1
        )

        # Explicit cache clear
        clear_config_cache()

        # Lookup after clear should trigger a new database trip
        get_config_value(self.mock_trx, 'TEST')
        self.assertEqual(
            self.mock_trx._cursor.execute.return_value.fetchall.call_count, 2
        )

    # ==========================================
    # TESTS FOR get_config_value (Edge Cases)
    # ==========================================

    def test_get_config_value_raises_invalid_config_error_when_no_rows(self):
        """Should throw an InvalidConfigurationError if zero rows match the key."""
        self.mock_trx._cursor.execute.return_value.fetchall.return_value = []

        with self.assertRaises(errors.InvalidConfigurationError):
            get_config_value(self.mock_trx, 'nbs_odse')

    def test_get_config_value_raises_integrity_error_on_duplicates(self):
        """Should throw a ConfigurationIntegrityError if unique tracking is broken."""
        corrupt_table = [('DB_ONE',), ('DB_TWO',)]
        self.mock_trx._cursor.execute.return_value.fetchall.return_value = corrupt_table

        with self.assertRaises(errors.ConfigurationIntegrityError):
            get_config_value(self.mock_trx, 'duplicate_key')

    def test_get_config_value_raises_integrity_error_on_all_nulls(self):
        """Should throw a ConfigurationIntegrityError if matching record is NULL."""
        null_table = [(None,)]
        self.mock_trx._cursor.execute.return_value.fetchall.return_value = null_table

        with self.assertRaises(errors.ConfigurationIntegrityError):
            get_config_value(self.mock_trx, 'null_key')


if __name__ == '__main__':
    unittest.main()
