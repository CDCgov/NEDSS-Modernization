import React, { ReactNode } from 'react';
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
import {
    formatAdvancedFilterErrors,
    QbRuleGroup,
    queryToAdvancedFilterRequest,
} from './filters/advanced/AdvancedFilter';
import { usePermissions } from 'libs/permission/usePermissions';
import { BASIC_SECTIONS, PERMISSION_GROUP_MAP } from '../constants';
import { LoadingBlock } from 'libs/loading/block';
import { NotFoundError } from 'pages/error/NotFoundError';
import { permitsAll } from 'libs/permission';
import { AlertMessage } from 'design-system/message';
import { ValidationErrorBanner, ValidationErrorChunk } from './ValidationError';

const NBS_MANAGE_REPORT_PAGE = '/nbs/ManageReports.do';

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
    const [status, setStatus] = useState<'configuring' | 'submitting' | 'saving' | 'complete' | 'redirecting'>(
        'configuring'
    );
    const [validationError, setValidationError] = useState<ReactNode | null>(null);
    const [apiError, setApiError] = useState<string | null>(null);
    const [wasExported, setWasExported] = useState<boolean>(true);
    const [lastReportExecutionRequest, setLastReportExecutionRequest] = useState<ReportExecutionRequest | undefined>(
        undefined
    );
    const { openNewTab } = useNewTab();
    const config = useLoaderData<ReportConfiguration>();
    const { permissions } = usePermissions();

    // Make sure user can actually use this report
    if (
        !!config &&
        !permitsAll(
            PERMISSION_GROUP_MAP[config.group].selectFilterCriteria,
            PERMISSION_GROUP_MAP[config.group].view
        )(permissions)
    ) {
        throw new NotFoundError();
    }

    const form = useForm<ReportExecuteForm>({
        mode: 'onSubmit',
    });

    const onSubmit = (event: React.BaseSyntheticEvent, isExport: boolean) => {
        form.handleSubmit(
            (data) => {
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

                const columnUids = config?.library.allowColumnSelection
                    ? data.columns!.map((v) => parseInt(v))
                    : undefined;

                const sort: SortSpec | undefined =
                    config?.library.allowColumnSelection && data.sort?.column
                        ? { columnUid: parseInt(data.sort.column), direction: data.sort.direction }
                        : undefined;

                handleSubmit(isExport, basicFilters, advancedFilter, columnUids, sort);
            },
            (errors) => {
                const errorMsgs = BASIC_SECTIONS.map(({ title, id, filterTypes }) => {
                    const sectionErrs = Object.entries(errors.basicFilter ?? {})
                        .filter(([k, _e]) =>
                            filterTypes.includes(
                                config.basicFilters.find((f) => f.reportFilterUid === parseInt(k.slice(3)))?.filterType
                                    .type ?? ''
                            )
                        )
                        .map(([k, e]) => e?.value?.message && <li key={k}>{e.value.message}</li>)
                        .filter(Boolean);

                    if (sectionErrs.length) {
                        return (
                            <ValidationErrorChunk id={id} title={title}>
                                {sectionErrs}
                            </ValidationErrorChunk>
                        );
                    }
                }).filter(Boolean);

                if (errors.advancedFilter?.message) {
                    errorMsgs.push(
                        <ValidationErrorChunk id="advanced-filter" title="Advanced filter">
                            {formatAdvancedFilterErrors(errors.advancedFilter.message)}
                        </ValidationErrorChunk>
                    );
                }

                if (errors.columns?.message) {
                    errorMsgs.push(
                        <ValidationErrorChunk id="column-selection" title="Column selection">
                            {formatAdvancedFilterErrors(errors.columns.message)}
                        </ValidationErrorChunk>
                    );
                }

                setValidationError(<ValidationErrorBanner level={2}>{errorMsgs}</ValidationErrorBanner>);
            }
        )(event);
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
            setValidationError(null);
            setApiError(null);
            const runner = isExport ? ReportControllerService.exportReport : ReportControllerService.runReport;
            const requestBody = { isExport, reportUid, dataSourceUid, basicFilters, advancedFilter, columnUids, sort };
            setLastReportExecutionRequest(requestBody);
            runner({ requestBody })
                .then((res) => {
                    setStatus('complete');

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
                        setApiError(JSON.stringify(err));
                    }
                })
                .catch((err) => setApiError(JSON.stringify(err)));
        },
        [config]
    );

    const handleSaveReport = () => {
        const runner = ReportControllerService.saveReport;
        setStatus('saving');
        setApiError(null);

        if (!lastReportExecutionRequest) {
            setApiError('No changes to report to save.');
            return;
        }
        runner({
            reportUid: lastReportExecutionRequest.reportUid,
            dataSourceUid: lastReportExecutionRequest.dataSourceUid,
            requestBody: lastReportExecutionRequest,
        })
            .then(() => {
                setStatus('redirecting');
                window.location.href = NBS_MANAGE_REPORT_PAGE;
            })
            .catch((err) => {
                setApiError(err.message);
            });
    };

    return !config ? (
        <>
            {apiError && <AlertMessage type="error">{apiError}</AlertMessage>}
            <LoadingBlock />
        </>
    ) : status === 'configuring' ? (
        <FormProvider {...form}>
            <ReportConfigurationPage config={config} handleSubmit={onSubmit} error={validationError} />
        </FormProvider>
    ) : (
        <ReportResultPage
            config={config}
            resultLoading={status === 'submitting'}
            resultSaving={status === 'saving'}
            isRedirecting={status === 'redirecting'}
            wasExported={wasExported}
            error={apiError}
            handleRefineReport={() => setStatus('configuring')}
            handleSaveReport={handleSaveReport}
        />
    );
};

export { ReportRunPage };
