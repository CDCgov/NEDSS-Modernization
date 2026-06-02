import datetime
import os
import re

from faker import Faker

from src import models


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

    def test_serialize_table_with_env_vars_for_date_formatting(self, monkeypatch):
        with monkeypatch.context() as m:
            m.setenv('REPORT_EXPORT_DATE_FORMAT', '%d-%m-%Y')
            m.setenv('REPORT_EXPORT_DATETIME_FORMAT', '%d-%m-%Y %H:%M')

            columns = ['example_date', 'example_datetime']
            data = [(datetime.date(1985, 4, 13), datetime.datetime(1985, 4, 13, 4, 15))]
            t = models.Table(data=data, columns=columns)

            csv_str = models.serialize_table(t)
            lines = csv_str.split('\r\n')

            d = lines[1].split(',')[0]
            dt = lines[1].split(',')[1]

            assert d == '13-04-1985'
            assert dt == '13-04-1985 04:15'

