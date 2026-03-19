import os
import sys

# hack to import project functions  # noqa: FIX004
sys.path.append(os.path.join(os.path.dirname(__file__), '..'))

from tests.conftest import get_faker_sql

schema_name = sys.argv[1]

sql = get_faker_sql(schema_name)

with open('out.sql', 'w') as f:
    f.write(sql)
