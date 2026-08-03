import { useRef } from 'react';

import { ModalRef, Icon, ModalToggleButton } from '@trussworks/react-uswds';

import { ConfirmationModal } from '../../../../confirmation';

import styles from './delete-question.module.scss';

type CommonProps = {
    isStaticElement: boolean;
    onDelete?: () => void;
};

const DeleteQuestion = ({ onDelete, isStaticElement }: CommonProps) => {
    const deleteModalRef = useRef<ModalRef>(null);
    const handleDeleteQuetions = () => {
        onDelete?.();
    };
    return (
        <div className={styles.delete}>
            <ModalToggleButton modalRef={deleteModalRef} className="delete-btn" unstyled={true}>
                <Icon.Delete style={{ cursor: 'pointer' }} className="primary-color" />
            </ModalToggleButton>
            <ConfirmationModal
                modal={deleteModalRef}
                confirmBtnClassName="questionDeleteConfirmBtn"
                title="Warning"
                message={`Are you sure you want to delete the ${isStaticElement ? 'static element' : 'question'}?`}
                // eslint-disable-next-line max-len
                detail={`Deleting this ${isStaticElement ? 'static element' : 'question'} cannot be undone. Are you sure you want to continue?`}
                confirmText="Yes, delete"
                onConfirm={() => {
                    handleDeleteQuetions();
                    deleteModalRef.current?.toggleModal();
                }}
            />
        </div>
    );
};

export default DeleteQuestion;
