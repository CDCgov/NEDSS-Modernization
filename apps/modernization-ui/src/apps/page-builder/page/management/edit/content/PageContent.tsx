import { PagesQuestion, PagesTab } from 'apps/page-builder/generated';
import { Sections } from '../section/Sections';
import { PageSideMenu } from './PageSideMenu';
import styles from './page-content.module.scss';
import { EditStaticElement } from '../staticelement/EditStaticElement';
import { Icon, ModalRef } from '@trussworks/react-uswds';
import { useEffect, useRef, useState } from 'react';
import { ValuesetLibrary } from '../../../../pages/ValuesetLibrary/ValuesetLibrary';
import { AddValueset } from '../../../../components/AddValueset/AddValueset';
import { CreateQuestion } from '../../../../components/CreateQuestion/CreateQuestion';
import { Heading } from '../../../../../../components/heading';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { AddQuestionModal } from '../add-question/modal/AddQuestionModal';
import { usePageManagement } from '../../usePageManagement';
import { useAlert } from 'alert';
import { useAddQuestionsToPage } from 'apps/page-builder/hooks/api/useAddQuestionsToPage';

type Props = {
    tab: PagesTab;
    refresh?: () => void;
    handleManageSection?: () => void;
    handleAddSection?: () => void;
};

const hyperlinkId = 1003;
const commentsReadOnlyId = 1014;
const lineSeparatorId = 1012;
const originalElecDocId = 1036;
const readOnlyPartId = 1030;

const staticTypes = [hyperlinkId, commentsReadOnlyId, lineSeparatorId, originalElecDocId, readOnlyPartId];

const questionTypes = [1001, 1006, 1007, 1008, 1009, 1013, 1017, 1019, 1024, 1025, 1026, 1027, 1028, 1029, 1031, 1032];

export const PageContent = ({ tab, refresh, handleAddSection, handleManageSection }: Props) => {
    const [currentEditQuestion, setCurrentEditQuestion] = useState<PagesQuestion>();
    const [subsectionId, setSubsectionId] = useState<number | undefined>(undefined);
    const { error, response, add } = useAddQuestionsToPage();
    const { showAlert } = useAlert();
    const { page } = usePageManagement();

    const editStaticElementRef = useRef<ModalRef>(null);
    const addQuestionModalRef = useRef<ModalRef>(null);
    const addValueModalRef = useRef<ModalRef>(null);
    const createValueModalRef = useRef<ModalRef>(null);
    const editQuestionModalRef = useRef<ModalRef>(null);

    const handleAddQuestion = (subsection: number) => {
        console.log('showing add question modal for subsection: ', subsection);
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
            refresh?.();
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
                refresh={refresh}
            />
            <PageSideMenu onAddSection={() => handleAddSection?.()} onManageSection={() => handleManageSection?.()} />
            <ModalComponent
                modalRef={editStaticElementRef}
                modalHeading={'Edit static elements'}
                modalBody={
                    currentEditQuestion !== undefined && (
                        <EditStaticElement
                            question={currentEditQuestion}
                            onCloseModal={onCloseModal}
                            refresh={refresh}
                        />
                    )
                }
            />
            <AddQuestionModal
                subsection={subsectionId}
                pageId={page.id}
                onClose={handleAddQuestionClose}
                modal={addQuestionModalRef}
            />
            <ModalComponent
                modalRef={editQuestionModalRef}
                closer
                size="wide"
                onCloseModal={onCloseModal}
                modalHeading={
                    <div className="edit-question-header">
                        <Heading level={2}>Edit question</Heading>
                    </div>
                }
                modalBody={
                    <CreateQuestion
                        onCloseModal={onCloseModal}
                        question={currentEditQuestion}
                        addValueModalRef={addValueModalRef}
                    />
                }
            />
            <ModalComponent
                size="wide"
                modalRef={addValueModalRef}
                modalHeading={<Heading level={2}>Search value set</Heading>}
                modalBody={
                    <ValuesetLibrary hideTabs modalRef={addValueModalRef} createValueModalRef={createValueModalRef} />
                }
                closer
            />
            <ModalComponent
                isLarge
                size="wide"
                modalRef={createValueModalRef}
                modalHeading={
                    <span className="header-icon-title">
                        <Icon.ArrowBack />
                        <Heading level={2}>Add value set</Heading>
                    </span>
                }
                modalBody={
                    <AddValueset
                        modalRef={createValueModalRef}
                        updateCallback={() => {
                            createValueModalRef.current?.toggleModal(undefined, false);
                            addValueModalRef.current?.toggleModal(undefined, false);
                        }}
                    />
                }
                closer
            />
        </div>
    );
};
