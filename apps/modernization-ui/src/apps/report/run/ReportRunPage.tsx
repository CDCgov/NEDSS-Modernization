import React from 'react';
import { AdvancedFilterRequest, BasicFilterRequest, ReportControllerService } from 'generated';
import { useCallback, useState } from 'react';
import { useParams } from 'react-router';
import { ReportConfigurationPage } from './ReportConfigurationPage';
import { useNewTab } from './useNewTab';
import { ResultDataPage } from './ResultDataPage';
import fileDownload from 'js-file-download';
import { ReportResultPage } from './ReportResultPage';
import { LoadingIndicator } from 'libs/loading/indicator';
import { FormProvider, useForm } from 'react-hook-form';
import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import { QbRuleGroup, queryToAdvancedFilterRequest } from './filters/advanced/AdvancedFilter';
import { useReportConfiguration } from 'apps/report/hooks/useReportConfiguration';

export type ReportExecuteForm = {
    // key is the report's ID
    basicFilter?: Record<string, { value: string[] | string | null; includeNulls: boolean }>;
    advancedFilter?: QbRuleGroup;
    columns?: string[];
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
    const [hasResult, setHasResult] = useState<boolean>(false);
    const [submitting, setSubmitting] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);
    const { openNewTab } = useNewTab();
    const config = useReportConfiguration({ reportUid, dataSourceUid, handleError: setError });

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

                handleSubmit(isExport, basicFilters, advancedFilter, columnUids);
            },
            (errors) => {
                // TODO make this gather all errors and nicely format
                setError(
                    Object.values(errors.basicFilter ?? {}).reduce((acc, cur) => `${acc}\n${cur?.value?.message}`, '')
                );
            }
        )(event);
    };

    const handleSubmit = useCallback(
        (
            isExport: boolean,
            basicFilters: BasicFilterRequest[],
            advancedFilter?: AdvancedFilterRequest,
            columnUids?: number[]
        ) => {
            setSubmitting(true);
            setError('');
            const runner = isExport ? ReportControllerService.exportReport : ReportControllerService.runReport;
            runner({ requestBody: { isExport, reportUid, dataSourceUid, basicFilters, advancedFilter, columnUids } })
                .then((res) => {
                    setHasResult(true);
                    if (!res.content) {
                        setError('No content!');
                        return;
                    }

                    if (isExport) {
                        fileDownload(res.content, `${res.header ?? 'ReportOutput'}.csv`);
                    } else {
                        openNewTab(<ResultDataPage result={res} />);
                    }
                })
                .catch((err) => setError(JSON.stringify(err)))
                .finally(() => setSubmitting(false));
        },
        []
    );

    return !config ? (
        <>
            {error && <AlertBanner type="error">{error}</AlertBanner>}
            <LoadingIndicator />
        </>
    ) : !hasResult && !submitting ? (
        <>
            {error && <AlertBanner type="error">{error}</AlertBanner>}
            <FormProvider {...form}>
                <ReportConfigurationPage config={config} handleSubmit={onSubmit} />
            </FormProvider>
        </>
    ) : (
        <ReportResultPage
            config={config}
            resultLoading={!hasResult}
            error={error}
            handleRefineReport={() => setHasResult(false)}
        />
    );
};

export { ReportRunPage };
