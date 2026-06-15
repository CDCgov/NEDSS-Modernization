import { ReportConfiguration, ReportControllerService } from 'generated';
import { LoaderFunction } from 'react-router';

const loadReportConfiguration: LoaderFunction = async (request): Promise<ReportConfiguration> => {
    const { reportUid, dataSourceUid } = request.params;
    const res = await ReportControllerService.getReportConfiguration({
        reportUid: parseInt(reportUid ?? '0'),
        dataSourceUid: parseInt(dataSourceUid ?? '0'),
    });
    return res;
};

export { loadReportConfiguration };
