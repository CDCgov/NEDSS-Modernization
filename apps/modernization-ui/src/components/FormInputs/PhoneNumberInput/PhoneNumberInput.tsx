import { useState } from 'react';
import { TextInput, Label } from '@trussworks/react-uswds';

type PhoneNumberInputProps = {
    label?: string;
    defaultValue?: string;
    onChange?: any;
};

export const PhoneNumberInput = ({ label, defaultValue, onChange, ...props }: PhoneNumberInputProps) => {
    const [valid, setValid] = useState(true);
    const validatePhone = (e: any) => {
        const validPhone = /^[0-9]{1,10}$/;
        const validPhoneFormatted = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
        onChange(e.target.value);
        if (e.target.value.match(validPhone) || e.target.value.match(validPhoneFormatted)) {
            setValid(true);
        } else {
            setValid(false);
        }
    };
    return (
        <>
            <Label htmlFor="phoneNumber">{label}</Label>
            <TextInput
                {...props}
                id="phoneNumber"
                onChange={(e) => validatePhone(e)}
                value={defaultValue ? defaultValue : ''}
                name="phoneNumber"
                validationStatus={!valid ? 'error' : undefined}
                type="tel"
            />
            <small className="text-red">{!valid && 'Not a valid number'}</small>
        </>
    );
};
