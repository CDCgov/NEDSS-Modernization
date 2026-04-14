import { Button } from 'design-system/button';
import { ReportConfiguration, ReportControllerService } from 'generated';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router';
import { useNewTab } from './useNewTab';
import { ResultDataPage } from './ResultDataPage';
import fileDownload from 'js-file-download';
import { InlineErrorMessage } from 'design-system/field/InlineErrorMessage';
import { permissions, Permitted } from 'libs/permission';

const ReportRun = () => {
    const params = useParams();
    const reportUid = parseInt(params.reportUid ?? '0');
    const dataSourceUid = parseInt(params.dataSourceUid ?? '0');
    const [config, setConfig] = useState<ReportConfiguration | null>(null);
    const [hasResult, setHasResult] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);
    const { openNewTab } = useNewTab();

    useEffect(() => {
        ReportControllerService.getReportConfiguration({ reportUid, dataSourceUid }).then((value) => setConfig(value));
    }, []);

    const handleSubmit = (isExport: boolean) => {
        const runner = isExport ? ReportControllerService.exportReport : ReportControllerService.runReport
        runner({ requestBody: { isExport, reportUid, dataSourceUid } })
            .then((res) => {
                setHasResult(true);
                if (!res.content) {
                    setError('No content!');
                    return;
                }
                isExport ? fileDownload(res.content, `${res.header}.csv`): openNewTab(<ResultDataPage result={res} />);
            })
            .catch((err) => setError(JSON.stringify(err)));
    };

    return (
        <section>
            <Permitted permission={permissions.reports.run}>
                <Button onClick={() => handleSubmit(false)}>Run</Button>
            </Permitted>
            <Permitted permission={permissions.reports.export}>
                <Button onClick={() => handleSubmit(true)}>Export</Button>
            </Permitted>
            {error && <InlineErrorMessage id="report-error">{error}</InlineErrorMessage>}
            {hasResult ? (
                <div>Result opened in new tab or downloaded</div>
            ) : (
                <div>
                    <p>Config:</p>
                    <p>{config ? JSON.stringify(config) : 'loading'}</p>
                </div>
            )}
        </section>
    );
};

export { ReportRun };
