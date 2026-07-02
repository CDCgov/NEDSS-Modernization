from typing import TYPE_CHECKING

from . import errors

# This block is evaluated as True ONLY by IDEs and type checkers,
# completely skipping execution at application runtime.
if TYPE_CHECKING:
    from src.db_transaction import Transaction

# Module-level cache dictionary
_CONFIG_CACHE: dict[str, str] = {}


def get_config_value(trx: Transaction, config_key: str) -> str:
    """Retrieves a configuration value from NBS_configuration, with a cache for
    subsequent lookups. Automatically handles case-insensitivity.
    """
    normalized_key = config_key.upper()

    # check the cache
    if normalized_key in _CONFIG_CACHE:
        return _CONFIG_CACHE[normalized_key]

    query = """
            SELECT config_value
            FROM NBS_ODSE..NBS_configuration
            WHERE config_key = ? \
            """

    # can't use 'query' as it needs this config to do row limit checks
    data = trx._cursor.execute(query, (normalized_key,)).fetchall()

    if not data:
        raise errors.InvalidConfigurationError(config_key)

    # Handle duplicate keys
    if len(data) > 1:
        raise errors.ConfigurationIntegrityError(
            f'Multiple rows found in NBS_Configuration for config key: {config_key}'
        )

    val = data[0][0]

    # Handle empty config and default values
    if val is None:
        raise errors.ConfigurationIntegrityError(
            f'Config key {config_key} exists in NBS_Configuration, but both '
            + 'config_value and default_value are null'
        )

    # Populate the cache with the normalized key
    resolved_value = str(val).strip()
    _CONFIG_CACHE[normalized_key] = resolved_value

    return resolved_value


def load_report_configurations(trx: Transaction) -> None:
    """Fetches and caches config keys needed by the report execution."""
    config_keys = [
        'REPORT_DB_NBS_RDB',
        'REPORT_DB_NBS_ODS',
        'REPORT_DB_NBS_SRT',
        'REPORT_DB_NBS_MSG',
        'REPORT_MAX_ROW_LIMIT_RUN',
        'REPORT_MAX_ROW_LIMIT_EXPORT',
        'REPORT_EXPORT_DATE_FORMAT',
        'REPORT_EXPORT_DATETIME_FORMAT',
    ]
    for key in config_keys:
        get_config_value(trx, key)


def get_cached_config_value(config_key: str) -> str:
    """Retrieves a configuration value from the cache.

    Raises:
        InvalidConfigurationError: If the key was never loaded, indicating
        a system lifecycle or development defect.
    """
    normalized_key = config_key.upper()

    if normalized_key not in _CONFIG_CACHE:
        raise errors.InvalidConfigurationError(
            f"Configuration key '{config_key}' was accessed but was never primed. "
            f'Please ensure it is registered in prime_report_configurations().'
        )

    return _CONFIG_CACHE[normalized_key]


def clear_config_cache():
    """Helper utility to clear the cache during testing."""
    _CONFIG_CACHE.clear()
