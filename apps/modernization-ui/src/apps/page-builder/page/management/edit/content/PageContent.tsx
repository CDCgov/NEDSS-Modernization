import { ModalRef } from '@trussworks/react-uswds';
import { useAlert } from 'libs/alert';
import { PagesQuestion, PagesSubSection, PagesTab } from 'apps/page-builder/generated';
import { useAddQuestionsToPage } from 'apps/page-builder/hooks/api/useAddQuestionsToPage';
import { useEffect, useRef, useState } from 'react';
import { usePageManagement } from '../../usePageManagement';
import { AddQuestionModal } from '../add-question/modal/AddQuestionModal';
import { ChangeValuesetModal } from '../change-valueset/ChangeValuesetModal';
import { EditQuestionModal } from '../edit-question/EditQuestionModal';
import { EditStaticElementModal } from '../edit-staticelement/EditStaticElementModal';
import { EditValuesetModal } from '../edit-valueset/EditValuesetModal';
import { Sections } from '../section/Sections';
import { PageSideMenu } from './PageSideMenu';
import styles from './page-content.module.scss';
import { GroupQuestionModal } from '../question/GroupQuestion/GroupQuestionModal';

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
    const [currentGroupSubsection, setCurrentGroupSubsection] = useState<PagesSubSection | undefined>(undefined);
    const [currentEditQuestion, setCurrentEditQuestion] = useState<PagesQuestion>();
    const [currentEditValueset, setCurrentEditValueset] = useState<string | undefined>(undefined);
    const [currentSubsection, setCurrentSubsection] = useState<PagesSubSection | undefined>(undefined);
    const { error, response, addQuestionsToPage } = useAddQuestionsToPage();
    const { showAlert, showError } = useAlert();
    const { page, refresh } = usePageManagement();

    const editStaticElementRef = useRef<ModalRef>(null);
    const addQuestionModalRef = useRef<ModalRef>(null);
    const editQuestionModalRef = useRef<ModalRef>(null);
    const editValuesetModalRef = useRef<ModalRef>(null);
    const changeValuesetModalRef = useRef<ModalRef>(null);
    const groupQuestionModalRef = useRef<ModalRef>(null);

    const handleAddQuestion = (subsection: PagesSubSection) => {
        setCurrentSubsection(subsection);
        addQuestionModalRef.current?.toggleModal();
    };

    const handleEditQuestion = (question: PagesQuestion) => {
        setCurrentEditQuestion(question);
        if (staticTypes.includes(question?.displayComponent ?? 0)) {
            editStaticElementRef.current?.toggleModal(undefined, true);
        } else if (questionTypes.includes(question?.displayComponent ?? 0)) {
            editQuestionModalRef.current?.toggleModal(undefined, true);
        }
    };

    const handleEditQuestionClose = () => {
        setCurrentEditQuestion(undefined);
    };

    const handleQuestionUpdated = () => {
        setCurrentEditQuestion(undefined);
        refresh();
    };

    const handleAddQuestionClose = (questions: number[]) => {
        if (currentSubsection?.isGrouped && (currentSubsection?.questions.length ?? 0) + questions.length > 20) {
            showError('Grouped subsections are limited to a total of 20 questions');
        } else if (questions.length > 0 && currentSubsection?.id && page.id) {
            addQuestionsToPage(questions, currentSubsection.id, page.id);
        }
    };

    const handleEditValueset = (valuesetName: string) => {
        setCurrentEditValueset(valuesetName);
        editValuesetModalRef.current?.toggleModal();
    };

    const handleValuesetEdited = () => {
        refresh();
    };

    const handleChangeValueset = (question: PagesQuestion) => {
        setCurrentEditQuestion(question);
        changeValuesetModalRef.current?.toggleModal();
    };

    const handleGroupQuestion = (subsection: PagesSubSection) => {
        setCurrentGroupSubsection(subsection);
        groupQuestionModalRef.current?.toggleModal();
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
                onEditValueset={handleEditValueset}
                onChangeValueset={handleChangeValueset}
                onGroupQuestion={handleGroupQuestion}
                onEditGroupedSubsection={handleGroupQuestion}
            />
            <PageSideMenu
                onAddSection={() => handleAddSection?.()}
                onManageSection={() => handleManageSection?.()}
                onReorderModal={() => handleReorderModal?.()}
            />
            <AddQuestionModal onAddQuestion={handleAddQuestionClose} modal={addQuestionModalRef} />
            <EditQuestionModal
                onUpdated={handleQuestionUpdated}
                onClosed={handleEditQuestionClose}
                question={currentEditQuestion}
                modal={editQuestionModalRef}
            />
            <EditStaticElementModal
                onClosed={handleEditQuestionClose}
                question={currentEditQuestion}
                modal={editStaticElementRef}
            />
            <EditValuesetModal
                onValuesetChanged={handleValuesetEdited}
                modal={editValuesetModalRef}
                valuesetName={currentEditValueset}
            />
            <ChangeValuesetModal
                page={page.id}
                onValuesetChanged={handleValuesetEdited}
                modal={changeValuesetModalRef}
                question={currentEditQuestion}
            />
            <GroupQuestionModal modal={groupQuestionModalRef} subsection={currentGroupSubsection} />
        </div>
    );
};
