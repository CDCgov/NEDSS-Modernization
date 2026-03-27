import { Button } from 'design-system/button';
import { ReportConfiguration, ReportControllerService } from 'generated';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router';

const ReportRun = () => {
    const params = useParams();
    const reportUid = parseInt(params.reportUid ?? '0');
    const dataSourceUid = parseInt(params.dataSourceUid ?? '0');
    const [config, setConfig] = useState<ReportConfiguration | null>(null);
    const [result, setResult] = useState<string>('');

    useEffect(() => {
        ReportControllerService.getReportConfiguration({ reportUid, dataSourceUid }).then((value) => setConfig(value));
    }, []);

    const handleSubmit = (isExport: boolean) => {
        ReportControllerService.executeReport({ requestBody: { isExport, reportUid, dataSourceUid } }).then((res) =>
            setResult(res)
        );
    };

    return (
        <section>
            <Button onClick={() => handleSubmit(false)}>Run</Button>
            <Button onClick={() => handleSubmit(true)}>Export</Button>
            <p>Config:</p>
            <p>{config ? JSON.stringify(config) : 'loading'}</p>
            <p>Result:</p>
            <p>{result ? JSON.stringify(result) : 'no result yet'}</p>
        </section>
    );
};

export { ReportRun };
