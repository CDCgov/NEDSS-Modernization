import {
    PageStaticControllerService,
    PagesSubSection,
    PageQuestionControllerService,
    PagesQuestion
} from 'apps/page-builder/generated';
import { SubsectionHeader } from './SubsectionHeader';
import styles from './subsection.module.scss';
import { RefObject, useState } from 'react';
import { Question } from '../question/Question';
import { usePageManagement } from '../../usePageManagement';
import { authorization } from 'authorization/authorization';
import { useAlert } from 'alert';
import { ModalRef } from '@trussworks/react-uswds';

type Props = {
    subsection: PagesSubSection;
    onEditQuestion: (question: PagesQuestion) => void;
    onAddQuestion: () => void;
    addQuestionModalRef: RefObject<ModalRef>;
};

const hyperlinkID = 1003;
const lineSeparatorID = 1012;
const readOnlyParticipants = 1030;
const readOnlyComments = 1014;
const originalElecDoc = 1036;

const staticElementTypes = [hyperlinkID, lineSeparatorID, readOnlyParticipants, readOnlyComments, originalElecDoc];

export const Subsection = ({ subsection, onAddQuestion, addQuestionModalRef, onEditQuestion }: Props) => {
    const [isExpanded, setIsExpanded] = useState<boolean>(true);
    const { page, fetch } = usePageManagement();
    const { showAlert } = useAlert();

    const handleAlert = (message: string) => {
        showAlert({ message: message, type: 'success' });
    };

    const handleExpandedChange = (expanded: boolean) => {
        setIsExpanded(expanded);
    };

    const handleDeleteQuestion = (id: number, componentId: number) => {
        if (staticElementTypes.includes(componentId)) {
            PageStaticControllerService.deleteStaticElementUsingDelete({
                authorization: authorization(),
                page: page.id,
                request: { componentId: id }
            }).then(() => {
                handleAlert(`Element deleted successfully`);
                fetch(page.id);
            });
        } else {
            PageQuestionControllerService.deleteQuestionUsingDelete({
                authorization: authorization(),
                page: page.id,
                questionId: Number(id)
            }).then(() => {
                fetch(page.id);
                handleAlert(`Question deleted successfully`);
            });
        }
    };

    const handleRequiredChange = (id: number) => {
        console.log('update question NYI', id);
    };

    return (
        <div className={styles.subsection}>
            <SubsectionHeader
                name={subsection.name ?? ''}
                id={subsection.id}
                questionCount={subsection.questions?.length ?? 0}
                onAddQuestion={onAddQuestion}
                addQuestionModalRef={addQuestionModalRef}
                onExpandedChange={handleExpandedChange}
                isExpanded={isExpanded}
            />
            {isExpanded && (
                <>
                    {subsection.questions.map((q, k) => (
                        <Question
                            question={q}
                            key={k}
                            onEditQuestion={onEditQuestion}
                            onDeleteQuestion={handleDeleteQuestion}
                            onRequiredChange={handleRequiredChange}
                        />
                    ))}
                </>
            )}
        </div>
    );
};
