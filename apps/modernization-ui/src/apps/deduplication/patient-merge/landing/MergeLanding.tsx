import { useExportMatches } from 'apps/deduplication/api/useExportMatches';
import { Heading } from 'components/heading';
import { Button } from 'design-system/button';
import { PaginationProvider } from 'pagination';
import { SortingProvider, useSorting } from 'libs/sorting';
import { MatchesRequiringReviewTable } from './matches-requiring-review-table/MatchesRequiringReviewTable';
import styles from './merge-landing.module.scss';

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

    return (
        <div className={styles.mergeLandingPage}>
            <header className={styles.mergeLandingHeader}>
                <Heading level={1}>Matches requiring review</Heading>
                <div className={styles.buttons}>
                    <Button
                        icon="print"
                        sizing="medium"
                        secondary
                        aria-label="Print"
                        data-tooltip-position="top"
                        onClick={() => exportPDF(sorting)}
                    />
                    <Button
                        icon="file_download"
                        sizing="medium"
                        secondary
                        aria-label="Download"
                        data-tooltip-position="top"
                        data-tooltip-offset="left"
                        onClick={() => exportCSV(sorting)}
                    />
                </div>
            </header>
            <main className={styles.mergeLandingContent}>
                <MatchesRequiringReviewTable />
            </main>
        </div>
    );
};
