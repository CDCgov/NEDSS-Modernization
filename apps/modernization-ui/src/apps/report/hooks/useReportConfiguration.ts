import { ReportConfiguration, ReportControllerService } from 'generated';
import { useEffect, useState } from 'react';

const useReportConfiguration = ({
    reportUid,
    dataSourceUid,
    handleError,
}: {
    reportUid: number;
    dataSourceUid: number;
    handleError: (err: string) => void;
}) => {
    const [config, setConfig] = useState<ReportConfiguration | null>(null);

    // Load the report configuration
    useEffect(() => {
        ReportControllerService.getReportConfiguration({ reportUid, dataSourceUid })
            .then((value) => setConfig(value))
            .catch((err) => handleError(JSON.stringify(err)));
    }, []);

    return config;
};

export { useReportConfiguration };
