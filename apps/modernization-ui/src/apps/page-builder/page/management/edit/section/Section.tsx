import { Modal, ModalRef } from '@trussworks/react-uswds';
import { useAlert } from 'libs/alert';
import DragDropProvider from 'apps/page-builder/context/DragDropProvider';
import { PagesQuestion, PagesSection, PagesSubSection, SubSectionControllerService } from 'apps/page-builder/generated';
import { useEffect, useRef, useState } from 'react';
import { ConfirmationModal } from '../../../../../../confirmation';
import { usePageManagement } from '../../usePageManagement';
import { Subsection } from '../subsection/Subsection';
import { AddSubSection } from '../subsection/manage/AddSubSection';
import { ManageSubsection } from '../subsection/manage/ManageSubsection';
import { SectionHeader } from './SectionHeader';
import { AddSection } from './manage/AddSection';
import { AlertInLineProps } from './manage/ManageSectionModal';
import './manage/ManageSectionModal.scss';
import styles from './section.module.scss';

type Props = {
    section: PagesSection;
    onAddQuestion: (subsection: PagesSubSection) => void;
    onEditQuestion: (question: PagesQuestion) => void;
    onDeleteSection: () => void;
    onDeleteStatus: () => void;
    onEditValueset: (valuesetName: string) => void;
    onChangeValueset: (question: PagesQuestion) => void;
    onGroupQuestion: (subsection: PagesSubSection) => void;
    onEditGroupedSubsection: (subsection: PagesSubSection) => void;
};

