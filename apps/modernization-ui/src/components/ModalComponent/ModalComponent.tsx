import { Modal, ModalHeading, ModalRef } from '@trussworks/react-uswds';
import React, { Ref } from 'react';

type ModalProps = {
    modalRef?: Ref<ModalRef> | undefined;
    modalHeading?: React.ReactNode | string;
    modalBody?: React.ReactNode | React.ReactNode[] | string;
    isLarge?: boolean;
};

export const ModalComponent = ({ modalRef, modalBody, modalHeading, isLarge }: ModalProps) => {
    // @ts-ignore
    return (
        <Modal
            ref={modalRef}
            isLarge={isLarge}
            id="example-modal-1"
            aria-labelledby="modal-1-heading"
            className="padding-0"
            aria-describedby="modal-1-description">
            <ModalHeading
                id="modal-1-heading"
                className="border-bottom border-base-lighter font-sans-lg padding-2 margin-0 modal-1-heading">
                {modalHeading}
            </ModalHeading>
            {modalBody}
        </Modal>
    );
};
