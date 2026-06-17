import { ReportLayout } from 'apps/report/layout/ReportLayout';
import { Button, LinkButton } from 'design-system/button';
import { LoadingIndicator } from 'libs/loading/indicator';
import { useRef, useState } from 'react';
import { useLoaderData, useParams } from 'react-router';
import { ReportConfigurationContent } from './ReportConfigurationContent';

import styles from 'apps/report/layout/layout.module.scss';
import { NBS_LIST_REPORT_CONFIG_PAGE } from './constants';
import { ModalRef } from '@trussworks/react-uswds';
import { ConfirmationModal } from 'confirmation';
import { ReportConfiguration, ReportControllerService } from 'generated';
import { LoadingBlock } from 'libs/loading/block';

const ViewReportConfiguration = () => {
    const params = useParams();
    const reportUid = parseInt(params.reportUid ?? '0');
    const dataSourceUid = parseInt(params.dataSourceUid ?? '0');
    const confirmDeleteRef = useRef<ModalRef>(null);
    const [deleting, setDeleting] = useState<boolean>(false);
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
                    <Button secondary={true} onClick={() => confirmDeleteRef.current?.toggleModal()}>
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
            <ConfirmationModal
                modal={confirmDeleteRef}
                title={`Delete report: ${config?.title}`}
                message={<>This action is permanent and cannot be undone.</>}
                confirmText="Yes, delete"
                cancelText="No, cancel"
                disabled={deleting}
                onConfirm={() => {
                    setDeleting(true);
                    ReportControllerService.deleteReport({ reportUid, dataSourceUid })
                        .then(() => {
                            window.location.href = NBS_LIST_REPORT_CONFIG_PAGE;
                        })
                        .catch((err) => {
                            setError(JSON.stringify(err));
                            confirmDeleteRef.current?.toggleModal();
                        })
                        .finally(() => setDeleting(false));
                }}
                onCancel={() => {
                    confirmDeleteRef.current?.toggleModal();
                }}
            />
        </ReportLayout>
    );
};

export { ViewReportConfiguration };
