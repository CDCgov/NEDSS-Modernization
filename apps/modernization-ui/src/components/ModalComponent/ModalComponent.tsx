import './ModalComponent.scss';
import { ReactNode, RefObject, useId } from 'react';

import { Modal, ModalFooter, ModalHeading, ModalRef } from '@trussworks/react-uswds';

type ModalProps = {
    modalRef?: RefObject<ModalRef> | undefined;
    modalHeading?: ReactNode | string;
    modalBody?: ReactNode | ReactNode[] | string;
    modalFooter?: ReactNode | ReactNode[] | string;
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
    const generatedId = useId();
    const modalId = id ?? generatedId;

    return (
        <Modal
            // allow escape to cancel unless interaction is disabled
            forceAction={disabled}
            ref={modalRef}
            isLarge={isLarge}
            id={modalId}
            aria-labelledby={`${modalId}-heading`}
            className={`padding-0 ${size} ${className}`}
            aria-describedby={`${modalId}-description`}
        >
            {modalHeading ? <ModalHeading id={`${modalId}-heading`}>{modalHeading}</ModalHeading> : null}
            {modalBody}
            {modalFooter ? <ModalFooter>{modalFooter}</ModalFooter> : null}
        </Modal>
    );
};
