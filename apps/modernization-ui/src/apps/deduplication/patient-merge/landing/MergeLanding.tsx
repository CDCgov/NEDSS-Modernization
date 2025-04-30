import { Heading } from 'components/heading';
import { Button } from 'design-system/button';
import { Icon } from 'design-system/icon';
import { PaginationProvider } from 'pagination';
import { MatchesRequiringReviewTable } from './matches-requiring-review-table/MatchesRequiringReviewTable';
import { useExportMatchesCSV } from 'apps/deduplication/api/useExportMatchesCSV';
import styles from './merge-landing.module.scss';

export const MergeLanding = () => {
    const { exportMatchesCSV } = useExportMatchesCSV();
    return (
        <>
            <header className={styles.mergeLandingHeader}>
                <Heading level={1}>Matches requiring review</Heading>
                <div className={styles.buttons}>
                    <Button icon={<Icon name="print" />} sizing="medium" secondary onClick={exportMatchesCSV} />
                    <Button icon={<Icon name="file_download" />} sizing="medium" secondary onClick={exportMatchesCSV} />
                </div>
            </header>
            <main className={styles.mergeLandingContent}>
                <PaginationProvider>
                    <MatchesRequiringReviewTable />
                </PaginationProvider>
            </main>
        </>
    );
};
