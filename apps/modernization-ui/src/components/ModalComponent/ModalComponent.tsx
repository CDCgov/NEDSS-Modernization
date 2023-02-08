import { Modal, ModalHeading, ModalRef } from '@trussworks/react-uswds';
import React, { Ref } from 'react';

type ModalProps = {
    modalRef?: Ref<ModalRef> | undefined;
    modalHeading?: string;
    modalBody?: React.ReactNode | React.ReactNode[] | string;
};

export const ModalComponent = ({ modalRef, modalBody, modalHeading }: ModalProps) => {
    return (
        <Modal
            ref={modalRef}
            id="example-modal-1"
            aria-labelledby="modal-1-heading"
            className="padding-0"
            aria-describedby="modal-1-description">
            <ModalHeading
                id="modal-1-heading"
                className="border-bottom border-base-lighter font-sans-lg padding-2 margin-0">
                {modalHeading}
            </ModalHeading>
            {modalBody}
        </Modal>
    );
};
