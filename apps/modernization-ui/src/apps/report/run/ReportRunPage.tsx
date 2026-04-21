import { ReportConfiguration, ReportControllerService } from 'generated';
import { useCallback, useEffect, useState } from 'react';
import { useParams } from 'react-router';
import { ReportConfigurationPage } from './ReportConfigurationPage';
import { useNewTab } from './useNewTab';
import { ResultDataPage } from './ResultDataPage';
import fileDownload from 'js-file-download';
import { ReportResultPage } from './ReportResultPage';
import { InlineErrorMessage } from 'design-system/field/InlineErrorMessage';
import { LoadingIndicator } from 'libs/loading/indicator';

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

    const handleSubmit = useCallback((isExport: boolean) => {
        setSubmitting(true);
        const runner = isExport ? ReportControllerService.exportReport : ReportControllerService.runReport;
        runner({ requestBody: { isExport, reportUid, dataSourceUid } })
            .then((res) => {
                setHasResult(true);
                if (!res.content) {
                    setError('No content!');
                    return;
                }
                isExport
                    ? fileDownload(res.content, `${config?.reportTitle ?? 'ReportOutput'}.csv`)
                    : openNewTab(<ResultDataPage result={res} />);
            })
            .catch((err) => setError(JSON.stringify(err)))
            .finally(() => setSubmitting(false));
    }, [config]);

    return !config ? (
        <>
            {error && <InlineErrorMessage id="report-config-error">{error}</InlineErrorMessage>}
            <LoadingIndicator />
        </>
    ) : !hasResult && !submitting ? (
        <ReportConfigurationPage config={config} handleSubmit={handleSubmit} />
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
