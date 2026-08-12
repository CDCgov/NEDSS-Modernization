import { RefObject } from 'react';

import { Button, Icon, Modal, ModalFooter, ModalHeading, ModalRef } from '@trussworks/react-uswds';

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
    id = 'status',
}: Props) => {
    return (
        <Modal
            forceAction={true}
            ref={modal}
            className={styles.modal}
            id={id}
            aria-labelledby={`${id}-header`}
            aria-describedby={`${id}-content`}
        >
            <ModalHeading id={`${id}-header`} className={styles.title}>
                {title}
            </ModalHeading>
            <div id={`${id}-content`} className={styles.content}>
                <div className={styles.warning}>
                    <Icon.Warning aria-label="warning" size={4} />
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
