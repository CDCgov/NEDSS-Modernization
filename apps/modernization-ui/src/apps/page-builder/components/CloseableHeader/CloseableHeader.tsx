import { Icon } from '@trussworks/react-uswds';
import styles from './closeable-header.module.scss';

type Props = {
    onClose: () => void;
    title: string;
};
export const CloseableHeader = ({ onClose, title }: Props) => {
    return (
        <div className={styles.header}>
            <div className={styles.title}>{title}</div>
            <button className={styles.closeButton}>
                <Icon.Close size={4} onClick={onClose} />
            </button>
        </div>
    );
};
