import { Heading } from 'components/heading';
import { Button } from 'design-system/button';
import { Icon } from 'design-system/icon';
import { MatchesRequiringReviewTable } from './matches-requiring-review-table/MatchesRequiringReviewTable';
import { useExportMatches } from 'apps/deduplication/api/useExportMatches';
import styles from './merge-landing.module.scss';
import { useState } from 'react';

export const MergeLanding = () => {
    const { exportCSV, exportPDF } = useExportMatches();
    const [sort, setSort] = useState('patient-id,desc');

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
                <MatchesRequiringReviewTable onSortChange={setSort} />
            </main>
        </div>
    );
};
