import { Modal, ModalRef } from '@trussworks/react-uswds';
import { CloseableHeader } from 'apps/page-builder/components/CloseableHeader/CloseableHeader';
import { EditValueset } from 'apps/page-builder/components/EditValueset/EditValueset';
import { useValueset } from 'apps/page-builder/hooks/api/useValueset';
import { Spinner } from 'components/Spinner';
import { RefObject, useEffect } from 'react';
import './EditValuesetModal.scss';
import styles from './edit-valueset-modal.module.scss';
type Props = {
    modal: RefObject<ModalRef>;
    valuesetName?: string;
    onValuesetChanged: () => void;
};
export const EditValuesetModal = ({ modal, valuesetName, onValuesetChanged }: Props) => {
    const { valueset, fetch } = useValueset();

    useEffect(() => {
        if (valuesetName) {
            fetch(valuesetName);
        }
    }, [valuesetName]);

    const handleClose = () => {
        modal.current?.toggleModal(undefined, false);
    };
    return (
        <Modal
            isLarge
            ref={modal}
            forceAction
            className="edit-valueset-modal"
            id="edit-valueset-modal"
            aria-labelledby="edit-valueset-modal"
            aria-describedby="edit-valueset-modal">
            <div className={styles.modal}>
                {valueset ? (
                    <EditValueset
                        valueset={valueset}
                        onAccept={onValuesetChanged}
                        onCancel={handleClose}
                        onClose={handleClose}
                    />
                ) : (
                    <div className={styles.loadingDisplay}>
                        <CloseableHeader
                            title={<div className={styles.addValuesetHeader}>Add value set</div>}
                            onClose={handleClose}
                        />
                        <div className={styles.spinnerContainer}>
                            <Spinner />
                        </div>
                    </div>
                )}
            </div>
        </Modal>
    );
};
