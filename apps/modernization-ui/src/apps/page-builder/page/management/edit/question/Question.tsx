import { PagesQuestion } from 'apps/page-builder/generated';
import { QuestionHeader } from './QuestionHeader';

import styles from './question.module.scss';
import { QuestionContent } from './QuestionContent';

type Props = {
    question: PagesQuestion;
    onRequiredChange: (id: number) => void;
    onEditQuestion: (id: number) => void;
    onDeleteQuestion: (id: number, componentId: number) => void;
};
export const Question = ({ question, onRequiredChange, onEditQuestion, onDeleteQuestion }: Props) => {
    return (
        <div className={styles.question}>
            <div className={styles.borderedContainer}>
                <QuestionHeader
                    isStandard={question.isStandard ?? false}
                    isRequired={question.required ?? false}
                    onRequiredChange={() => onRequiredChange(question.id)}
                    onEditQuestion={() => onEditQuestion(question.id)}
                    onDeleteQuestion={() => onDeleteQuestion(question.id, question.displayComponent!)}
                />
                <QuestionContent
                    name={question.name}
                    type={question.dataType ?? ''}
                    displayComponent={question.displayComponent}
                />
            </div>
        </div>
    );
};
