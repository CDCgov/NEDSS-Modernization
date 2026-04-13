import os
import sys

# hack to import project functions  # noqa: FIX004
sys.path.append(os.path.join(os.path.dirname(__file__), '..'))

from src.utils import get_env_or_error
from tests.conftest import get_tables_from_faker, restore_original_data

faker_schema = sys.argv[1]

(db_tables, fk_tables) = get_tables_from_faker(faker_schema)

conn_string = get_env_or_error('DATABASE_CONN_STRING')

restore_original_data(conn_string, db_tables, fk_tables)
