import { Modal, ModalHeading, ModalRef } from '@trussworks/react-uswds';
import { ReactNode, RefObject } from 'react';
import './entry-modal.scss';

type Props = {
    modal: RefObject<ModalRef>;
    id: string;
    title?: string;
    overflow?: boolean;
    children: ReactNode;
    onClose?: () => void;
};

export const EntryModal = ({ modal, id, title, children, overflow = false }: Props) => {
    return (
        <Modal id={id} forceAction ref={modal} className={overflow ? 'overflow' : ''}>
            {title && (
                <ModalHeading className="border-bottom border-base-lighter font-sans-lg padding-2 margin-0 modal-1-heading">
                    {title}
                </ModalHeading>
            )}
            {children}
        </Modal>
    );
};
