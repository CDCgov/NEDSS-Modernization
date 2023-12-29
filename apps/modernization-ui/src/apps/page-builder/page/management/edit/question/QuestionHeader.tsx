import styles from './question-header.module.scss';
import { Icon } from '@trussworks/react-uswds';
import { ToggleButton } from 'apps/page-builder/components/ToggleButton';
import { PagesQuestion } from 'apps/page-builder/generated';
import classNames from 'classnames';
import { Heading } from 'components/heading';
type Props = {
    visible?: boolean;
    question: PagesQuestion;
    onRequiredChange?: () => void;
    onEditQuestion?: () => void;
    onDeleteQuestion?: () => void;
};

const hyperlinkId = 1003;
const commentsReadOnlyId = 1014;
const lineSeparatorId = 1012;
const originalElecDocId = 1036;
const readOnlyPartId = 1030;

const staticTypes = [hyperlinkId, commentsReadOnlyId, lineSeparatorId, originalElecDocId, readOnlyPartId];

export const QuestionHeader = ({
    question,
    onRequiredChange,
    onEditQuestion,
    onDeleteQuestion,
    visible = true
}: Props) => {
    return (
        <div className={classNames(styles.header, { [styles.visible]: visible })}>
            <div className={styles.typeDisplay}>
                {question.isStandard && <div className={styles.standardIndicator}>S</div>}
                {staticTypes.includes(question.displayComponent!) ? (
                    <>
                        {question.displayComponent === hyperlinkId && (
                            <Heading level={3}>Static element: Hyperlink</Heading>
                        )}
                        {question.displayComponent === lineSeparatorId && (
                            <Heading level={3}>Static element: Line separator</Heading>
                        )}
                        {question.displayComponent === commentsReadOnlyId && (
                            <Heading level={3}>Static element: Comment </Heading>
                        )}
                        {question.displayComponent === originalElecDocId && (
                            <Heading level={3}>Static element: Electronic document list</Heading>
                        )}
                        {question.displayComponent === readOnlyPartId && (
                            <Heading level={3}>Static element: Participant list</Heading>
                        )}
                    </>
                ) : (
                    <>
                        <Heading level={3}>Question</Heading>
                    </>
                )}
            </div>
            <div className={styles.questionButtons}>
                <Icon.Edit onClick={onEditQuestion} />
                <Icon.Delete onClick={onDeleteQuestion} />
                <div className={styles.divider}>|</div>
                <div className={styles.requiredToggle}>Required</div>
                <ToggleButton defaultChecked={question.required} onChange={onRequiredChange} />
            </div>
        </div>
    );
};
