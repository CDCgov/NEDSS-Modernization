import styles from './question-header.module.scss';
import { Icon, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { ToggleButton } from 'apps/page-builder/components/ToggleButton';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { Heading } from 'components/heading';
import { useRef } from 'react';
import { EditStaticElement } from '../staticelement/EditStaticElement';
import { PagesQuestion } from 'apps/page-builder/generated';
import classNames from 'classnames';
import DeleteQuestion from 'apps/page-builder/components/DeleteQuestion/DeleteQuestion';

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

export const QuestionHeader = ({
    question,
    onRequiredChange,
    onEditQuestion,
    onDeleteQuestion,
    visible = true
}: Props) => {
    const editStaticElementRef = useRef<ModalRef>(null);
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
        <div className={classNames(styles.header, { [styles.visible]: visible })}>
            <div className={styles.typeDisplay}>
                {question.isStandard && <div className={styles.standardIndicator}>S</div>}
                <Heading level={3}>{getHeadingText(question.displayComponent)}</Heading>
            </div>
            <div className={styles.questionButtons}>
                <ModalToggleButton type="button" modalRef={editStaticElementRef}>
                    <Icon.Edit size={3} />
                </ModalToggleButton>
                <Icon.Delete onClick={onDeleteQuestion} />
                <Icon.Edit onClick={onEditQuestion} />
                {!question.isStandard && <DeleteQuestion onDelete={onDeleteQuestion} />}
                <div className={styles.divider}>|</div>
                <div className={styles.requiredToggle}>Required</div>
                <ToggleButton defaultChecked={question.required} onChange={onRequiredChange} />
            </div>
            <ModalComponent
                modalRef={editStaticElementRef}
                modalHeading={'Edit static elements'}
                modalBody={
                    <EditStaticElement modalRef={editStaticElementRef} question={question} onChange={onEditQuestion} />
                }
            />
        </div>
    );
};
