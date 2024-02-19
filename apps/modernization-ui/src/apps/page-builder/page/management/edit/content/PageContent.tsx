import { ModalRef } from '@trussworks/react-uswds';
import { useAlert } from 'alert';
import { PagesQuestion, PagesTab } from 'apps/page-builder/generated';
import { useAddQuestionsToPage } from 'apps/page-builder/hooks/api/useAddQuestionsToPage';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { useEffect, useRef, useState } from 'react';
import { usePageManagement } from '../../usePageManagement';
import { AddQuestionModal } from '../add-question/modal/AddQuestionModal';
import { Sections } from '../section/Sections';
import { EditStaticElement } from '../staticelement/EditStaticElement';
import { PageSideMenu } from './PageSideMenu';
import styles from './page-content.module.scss';

type Props = {
    tab: PagesTab;
    handleManageSection?: () => void;
    handleAddSection?: () => void;
    handleReorderModal?: () => void;
};

const hyperlinkId = 1003;
const commentsReadOnlyId = 1014;
const lineSeparatorId = 1012;
const originalElecDocId = 1036;
const readOnlyPartId = 1030;

const staticTypes = [hyperlinkId, commentsReadOnlyId, lineSeparatorId, originalElecDocId, readOnlyPartId];

const questionTypes = [1001, 1006, 1007, 1008, 1009, 1013, 1017, 1019, 1024, 1025, 1026, 1027, 1028, 1029, 1031, 1032];

export const PageContent = ({ tab, handleAddSection, handleManageSection, handleReorderModal }: Props) => {
    const [currentEditQuestion, setCurrentEditQuestion] = useState<PagesQuestion>();
    const [subsectionId, setSubsectionId] = useState<number>(-1);
    const { error, response, add } = useAddQuestionsToPage();
    const { showAlert } = useAlert();
    const { page, refresh } = usePageManagement();

    const editStaticElementRef = useRef<ModalRef>(null);
    const addQuestionModalRef = useRef<ModalRef>(null);
    const editQuestionModalRef = useRef<ModalRef>(null);

    const handleAddQuestion = (subsection: number) => {
        setSubsectionId(subsection);
        addQuestionModalRef.current?.toggleModal();
    };

    const onCloseModal = () => {
        if (staticTypes.includes(currentEditQuestion?.displayComponent!)) {
            editStaticElementRef.current?.toggleModal(undefined, false);
        } else {
            editQuestionModalRef.current?.toggleModal(undefined, false);
        }
        setCurrentEditQuestion(undefined);
    };

    const handleEditQuestion = (question: PagesQuestion) => {
        setCurrentEditQuestion(question);
    };

    useEffect(() => {
        if (staticTypes.includes(currentEditQuestion?.displayComponent!)) {
            editStaticElementRef.current?.toggleModal(undefined, true);
        } else if (questionTypes.includes(currentEditQuestion?.displayComponent!)) {
            editQuestionModalRef.current?.toggleModal(undefined, true);
        }
    }, [currentEditQuestion]);

    const handleAddQuestionClose = (questions: number[]) => {
        if (questions.length > 0 && subsectionId && page.id) {
            add(questions, subsectionId, page.id);
        }
    };

    useEffect(() => {
        if (response) {
            showAlert({
                type: 'success',
                message: `Successfully added questions to page.`
            });
            refresh();
        }
        if (error) {
            showAlert({
                type: 'error',
                message: `Failed to add question(s) to page.`
            });
        }
    }, [response, error]);

    return (
        <div className={styles.pageContent}>
            <div className={styles.invisible} />
            <Sections
                sections={tab.sections ?? []}
                onEditQuestion={handleEditQuestion}
                onAddQuestion={handleAddQuestion}
            />
            <PageSideMenu
                onAddSection={() => handleAddSection?.()}
                onManageSection={() => handleManageSection?.()}
                onReorderModal={() => handleReorderModal?.()}
            />
            <ModalComponent
                modalRef={editStaticElementRef}
                modalHeading={'Edit static elements'}
                modalBody={
                    currentEditQuestion !== undefined && (
                        <EditStaticElement question={currentEditQuestion} onCloseModal={onCloseModal} />
                    )
                }
            />
            <AddQuestionModal onAddQuestion={handleAddQuestionClose} modal={addQuestionModalRef} />
        </div>
    );
};
