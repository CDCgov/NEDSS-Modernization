import { Button } from 'design-system/button';
import { ReportLayout } from '../layout/ReportLayout';
import { ReportConfiguration } from 'generated';
import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import { LoadingIndicator } from 'libs/loading/indicator';
import { ReactNode } from 'react';
import { Heading } from 'components/heading';
import { permissions, permitsAny, Permitted } from 'libs/permission';
import { Shown } from 'conditional-render';
import { useUser } from 'user';

import layoutStyles from '../layout/layout.module.scss';

const PERMISSION_GROUP_MAP = {
    [ReportConfiguration.group.PRIVATE]: permissions.reports.private,
    [ReportConfiguration.group.PUBLIC]: permissions.reports.public,
    [ReportConfiguration.group.TEMPLATE]: permissions.reports.template,
    [ReportConfiguration.group.REPORTING_FACILITY]: permissions.reports.reportingFacility,
};

const ReportResultPage = ({
    config,
    error,
    wasExported,
    resultLoading,
    handleRefineReport,
}: {
    config: ReportConfiguration;
    error: string | null;
    wasExported: boolean;
    resultLoading: boolean;
    handleRefineReport: () => void;
}) => {
    const {
        state: { user },
    } = useUser();

    return (
        <ReportLayout
            title={config.title}
            actions={
                <>
                    <Permitted permission={permitsAny(PERMISSION_GROUP_MAP[config.group].selectFilterCriteria)}>
                        <Button onClick={handleRefineReport} secondary={true} disabled={resultLoading}>
                            Refine Report
                        </Button>
                    </Permitted>
                    <Permitted
                        permission={permitsAny(
                            permissions.reports.public.create,
                            permissions.reports.private.create,
                            permissions.reports.reportingFacility.create
                        )}>
                        <Button onClick={() => {}} disabled={resultLoading || !!error}>
                            Save As
                        </Button>
                    </Permitted>
                    <Shown when={user?.identifier === config.ownerUid}>
                        <Permitted permission={PERMISSION_GROUP_MAP[config.group].edit}>
                            <Button onClick={() => {}} disabled={resultLoading || !!error}>
                                Save
                            </Button>
                        </Permitted>
                    </Shown>
                </>
            }>
            {error && <AlertBanner type="error">{error}</AlertBanner>}
            {resultLoading ? (
                <TextCard loading={true}>
                    <Heading level={3}>
                        Your report is {wasExported ? 'downloading' : 'opening in a new tab'}. Please do not leave this
                        page while your report is generating.
                    </Heading>
                    <p>
                        This might take several minutes for large reports. To be sure it opens, check that pop-ups are
                        enabled in your browser.
                    </p>
                </TextCard>
            ) : (
                !error && (
                    <TextCard>
                        <Heading level={3}>
                            Your report has {wasExported ? 'downloaded' : 'opened in a new tab'}.
                        </Heading>
                    </TextCard>
                )
            )}
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
