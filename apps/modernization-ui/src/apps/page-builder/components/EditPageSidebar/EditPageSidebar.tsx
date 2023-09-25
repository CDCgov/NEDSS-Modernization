import { ModalRef, Icon, ModalToggleButton } from '@trussworks/react-uswds';
import { Icon as EQIcon } from 'components/Icon/Icon';
import './EditPageSidebar.scss';
import { RefObject } from 'react';

// create props type
type EditPageSidebarProps = {
    modalRef1: RefObject<ModalRef>;
    modalRef2: RefObject<ModalRef>;
};

export const EditPageSidebar = ({ modalRef1, modalRef2 }: EditPageSidebarProps) => {
    return (
        <>
            <div className="edit-page-sidebar">
                <ModalToggleButton modalRef={modalRef1} opener unstyled className="item">
                    <div className="edit-page-sidebar__button">
                        <Icon.Add />
                        <span className="edit-page-sidebar__button--label">Add section</span>
                    </div>
                </ModalToggleButton>
                <ModalToggleButton modalRef={modalRef2} opener unstyled className="item">
                    <div className="edit-page-sidebar__button">
                        <EQIcon name="reorder" />
                        <span className="edit-page-sidebar__button--label">Reorder</span>
                    </div>
                </ModalToggleButton>
            </div>
        </>
    );
};
