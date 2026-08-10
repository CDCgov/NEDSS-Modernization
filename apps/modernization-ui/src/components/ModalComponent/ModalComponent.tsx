import './ModalComponent.scss';
import {ReactNode, RefObject, useId} from 'react';

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
    return (
        <Modal
            // allow escape to cancel unless interaction is disabled
            forceAction={disabled}
            ref={modalRef}
            isLarge={isLarge}
            id={id ?? useId()}
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
