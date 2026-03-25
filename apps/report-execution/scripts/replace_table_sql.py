import os
import sys

# hack to import project functions  # noqa: FIX004
sys.path.append(os.path.join(os.path.dirname(__file__), '..'))

from src.utils import get_env_or_error
from tests.conftest import insert_fake_data

sql_file_path = sys.argv[1]
db_table = sys.argv[2]

conn_string = get_env_or_error('DATABASE_CONN_STRING')

with open(sql_file_path) as f:
    sql = f.read()
    insert_fake_data(
        conn_string,
        sql,
        [db_table],
        []
    )
