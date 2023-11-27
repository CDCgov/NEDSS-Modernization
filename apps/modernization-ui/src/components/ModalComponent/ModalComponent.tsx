import './ModalComponent.scss';
import { Modal, ModalHeading, ModalRef, ModalFooter } from '@trussworks/react-uswds';
import React, { Ref } from 'react';

type ModalProps = {
    modalRef?: Ref<ModalRef> | undefined;
    modalHeading?: React.ReactNode | string;
    modalBody?: React.ReactNode | React.ReactNode[] | string;
    modalFooter?: React.ReactNode | React.ReactNode[] | string;
    isLarge?: boolean;
    size?: string;
};

export const ModalComponent = ({ modalRef, modalBody, modalHeading, modalFooter, isLarge, size }: ModalProps) => {
    // @ts-ignore
    return (
        <Modal
            ref={modalRef}
            isLarge={isLarge}
            id="example-modal-1"
            forceAction={true}
            aria-labelledby="modal-1-heading"
            className={`padding-0 ${size}`}
            aria-describedby="modal-1-description">
            <ModalHeading id="modal-1-heading">{modalHeading}</ModalHeading>
            {modalBody}
            {modalFooter ? <ModalFooter>{modalFooter}</ModalFooter> : null}
        </Modal>
    );
};
