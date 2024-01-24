import { PagesQuestion, PagesSection, PagesSubSection, SubSectionControllerService } from 'apps/page-builder/generated';
import { SectionHeader } from './SectionHeader';
import styles from './section.module.scss';
import { Subsection } from '../subsection/Subsection';
import { RefObject, useEffect, useRef, useState } from 'react';
import { Modal, ModalRef } from '@trussworks/react-uswds';
import { AddSection } from './manage/AddSection';
import { useAlert } from 'alert';
import { usePageManagement } from '../../usePageManagement';
import './manage/ManageSectionModal.scss';
import { AddSubSection } from '../subsection/manage/AddSubSection';
import { AlertInLineProps } from './manage/ManageSectionModal';
import { ManageSubsection } from '../subsection/manage/ManageSubsection';
import { authorization } from 'authorization';

type Props = {
    section: PagesSection;
    onAddQuestion: (subsection: number) => void;
    onEditQuestion: (question: PagesQuestion) => void;
    handleDeleteSection: () => void;
    addQuestionModalRef: RefObject<ModalRef>;
    handleStatus: () => void;
    refresh?: () => void;
};

export const Section = ({
    section,
    onAddQuestion,
    addQuestionModalRef,
    onEditQuestion,
    handleDeleteSection,
    refresh,
    handleStatus
}: Props) => {
    const [isExpanded, setIsExpanded] = useState<boolean>(true);

    const { page, fetch, selected } = usePageManagement();

    const handleExpandedChange = (expanded: boolean) => {
        setIsExpanded(expanded);
    };

    const editSectionModalRef = useRef<ModalRef>(null);

    const addSubsectionModalRef = useRef<ModalRef>(null);

    const manageSubsectionModalRef = useRef<ModalRef>(null);

    const { showAlert } = useAlert();

    const [alert, setAlert] = useState<AlertInLineProps | undefined>(undefined);

    useEffect(() => {
        if (alert !== undefined) {
            setTimeout(() => setAlert(undefined), 5000);
        }
    }, [alert]);

    const handleManageSubsection = () => {
        manageSubsectionModalRef.current?.toggleModal(undefined, true);
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
            handleStatus();
        } else {
            SubSectionControllerService.deleteSubSectionUsingDelete({
                authorization: authorization(),
                page: page.id,
                subSectionId: subsection.id
            }).then(() => {
                showAlert({
                    message: `You've successfully deleted "${subsection.name}"`,
                    type: `success`
                });
                fetch(page.id);
            });
        }
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
                handleDeleteSection={handleDeleteSection}
                isExpanded={isExpanded}
            />
            {isExpanded && (
                <div className={styles.subsectionWrapper}>
                    {section.subSections?.map((subsection, k) => (
                        <Subsection
                            subsection={subsection}
                            key={k}
                            onEditQuestion={onEditQuestion}
                            addQuestionModalRef={addQuestionModalRef}
                            onAddQuestion={() => onAddQuestion(subsection.id!)}
                            onDeleteSubsection={handleDeleteSubsection}
                        />
                    ))}
                </div>
            )}
            <Modal id={'add-section-modal'} ref={editSectionModalRef} className={'add-section-modal'} isLarge>
                <AddSection
                    pageId={page.id}
                    tabId={selected?.id}
                    onSectionTouched={() => {
                        onCloseEditSectionModal?.();
                        showAlert({ message: `Your changes have been saved succesfully.`, type: `success` });
                        fetch(page.id);
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
                    onCancel={() => {
                        onCloseAddSubSection?.();
                    }}
                    onSubSectionTouched={(section: string) => {
                        onCloseAddSubSection?.();
                        showAlert({ message: `You have successfully added subsection "${section}"`, type: `success` });
                        refresh?.();
                    }}
                />
            </Modal>

            <Modal
                id={'manage-section-modal'}
                ref={manageSubsectionModalRef}
                className={'manage-section-modal'}
                forceAction
                isLarge>
                <ManageSubsection
                    section={section}
                    alert={alert}
                    onResetAlert={() => setAlert(undefined)}
                    onSetAlert={(message, type) => {
                        setAlert({ message: message, type: type });
                    }}
                    refresh={refresh}
                    onCancel={onCloseManageSubsection}
                />
            </Modal>
        </div>
    );
};
