import { ModalRef } from '@trussworks/react-uswds';
import { RefObject } from 'react';
import { ConfirmationModal } from 'confirmation';

type SaveReportModalProps = {
    saveReportModalRef: RefObject<ModalRef>;
    saving: boolean;
    onSave: () => void;
};


export const SaveReportModal = ({ saveReportModalRef, saving, onSave }: SaveReportModalProps) => {
    return (
        <ConfirmationModal
            modal={saveReportModalRef}
            title="Overwrite saved report?"
            message={
                <>
                    This will replace the saved criteria with your current criteria. This action cannot be undone.
                </>
            }
            confirmText="Save"
            cancelText="Cancel"
            onConfirm={onSave}
            disabled={saving}
        />
    );
};
