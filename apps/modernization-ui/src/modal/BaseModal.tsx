import 'styles/modal.scss';
import { Icon, Modal, ModalHeading, ModalRef } from '@trussworks/react-uswds';
import { RefObject } from 'react';

type Props = {
    modal: RefObject<ModalRef>;
    id: string;
    title: string;
    isLarge?: boolean;
    onClose: () => void;
};

const BaseModal = ({ modal, id, title, isLarge = false, onClose }: Props) => {
    return (
        <Modal forceAction isLarge ref={modal} id={id} className="modal">
            <ModalHeading id="modal-heading" className="border-bottom border-base-lighter font-sans-lg padding-2">
                {title}
                <Icon.Close className="cursor-pointer" onClick={onClose} />
            </ModalHeading>
            <div className="modal-content"></div>
        </Modal>
    );
};
