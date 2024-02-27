import { Icon } from '@trussworks/react-uswds';
import styles from './closeable-header.module.scss';
import { ReactNode } from 'react';

type Props = {
    onClose: () => void;
    title: string | ReactNode;
};
export const CloseableHeader = ({ onClose, title }: Props) => {
    return (
        <div className={styles.header}>
            <div className={styles.title}>{title}</div>
            <button className={styles.closeButton} onClick={onClose}>
                <Icon.Close size={4} />
            </button>
        </div>
    );
};
