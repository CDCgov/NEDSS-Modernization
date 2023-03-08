import { TextInput, Label, ErrorMessage } from '@trussworks/react-uswds';
import './PhoneNumberInput.scss';

type PhoneNumberInputProps = {
    label?: string;
    defaultValue?: string;
    onChange?: any;
    error?: any;
};
export const PhoneNumberInput = ({ label, defaultValue, onChange, error, ...props }: PhoneNumberInputProps) => {
    return (
        <div className={`phone-number-input ${error ? 'input--error' : ''}`}>
            <Label htmlFor="phoneNumber">{label}</Label>
            <ErrorMessage id={`${error}-message`}>{error}</ErrorMessage>
            <TextInput
                {...props}
                id="phoneNumber"
                onChange={onChange}
                value={defaultValue ? defaultValue : ''}
                name="phoneNumber"
                validationStatus={error ? 'error' : undefined}
                type="tel"
            />
        </div>
    );
};
