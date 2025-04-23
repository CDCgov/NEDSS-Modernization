import { Heading } from 'components/heading';
import { Button } from 'design-system/button';
import { Icon } from 'design-system/icon';
import { PaginationProvider } from 'pagination';
import { MatchesRequiringReviewTable } from './matches-requiring-review-table/MatchesRequiringReviewTable';
import styles from './merge-landing.module.scss';

export const MergeLanding = () => {
    return (
        <>
            <header>
                <Heading level={1}>Matches requiring review</Heading>
                <div className={styles.buttons}>
                    <Button icon={<Icon name="print" />} sizing="medium" secondary />
                    <Button icon={<Icon name="file_download" />} sizing="medium" secondary />
                </div>
            </header>
            <main>
                <PaginationProvider>
                    <MatchesRequiringReviewTable />
                </PaginationProvider>
            </main>
        </>
    );
};
