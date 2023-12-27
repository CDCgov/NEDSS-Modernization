import { PageStaticControllerService, PagesSubSection } from 'apps/page-builder/generated';
import { SubsectionHeader } from './SubsectionHeader';
import styles from './subsection.module.scss';
import { useState } from 'react';
import { Question } from '../question/Question';
import { usePageManagement } from '../../usePageManagement';
import { authorization } from 'authorization/authorization';
import { useAlert } from 'alert';

type Props = {
    subsection: PagesSubSection;
    onAddQuestion: () => void;
};

const hyperlinkID = 1003;
const lineSeparatorID = 1012;
const readOnlyParticipants = 1030;
const readOnlyComments = 1014;
const originalElecDoc = 1036;

const staticElementTypes = [hyperlinkID, lineSeparatorID, readOnlyParticipants, readOnlyComments, originalElecDoc];

export const Subsection = ({ subsection, onAddQuestion }: Props) => {
    const [isExpanded, setIsExpanded] = useState<boolean>(true);
    const { page, fetch } = usePageManagement();

    const { showAlert } = useAlert();

    const handleAlert = (message: string) => {
        showAlert({ message: message, type: 'success' });
    };

    const handleExpandedChange = (expanded: boolean) => {
        setIsExpanded(expanded);
    };

    const handleEditQuestion = (id: number, componentId: number) => {
        if (staticElementTypes.includes(componentId)) {
            console.log('edit question NYI', id);
            if (hyperlinkID === componentId) {
                PageStaticControllerService.updateHyperlinkUsingPut({
                    authorization: authorization(),
                    page: page.id,
                    request: undefined
                }).then(() => {
                    handleAlert("Element is editted");
                })
            } else if (readOnlyComments === componentId) {
            } else {
            }
        }
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
            console.log('delete question NYI', id);
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
            />
            {subsection.questions.map((q, k) => (
                <Question
                    question={q}
                    key={k}
                    onEditQuestion={handleEditQuestion}
                    onDeleteQuestion={handleDeleteQuestion}
                    onRequiredChange={handleRequiredChange}
                />
            ))}
        </div>
    );
};
