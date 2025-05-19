import { Heading } from 'components/heading';
import { Button } from 'design-system/button';
import styles from './merge-preview.module.scss';

type MergePreviewProps = {
    onBack: () => void;
};

export const MergePreview = ({ onBack }: MergePreviewProps) => {
    return (
        <div className={styles.mergePreview}>
            <header>
                <Heading level={1}>Merge preview</Heading>
                <div className={styles.buttons}>
                    <Button secondary onClick={onBack}>
                        Back
                    </Button>
                    <Button
                        aria-label="Confirm and merge patient records"
                        onClick={() => console.log('Merge record NYI')}>
                        Merge record
                    </Button>
                </div>
            </header>
        </div>
    );
};
