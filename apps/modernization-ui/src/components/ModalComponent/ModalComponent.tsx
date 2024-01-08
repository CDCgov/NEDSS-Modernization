import './ModalComponent.scss';
import { Modal, ModalHeading, ModalRef, ModalFooter, Icon, ModalToggleButton } from '@trussworks/react-uswds';
import React, { RefObject } from 'react';

type ModalProps = {
    modalRef?: RefObject<ModalRef> | undefined;
    modalHeading?: React.ReactNode | string;
    modalBody?: React.ReactNode | React.ReactNode[] | string;
    modalFooter?: React.ReactNode | React.ReactNode[] | string;
    isLarge?: boolean;
    size?: string;
    close?: boolean;
};

export const ModalComponent = ({
    modalRef,
    modalBody,
    modalHeading,
    modalFooter,
    isLarge,
    size,
    close
}: ModalProps) => {
    return (
        <Modal
            ref={modalRef}
            isLarge={isLarge}
            id="example-modal-1"
            forceAction={true}
            aria-labelledby="modal-1-heading"
            className={`padding-0 ${size}`}
            aria-describedby="modal-1-description">
            <ModalHeading id="modal-1-heading">
                {modalHeading}
                {close ? (
                    <ModalToggleButton modalRef={modalRef!} closer unstyled>
                        <Icon.Close size={4} />
                    </ModalToggleButton>
                ) : null}
            </ModalHeading>
            {modalBody}
            {modalFooter ? <ModalFooter>{modalFooter}</ModalFooter> : null}
        </Modal>
    );
};