export const Section = ({
    section,
    onAddQuestion,
    onEditQuestion,
    onDeleteSection,
    onDeleteStatus,
    onEditValueset,
    onChangeValueset,
    onGroupQuestion,
    onEditGroupedSubsection
}: Props) => {
    const [isExpanded, setIsExpanded] = useState<boolean>(true);

    const { page, refresh, selected } = usePageManagement();

    const handleExpandedChange = (expanded: boolean) => {
        setIsExpanded(expanded);
    };

    const editSectionModalRef = useRef<ModalRef>(null);

    const addSubsectionModalRef = useRef<ModalRef>(null);

    const editSubsectionModalRef = useRef<ModalRef>(null);
    const [editSubsection, setEditSubsection] = useState<PagesSubSection | undefined>(undefined);

    const manageSubsectionModalRef = useRef<ModalRef>(null);

    const { showAlert } = useAlert();

    const [alert, setAlert] = useState<AlertInLineProps | undefined>(undefined);

    const deleteSubsectionModalRef = useRef<ModalRef>(null);
    const [selectedSubsectionToDelete, setSelectedSubsectionToDelete] = useState<PagesSubSection | undefined>(
        undefined
    );

    useEffect(() => {
        if (alert !== undefined) {
            setTimeout(() => setAlert(undefined), 5000);
        }
    }, [alert]);

    const handleManageSubsection = () => {
        manageSubsectionModalRef.current?.toggleModal(undefined, true);
    };

    const handleEditSubsection = (subsection: PagesSubSection) => {
        if (subsection.isGrouped) {
            onEditGroupedSubsection(subsection);
        } else {
            setEditSubsection(subsection);
            editSubsectionModalRef.current?.toggleModal(undefined, true);
        }
    };

    const onCloseEditSubsectionModal = () => {
        setEditSubsection(undefined);
        editSubsectionModalRef.current?.toggleModal(undefined, false);
    };

    const onCloseManageSubsection = () => {
        manageSubsectionModalRef.current?.toggleModal(undefined, false);
    };

    const onCloseEditSectionModal = () => {
        editSectionModalRef.current?.toggleModal(undefined, false);
    };

    const handleEditSection = () => {
        editSectionModalRef.current?.toggleModal(undefined, true);
    };

    const handleAddSubSection = () => {
        addSubsectionModalRef.current?.toggleModal(undefined, true);
    };

    const onCloseAddSubSection = () => {
        addSubsectionModalRef.current?.toggleModal(undefined, false);
    };

    const handleDeleteSubsection = (subsection: PagesSubSection) => {
        if (subsection.questions.length > 0) {
            onDeleteStatus();
        } else {
            setSelectedSubsectionToDelete(subsection);
            deleteSubsectionModalRef.current?.toggleModal(undefined, true);
        }
    };

    const deleteSubsection = () => {
        if (!selectedSubsectionToDelete) {
            return;
        }
        SubSectionControllerService.deleteSubSection({
            page: page.id,
            subSectionId: selectedSubsectionToDelete.id
        }).then(() => {
            showAlert({
                message: `You've successfully deleted "${selectedSubsectionToDelete.name}"`,
                type: `success`
            });
            refresh();
        });
    };

    const handleReorderSubsection = () => {
        refresh();
    };

    return (
        <div className={styles.section}>
            <SectionHeader
                name={section.name ?? ''}
                subsectionCount={section.subSections?.length ?? 0}
                onAddSubsection={handleAddSubSection}
                handleManageSubsection={handleManageSubsection}
                onExpandedChange={handleExpandedChange}
                handleEditSection={handleEditSection}
                handleDeleteSection={onDeleteSection}
                isExpanded={isExpanded}
            />
            {isExpanded && (
                <div className={styles.subsectionWrapper}>
                    {section.subSections?.map((subsection, k) => (
                        <Subsection
                            subsection={subsection}
                            key={k}
                            onEditQuestion={onEditQuestion}
                            onAddQuestion={() => onAddQuestion(subsection)}
                            onDeleteSubsection={handleDeleteSubsection}
                            onEditSubsection={handleEditSubsection}
                            onEditValueset={onEditValueset}
                            onChangeValueset={onChangeValueset}
                            onGroupQuestion={onGroupQuestion}
                        />
                    ))}
                </div>
            )}
            <Modal id={'add-section-modal'} ref={editSectionModalRef} className={'add-section-modal'} isLarge>
                <AddSection
                    pageId={page.id}
                    tabId={selected?.id}
                    onSectionTouched={() => {
                        onCloseEditSectionModal();
                        showAlert({ message: `Your changes have been saved succesfully.`, type: `success` });
                        refresh();
                    }}
                    onCancel={onCloseEditSectionModal}
                    isEdit={true}
                    section={section}
                />
            </Modal>

            <Modal
                id={'add-section-modal'}
                ref={addSubsectionModalRef}
                className={'add-section-modal'}
                isLarge
                forceAction>
                <AddSubSection
                    sectionId={section.id}
                    pageId={page.id}
                    onCancel={onCloseAddSubSection}
                    onSubSectionTouched={(section: string) => {
                        onCloseAddSubSection();
                        showAlert({ message: `You have successfully added subsection "${section}"`, type: `success` });
                        refresh();
                    }}
                />
            </Modal>

            <Modal
                id={'add-section-modal'}
                ref={editSubsectionModalRef}
                className={'add-section-modal'}
                isLarge
                forceAction>
                <AddSubSection
                    sectionId={section.id}
                    pageId={page.id}
                    onCancel={onCloseEditSubsectionModal}
                    onSubSectionTouched={() => {
                        onCloseEditSubsectionModal();
                        showAlert({ message: `Your changes have been successfully updated`, type: `success` });
                        refresh();
                    }}
                    subsectionEdit={editSubsection}
                    isEdit
                />
            </Modal>

            <Modal
                id={'manage-section-modal'}
                ref={manageSubsectionModalRef}
                className={'manage-section-modal'}
                forceAction
                isLarge>
                <DragDropProvider pageData={page} successCallBack={handleReorderSubsection}>
                    <ManageSubsection
                        section={section}
                        alert={alert}
                        onResetAlert={() => setAlert(undefined)}
                        onSetAlert={(message, type) => {
                            setAlert({ message: message, type: type });
                        }}
                        onCancel={onCloseManageSubsection}
                    />
                </DragDropProvider>
            </Modal>

            <ConfirmationModal
                modal={deleteSubsectionModalRef}
                title="Warning"
                message="Are you sure you want to delete the subsection?"
                detail="Deleting this subsection cannot be undone. Are you sure you want to continue?"
                confirmText="Yes, delete"
                onConfirm={() => {
                    deleteSubsection();
                    deleteSubsectionModalRef.current?.toggleModal();
                }}
                onCancel={() => {
                    deleteSubsectionModalRef.current?.toggleModal();
                }}
            />
        </div>
    );
};
