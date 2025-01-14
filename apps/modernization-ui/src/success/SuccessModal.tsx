import { ReactNode, RefObject } from 'react';
import { Icon, Modal, ModalFooter, ModalHeading, ModalRef } from '@trussworks/react-uswds';
import classNames from 'classnames';
import styles from './successModal.module.scss';

type Props = {
    id?: string;
    ariaDescribedBy?: string;
    modal: RefObject<ModalRef>;
    title: string;
    children: ReactNode;
    actions: ReactNode;
};

export const SuccessModal = ({
    id = 'success',
    ariaDescribedBy = 'success-description',
    modal,
    title,
    children,
    actions
}: Props) => {
    return (
        <Modal
            forceAction
            ref={modal}
            id={id}
            isLarge
            aria-labelledby="success-heading"
            className={styles.modal}
            aria-describedby={ariaDescribedBy}>
            <ModalHeading id="success-heading">{title}</ModalHeading>
            <div className={classNames(styles.content, 'modal-content')}>
                <div className={classNames(styles.success)}>
                    <Icon.CheckCircle className={classNames(styles.successIcon)} />
                </div>
                <div id={ariaDescribedBy} className={classNames(styles.message, 'modal-message')}>
                    {children}
                </div>
            </div>
            <ModalFooter className={styles.successFooter}>{actions}</ModalFooter>
        </Modal>
    );
};
