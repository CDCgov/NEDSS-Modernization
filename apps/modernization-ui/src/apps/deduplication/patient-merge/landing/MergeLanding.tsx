import { Heading } from 'components/heading';
import { Button } from 'design-system/button';
import { Icon } from 'design-system/icon';
import { MatchesRequiringReviewTable } from './matches-requiring-review-table/MatchesRequiringReviewTable';
import { useExportMatches } from 'apps/deduplication/api/useExportMatches';
import styles from './merge-landing.module.scss';
import { SortingProvider, useSorting } from 'sorting';
import { PaginationProvider } from 'pagination';

export const MergeLanding = () => {
    return (
        <PaginationProvider pageSize={20}>
            <SortingProvider>
                <MergeLandingContent />
            </SortingProvider>
        </PaginationProvider>
    );
};

const MergeLandingContent = () => {
    const { exportCSV, exportPDF } = useExportMatches();
    const { sorting } = useSorting();
    const sort = sorting ?? 'patient-id,desc';

    const handleCSVExport = async () => {
        await exportCSV(sort);
    };

    const handlePDFExport = async () => {
        await exportPDF(sort);
    };

    return (
        <div className={styles.mergeLandingPage}>
            <header className={styles.mergeLandingHeader}>
                <Heading level={1}>Matches requiring review</Heading>
                <div className={styles.buttons}>
                    <Button icon={<Icon name="print" />} sizing="medium" secondary onClick={handlePDFExport} />
                    <Button icon={<Icon name="file_download" />} sizing="medium" secondary onClick={handleCSVExport} />
                </div>
            </header>
            <main className={styles.mergeLandingContent}>
                <MatchesRequiringReviewTable />
            </main>
        </div>
    );
};
