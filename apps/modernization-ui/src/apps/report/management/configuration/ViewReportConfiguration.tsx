import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import { useReportConfiguration } from 'apps/report/hooks/useReportConfiguration';
import { ReportLayout } from 'apps/report/layout/ReportLayout';
import { Button } from 'design-system/button';
import { LoadingIndicator } from 'libs/loading/indicator';
import { useState } from 'react';
import { useParams } from 'react-router';
import { ReportConfigurationContent } from './ReportConfigurationContent';

import styles from 'apps/report/layout/layout.module.scss';

const ViewReportConfiguration = () => {
    const params = useParams();
    const reportUid = parseInt(params.reportUid ?? '0');
    const dataSourceUid = parseInt(params.dataSourceUid ?? '0');
    const [error, setError] = useState<string | null>(null);
    const config = useReportConfiguration({ reportUid, dataSourceUid, handleError: setError });

    return !config ? (
        <>
            {error && <AlertBanner type="error">{error}</AlertBanner>}
            <LoadingIndicator />
        </>
    ) : (
        <ReportLayout title="View Report" actions={<Button>Edit</Button>}>
            <div className={styles.columnContent}>
                <ReportConfigurationContent isEditable={false} config={config} />
            </div>
        </ReportLayout>
    );
};

export { ViewReportConfiguration };
