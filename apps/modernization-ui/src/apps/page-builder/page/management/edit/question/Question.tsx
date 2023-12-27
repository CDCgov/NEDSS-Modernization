import { PagesQuestion } from 'apps/page-builder/generated';
import { QuestionHeader } from './QuestionHeader';
import styles from './question.module.scss';
import { QuestionContent } from './QuestionContent';
import { ModalRef } from '@trussworks/react-uswds';
import { ConfirmationModal } from 'confirmation';
import { useEffect, useRef, useState } from 'react';

type Props = {
    question: PagesQuestion;
    onRequiredChange: (id: number) => void;
    onEditQuestion: (id: number, componentId: number) => void;
    onDeleteQuestion: (id: number, componentId: number) => void;
};

const staticComponents = [1003, 1036, 1012, 1014, 1030, undefined];

export const Question = ({ question, onRequiredChange, onEditQuestion, onDeleteQuestion }: Props) => {
    const modal = useRef<ModalRef>(null);
    const [confirmModal, setConfirmModal] = useState(false);

    useEffect(() => {
        const shown = confirmModal && staticComponents.includes(question.displayComponent);
        modal.current?.toggleModal(undefined, shown);
    }, [confirmModal]);

    return (
        <div className={styles.question}>
            <div className={styles.borderedContainer}>
                <QuestionHeader
                    isStandard={question.isStandard ?? false}
                    isRequired={question.required ?? false}
                    question={question}
                    onRequiredChange={() => onRequiredChange(question.id)}
                    onEditQuestion={() => onEditQuestion(question.id, question.displayComponent!)}
                    onDeleteQuestion={() => {
                        setConfirmModal(true);
                    }}
                />
                {confirmModal === true && (
                    <ConfirmationModal
                        modal={modal}
                        title="Warning"
                        message="Are you sure you want to delete element?"
                        detail="You have indicated that you would like to delete element. Select Delete to continue or Cancel to return to Edit Page."
                        confirmText="Delete"
                        onConfirm={() => {
                            onDeleteQuestion(question.id, question.displayComponent!);
                            setConfirmModal(false);
                        }}
                        cancelText="Cancel"
                        onCancel={() => {}}
                    />
                )}
                <QuestionContent
                    name={question.name}
                    type={question.dataType ?? ''}
                    displayComponent={question.displayComponent}
                />
            </div>
        </div>
    );
};
