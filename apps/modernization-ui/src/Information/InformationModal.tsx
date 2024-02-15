import { RefObject } from 'react';
import { Button, ButtonGroup, Modal, ModalFooter, ModalHeading, ModalRef } from '@trussworks/react-uswds';
import { AlertModal } from '../apps/page-builder/components/AlertModal/AlertModal';

type Props = {
    id?: string;
    ariaDescribedBy?: string;
    modal: RefObject<ModalRef>;
    title: string;
    message: string;
    detail?: string;
    closeText?: string;
    onClose: () => void;
};

export const InformationModal = ({
    id = 'information',
    ariaDescribedBy = 'information-description',
    modal,
    title,
    message,
    detail,
    closeText = 'Close',
    onClose
}: Props) => {
    return (
        <Modal
            forceAction
            ref={modal}
            id={id}
            aria-labelledby="informatim-heading"
            className="modal"
            aria-describedby={ariaDescribedBy}>
            <ModalHeading id="informatim-heading" className="border-bottom border-base-lighter font-sans-lg padding-2">
                {title}
            </ModalHeading>
            <div className="modal-content">
                <AlertModal type="success" message={detail} header={message} />
            </div>
            <ModalFooter className="border-top border-base-lighter padding-2 margin-left-auto">
                <ButtonGroup>
                    <Button type="button" onClick={onClose} className="padding-105 text-center">
                        {closeText}
                    </Button>
                </ButtonGroup>
            </ModalFooter>
        </Modal>
    );
};
