import { Modal, ModalRef } from '@trussworks/react-uswds';
import { PagesSubSection } from 'apps/page-builder/generated';
import { RefObject } from 'react';
import { GroupQuestion } from './GroupQuestion';
import './GroupQuestionModal.scss';
import styles from './group-question-modal.module.scss';
import { usePageManagement } from '../../../usePageManagement';

type Props = {
    modal: RefObject<ModalRef>;
    subsection?: PagesSubSection;
};
export const GroupQuestionModal = ({ modal, subsection }: Props) => {
    const { page, refresh } = usePageManagement();

    const handleCancel = () => {
        modal.current?.toggleModal();
    };
    const handleSuccess = () => {
        refresh();
        modal.current?.toggleModal();
    };
    return (
        <Modal
            isLarge
            ref={modal}
            forceAction
            className="group-question-modal"
            id="group-question-modal"
            aria-labelledby="group-question-modal"
            aria-describedby="group-question-modal">
            <div className={styles.modal}>
                {subsection && (
                    <GroupQuestion
                        page={page.id}
                        subsection={subsection}
                        questions={subsection.questions}
                        onCancel={handleCancel}
                        onSuccess={handleSuccess}
                    />
                )}
            </div>
        </Modal>
    );
};
