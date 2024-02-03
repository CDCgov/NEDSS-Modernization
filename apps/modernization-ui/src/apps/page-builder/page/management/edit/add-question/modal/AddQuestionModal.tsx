import { Modal, ModalRef } from '@trussworks/react-uswds';
import { AddQuestion } from 'apps/page-builder/components/AddQuestion/AddQuestion';
import { PageProvider, usePage } from 'page';
import { RefObject, useEffect, useState } from 'react';
import { usePageManagement } from '../../../usePageManagement';
import { QuestionSearch } from '../search/QuestionSearch';
import './AddQuestionModal.scss';
import styles from './add-question-modal.module.scss';

type Props = {
    modal: RefObject<ModalRef>;
    pageId: number;
    onClose?: (questions: number[]) => void;
    valueSetModalRef: RefObject<ModalRef>;
};

export const AddQuestionModal = (props: Props) => {
    return (
        <PageProvider>
            <AddQuestionModalContent {...props} />
        </PageProvider>
    );
};

const AddQuestionModalContent = ({ pageId, modal, onClose }: Props) => {
    const [state, setState] = useState<'search' | 'add'>('search');
    const { page } = usePageManagement();
    const { firstPage } = usePage();

    const handleClose = (questions?: number[]) => {
        if (onClose) {
            onClose(questions ?? []);
        }
        setState('search');
        if (!questions) {
            firstPage();
        }
        modal.current?.toggleModal(undefined, false);
    };

    useEffect(() => {
        firstPage();
    }, [JSON.stringify(page)]);

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
                    <AddQuestion
                        onBack={() => setState('search')}
                        onClose={handleClose}
                        onQuestionCreated={(e) => handleClose([e])}
                    />
                )}
            </div>
        </Modal>
    );
};
