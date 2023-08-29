import { ModalRef, Icon, ModalToggleButton } from '@trussworks/react-uswds';
import './EditPageSidebar.scss';
import { RefObject } from 'react';

// create props type
type EditPageSidebarProps = {
    modalRef: RefObject<ModalRef>;
};

export const EditPageSidebar = ({ modalRef }: EditPageSidebarProps) => {
    return (
        <div className="edit-page-sidebar">
            <ModalToggleButton modalRef={modalRef} opener unstyled className="item">
                <div className="item-text">
                    <Icon.Add />
                    <span className="item-label">Add section</span>
                </div>
            </ModalToggleButton>
        </div>
    );
};
