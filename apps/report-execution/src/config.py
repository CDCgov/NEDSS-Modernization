from src.db_transaction import Transaction

from . import errors


def get_config_value(trx: Transaction, config_key: str) -> str:
    """Retrieves a configuration value from NBS_ODSE..NBS_configuration."""
    query = """
            SELECT COALESCE(config_value, default_value)
            FROM NBS_ODSE..NBS_configuration
            WHERE config_key = ? \
            """

    # trx.query returns a Table object. table.data holds row tuples.
    table = trx.query(query, (config_key,))

    # Handle Empty Result
    if not table.data:
        raise errors.InvalidConfigurationError(config_key)

    # Handle Duplicate Keys
    if len(table.data) > 1:
        raise errors.ConfigurationIntegrityError(
            f'Multiple rows found in NBS_Configuration for config key: {config_key}'
        )

    val = table.data[0][0]

    # Handle Completely Null Payload
    if val is None:
        raise errors.ConfigurationIntegrityError(
            f'Config key {config_key} exists in NBS_Configuration, but both '
            + 'config_value and default_value are null'
        )

    return str(val).strip()
