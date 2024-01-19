import { Modal, ModalRef } from '@trussworks/react-uswds';
import { RefObject, useState } from 'react';
import { QuestionSearch } from './QuestionSearch';
import { CreateQuestionForm } from 'apps/page-builder/components/CreateQuestionForm/CreateQuestionForm';
import styles from './add-question-modal.module.scss';
import './AddQuestionModal.scss';

type Props = {
    modal: RefObject<ModalRef>;
    pageId: number;
    onClose?: (questions: number[]) => void;
    subsection?: number;
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
                    <QuestionSearch
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
