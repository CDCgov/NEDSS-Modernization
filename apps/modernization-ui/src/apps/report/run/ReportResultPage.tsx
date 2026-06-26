import { Button } from 'design-system/button';
import { ReportLayout } from '../layout/ReportLayout';
import { ReportConfiguration } from 'generated';
import { LoadingIndicator } from 'libs/loading/indicator';
import React, { ReactNode, useRef } from 'react';
import { Heading } from 'components/heading';
import { permissions, permitsAny, Permitted } from 'libs/permission';
import { Shown } from 'conditional-render';
import { useUser } from 'user';

import layoutStyles from '../layout/layout.module.scss';
import { PERMISSION_GROUP_MAP } from '../constants';
import { ModalRef } from '@trussworks/react-uswds';
import { SaveReportModal } from './modals/SaveReportModal.tsx';
import { AlertMessage } from '../../../design-system/message';

const ReportResultPage = ({
    config,
    error,
    wasExported,
    resultLoading,
    resultSaving,
    isRedirecting,
    handleRefineReport,
    handleSaveReport,
}: {
    config: ReportConfiguration;
    error: string | null;
    wasExported: boolean;
    resultLoading: boolean;
    resultSaving: boolean;
    isRedirecting: boolean;
    handleRefineReport: () => void;
    handleSaveReport: () => void;
}) => {
    const {
        state: { user },
    } = useUser();
    const saveReportModalRef = useRef<ModalRef>(null);

    const getLoadingHeader = () => {
        if (resultLoading) {
            return `Your report is ${wasExported ? 'downloading' : 'opening in a new tab'}. Please do not leave
        this page while your report is generating.`;
        } else {
            return 'Your report is saving and you will be redirected to the Manage Reports page once complete.';
        }
    };

    const getLoadingBody = () => {
        if (resultLoading) {
            return (
                'This might take several minutes for large reports. To be sure it opens, check that pop-ups ' +
                ' are enabled in your browser.'
            );
        }
    };

    return (
        <ReportLayout
            title={config.title}
            actions={
                <>
                    <Permitted permission={PERMISSION_GROUP_MAP[config.group].selectFilterCriteria}>
                        <Button onClick={handleRefineReport} secondary={true} disabled={resultLoading || resultSaving}>
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
                        <Button onClick={() => {}} disabled={resultLoading || resultSaving || !!error}>
                            Save As
                        </Button>
                    </Permitted>
                    <Shown when={user?.identifier === config.ownerUid}>
                        <Permitted permission={PERMISSION_GROUP_MAP[config.group].edit}>
                            <Button
                                onClick={() => saveReportModalRef.current?.toggleModal()}
                                disabled={resultLoading || resultSaving || !!error}
                            >
                                Save
                            </Button>
                            <SaveReportModal saveReportModalRef={saveReportModalRef} onSave={handleSaveReport} />
                        </Permitted>
                    </Shown>
                </>
            }
        >
            <>
                {error && (
                    <div className={'padding-2'}>
                        <>
                            <AlertMessage
                                type="error"
                                title={
                                    'There was an error saving your report. ' +
                                    'If this error persists, contact your NBS administrator for help.'
                                }
                            >
                                {error}
                            </AlertMessage>
                        </>
                    </div>
                )}
                {resultLoading || resultSaving ? (
                    <TextCard loading={true}>
                        <Heading level={2}>{getLoadingHeader()}</Heading>
                        <p>{getLoadingBody()}</p>
                    </TextCard>
                ) : (
                    !error && (
                        <TextCard>
                            <Heading level={2}>
                                {isRedirecting
                                    ? 'Your report has been saved. You are being redirected to the Manage Reports page.'
                                    : `Your report has ${wasExported ? 'downloaded' : 'opened in a new tab'}.`}
                            </Heading>
                        </TextCard>
                    )
                )}
            </>
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
