import { Icon, Modal, ModalRef } from '@trussworks/react-uswds';
import classNames from 'classnames';
import { ReactNode, RefObject } from 'react';
import styles from './entry-modal.module.scss';

type Save = () => void;
type Close = () => void;

type Props = {
    modal: RefObject<ModalRef>;
    id: string;
    title: string;
    className?: string;
    overflow?: boolean;
    children: ReactNode;
    onClose: Close;
    onSave?: Save;
    onDelete?: () => void;
};

export const EntryModal = ({ modal, id, title, children, overflow = false, className, onClose }: Props) => {
    return (
        <Modal
            id={id}
            forceAction
            ref={modal}
            className={classNames(styles.modal, className, { [styles.overflow]: overflow })}>
            <header className={styles.modalHeader}>
                <h2>{title}</h2>
                <Icon.Close size={3} onClick={onClose} />
            </header>
            <div className={styles.content}>{children}</div>
        </Modal>
    );
};
