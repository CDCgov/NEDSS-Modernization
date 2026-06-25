import logging

from src.db_transaction import Transaction


def retrieve_config_value(trx: Transaction, config_key: str) -> str:
    """Translates a database alias into its standardized, real SQL name."""
    config_key_normalized = config_key.lower().strip()

    mapped_config_value = get_config_value(trx, config_key_normalized)

    if not mapped_config_value:
        raise ValueError(
            f'No qualified mapping found in NBS_Configuration for '
            f"config key: '{config_key}'"
        )

    return f'[{mapped_config_value}]'


def get_config_value(trx: Transaction, key: str) -> str:
    """Retrieves a configuration value from NBS_ODSE..NBS_configuration."""
    query = """
            SELECT COALESCE(config_value, default_value)
            FROM NBS_ODSE..NBS_configuration
            WHERE config_key = ? \
            """

    # trx.query returns a Table object. table.data holds row tuples.
    table = trx.query(query, (key,))

    # Handle Empty Result
    if not table.data:
        logging.error(f'Zero rows found in NBS_Configuration for config key: {key}')
        return ''

    # Handle Duplicate Keys
    if len(table.data) > 1:
        logging.error(f'Multiple rows found in NBS_Configuration for config key: {key}')
        return ''

    val = table.data[0][0]

    # Handle Completely Null Payload
    if val is None:
        logging.error(
            f'Config key {key} exists in NBS_Configuration, but both config_value and '
            f'default_value are null'
        )
        return ''

    return str(val).strip()
