import './ModalComponent.scss';
import { Modal, ModalHeading, ModalRef, ModalFooter } from '@trussworks/react-uswds';
import React, { RefObject } from 'react';

type ModalProps = {
    modalRef?: RefObject<ModalRef> | undefined;
    modalHeading?: React.ReactNode | string;
    modalBody?: React.ReactNode | React.ReactNode[] | string;
    modalFooter?: React.ReactNode | React.ReactNode[] | string;
    isLarge?: boolean;
    size?: string;
    forceAction?: boolean;
    id?: string;
    className?: string;
    disabled?: boolean;
};

export const ModalComponent = ({
    modalRef,
    modalBody,
    modalHeading,
    modalFooter,
    isLarge,
    size,
    className,
    id,
    disabled = false,
}: ModalProps) => {
    return (
        <Modal
            // allow escape to cancel unless interaction is disabled
            forceAction={disabled}
            ref={modalRef}
            isLarge={isLarge}
            id={id}
            aria-labelledby={`${id}-heading`}
            className={`padding-0 ${size} ${className}`}
            aria-describedby={`${id}-description`}
        >
            {modalHeading ? <ModalHeading id={`${id}-heading`}>{modalHeading}</ModalHeading> : null}
            {modalBody}
            {modalFooter ? <ModalFooter>{modalFooter}</ModalFooter> : null}
        </Modal>
    );
};
