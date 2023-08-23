import { RefObject } from 'react';
import { Icon, Modal, ModalHeading, ModalRef } from '@trussworks/react-uswds';

type Props = {
    modal: RefObject<ModalRef>;
    title: string;
    message: string;
};

export const MessageModal = ({ modal, title = '', message = '' }: Props) => {
    return (
        <Modal
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
                <p id="modal-1-description" className="width-full">
                    {message}
                </p>
            </div>
        </Modal>
    );
};
