import { Button, Icon } from '@trussworks/react-uswds';
import DeleteQuestion from 'apps/page-builder/components/DeleteQuestion/DeleteQuestion';
import { ToggleButton } from 'apps/page-builder/components/ToggleButton';
import { PagesQuestion } from 'apps/page-builder/generated';
import { Heading } from 'components/heading';
import { useEffect, useState } from 'react';
import styles from './question-header.module.scss';

type Props = {
    question: PagesQuestion;
    onRequiredChange: (required: boolean) => void;
    onEditQuestion?: () => void;
    onDeleteQuestion?: () => void;
};

const hyperlinkId = 1003;
const commentsReadOnlyId = 1014;
const lineSeparatorId = 1012;
const originalElecDocId = 1036;
const readOnlyPartId = 1030;

export const QuestionHeader = ({ question, onRequiredChange, onEditQuestion, onDeleteQuestion }: Props) => {
    const [required, setRequired] = useState<boolean>(question.required === true);

    useEffect(() => {
        setRequired(question.required === true);
    }, [question.required]);

    const getHeadingText = (displayComponent: number | undefined) => {
        switch (displayComponent) {
            case hyperlinkId:
                return 'Static element: Hyperlink';
            case lineSeparatorId:
                return 'Static element: Line separator';
            case commentsReadOnlyId:
                return 'Static element: Comment';
            case originalElecDocId:
                return 'Static element: Electronic document list';
            case readOnlyPartId:
                return 'Static element: Participant list';
            default:
                return 'Question';
        }
    };
    return (
        <div className={styles.header}>
            <div className={styles.typeDisplay}>
                {question.isStandard && <div className={styles.standardIndicator}>S</div>}
                {!question.isStandard && question.isPublished && <div className={styles.publishedIndicator}>P</div>}
                <Heading level={3}>{getHeadingText(question.displayComponent)}</Heading>
            </div>
            <div className={`${styles.questionButtons} question-header-button`}>
                <Button unstyled className={styles.editButton} type="button" onClick={onEditQuestion}>
                    <Icon.Edit style={{ cursor: 'pointer' }} size={3} className="primary-color" />
                </Button>
                {!question.isStandard && !question.isPublished && <DeleteQuestion onDelete={onDeleteQuestion} />}
                <div className={styles.divider}>|</div>
                <div className={styles.requiredToggle}>Not required</div>
                <ToggleButton
                    checked={required}
                    onChange={() => {
                        setRequired(!question.required);
                        onRequiredChange(!question.required);
                    }}
                />
                <div className={styles.requiredToggle}>Required</div>
            </div>
        </div>
    );
};
