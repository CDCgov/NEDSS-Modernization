import React from 'react';
import {
    AdvancedFilterRequest,
    BasicFilterRequest,
    ReportConfiguration,
    ReportControllerService,
    ReportExecutionRequest,
    SortSpec,
} from 'generated';
import { useCallback, useState } from 'react';
import { useLoaderData, useParams } from 'react-router';
import { ReportConfigurationPage } from './ReportConfigurationPage';
import { useNewTab } from './useNewTab';
import { ResultDataPage } from './ResultDataPage';
import fileDownload from 'js-file-download';
import { ReportResultPage } from './ReportResultPage';
import { FormProvider, useForm } from 'react-hook-form';
import { QbRuleGroup, queryToAdvancedFilterRequest } from './filters/advanced/AdvancedFilter';
import { usePermissions } from 'libs/permission/usePermissions';
import { PERMISSION_GROUP_MAP } from '../constants';
import { LoadingBlock } from 'libs/loading/block';
import { NotFoundError } from 'pages/error/NotFoundError';
import { permissions, permitsAll } from 'libs/permission';
import { ApiErrorBanner } from 'design-system/errors/ApiError';

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
    const { openNewTab } = useNewTab();
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
    });

    const onSubmit = (event: React.BaseSyntheticEvent, isExport: boolean) => {
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
            const runner = isExport ? ReportControllerService.exportReport : ReportControllerService.runReport;
            const requestBody = { isExport, reportUid, dataSourceUid, basicFilters, advancedFilter, columnUids, sort };
            setLastReportExecutionRequest(requestBody);
            runner({ requestBody })
                .then((res) => {
                    try {
                        if (isExport) {
                            fileDownload(res.result.content, `${config?.title ?? 'ReportOutput'}.csv`);
                        } else {
                            openNewTab(
                                <ResultDataPage
                                    result={res}
                                    title={config?.title ?? ''}
                                    dataSourceName={config?.dataSource.name ?? ''}
                                />,
                                `NBS Report: ${config?.title ?? ''}`
                            );
                        }
                    } catch (err) {
                        setError(err);
                    }
                })
                .catch(setError)
                .finally(() => setStatus('complete'));
        },
        [config]
    );

    return !config ? (
        <>
            {error && <ApiErrorBanner action="loading" item="report" error={error} />}
            <LoadingBlock />
        </>
    ) : status === 'configuring' ? (
        <FormProvider {...form}>
            <form onSubmit={(e) => onSubmit(e, !canRunReport)}>
                <ReportConfigurationPage config={config} handleSubmit={onSubmit} />
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
