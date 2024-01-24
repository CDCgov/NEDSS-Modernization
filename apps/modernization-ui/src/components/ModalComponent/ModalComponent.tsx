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
};

export const ModalComponent = ({
    modalRef,
    modalBody,
    modalHeading,
    modalFooter,
    isLarge,
    size,
    closer,
    onCloseModal
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
            {modalHeading ? (
                <ModalHeading id="modal-1-heading">
                    {modalHeading}
                    {closer ? (
                        <ModalToggleButton unstyled closer modalRef={modalRef!} onClick={onCloseModal}>
                            <Icon.Close size={4} />
                        </ModalToggleButton>
                    ) : null}
                </ModalHeading>
            ) : null}
            {modalBody}
            {modalFooter ? <ModalFooter>{modalFooter}</ModalFooter> : null}
        </Modal>
    );
};
