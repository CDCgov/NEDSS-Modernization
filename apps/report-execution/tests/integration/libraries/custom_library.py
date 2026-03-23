import http.client
import json

import pytest


@pytest.mark.usefixtures('setup_containers')
@pytest.mark.integration
class TestCustomLibrary:
    """Integration tests for custom library execution."""

    def test_custom_library_runs(self):
        report_spec = {
            'version': 1,
            'is_export': True,
            'is_builtin': False,
            'report_title': 'Test Report',
            'library_name': 'custom_library',
            # Filter code is used here as it is a stable, small table
            'data_source_name': '[NBS_ODSE].[dbo].[Filter_code]',
            'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[Filter_code]',
        }

        connection = http.client.HTTPConnection('localhost:8001')

        headers = {'Content-type': 'application/json'}
        body = json.dumps(report_spec)

        connection.request('POST', '/report/execute', body, headers)

        response = connection.getresponse()
        assert response.status == 200

        result = json.loads(response.read())
        assert result['description'] == 'Custom pass through query'
