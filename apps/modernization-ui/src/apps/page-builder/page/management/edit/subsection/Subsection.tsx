import { useAlert } from 'alert';
import {
    PageQuestionControllerService,
    PageStaticControllerService,
    PagesQuestion,
    PagesSubSection
} from 'apps/page-builder/generated';
import { useSetPageQuestionRequired } from 'apps/page-builder/hooks/api/useSetPageQuestionRequired';
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
    onEditSubsection: (subsecition: PagesSubSection) => void;
    onGroupQuestion: (subsecition: PagesSubSection) => void;
    onEditValueset: (valuesetName: string) => void;
    onChangeValueset: (question: PagesQuestion) => void;
};

const hyperlinkID = 1003;
const lineSeparatorID = 1012;
const readOnlyParticipants = 1030;
const readOnlyComments = 1014;
const originalElecDoc = 1036;

const staticElementTypes = [hyperlinkID, lineSeparatorID, readOnlyParticipants, readOnlyComments, originalElecDoc];

export const Subsection = ({
    subsection,
    onAddQuestion,
    onEditQuestion,
    onDeleteSubsection,
    onEditSubsection,
    onGroupQuestion,
    onEditValueset,
    onChangeValueset
}: Props) => {
    const [isExpanded, setIsExpanded] = useState<boolean>(true);
    const { page, refresh } = usePageManagement();
    const { showAlert } = useAlert();
    const { setRequired } = useSetPageQuestionRequired();

    const handleAlert = (message: string) => {
        showAlert({ message: message, type: 'success' });
    };

    const handleExpandedChange = (expanded: boolean) => {
        setIsExpanded(expanded);
    };

    const handleDeleteQuestion = (id: number, componentId: number) => {
        if (staticElementTypes.includes(componentId)) {
            PageStaticControllerService.deleteStaticElement({
                page: page.id,
                requestBody: { componentId: id }
            }).then(() => {
                handleAlert(`Element deleted successfully`);
                refresh();
            });
        } else {
            PageQuestionControllerService.deleteQuestion({
                page: page.id,
                questionId: Number(id)
            }).then(() => {
                refresh();
                handleAlert(`Question deleted successfully`);
            });
        }
    };

    const handleRequiredChange = (question: number, required: boolean) => {
        setRequired(page.id, question, required);
        refresh();
    };

    return (
        <div className={styles.subsection}>
            <SubsectionHeader
                subsection={subsection}
                onAddQuestion={onAddQuestion}
                onExpandedChange={handleExpandedChange}
                isExpanded={isExpanded}
                onDeleteSubsection={() => onDeleteSubsection(subsection)}
                onEditSubsection={() => onEditSubsection(subsection)}
                onGroupQuestion={onGroupQuestion}
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
                            onEditValueset={onEditValueset}
                            onChangeValueset={onChangeValueset}
                        />
                    ))}
                </>
            )}
        </div>
    );
};
