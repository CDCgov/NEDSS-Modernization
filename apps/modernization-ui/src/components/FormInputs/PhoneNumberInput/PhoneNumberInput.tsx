import { TextInput, Label, ErrorMessage, TextInputMask } from '@trussworks/react-uswds';
import './PhoneNumberInput.scss';

type PhoneNumberInputProps = {
    label?: string;
    defaultValue?: string;
    onChange?: any;
    error?: any;
    placeholder?: string;
    id?: string;
    mask?: string;
    pattern?: string;
};
export const PhoneNumberInput = ({
    label,
    defaultValue,
    onChange,
    error,
    placeholder,
    id,
    mask,
    pattern,
    ...props
}: PhoneNumberInputProps) => {
    return (
        <div className={`phone-number-input ${error ? 'input--error' : ''}`}>
            <Label htmlFor={id || 'phoneNumber'}>{label}</Label>
            <ErrorMessage id={`${error}-message`}>{error}</ErrorMessage>
            {mask ? (
                <TextInputMask
                    {...props}
                    id={id || 'phoneNumber'}
                    onChange={onChange}
                    defaultValue={defaultValue ? defaultValue : ''}
                    name={id || 'phoneNumber'}
                    validationStatus={error ? 'error' : undefined}
                    type="tel"
                    placeholder={placeholder || ''}
                    mask={mask}
                    pattern={pattern}
                    className={'masked-input'}
                />
            ) : (
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
            )}
        </div>
    );
};
