import { PagesQuestion } from 'apps/page-builder/generated';
import { QuestionHeader } from './QuestionHeader';
import styles from './question.module.scss';
import { QuestionContent } from './QuestionContent';
import { ModalRef } from '@trussworks/react-uswds';
import { ConfirmationModal } from 'confirmation';
import { useEffect, useRef, useState } from 'react';

type Props = {
    question: PagesQuestion;
    onRequiredChange: (id: number, required: boolean) => void;
    onEditQuestion: (question: PagesQuestion) => void;
    onDeleteQuestion: (id: number, componentId: number) => void;
    onEditValueset: (valuesetName: string) => void;
};

const staticComponents = [1003, 1036, 1012, 1014, 1030, undefined];

export const Question = ({ question, onRequiredChange, onEditQuestion, onDeleteQuestion, onEditValueset }: Props) => {
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
                    question={question}
                    onRequiredChange={(required) => onRequiredChange(question.id, required)}
                    onEditQuestion={() => onEditQuestion(question)}
                    onDeleteQuestion={() => {
                        onDeleteQuestion(question.id, question.displayComponent ?? 0);
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
                            modal.current?.toggleModal();
                        }}
                        cancelText="Cancel"
                        onCancel={() => {
                            modal.current?.toggleModal();
                        }}
                    />
                )}
                <QuestionContent
                    defaultValue={question.defaultValue ?? ''}
                    valueSet={question.valueSet ?? ''}
                    identifier={question.question ?? ''}
                    name={question.name}
                    id={question.id}
                    isStandard={question.isStandard ?? false}
                    type={question.dataType ?? ''}
                    displayComponent={question.displayComponent}
                    onEditValueset={onEditValueset}
                />
            </div>
        </div>
    );
};
