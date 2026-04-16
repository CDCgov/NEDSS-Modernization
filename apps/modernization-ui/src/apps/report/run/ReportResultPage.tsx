import { Button } from 'design-system/button';
import { InlineErrorMessage } from 'design-system/field/InlineErrorMessage';
import { ReportRunLayout } from './layout/ReportRunLayout';
import { ReportConfiguration } from 'generated';

const ReportResultPage = ({
    config,
    error,
    resultLoading,
    handleRefineReport,
}: {
    config: ReportConfiguration;
    error: string | null;
    resultLoading: boolean;
    handleRefineReport: () => void;
}) => {
    return (
        <ReportRunLayout
            config={config}
            actions={
                <>
                    <Button onClick={handleRefineReport}>Refine Report</Button>
                    <Button onClick={() => {}}>Save As</Button>
                </>
            }
        >
            {error && <InlineErrorMessage id="report-result-error">{error}</InlineErrorMessage>}
            {resultLoading ? 'Your report is running, this can take some time' : 'Your report has run'}
        </ReportRunLayout>
    );
};

export { ReportResultPage };
