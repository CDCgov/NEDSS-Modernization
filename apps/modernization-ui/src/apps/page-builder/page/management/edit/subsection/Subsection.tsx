import { PagesSubSection } from 'apps/page-builder/generated';
import { SubsectionHeader } from './SubsectionHeader';
import styles from './subsection.module.scss';
import { useState } from 'react';
import { Question } from '../question/Question';

type Props = {
    subsection: PagesSubSection;
    onAddQuestion: () => void;
};
export const Subsection = ({ subsection, onAddQuestion }: Props) => {
    const [isExpanded, setIsExpanded] = useState<boolean>(true);

    const handleExpandedChange = (expanded: boolean) => {
        setIsExpanded(expanded);
    };

    const handleEditQuestion = (id: number) => {
        console.log('edit question NYI', id);
    };
    const handleDeleteQuestion = (id: number) => {
        console.log('delete question NYI', id);
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
