import { Button, Icon } from '@trussworks/react-uswds';

import styles from './entry-footer.module.scss';

type Props = {
    onSave?: () => void;
    onDelete?: () => void;
};

const EntryFooter = ({ onSave, onDelete }: Props) => {
    return (
        <footer className={styles.footer}>
            {onDelete && (
                <Button unstyled className={styles.delete} type="button" onClick={onDelete}>
                    <Icon.Delete size={3} />
                    Delete
                </Button>
            )}
            <Button onClick={onSave} type="submit" className={styles.cta}>
                Save
            </Button>
        </footer>
    );
};

export { EntryFooter };
