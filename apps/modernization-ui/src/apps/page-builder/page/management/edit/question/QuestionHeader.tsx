import styles from './question-header.module.scss';
import { Icon, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { ToggleButton } from 'apps/page-builder/components/ToggleButton';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { Heading } from 'components/heading';
import { useRef } from 'react';
import { EditStaticElement } from '../staticelement/EditStaticElement';
import { PagesQuestion } from 'apps/page-builder/generated';
type Props = {
    isStandard: boolean;
    isRequired: boolean;
    question: PagesQuestion;
    onRequiredChange?: () => void;
    onEditQuestion?: () => void;
    onDeleteQuestion?: () => void;
};
export const QuestionHeader = ({
    isStandard,
    isRequired,
    question,
    onRequiredChange,
    onEditQuestion,
    onDeleteQuestion
}: Props) => {
    const editStaticElementRef = useRef<ModalRef>(null);

    return (
        <div className={styles.header}>
            <div className={styles.typeDisplay}>
                {isStandard && <div className={styles.standardIndicator}>S</div>}
                <Heading level={3}>Question</Heading>
            </div>
            <div className={styles.questionButtons}>
                <ModalToggleButton type="button" modalRef={editStaticElementRef}>
                    <Icon.Edit size={3} />
                </ModalToggleButton>
                <Icon.Delete onClick={onDeleteQuestion} />
                <div className={styles.divider}>|</div>
                <div className={styles.requiredToggle}>Required</div>
                <ToggleButton defaultChecked={isRequired} onChange={onRequiredChange} />
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
