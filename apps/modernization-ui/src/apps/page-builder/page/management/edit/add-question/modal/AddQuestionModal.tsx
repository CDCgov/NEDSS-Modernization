import { Icon, Modal, ModalRef } from '@trussworks/react-uswds';
import { RefObject, useState } from 'react';
import { QuestionSearch } from '../search/QuestionSearch';
import styles from './add-question-modal.module.scss';
import './AddQuestionModal.scss';
import { CreateQuestion } from 'apps/page-builder/components/CreateQuestion/CreateQuestion';
import { CloseableHeader } from 'apps/page-builder/components/CloseableHeader/CloseableHeader';

type Props = {
    modal: RefObject<ModalRef>;
    pageId: number;
    onClose?: (questions: number[]) => void;
    valueSetModalRef: RefObject<ModalRef>;
};
export const AddQuestionModal = ({ pageId, modal, onClose, valueSetModalRef }: Props) => {
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
                        onCancel={handleClose}
                        onAccept={handleClose}
                        onCreateNew={() => setState('add')}
                        pageId={pageId}
                    />
                ) : (
                    <>
                        <CloseableHeader
                            title={
                                <div className={styles.createQuestionHeader}>
                                    <Icon.ArrowBack onClick={() => setState('search')} /> Add question
                                </div>
                            }
                            onClose={handleClose}
                        />
                        <CreateQuestion
                            onAddQuestion={(e: number) => handleClose([e])}
                            onCloseModal={handleClose}
                            addValueModalRef={valueSetModalRef}
                        />
                    </>
                )}
            </div>
        </Modal>
    );
};
