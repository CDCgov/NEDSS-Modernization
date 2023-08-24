import { RefObject } from 'react';
import { Icon, Modal, ModalHeading, ModalRef } from '@trussworks/react-uswds';
import { Note } from 'components/Note';

export type MessageModalContent = { message: string; detail?: string };

type Props = {
    modal: RefObject<ModalRef>;
    title: string;
    content: MessageModalContent;
};

export const MessageModal = ({ modal, title = '', content }: Props) => {
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
            <div className="margin-2 grid-row flex-no-wrap border-left-1 border-accent-warm flex-align-start">
                <Icon.Warning className="font-sans-2xl margin-x-2 text-accent-warm" />
                <div className="width-full">
                    <p id="modal-1-description" className="margin-top-0">
                        {content.message}
                    </p>
                    {content.detail && (
                        <div>
                            <Note>{content.detail}</Note>
                        </div>
                    )}
                </div>
            </div>
        </Modal>
    );
};
