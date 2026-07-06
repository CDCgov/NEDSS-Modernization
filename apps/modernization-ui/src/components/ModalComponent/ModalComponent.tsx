import './ModalComponent.scss';
import { Modal, ModalHeading, ModalRef, ModalFooter, ModalToggleButton } from '@trussworks/react-uswds';
import React, { RefObject } from 'react';
import { Icon } from '@trussworks/react-uswds';

type ModalProps = {
    modalRef?: RefObject<ModalRef> | undefined;
    modalHeading?: React.ReactNode | string;
    modalBody?: React.ReactNode | React.ReactNode[] | string;
    modalFooter?: React.ReactNode | React.ReactNode[] | string;
    isLarge?: boolean;
    size?: string;
    forceAction?: boolean;
    closer?: boolean;
    onCloseModal?: () => void;
    id?: string;
    className?: string;
};

export const ModalComponent = ({
    modalRef,
    modalBody,
    modalHeading,
    modalFooter,
    isLarge,
    size,
    className,
    closer,
    onCloseModal,
    id,
}: ModalProps) => {
    return (
        <Modal
            ref={modalRef}
            isLarge={isLarge}
            id={id}
            forceAction={true}
            aria-labelledby={`${id}-heading`}
            className={`padding-0 ${size} ${className}`}
            aria-describedby={`${id}-description`}
        >
            {modalHeading ? (
                <ModalHeading id={`${id}-heading`}>
                    {modalHeading}
                    {closer ? (
                        <ModalToggleButton unstyled closer modalRef={modalRef!} onClick={onCloseModal}>
                            <Icon.Close size={4} aria-label={'Close modal'} />
                        </ModalToggleButton>
                    ) : null}
                </ModalHeading>
            ) : null}
            {modalBody}
            {modalFooter ? <ModalFooter>{modalFooter}</ModalFooter> : null}
        </Modal>
    );
};
