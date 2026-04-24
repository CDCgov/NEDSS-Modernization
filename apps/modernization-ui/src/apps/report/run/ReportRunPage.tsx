import React from 'react';
import { ReportConfiguration, ReportControllerService } from 'generated';
import { useCallback, useEffect, useState } from 'react';
import { useParams } from 'react-router';
import { ReportConfigurationPage } from './ReportConfigurationPage';
import { AdvancedFilterRequest, BasicFilterRequest } from 'generated';
import { useNewTab } from './useNewTab';
import { ResultDataPage } from './ResultDataPage';
import fileDownload from 'js-file-download';
import { ReportResultPage } from './ReportResultPage';
import { LoadingIndicator } from 'libs/loading/indicator';
import { FormProvider, useForm } from 'react-hook-form';
import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';

export type ReportExecuteForm = {
    // key is the report's ID
    basicFilter?: Record<string, string[] | string>;
    advancedFilter?: AdvancedFilterRequest;
    columns?: string[];
};

const ReportRunPage = () => {
    const params = useParams();
    const reportUid = parseInt(params.reportUid ?? '0');
    const dataSourceUid = parseInt(params.dataSourceUid ?? '0');
    const [config, setConfig] = useState<ReportConfiguration | null>(null);
    const [hasResult, setHasResult] = useState<boolean>(false);
    const [submitting, setSubmitting] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);
    const { openNewTab } = useNewTab();

    // Load the report configuration
    useEffect(() => {
        ReportControllerService.getReportConfiguration({ reportUid, dataSourceUid })
            .then((value) => setConfig(value))
            .catch((err) => setError(JSON.stringify(err)));
    }, []);

    const form = useForm<ReportExecuteForm>({
        mode: 'onBlur',
    });

    const onSubmit = (event: React.BaseSyntheticEvent, isExport: boolean) => {
        form.handleSubmit(
            (data) => {
                const basicFilters: BasicFilterRequest[] = Object.entries(data.basicFilter ?? {})
                    .map(([id, value]) => {
                        const values = typeof value === 'string' ? [value] : value;
                        return {
                            reportFilterUid: parseInt(id),
                            values,
                        };
                    })
                    .filter((f) => !!f.values);
                handleSubmit(isExport, basicFilters);
            },
            (errors) => {
                // TODO make this gather all errors and nicely format
                setError(Object.values(errors.basicFilter ?? {}).reduce((acc, cur) => `${acc}\n${cur?.message}`, ''));
            }
        )(event);
    };

    const handleSubmit = useCallback((isExport: boolean, basicFilters: BasicFilterRequest[]) => {
        setSubmitting(true);
        setError('');
        const runner = isExport ? ReportControllerService.exportReport : ReportControllerService.runReport;
        runner({ requestBody: { isExport, reportUid, dataSourceUid, basicFilters } })
            .then((res) => {
                setHasResult(true);
                if (!res.content) {
                    setError('No content!');
                    return;
                }
                isExport
                    ? fileDownload(res.content, `${res.header ?? 'ReportOutput'}.csv`)
                    : openNewTab(<ResultDataPage result={res} />);
            })
            .catch((err) => setError(JSON.stringify(err)))
            .finally(() => setSubmitting(false));
    }, []);

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
