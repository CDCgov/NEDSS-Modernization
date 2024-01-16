import { Modal, ModalRef } from '@trussworks/react-uswds';
import { AddSection } from './AddSection';
import { ManageSection } from './ManageSection';
import { PagesTab } from 'apps/page-builder/generated';
import { RefObject } from 'react';

type ManageSectionModalProps = {
    tab: PagesTab;
    pageId: number;
    refresh?: () => void;
    addSecModalRef: RefObject<ModalRef>;
    manageSecModalRef: RefObject<ModalRef>;
};

export const ManageSectionModal = ({
    tab,
    refresh,
    addSecModalRef,
    manageSecModalRef,
    pageId
}: ManageSectionModalProps) => {
    const manageSectionModalRef = manageSecModalRef;
    const addSectionModalRef = addSecModalRef;

    const onCloseManageSectionModal = () => {
        manageSectionModalRef.current?.toggleModal(undefined, false);
    };

    const closeAddSection = () => {
        addSectionModalRef.current?.toggleModal(undefined, false);
    };

    return (
        <>
            <Modal id={'manage-section-modal'} ref={manageSectionModalRef} forceAction>
                <ManageSection
                    pageId={pageId}
                    tab={tab}
                    key={tab?.sections.length}
                    onContentChange={() => {
                        refresh?.();
                    }}
                    onCancel={onCloseManageSectionModal}
                />
            </Modal>
            <Modal id={'add-section-modl'} ref={addSectionModalRef}>
                <AddSection
                    pageId={pageId}
                    tabId={tab.id}
                    onAddSectionCreated={() => {
                        refresh?.();
                    }}
                    onCancel={closeAddSection}
                />
            </Modal>
        </>
    );
};
