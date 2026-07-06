import { Button } from 'design-system/button';
import { ReportLayout } from '../layout/ReportLayout';
import { ReportConfiguration, ReportControllerService, ReportExecutionRequest, SaveAsReportRequest } from 'generated';
import { LoadingIndicator } from 'libs/loading/indicator';
import { ReactNode, useRef, useState } from 'react';
import { Heading } from 'components/heading';
import { permissions, permitsAny, Permitted } from 'libs/permission';
import { Shown } from 'conditional-render';
import { useUser } from 'user';

import layoutStyles from '../layout/layout.module.scss';
import { NBS_MANAGE_REPORT_PAGE, PERMISSION_GROUP_MAP } from '../constants';
import { ModalRef } from '@trussworks/react-uswds';
import { SaveReportModal } from './modals/SaveReportModal.tsx';
import { AlertMessage } from 'design-system/message';
import { SaveAsReportFormData, SaveAsReportModal } from './modals/SaveAsReportModal.tsx';
import { redirectToNBS6 } from 'utils';
import classNames from 'classnames';

const INTERNAL_ERROR_MSG = 'Something went wrong.';

const ReportResultPage = ({
    config,
    error,
    wasExported,
    resultLoading,
    handleRefineReport,
    executionRequest,
}: {
    config: ReportConfiguration;
    error: string | null;
    wasExported: boolean;
    resultLoading: boolean;
    handleRefineReport: () => void;
    executionRequest?: ReportExecutionRequest;
}) => {
    const {
        state: { user },
    } = useUser();
    const [saving, setSaving] = useState<boolean>(false);
    const [saveError, setSaveError] = useState<string | null>(null);
    const saveReportModalRef = useRef<ModalRef>(null);
    const saveAsReportModalRef = useRef<ModalRef>(null);

    const handleReportSave = (isSaveAs: boolean, request?: SaveAsReportRequest) => {
        if (!executionRequest || (isSaveAs && !request)) {
            setSaveError(INTERNAL_ERROR_MSG);
            return;
        }

        setSaving(true);
        setSaveError(null);

        const runner = isSaveAs ? ReportControllerService.saveAsReport : ReportControllerService.saveReport;
        runner({
            reportUid: executionRequest.reportUid,
            dataSourceUid: executionRequest.dataSourceUid,
            requestBody: (isSaveAs ? request : executionRequest) as SaveAsReportRequest & ReportExecutionRequest,
        })
            .then(() => {
                redirectToNBS6(NBS_MANAGE_REPORT_PAGE);
            })
            .catch((err) => {
                const modalRef = isSaveAs ? saveAsReportModalRef : saveReportModalRef;
                modalRef.current?.toggleModal();
                setSaveError(err.message);
            })
            .finally(() => {
                setSaving(false);
            });
    };

    const onSave = () => {
        handleReportSave(false);
    };

    const onSaveAs = (response: SaveAsReportFormData) => {
        handleReportSave(true, { ...response, executionRequest } as SaveAsReportRequest);
    };

    return (
        <ReportLayout
            title={config.title}
            startHref={NBS_MANAGE_REPORT_PAGE}
            startPage="reports"
            actions={
                <>
                    <Permitted permission={PERMISSION_GROUP_MAP[config.group].selectFilterCriteria}>
                        <Button onClick={handleRefineReport} secondary={true} disabled={resultLoading}>
                            Refine Report
                        </Button>
                    </Permitted>
                    <Permitted
                        permission={permitsAny(
                            permissions.reports.public.create,
                            permissions.reports.private.create,
                            permissions.reports.reportingFacility.create
                        )}
                    >
                        <Button
                            onClick={() => saveAsReportModalRef.current?.toggleModal()}
                            disabled={resultLoading || !!error}
                        >
                            Save as new
                        </Button>
                        <SaveAsReportModal
                            saveAsReportModalRef={saveAsReportModalRef}
                            saving={saving}
                            onSaveAs={onSaveAs}
                        />
                    </Permitted>
                    <Shown when={user?.identifier === config.ownerUid}>
                        <Permitted permission={PERMISSION_GROUP_MAP[config.group].edit}>
                            <Button
                                onClick={() => saveReportModalRef.current?.toggleModal()}
                                disabled={resultLoading || !!error}
                            >
                                Save
                            </Button>
                            <SaveReportModal saveReportModalRef={saveReportModalRef} saving={saving} onSave={onSave} />
                        </Permitted>
                    </Shown>
                </>
            }
        >
            <div className="display-flex flex-column">
                {(error || saveError) && (
                    <AlertMessage
                        className={classNames(layoutStyles.alertMessage, 'margin-top-2 margin-x-2')}
                        type="error"
                        title={`There was an error ${saveError ? 'saving' : wasExported ? 'exporting' : 'running'} 
                        your report. If this error persists, contact your NBS administrator for help.`}
                    >
                        {saveError}
                    </AlertMessage>
                )}
                {resultLoading ? (
                    <TextCard loading={true}>
                        <Heading level={2}>
                            {`Your report is ${wasExported ? 'downloading' : 'opening in a new tab'}. 
                            Please do not leave this page while your report is generating.`}
                        </Heading>
                        <p>
                            This might take several minutes for large reports. To be sure it opens, check that pop-ups
                            are enabled in your browser.
                        </p>
                    </TextCard>
                ) : (
                    !error && (
                        <TextCard>
                            <Heading level={2}>
                                {`Your report has ${wasExported ? 'downloaded' : 'opened in a new tab'}.`}
                            </Heading>
                        </TextCard>
                    )
                )}
            </div>
        </ReportLayout>
    );
};

const TextCard = ({ loading = false, children }: { loading?: boolean; children: ReactNode }) => {
    return (
        <div className={layoutStyles.fullPageBlock}>
            {children}
            {loading && <LoadingIndicator />}
        </div>
    );
};

export { ReportResultPage };
