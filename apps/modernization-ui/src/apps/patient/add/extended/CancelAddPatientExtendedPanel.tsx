import { Checkbox } from '@trussworks/react-uswds';
import { Confirmation } from 'design-system/modal';
import { ChangeEvent, useState } from 'react';
import { useLocalStorage } from 'storage';

type Props = {
    onClose: () => void;
    onConfirm: () => void;
};

export const CancelAddPatientExtendedPanel = ({ onClose, onConfirm }: Props) => {
    const [visibilityCheckBox, setVisibilityCheckBox] = useState<boolean>(false);
    const { save } = useLocalStorage({ key: 'patient.create.extended.cancel', initial: false });

    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        setVisibilityCheckBox(e.target.checked);
    };

    const onSubmit = () => {
        save(visibilityCheckBox);
        onConfirm();
    };

    return (
        <Confirmation
            onCancel={() => {
                onClose();
            }}
            title="Warning"
            confirmText="Yes, cancel"
            cancelText="No, back to form"
            showCloseX={false}
            onConfirm={() => {
                onSubmit();
            }}>
            Canceling the form will result in the loss of all additional data entered. Are you sure you want to cancel?
            <Checkbox
                label="Don't show again"
                id={'visibilityCheckbox'}
                name={'visibilityCheckbox'}
                onChange={(e) => handleChange(e)}
            />
        </Confirmation>
    );
};
