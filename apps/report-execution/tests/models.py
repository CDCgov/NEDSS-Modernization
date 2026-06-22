import datetime
import io
import re

import pandas as pd
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

    def test_number_formatting(self):
        columns = [
            'example_small_float',
            'example_large_float',
            'example_small_int',
            'example_large_int',
        ]
        data = [(0.00000001, 123456789.23456789, 1, 1000000000000001)]

        t = models.Table(data=data, columns=columns)
        csv_str = models.serialize_table(t)

        data_line = csv_str.split('\r\n')[1]

        (
            example_small_float,
            example_large_float,
            example_small_int,
            example_large_int,
        ) = data_line.split(',')

        assert example_small_float == '0'
        assert example_large_float == '123456789.23'
        assert example_small_int == '1'
        assert example_large_int == '1000000000000001'

    def test_newline_comma_formatting(self):
        data = [
            (
                'a comment with\na new line',
                'a comment with\r\na carriage return',
                'a comment with " a quote',
                'a comment with , a comma',
                'a comment with\r\nall, the " things',
            )
        ]
        columns = ['new line', 'carriage return', 'quote', 'comma', 'all the things']

        t = models.Table(data=data, columns=columns)
        csv_str = models.serialize_table(t)
        str_io = io.StringIO(csv_str)
        df = pd.read_csv(str_io)

        assert df['new line'][0] == 'a comment with\na new line'
        assert df['carriage return'][0] == 'a comment with\r\na carriage return'
        assert df['quote'][0] == 'a comment with " a quote'
        assert df['comma'][0] == 'a comment with , a comma'
        assert df['all the things'][0] == 'a comment with\r\nall, the " things'
