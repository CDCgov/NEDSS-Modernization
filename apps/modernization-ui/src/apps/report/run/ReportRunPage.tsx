import { BaseSyntheticEvent } from 'react';
import { useCallback, useState } from 'react';

import fileDownload from 'js-file-download';
import { FormProvider, useForm } from 'react-hook-form';
import { useLoaderData, useParams } from 'react-router';

import { ApiResult } from 'apps/page-builder/generated/core/ApiResult';
import { catchErrorCodes, getResponseBody } from 'apps/page-builder/generated/core/request';
import { ApiErrorBanner } from 'design-system/errors/ApiError';
import {
    AdvancedFilterRequest,
    BasicFilterRequest,
    ReportConfiguration,
    ReportExecutionRequest,
    SortSpec,
} from 'generated';
import { LoadingBlock } from 'libs/loading/block';
import { permissions, permitsAll } from 'libs/permission';
import { usePermissions } from 'libs/permission/usePermissions';
import { NotFoundError } from 'pages/error/NotFoundError';

import { LOCAL_STORAGE_RESULT_PREFIX, PERMISSION_GROUP_MAP } from '../constants';
import { openNewTab } from '../utils/openNewTab';

import { ReportConfigurationPage } from './ReportConfigurationPage';
import { ReportResultPage } from './ReportResultPage';
import { QbRuleGroup, queryToAdvancedFilterRequest } from './filters/advanced/AdvancedFilter';

export type ReportExecuteForm = {
    // key is the report's ID
    basicFilter?: Record<string, { value: string[] | string | null; includeNulls: boolean }>;
    advancedFilter?: QbRuleGroup;
    columns?: string[];
    sort?: { column: string; direction: SortSpec.direction };
};

const normalizeFormValueToStringArray = (value: unknown): string[] => {
    if (value === undefined || value === null) {
        return [];
    }
    if (Array.isArray(value)) {
        return value;
    }
    return [value.toString()];
};

const ReportRunPage = () => {
    const params = useParams();
    const reportUid = parseInt(params.reportUid ?? '0');
    const dataSourceUid = parseInt(params.dataSourceUid ?? '0');
    const [status, setStatus] = useState<'configuring' | 'submitting' | 'complete'>('configuring');
    const [error, setError] = useState<unknown | null>(null);
    const [wasExported, setWasExported] = useState<boolean>(true);
    const [lastReportExecutionRequest, setLastReportExecutionRequest] = useState<ReportExecutionRequest | undefined>(
        undefined
    );
    const config = useLoaderData<ReportConfiguration>();
    const { permissions: userPermissions, allows } = usePermissions();
    const canRunReport = allows(permissions.reports.run);

    // Make sure user can actually use this report
    if (
        !!config &&
        !permitsAll(
            PERMISSION_GROUP_MAP[config.group].selectFilterCriteria,
            PERMISSION_GROUP_MAP[config.group].view
        )(userPermissions)
    ) {
        throw new NotFoundError();
    }

    const form = useForm<ReportExecuteForm>({
        mode: 'onSubmit',
        reValidateMode: 'onSubmit',
    });

    const onSubmit = (event: BaseSyntheticEvent, isExport: boolean) => {
        form.handleSubmit((data) => {
            const basicFilters: BasicFilterRequest[] = Object.entries(data.basicFilter ?? {})
                .map(([id, { value, includeNulls }]) => {
                    const values = normalizeFormValueToStringArray(value);
                    return {
                        // remove `id_` prefix
                        reportFilterUid: Number.parseInt(id.slice(3)),
                        values,
                        includeNulls,
                    };
                })
                .filter((f) => !!f.values);

            const advancedFilterQuery =
                data.advancedFilter && config
                    ? queryToAdvancedFilterRequest(data.advancedFilter, config.columns)
                    : undefined;
            const advancedFilter =
                advancedFilterQuery && config?.advancedFilter?.reportFilterUid
                    ? { reportFilterUid: config.advancedFilter?.reportFilterUid, value: advancedFilterQuery }
                    : undefined;

            const columnUids = config?.library.allowColumnSelection ? data.columns!.map((v) => parseInt(v)) : undefined;

            const sort: SortSpec | undefined =
                config?.library.allowColumnSelection && data.sort?.column
                    ? { columnUid: parseInt(data.sort.column), direction: data.sort.direction }
                    : undefined;

            handleSubmit(isExport, basicFilters, advancedFilter, columnUids, sort);
        })(event);
    };

    const handleSubmit = useCallback(
        (
            isExport: boolean,
            basicFilters: BasicFilterRequest[],
            advancedFilter?: AdvancedFilterRequest,
            columnUids?: number[],
            sort?: SortSpec
        ) => {
            setWasExported(isExport);
            setStatus('submitting');
            setError(null);

            const requestBody = { isExport, reportUid, dataSourceUid, basicFilters, advancedFilter, columnUids, sort };
            setLastReportExecutionRequest(requestBody);

            const url = `/nbs/api/report/${isExport ? 'export' : 'run'}`;
            const method = 'POST';

            //  Manually invoking this endpoint instead of using the generated API client
            //  because said API client doesn't correctly support the 'text/csv' media type
            fetch(url, {
                method,
                headers: {
                    Accept: 'text/csv',
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(requestBody),
            })
                .then(async (response) => {
                    console.log({ response });
                    console.log({ headers: response.headers.get('X-Report-Query') });

                    const responseBody = await getResponseBody(response);
                    console.log({ responseBody });

                    // const text = await new Response(response.body).text();
                    // console.log({ text });

                    const result: ApiResult = {
                        url,
                        ok: response.ok,
                        status: response.status,
                        statusText: response.statusText,
                        body: responseBody,
                    };
                    catchErrorCodes({ url, method }, result);

                    try {
                        if (isExport) {
                            fileDownload(responseBody, `${config?.title ?? 'ReportOutput'}.csv`);
                        } else {
                            const resultId = crypto.randomUUID();
                            openNewTab(
                                `/report/result/${resultId}`,
                                {
                                    result: {
                                        content: responseBody,
                                        description: response.headers.get('X-Report-Description'),
                                        context_header: response.headers.get('X-Report-Context-Header'),
                                        timestamp: response.headers.get('X-Report-Timestamp'),
                                        query: response.headers.get('X-Report-Query'),
                                    },
                                    title: config?.title ?? '',
                                    dataSourceName: config?.dataSource.name ?? '',
                                },
                                `${LOCAL_STORAGE_RESULT_PREFIX}.${resultId}`
                            );
                        }
                    } catch (err) {
                        setError(err);
                    }
                })
                .catch(setError)
                .finally(() => setStatus('complete'));
        },
        [config?.dataSource.name, config?.title, dataSourceUid, reportUid]
    );

    return !config ? (
        <>
            {error && <ApiErrorBanner action="loading" item="report" error={error} />}
            <LoadingBlock />
        </>
    ) : status === 'configuring' ? (
        <FormProvider {...form}>
            <form onSubmit={(e) => onSubmit(e, !canRunReport)}>
                <ReportConfigurationPage
                    reportUid={reportUid}
                    dataSourceUid={dataSourceUid}
                    config={config}
                    handleSubmit={onSubmit}
                />
            </form>
        </FormProvider>
    ) : (
        <ReportResultPage
            config={config}
            resultLoading={status === 'submitting'}
            wasExported={wasExported}
            error={error}
            handleRefineReport={() => setStatus('configuring')}
            executionRequest={lastReportExecutionRequest}
        />
    );
};

export { ReportRunPage };
