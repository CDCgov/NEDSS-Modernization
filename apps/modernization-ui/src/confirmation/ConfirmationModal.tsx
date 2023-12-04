import './_ConfirmationModal.scss';
import { RefObject } from 'react';
import { Button, ButtonGroup, Icon, Modal, ModalFooter, ModalHeading, ModalRef } from '@trussworks/react-uswds';

type Props = {
    id?: string;
    ariaDescribedBy?: string;
    modal: RefObject<ModalRef>;
    title: string;
    message: string;
    detail?: string;
    confirmText?: string;
    onConfirm: () => void;
    cancelText?: string;
    onCancel: () => void;
};

export const ConfirmationModal = ({
    id = 'confirmation',
    ariaDescribedBy = 'confirmation-description',
    modal,
    title,
    message,
    detail,
    confirmText = 'Confirm',
    onConfirm,
    cancelText = 'Cancel',
    onCancel
}: Props) => {
    return (
        <Modal
            forceAction
            ref={modal}
            id={id}
            aria-labelledby="confirmation-heading"
            className="modal"
            aria-describedby={ariaDescribedBy}>
            <ModalHeading
                id="confirmation-heading"
                className="border-bottom border-base-lighter font-sans-lg padding-2">
                {title}
            </ModalHeading>
            <div className="modal-content">
                <div className="warning">
                    <Icon.Warning className="font-sans-2xl margin-x-2" />
                </div>
                <div className="modal-message">
                    <p id={ariaDescribedBy}>{message}</p>
                    {detail && <p id="confirmation-modal-details">{detail}</p>}
                </div>
            </div>
            <ModalFooter className="border-top border-base-lighter padding-2 margin-left-auto">
                <ButtonGroup>
                    <Button type="button" onClick={onCancel} outline>
                        {cancelText}
                    </Button>
                    <Button type="button" onClick={onConfirm} className="padding-105 text-center">
                        {confirmText}
                    </Button>
                </ButtonGroup>
            </ModalFooter>
        </Modal>
    );
};
