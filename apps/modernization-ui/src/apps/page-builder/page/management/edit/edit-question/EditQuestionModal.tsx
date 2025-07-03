import { Button, Modal, ModalRef } from '@trussworks/react-uswds';
import { useAlert } from 'libs/alert';
import { ButtonBar } from 'apps/page-builder/components/ButtonBar/ButtonBar';
import { CloseableHeader } from 'apps/page-builder/components/CloseableHeader/CloseableHeader';
import { PagesQuestion } from 'apps/page-builder/generated';
import { useFetchEditableQuestion } from 'apps/page-builder/hooks/api/useFetchEditableQuestion';
import { useUpdatePageQuestion } from 'apps/page-builder/hooks/api/useUpdatePageQuestion';
import { RefObject, useEffect } from 'react';
import { FormProvider, useForm, useFormState } from 'react-hook-form';
import { usePageManagement } from '../../usePageManagement';
import './EditQuestionModal.scss';
import styles from './edit-question-modal.module.scss';
import { EditPageQuestion, EditPageQuestionForm } from './form/EditPageQuestion';
import { Spinner } from 'components/Spinner';
type Props = {
    modal: RefObject<ModalRef>;
    question?: PagesQuestion;
    onClosed: () => void;
    onUpdated: () => void;
};
export const EditQuestionModal = ({ modal, question, onClosed, onUpdated }: Props) => {
    const handleClose = () => {
        modal.current?.toggleModal();
        onClosed();
    };

    const handleUpdated = () => {
        modal.current?.toggleModal();
        onUpdated();
    };
    return (
        <Modal
            isLarge
            ref={modal}
            forceAction
            className="edit-question-modal"
            id="edit-question-modal"
            aria-labelledby="edit-question-modal"
            aria-describedby="edit-question-modal">
            <div className={styles.modal}>
                {question && (
                    <EditQuestionContent question={question} onClose={handleClose} onUpdated={handleUpdated} />
                )}
            </div>
        </Modal>
    );
};

type ContentProps = {
    onClose: () => void;
    onUpdated: () => void;
    question: PagesQuestion;
};
const EditQuestionContent = ({ onUpdated, onClose, question }: ContentProps) => {
    const { page } = usePageManagement();
    const { response: editableQuestion, fetch } = useFetchEditableQuestion();
    const form = useForm<EditPageQuestionForm>({ mode: 'onBlur' });
    const { showError, showSuccess } = useAlert();
    const { isLoading, response, error, update } = useUpdatePageQuestion();
    const { isDirty, isValid } = useFormState(form);

    const handleSave = () => {
        if (question?.id) {
            update(page.id, question.id, { ...form.getValues() });
        }
    };

    useEffect(() => {
        fetch(page.id, question.id);
    }, [question.id]);

    useEffect(() => {
        if (editableQuestion) {
            let unitType: 'literal' | 'coded' | undefined;
            if (editableQuestion.relatedUnitsLiteral) {
                unitType = 'literal';
            } else if (editableQuestion.relatedUnitsValueSet) {
                unitType = 'coded';
            }
            form.reset({
                ...editableQuestion,
                questionType: editableQuestion.questionType as 'TEXT' | 'CODED' | 'NUMERIC' | 'DATE',
                relatedUnits: unitType !== undefined,
                unitType: unitType
            });
        }
    }, [editableQuestion]);

    useEffect(() => {
        if (response) {
            showSuccess('Successfully updated question');
            onUpdated();
        } else if (error) {
            showError('Failed to update question');
        }
    }, [error, response]);

    return (
        <>
            {isLoading && (
                <div className={styles.loadingIndicator}>
                    <Spinner />
                </div>
            )}
            <CloseableHeader title="Edit question" onClose={onClose} />
            <div className={styles.content}>
                <div className={styles.formWrapper}>
                    <FormProvider {...form}>
                        <EditPageQuestion page={page.id} question={question} />
                    </FormProvider>
                </div>
            </div>
            <ButtonBar>
                <Button type="button" onClick={onClose}>
                    Cancel
                </Button>
                <Button
                    className="editQuestionSaveBtn"
                    disabled={!isDirty || !isValid}
                    type="button"
                    onClick={handleSave}>
                    Save changes
                </Button>
            </ButtonBar>
        </>
    );
};
