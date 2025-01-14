import { Button, Icon, Modal, ModalFooter, ModalHeading, ModalRef } from '@trussworks/react-uswds';
import { RefObject } from 'react';
import styles from './statusmodal.module.scss';

type Props = {
    modal: RefObject<ModalRef>;
    title: string;
    id?: string;
    message: string;
    messageHeader: string;
    confirmText?: string;
    onConfirm: () => void;
};

export const StatusModal = ({
    modal,
    title,
    message,
    messageHeader,
    confirmText = 'Close',
    onConfirm,
    id = 'status'
}: Props) => {
    return (
        <Modal forceAction ref={modal} className={styles.modal} id={id}>
            <ModalHeading className={styles.title}>
                <h2>{title}</h2>
            </ModalHeading>
            <div className={styles.content}>
                <div className={styles.warning}>
                    <Icon.Warning size={4} />
                </div>
                <div className={styles.message}>
                    <h3>{messageHeader}</h3>
                    <p>{message}</p>
                </div>
            </div>
            <ModalFooter className={styles.footer}>
                <Button type="button" onClick={onConfirm} className="padding-105 text-center">
                    {confirmText}
                </Button>
            </ModalFooter>
        </Modal>
    );
};
