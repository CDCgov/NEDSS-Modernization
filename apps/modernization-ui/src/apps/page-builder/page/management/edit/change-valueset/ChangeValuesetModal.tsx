import { Modal, ModalRef } from '@trussworks/react-uswds';
import { ValuesetSearch } from 'apps/page-builder/components/AddQuestion/valueset/ValuesetSearch';
import { PagesQuestion } from 'apps/page-builder/generated';
import { PageProvider } from 'page';
import { RefObject, useEffect } from 'react';
import './ChangeValuesetModal.scss';
import styles from './change-valueset-modal.module.scss';
import { useUpdatePageQuestionValueset } from 'apps/page-builder/hooks/api/useUpdatePageQuestionValueset';
import { useAlert } from 'alert';
import { Spinner } from 'components/Spinner';

type Props = {
    modal: RefObject<ModalRef>;
    page: number;
    question?: PagesQuestion;
    onValuesetChanged: () => void;
};
export const ChangeValuesetModal = ({ modal, question, page, onValuesetChanged }: Props) => {
    const { response, error, update, isLoading } = useUpdatePageQuestionValueset();
    const { showError, showSuccess } = useAlert();
    const handleClose = () => {
        modal.current?.toggleModal(undefined, false);
    };

    const handleSetValueset = (valueset: number) => {
        if (question) {
            update(page, question.id, valueset);
        }
    };

    useEffect(() => {
        if (response) {
            showSuccess('Successfully updated question value set');
            onValuesetChanged();
            modal.current?.toggleModal(undefined, false);
        } else if (error) {
            showError('Failed to update question value set');
        }
    }, [response, error]);

    return (
        <Modal
            isLarge
            ref={modal}
            forceAction
            className="change-valueset-modal"
            id="change-valueset-modal"
            aria-labelledby="change-valueset-modal"
            aria-describedby="change-valueset-modal">
            <div className={styles.modal}>
                {isLoading && (
                    <div className={styles.loadingIndicator}>
                        <Spinner />
                    </div>
                )}
                <PageProvider>
                    <ValuesetSearch onCancel={handleClose} onClose={handleClose} onAccept={handleSetValueset} />
                </PageProvider>
            </div>
        </Modal>
    );
};
