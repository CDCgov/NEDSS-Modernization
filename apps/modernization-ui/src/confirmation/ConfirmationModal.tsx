import { RefObject } from 'react';
import { Button, ButtonGroup, Icon, Modal, ModalFooter, ModalHeading, ModalRef } from '@trussworks/react-uswds';

type Props = {
    modal: RefObject<ModalRef>;
    title: string;
    message: string;
    confirmText?: string;
    onConfirm: () => void;
    cancelText?: string;
    onCancel: () => void;
};

export const ConfirmationModal = ({
    modal,
    title,
    message,
    confirmText = 'Confirm',
    onConfirm,
    cancelText = 'Cancel',
    onCancel
}: Props) => {
    return (
        <Modal
            forceAction
            ref={modal}
            id="example-modal-1"
            aria-labelledby="modal-1-heading"
            className="padding-0"
            aria-describedby="modal-1-description">
            <ModalHeading id="modal-1-heading" className="border-bottom border-base-lighter font-sans-lg padding-2">
                {title}
            </ModalHeading>
            <div className="margin-2 grid-row flex-no-wrap border-left-1 border-accent-warm flex-align-center">
                <Icon.Warning className="font-sans-2xl margin-x-2" />
                <p id="modal-1-description">{message}</p>
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
