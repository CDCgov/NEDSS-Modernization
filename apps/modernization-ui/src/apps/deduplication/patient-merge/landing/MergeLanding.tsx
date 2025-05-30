import { useExportMatches } from 'apps/deduplication/api/useExportMatches';
import { Heading } from 'components/heading';
import { Button } from 'design-system/button';
import { Icon } from 'design-system/icon';
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
                    <Button icon={<Icon name="print" />} sizing="medium" secondary onClick={() => exportPDF(sorting)} />
                    <Button
                        icon={<Icon name="file_download" />}
                        sizing="medium"
                        secondary
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
