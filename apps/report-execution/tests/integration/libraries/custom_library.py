import http.client
import json

import pytest


@pytest.mark.usefixtures('download_custom_library')
@pytest.mark.usefixtures('setup_containers')
@pytest.mark.integration
class TestCustomLibrary:
    """Integration tests for custom library execution."""

    def test_custom_library_runs(self):
        report_spec = {
            'is_export': True,
            'is_builtin': False,
            'library_name': 'custom_library',
            # Filter code is used here as it is a stable, small table
            'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[Filter_code]',
        }

        connection = http.client.HTTPConnection('localhost:8001')

        headers = {'Content-type': 'application/json'}
        body = json.dumps(report_spec)

        connection.request('POST', '/report/execute', body, headers)

        response = connection.getresponse()
        assert response.status == 200

        assert (
            response.headers['X-Report-Description']
            == 'Custom pass through query%n%n        It is many lines _with_ *markdown*'
        )
        assert response.headers['X-Report-Context-Header'] == 'custom header'
        body = response.read()
        assert len(body) > 10

    def test_example_library_runs(self):
        """This method tests the example library file found in the
        NEDSS-Custom-Library-Example repository.  File is downloaded via pytest fixture
        prior to this test runing.
        """
        report_spec = {
            'is_export': True,
            'is_builtin': False,
            'library_name': 'custom_lib_repo_example',
            'sort_by': '[Code Table]',
            # Filter code is used here as it is a stable, small table
            'subset_query': (
                'SELECT code_table AS [Code Table] FROM [NBS_ODSE].[dbo].[Filter_code]'
            ),
            'column_map': [['code_table', 'Code Table']],
        }

        connection = http.client.HTTPConnection('localhost:8001')

        headers = {'Content-type': 'application/json'}
        body = json.dumps(report_spec)

        connection.request('POST', '/report/execute', body, headers)

        response = connection.getresponse()
        assert response.status == 200

        body = response.read()
        assert len(body) > 10
