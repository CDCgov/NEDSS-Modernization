import { useEffect, useState } from 'react';
import { TextInput, Label } from '@trussworks/react-uswds';
import { validatePhoneNumber } from '../../../utils/PhoneValidation';
import './PhoneNumberInput.scss';

type PhoneNumberInputProps = {
    label?: string;
    defaultValue?: string;
    onChange?: any;
};

export const PhoneNumberInput = ({ label, defaultValue, onChange, ...props }: PhoneNumberInputProps) => {
    const [valid, setValid] = useState(true);

    useEffect(() => {
        if (defaultValue) {
            validatePhone(defaultValue);
        }
    }, []);
    const validatePhone = (number: string) => {
        if (onChange) {
            onChange(number);
        }
        setValid(validatePhoneNumber(number));
    };
    return (
        <div className={`phone-number-input ${valid ? '' : 'error'}`}>
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
        </div>
    );
};
