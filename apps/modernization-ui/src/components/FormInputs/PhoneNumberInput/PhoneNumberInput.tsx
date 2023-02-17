import { useState } from 'react';
import { TextInput, Label } from '@trussworks/react-uswds';
import { validatePhoneNumber } from '../../../utils/PhoneValidation';

type PhoneNumberInputProps = {
    label?: string;
    defaultValue?: string;
    onChange?: any;
};

export const PhoneNumberInput = ({ label, defaultValue, onChange, ...props }: PhoneNumberInputProps) => {
    const [valid, setValid] = useState(true);
    const validatePhone = (number: string) => {
        onChange(number);
        setValid(validatePhoneNumber(number));
    };
    return (
        <>
            <Label htmlFor="phoneNumber">{label}</Label>
            <TextInput
                {...props}
                id="phoneNumber"
                onChange={(e) => validatePhone(e.target.value)}
                value={defaultValue ? defaultValue : ''}
                name="phoneNumber"
                validationStatus={!valid ? 'error' : undefined}
                type="tel"
            />
            <small className="text-red">{!valid && 'Not a valid number'}</small>
        </>
    );
};
