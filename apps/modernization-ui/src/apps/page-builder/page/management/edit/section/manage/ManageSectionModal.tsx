import { ModalRef } from '@trussworks/react-uswds';
import { AddSection } from './AddSection';
import { ManageSection } from './ManageSection';
import './ManageSectionModal.scss';
import { PagesResponse, PagesSection, PagesTab } from 'apps/page-builder/generated';
import { RefObject, useState } from 'react';
import DragDropProvider from 'apps/page-builder/context/DragDropProvider';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';

type ManageSectionModalProps = {
    page: PagesResponse;
    tab: PagesTab;
    pageId: number;
    refresh?: () => void;
    addSecModalRef: RefObject<ModalRef>;
    manageSecModalRef: RefObject<ModalRef>;
    handleDelete: (section: PagesSection) => void;
    reset: () => void;
};

export const ManageSectionModal = ({
    page,
    tab,
    refresh,
    addSecModalRef,
    manageSecModalRef,
    pageId,
    handleDelete,
    reset
}: ManageSectionModalProps) => {
    const manageSectionModalRef = manageSecModalRef;
    const addSectionModalRef = addSecModalRef;
    const [selectedForEdit, setSelectedForEdit] = useState<PagesSection | undefined>(undefined);
    const [selectedForDelete, setSelectedForDelete] = useState<PagesSection | undefined>(undefined);

    const onCloseManageSectionModal = () => {
        manageSectionModalRef.current?.toggleModal(undefined, false);
    };

    const closeAddSection = () => {
        addSectionModalRef.current?.toggleModal(undefined, false);
    };

    const onReorderSuccess = () => {
        refresh?.();
    };

    return (
        <>
            <ModalComponent
                modalRef={manageSectionModalRef}
                forceAction
                size="tall"
                modalHeading={null}
                modalBody={
                    <DragDropProvider
                        pageData={page}
                        currentTab={page!.tabs!.findIndex((x: PagesTab) => x.name === tab.name)}
                        successCallBack={onReorderSuccess}>
                        <ManageSection
                            pageId={pageId}
                            tab={tab}
                            key={tab?.sections.length}
                            onContentChange={() => {
                                refresh?.();
                            }}
                            onCancel={onCloseManageSectionModal}
                            setSelectedForEdit={setSelectedForEdit}
                            selectedForEdit={selectedForEdit}
                            setSelectedForDelete={setSelectedForDelete}
                            selectedForDelete={selectedForDelete}
                            handleDelete={handleDelete}
                            reset={reset}
                        />
                    </DragDropProvider>
                }
            />
            <ModalComponent
                modalRef={addSectionModalRef}
                size="tall"
                modalBody={
                    <AddSection
                        pageId={pageId}
                        tabId={tab.id}
                        onAddSectionCreated={() => {
                            refresh?.();
                            closeAddSection?.();
                        }}
                        onCancel={closeAddSection}
                        selectedForEdit={selectedForEdit}
                    />
                }
            />
        </>
    );
};
