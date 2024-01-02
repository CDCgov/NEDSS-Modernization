import { Icon, Modal, ModalRef } from '@trussworks/react-uswds';
import classNames from 'classnames';
import { Heading } from 'components/heading';
import { ReactNode, RefObject } from 'react';
import styles from './entry-modal.module.scss';

type Props = {
    modal: RefObject<ModalRef>;
    id: string;
    title?: string;
    overflow?: boolean;
    children: ReactNode;
    onClose?: () => void;
    className?: string;
};

export const EntryModal = ({ modal, id, title, children, overflow = false, className, onClose }: Props) => {
    return (
        <Modal
            id={id}
            forceAction
            ref={modal}
            className={classNames(styles.modal, className, { [styles.overflow]: overflow })}>
            {title && (
                <header>
                    <Heading level={2}>{title}</Heading>
                    <Icon.Close size={3} onClick={onClose} />
                </header>
            )}
            {children}
        </Modal>
    );
};
