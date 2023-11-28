import { Modal, ModalRef } from '@trussworks/react-uswds';
import { CreateQuestionForm } from 'components/CreateQuestionForm/CreateQuestionForm';
import { RefObject, useState } from 'react';
import './AddQuestionModal.scss';
import { QuestionLookup } from './QuestionLookup';
import styles from './add-question-modal.module.scss';

type Props = {
    modal: RefObject<ModalRef>;
    pageId: number;
    onClose?: (questions: number[]) => void;
};
export const AddQuestionModal = ({ pageId, modal, onClose }: Props) => {
    const [state, setState] = useState<'search' | 'add'>('search');

    const handleClose = (questions?: number[]) => {
        if (onClose) {
            onClose(questions ?? []);
        }
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
                    <QuestionLookup
                        onCancel={() => handleClose([])}
                        onAccept={handleClose}
                        onCreateNew={() => setState('add')}
                        pageId={pageId}
                    />
                ) : (
                    <CreateQuestionForm
                        onCreated={(question) => handleClose([question])}
                        onCancel={() => handleClose([])}
                    />
                )}
            </div>
        </Modal>
    );
};
