import { ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import './EditPageSidebar.scss';
import { RefObject } from 'react';

// create props type
type EditPageSidebarProps = {
    modalRef: RefObject<ModalRef>;
};

export const EditPageSidebar = ({ modalRef }: EditPageSidebarProps) => {
    return (
        <div className="edit-page-sidebar">
            <ModalToggleButton modalRef={modalRef} unstyled>
                <span>Add Section</span>
            </ModalToggleButton>
        </div>
    );
};
