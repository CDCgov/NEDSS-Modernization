import os
import sys

# hack to import project functions  # noqa: FIX004
sys.path.append(os.path.join(os.path.dirname(__file__), '..'))

from src.utils import get_env_or_error
from tests.conftest import get_faker_sql, get_tables_from_faker, insert_fake_data

faker_schema = sys.argv[1]

(db_tables, fk_tables) = get_tables_from_faker(faker_schema)
faker_sql = get_faker_sql(faker_schema)

conn_string = get_env_or_error('DATABASE_CONN_STRING')

insert_fake_data(conn_string, faker_sql, db_tables, fk_tables)
