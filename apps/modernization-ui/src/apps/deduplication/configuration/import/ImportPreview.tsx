import { Heading } from 'components/heading';
import { Button } from 'design-system/button';
import styles from './import-preview.module.scss';

type Props = {
    onCancel: () => void;
};
export const ImportPreview = ({ onCancel }: Props) => {
    return (
        <header className={styles.importPreviewHeading}>
            <Heading level={1}>Preview configuration</Heading>
            <div>
                <Button secondary onClick={onCancel}>
                    Cancel
                </Button>
            </div>
        </header>
    );
};
