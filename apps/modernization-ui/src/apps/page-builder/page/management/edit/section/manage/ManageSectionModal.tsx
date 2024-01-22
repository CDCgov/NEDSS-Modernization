import { Modal, ModalRef } from '@trussworks/react-uswds';
import { AddSection } from './AddSection';
import { ManageSection } from './ManageSection';
import { RefObject, useEffect, useState } from 'react';
import './ManageSectionModal.scss';
import { usePageManagement } from '../../../usePageManagement';
import { PagesTab } from 'apps/page-builder/generated';
import DragDropProvider from 'apps/page-builder/context/DragDropProvider';
import { useAlert } from 'alert';

type ManageSectionModalProps = {
    refresh?: () => void;
    addSecModalRef: RefObject<ModalRef>;
    manageSecModalRef: RefObject<ModalRef>;
};
export type AlertInLineProps = {
    type: 'success' | 'error' | 'warning' | 'info';
    message: string;
    onClose?: () => void;
};

export const ManageSectionModal = ({ refresh, addSecModalRef, manageSecModalRef }: ManageSectionModalProps) => {
    const manageSectionModalRef = manageSecModalRef;
    const addSectionModalRef = addSecModalRef;

    const { showAlert } = useAlert();

    const [alert, setAlert] = useState<AlertInLineProps | undefined>(undefined);

    const { page, selected } = usePageManagement();

    const onCloseManageSectionModal = () => {
        manageSectionModalRef.current?.toggleModal(undefined, false);
    };

    const closeAddSection = () => {
        addSectionModalRef.current?.toggleModal(undefined, false);
    };

    useEffect(() => {
        if (alert !== undefined) {
            setTimeout(() => setAlert(undefined), 5000);
        }
    }, [alert]);
    const onReorderSuccess = () => {
        refresh?.();
    };

    return (
        <>
            <Modal
                id={'manage-section-modal'}
                className={'manage-section-modal'}
                ref={manageSectionModalRef}
                forceAction
                isLarge>
                <DragDropProvider
                    pageData={page}
                    currentTab={page.tabs?.findIndex((x: PagesTab) => x.name === selected?.name) ?? 0}
                    successCallBack={onReorderSuccess}>
                    <ManageSection
                        pageId={page.id}
                        alert={alert}
                        onResetAlert={() => setAlert(undefined)}
                        tab={selected}
                        key={selected?.sections.length}
                        onContentChange={() => {
                            refresh?.();
                        }}
                        onUpdateSection={() => {
                            setAlert({ message: `Your changes have been saved successfully.`, type: `success` });
                        }}
                        onDeleteSection={() => {
                            setAlert({ message: `You've successfully deleted section!`, type: `success` });
                        }}
                        onAddSection={(section: string) => {
                            setAlert({ message: `You have successfully added section "${section}"`, type: `success` });
                        }}
                        onCancel={onCloseManageSectionModal}
                    />
                </DragDropProvider>
            </Modal>
            <Modal id={'add-section-modal'} ref={addSectionModalRef} className={'add-section-modal'} isLarge>
                <AddSection
                    pageId={page.id}
                    tabId={selected?.id}
                    onSectionTouched={() => {
                        refresh?.();
                        closeAddSection?.();
                    }}
                    onAddSection={(section: string) => {
                        showAlert({ message: `You have successfully added section "${section}"`, type: `success` });
                    }}
                    onCancel={closeAddSection}
                    isEdit={false}
                />
            </Modal>
        </>
    );
};
