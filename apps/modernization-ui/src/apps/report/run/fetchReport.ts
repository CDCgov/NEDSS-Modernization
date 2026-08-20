import { ReportExecutionRequest } from 'generated';
import { ApiResult } from 'generated/core/ApiResult';
import { catchErrorCodes, getResponseBody } from 'generated/core/request';

// Manually invoking this endpoint instead of using the generated API client
// because said API client doesn't support the streamed CSV body as we need due to
// 1) it will read it all into a string, which is find for run, but not export; and
// 2) it doesn't give us access to the headers, where additional metadata is passed back
// Matching the signature of the generated endpoint for consistency.
const fetchReport = async ({ requestBody }: { requestBody: ReportExecutionRequest }) => {
    const { isExport } = requestBody;
    const url = `/nbs/api/report/${isExport ? 'export' : 'run'}`;
    const method = 'POST';
    return fetch(url, {
        method,
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(requestBody),
    }).then(async (response) => {
        if (!response.ok) {
            // only read body when we're in the error case to allow consumer to read as they need it
            const responseBody = await getResponseBody(response);

            //  Duplicating error handling behavior defined in generated API client
            const result: ApiResult = {
                url,
                ok: response.ok,
                status: response.status,
                statusText: response.statusText,
                body: responseBody,
            };
            catchErrorCodes({ url, method }, result);
        }

        return response;
    });
};

export { fetchReport };
