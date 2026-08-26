import http.client
import json

import pytest
import yaml


@pytest.mark.usefixtures('setup_containers')
@pytest.mark.integration
class TestCustomLibrary:
    """Integration tests for custom library execution."""

    def test_custom_library_runs(self, snapshot):
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
        body = response.read().decode('UTF-8')
        assert len(body) > 10

        snapshot.assert_match(yaml.dump(body), 'snapshot.yml')
