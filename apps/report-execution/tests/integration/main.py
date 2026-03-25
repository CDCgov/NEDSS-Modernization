import http.client
import json
import os

import pytest


@pytest.mark.usefixtures('setup_containers')
@pytest.mark.integration
class TestMainApp:
    """Integration tests for rest API endpoint access."""

    def test_report_runs(self):
        report_spec = {
            'version': 1,
            'is_export': True,
            'is_builtin': True,
            'report_title': 'Test Report',
            'library_name': 'nbs_custom',
            # Filter code is used here as it is a stable, small table
            'data_source_name': '[NBS_ODSE].[dbo].[Filter_code]',
            'subset_query': 'SELECT * FROM [NBS_ODSE].[dbo].[Filter_code]',
        }

        connection = http.client.HTTPConnection(
            f'localhost:{os.getenv("UVICORN_PORT", "8001")}'
        )

        headers = {'Content-type': 'application/json'}
        body = json.dumps(report_spec)

        connection.request('POST', '/report/execute', body, headers)

        response = connection.getresponse()
        assert response.status == 200

        result = json.loads(response.read())
        assert (
            result['header']
            == 'Custom Report For Table: [NBS_ODSE].[dbo].[Filter_code]'
        )
