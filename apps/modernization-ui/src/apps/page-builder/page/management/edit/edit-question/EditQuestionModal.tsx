import { Modal, ModalRef } from '@trussworks/react-uswds';
import { RefObject } from 'react';
import './EditQuestionModal.scss';
import styles from './edit-question-modal.module.scss';
import { CloseableHeader } from 'apps/page-builder/components/CloseableHeader/CloseableHeader';
import { PagesQuestion } from 'apps/page-builder/generated';
type Props = {
    modal: RefObject<ModalRef>;
    question?: PagesQuestion;
};
export const EditQuestionModal = ({ modal, question }: Props) => {
    const handleClose = () => {
        modal.current?.toggleModal();
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
                <EditQuestionContent question={question} onClose={handleClose} />
            </div>
        </Modal>
    );
};

type ContentProps = {
    onClose: () => void;
    question?: PagesQuestion;
};
const EditQuestionContent = ({ onClose, question }: ContentProps) => {
    return (
        <>
            <CloseableHeader title="Edit question" onClose={onClose} />
            Question: {question?.id}
        </>
    );
};
