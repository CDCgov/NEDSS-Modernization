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
            'is_export': True,
            'is_builtin': True,
            'library_name': 'nbs_custom',
            # Filter code is used here as it is a stable, small table
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

        assert result is not None

        for k in ['content', 'context_header', 'description']:
            assert k in result.keys()

        assert len(result['content']) > 0

        exepected_cols = [
            'filter_uid',
            'code_table',
            'desc_txt',
            'effective_from_time',
            'effective_to_time',
            'filter_code',
            'filter_code_set_nm',
            'filter_type',
            'filter_name',
            'status_cd',
            'status_time',
        ]
        found_cols = result['content'].split('\r\n')[0].split(',')

        assert found_cols == exepected_cols
