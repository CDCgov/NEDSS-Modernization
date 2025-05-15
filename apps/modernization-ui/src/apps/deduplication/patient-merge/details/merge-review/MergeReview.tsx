import { Heading } from 'components/heading';
import { Button } from 'design-system/button';
import { useNavigate } from 'react-router';
import styles from './merge-review.module.scss';

export const MergeReview = ({ onPreviewClick }: { onPreviewClick: () => void }) => {
    const nav = useNavigate();
    return (
        <div className={styles.mergeReview}>
            <header>
                <Heading level={1}>Matches requiring review</Heading>
                <div className={styles.buttons}>
                    <Button secondary onClick={() => nav('/deduplication/merge')}>
                        Back
                    </Button>
                    <Button secondary onClick={onPreviewClick}>
                        Preview merge
                    </Button>
                    <Button onClick={() => console.log('Keep all separate NYI')}>Keep all separate</Button>
                    <Button onClick={() => console.log('Merge all NYI')}>Merge all</Button>
                </div>
            </header>
            <main>
                <div className={styles.infoText}>
                    Only one record is selected for Patient ID. By default, the oldest record is selected as the
                    surviving ID. If this is not correct, select the appropriate record.
                </div>
            </main>
        </div>
    );
};
