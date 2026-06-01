import datetime
import re
from src import models

from faker import Faker


class TestModels:
    """Tests for the Models module."""

    def test_serialize_table_formats_dates_correctly(self):
        f = Faker()

        columns = ['example_date', 'example_datetime']
        data = []
        for _ in range(20):
            data.append(
                (
                    datetime.date.fromisoformat(f.date()),
                    f.date_time(),
                )
            )

        t = models.Table(data=data, columns=columns)
        csv_str = models.serialize_table(t)

        date_re = re.compile(r'^\d{2}/\d{2}/\d{4}$')
        datetime_re = re.compile(r'^\d{2}/\d{2}/\d{4} \d{2}:\d{2}:\d{2}$')

        for line in csv_str.split('\r\n')[1:]:
            d, dt = line.split(',')

            assert date_re.match(d) is not None
            assert datetime_re.match(dt) is not None
