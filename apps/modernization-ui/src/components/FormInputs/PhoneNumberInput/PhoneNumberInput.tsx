import { TextInput, Label, ErrorMessage } from '@trussworks/react-uswds';
import './PhoneNumberInput.scss';

type PhoneNumberInputProps = {
    label?: string;
    defaultValue?: string;
    onChange?: any;
    error?: any;
    placeholder?: string;
    id?: string;
};
export const PhoneNumberInput = ({
    label,
    defaultValue,
    onChange,
    error,
    placeholder,
    id,
    ...props
}: PhoneNumberInputProps) => {
    return (
        <div className={`phone-number-input ${error ? 'input--error' : ''}`}>
            <Label htmlFor={id || 'phoneNumber'}>{label}</Label>
            <ErrorMessage id={`${error}-message`}>{error}</ErrorMessage>
            <TextInput
                {...props}
                id={id || 'phoneNumber'}
                onChange={onChange}
                defaultValue={defaultValue ? defaultValue : ''}
                name={id || 'phoneNumber'}
                validationStatus={error ? 'error' : undefined}
                type="tel"
                placeholder={placeholder || ''}
            />
        </div>
    );
};
