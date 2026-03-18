import { ReportConfiguration, ReportControllerService } from 'generated';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router';

const ReportRun = () => {
    const params = useParams();
    console.log(params);
    const reportUid = parseInt(params.reportUid ?? '0');
    const dataSourceUid = parseInt(params.dataSourceUid ?? '0');
    const [config, setConfig] = useState<ReportConfiguration | null>(null);

    useEffect(() => {
        ReportControllerService.getReportConfiguration({ reportUid, dataSourceUid }).then((value) => {
            console.log(value);
            setConfig(value);
        });
    }, []);

    return (
        <section>
            <p>Hello!</p>
            <p>{config ? JSON.stringify(config) : "loading"}</p>
        </section>
    );
};

export { ReportRun };
