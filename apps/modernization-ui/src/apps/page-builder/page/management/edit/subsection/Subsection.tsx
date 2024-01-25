import { useAlert } from 'alert';
import {
    PageQuestionControllerService,
    PageStaticControllerService,
    PagesQuestion,
    PagesSubSection
} from 'apps/page-builder/generated';
import { authorization } from 'authorization/authorization';
import { useState } from 'react';
import { usePageManagement } from '../../usePageManagement';
import { Question } from '../question/Question';
import { SubsectionHeader } from './SubsectionHeader';
import styles from './subsection.module.scss';

type Props = {
    subsection: PagesSubSection;
    onEditQuestion: (question: PagesQuestion) => void;
    onAddQuestion: () => void;
    onDeleteSubsection: (subsection: PagesSubSection) => void;
};

const hyperlinkID = 1003;
const lineSeparatorID = 1012;
const readOnlyParticipants = 1030;
const readOnlyComments = 1014;
const originalElecDoc = 1036;

const staticElementTypes = [hyperlinkID, lineSeparatorID, readOnlyParticipants, readOnlyComments, originalElecDoc];

export const Subsection = ({ subsection, onAddQuestion, onEditQuestion, onDeleteSubsection }: Props) => {
    const [isExpanded, setIsExpanded] = useState<boolean>(true);
    const { page, refresh } = usePageManagement();
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
                refresh();
            });
        } else {
            PageQuestionControllerService.deleteQuestionUsingDelete({
                authorization: authorization(),
                page: page.id,
                questionId: Number(id)
            }).then(() => {
                refresh();
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
                onExpandedChange={handleExpandedChange}
                isExpanded={isExpanded}
                onDeleteSubsection={() => onDeleteSubsection(subsection)}
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
