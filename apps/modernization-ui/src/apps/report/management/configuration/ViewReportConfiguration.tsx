import { ReportLayout } from 'apps/report/layout/ReportLayout';
import { Button, LinkButton } from 'design-system/button';
import { useLoaderData, useParams } from 'react-router';
import { ReportConfigurationContent } from './ReportConfigurationContent';

import styles from 'apps/report/layout/layout.module.scss';
import { NBS_LIST_REPORT_CONFIG_PAGE } from './constants';
import { ReportConfiguration } from 'generated';
import { LoadingBlock } from 'libs/loading/block';

const ViewReportConfiguration = () => {
    const params = useParams();
    const reportUid = parseInt(params.reportUid ?? '0');
    const dataSourceUid = parseInt(params.dataSourceUid ?? '0');
    const config = useLoaderData<ReportConfiguration>();

    return !config ? (
        <LoadingBlock />
    ) : (
        <ReportLayout
            title="View Report"
            startHref={NBS_LIST_REPORT_CONFIG_PAGE}
            startPage="Manage Reports"
            actions={
                <>
                    <Button secondary={true} onClick={() => window.alert('to do')}>
                        Delete
                    </Button>
                    <LinkButton href={`/report/management/configuration/${reportUid}/${dataSourceUid}/edit`}>
                        Edit
                    </LinkButton>
                </>
            }
        >
            <div className={styles.columnContent}>
                <ReportConfigurationContent isEditable={false} config={config} />
            </div>
        </ReportLayout>
    );
};

export { ViewReportConfiguration };
