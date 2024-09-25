import { Button } from 'components/button';
import { DataEntrySideNav } from '../DataEntrySideNav/DataEntrySideNav';
import { AddPatientExtendedForm } from './AddPatientExtendedForm';
import styles from './add-patient-extended.module.scss';
import { ConfirmationModal } from 'confirmation';
import { useRef } from 'react';
import { ModalRef } from '@trussworks/react-uswds';

export const AddPatientExtended = () => {
    const cancelPatientModal = useRef<ModalRef>(null);

    return (
        <div className={styles.addPatientExtended}>
            <DataEntrySideNav />
            <div className={styles.contet}>
                <header>
                    <h1>New patient - extended</h1>
                    <div className={styles.buttonGroup}>
                        <Button onClick={() => cancelPatientModal.current?.toggleModal()} outline>
                            Cancel
                        </Button>
                        <Button>Save</Button>
                    </div>
                </header>
                <main>
                    <AddPatientExtendedForm />
                </main>
            </div>
            <ConfirmationModal
                modal={cancelPatientModal}
                title="Warning"
                message="Canceling the form will result in the loss of all additional data entered.  Are you sure you want to cancel?"
                confirmText="Yes, cancel"
                cancelText="No, back to form"
                onConfirm={() => {
                    cancelPatientModal.current?.toggleModal();
                }}
                onCancel={() => {
                    cancelPatientModal.current?.toggleModal();
                }}
            />
        </div>
    );
};
