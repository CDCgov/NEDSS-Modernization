import { Checkbox } from '@trussworks/react-uswds';
import { Confirmation } from 'design-system/modal';
import { ChangeEvent, useCallback, useState } from 'react';
import { useShowCancelModal } from './useShowCancelModal';

type CancelAddPatientPanelProps = {
    onClose?: () => void;
    onConfirm?: () => void;
};

export const CancelAddPatientPanel = ({ onClose, onConfirm }: CancelAddPatientPanelProps) => {
    const [visibilityCheckBox, setVisibilityCheckBox] = useState<boolean>(false);
    const { save } = useShowCancelModal();

    const handleCheckboxChange = (e: ChangeEvent<HTMLInputElement>) => {
        setVisibilityCheckBox(e.target.checked);
    };

    const handleConfirm = useCallback(() => {
        save(visibilityCheckBox);
        onConfirm?.();
    }, [visibilityCheckBox, onConfirm, save]);

    return (
        <Confirmation
            title="Warning"
            confirmText="Yes, cancel"
            cancelText="No, back to form"
            forceAction={true}
            onConfirm={handleConfirm}
            onCancel={() => {
                onClose && onClose();
            }}>
            Canceling the form will result in the loss of all additional data entered. Are you sure you want to cancel?
            <Checkbox
                label="Don't show again"
                id={'visibilityCheckbox'}
                name={'visibilityCheckbox'}
                onChange={handleCheckboxChange}
            />
        </Confirmation>
    );
};
