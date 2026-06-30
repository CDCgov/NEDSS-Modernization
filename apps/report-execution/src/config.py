import logging

from src.db_transaction import Transaction
from . import errors

# Module-level cache dictionary
_CONFIG_CACHE: dict[str, str] = {}


def get_config_value(trx: Transaction, config_key: str) -> str:
    """Retrieves a configuration value from NBS_configuration, with a cache for
    subsequent lookups.
    """
    # check the cache
    if config_key in _CONFIG_CACHE:
        return _CONFIG_CACHE[config_key]

    query = """
            SELECT COALESCE(config_value, default_value)
            FROM NBS_ODSE..NBS_configuration
            WHERE config_key = ? \
            """

    table = trx.query(query, (config_key,))

    if not table.data:
        raise errors.InvalidConfigurationError(config_key)

    # Handle duplicate keys
    if len(table.data) > 1:
        raise errors.ConfigurationIntegrityError(
            f'Multiple rows found in NBS_Configuration for config key: {config_key}'
        )

    val = table.data[0][0]

    # Handle empty config and default values
    if val is None:
        raise errors.ConfigurationIntegrityError(
            f'Config key {config_key} exists in NBS_Configuration, but both '
            + 'config_value and default_value are null'
        )

    # Populate the cache with the pulled value
    resolved_value = str(val).strip()
    _CONFIG_CACHE[config_key] = resolved_value

    return resolved_value


def clear_config_cache():
    """
    Helper utility to clear the cache during testing
    """
    _CONFIG_CACHE.clear()
