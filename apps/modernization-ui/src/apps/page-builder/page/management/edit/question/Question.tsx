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
    onEditQuestion: (id: number) => void;
    onDeleteQuestion: (id: number, componentId: number) => void;
};
export const Question = ({ question, onRequiredChange, onEditQuestion, onDeleteQuestion }: Props) => {
    const modal = useRef<ModalRef>(null);
    const [confirmModal, setConfirmModal] = useState(false);

    const handleCancel = () => {
        window.location.reload();
    };

    useEffect(() => {
        const shown =
            confirmModal &&
            (question.displayComponent == 1003 ||
                question.displayComponent == 1036 ||
                question.displayComponent == 1012 ||
                question.displayComponent == 1014 ||
                question.displayComponent == 1030);
        modal.current?.toggleModal(undefined, shown);
    }, [confirmModal]);

    return (
        <div className={styles.question}>
            <div className={styles.borderedContainer}>
                <QuestionHeader
                    isStandard={question.isStandard ?? false}
                    isRequired={question.required ?? false}
                    onRequiredChange={() => onRequiredChange(question.id)}
                    onEditQuestion={() => onEditQuestion(question.id)}
                    onDeleteQuestion={() => {
                        setConfirmModal(true);
                        console.log('clicked', confirmModal);
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
                        onCancel={handleCancel}
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
