import { Modal, ModalRef } from '@trussworks/react-uswds';
import { PaginationProvider, usePagination } from 'pagination';
import { RefObject, useEffect, useState } from 'react';
import { usePageManagement } from '../../../usePageManagement';
import { QuestionSearch } from '../search/QuestionSearch';
import './AddQuestionModal.scss';
import styles from './add-question-modal.module.scss';
import { AddQuestion } from 'apps/page-builder/components/AddQuestion/AddQuestion';

type Props = {
    modal: RefObject<ModalRef>;
    onAddQuestion: (questions: number[]) => void;
};

export const AddQuestionModal = ({ modal, onAddQuestion }: Props) => {
    const [state, setState] = useState<'search' | 'add'>('search');

    const handleClose = () => {
        modal.current?.toggleModal(undefined, false);
        setState('search');
    };

    const handleAccept = (questions: number[]) => {
        onAddQuestion(questions);
        setState('search');
        modal.current?.toggleModal(undefined, false);
    };
    return (
        <Modal
            isLarge
            ref={modal}
            forceAction
            className="add-question-modal"
            id="add-question-modal"
            aria-labelledby="modal-1-heading"
            aria-describedby="modal-1-description">
            <div className={styles.modal}>
                {state === 'search' ? (
                    <PaginationProvider>
                        <QuestionSearchWrapper
                            onClose={handleClose}
                            onAccept={handleAccept}
                            onCreateNew={() => setState('add')}
                        />
                    </PaginationProvider>
                ) : (
                    <AddQuestion
                        onBack={() => setState('search')}
                        onClose={handleClose}
                        onQuestionCreated={(e) => handleAccept([e])}
                    />
                )}
            </div>
        </Modal>
    );
};

type WrapperProps = {
    onClose: () => void;
    onAccept: (questions: number[]) => void;
    onCreateNew: () => void;
};
const QuestionSearchWrapper = ({ onClose, onCreateNew, onAccept }: WrapperProps) => {
    const { firstPage } = usePagination();
    const { page } = usePageManagement();

    useEffect(() => {
        firstPage();
    }, [JSON.stringify(page)]);

    const handleCancel = () => {
        onClose();
        firstPage();
    };
    return <QuestionSearch onCancel={handleCancel} onAccept={onAccept} onCreateNew={onCreateNew} pageId={page.id} />;
};
