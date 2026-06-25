import unittest
from unittest.mock import MagicMock

from src.config import get_config_value, retrieve_config_value
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
    # TESTS FOR retrieve_config_value
    # ==========================================

    def test_retrieve_config_value_should_return_bracketed_name(self):
        """Should correctly frame a successful database lookup in brackets."""
        # Setup mock Table containing one valid row tuple
        mock_table = Table(columns=['config_value'], data=[('NBS_ODSE',)])
        self.mock_trx.query.return_value = mock_table

        result = retrieve_config_value(self.mock_trx, 'nbs_ods')
        self.assertEqual(result, '[NBS_ODSE]')

    def test_retrieve_config_value_should_raise_value_error_when_missing(self):
        """Should raise ValueError if the row does not exist at all."""
        # Setup mock Table to return zero data rows
        mock_table = Table(columns=['config_value'], data=[])
        self.mock_trx.query.return_value = mock_table

        with self.assertRaises(ValueError) as context:
            retrieve_config_value(self.mock_trx, 'missing_alias')

        self.assertEqual(
            str(context.exception),
            "No qualified mapping found in NBS_Configuration for "
            + "config key: 'missing_alias'",
        )

    # ==========================================
    # TESTS FOR get_config_value (Edge Cases)
    # ==========================================

    def test_get_config_value_should_return_empty_string_when_no_rows(self):
        """Should return "" cleanly if zero rows match the key query."""
        self.mock_trx.query.return_value = Table(columns=['config_value'], data=[])

        result = get_config_value(self.mock_trx, 'nbs_ods')
        self.assertEqual(result, '')

    def test_get_config_value_should_return_empty_string_on_duplicates(self):
        """Should log an error and return "" if unique constraints are broken."""
        corrupt_table = Table(columns=['config_value'], data=[('DB_ONE',), ('DB_TWO',)])
        self.mock_trx.query.return_value = corrupt_table

        result = get_config_value(self.mock_trx, 'duplicate_key')
        self.assertEqual(result, '')

    def test_get_config_value_should_return_empty_string_on_all_nulls(self):
        """Should log an error and return "" if the matching row resolves to NULL."""
        null_table = Table(columns=['config_value'], data=[(None,)])
        self.mock_trx.query.return_value = null_table

        result = get_config_value(self.mock_trx, 'null_key')
        self.assertEqual(result, '')


if __name__ == '__main__':
    unittest.main()
