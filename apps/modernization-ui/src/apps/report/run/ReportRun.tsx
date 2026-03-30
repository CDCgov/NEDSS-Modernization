import { Button } from 'design-system/button';
import { ReportConfiguration, ReportControllerService } from 'generated';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router';
import { useNewTab } from './useNewTab';

const ReportRun = () => {
    const params = useParams();
    const reportUid = parseInt(params.reportUid ?? '0');
    const dataSourceUid = parseInt(params.dataSourceUid ?? '0');
    const [config, setConfig] = useState<ReportConfiguration | null>(null);
    const [hasResult, setHasResult] = useState<boolean>(false);
    const { openNewTab } = useNewTab();

    useEffect(() => {
        ReportControllerService.getReportConfiguration({ reportUid, dataSourceUid }).then((value) => setConfig(value));
    }, []);

    const handleSubmit = (isExport: boolean) => {
        ReportControllerService.executeReport({ requestBody: { isExport, reportUid, dataSourceUid } })
            .then((res) => {
                setHasResult(true);
                openNewTab(<div>{JSON.stringify(res)}</div>);
            })
            // TODO: in-component handling
            // eslint-disable-next-line no-console
            .catch((err) => console.error(err));
    };

    return (
        <section>
            <Button onClick={() => handleSubmit(false)}>Run</Button>
            <Button onClick={() => handleSubmit(true)}>Export</Button>
            {hasResult ? (
                <div>Result opened in new tab</div>
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
