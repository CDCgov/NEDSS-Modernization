import { ErrorMessage, Label, TextInput } from '@trussworks/react-uswds';
import classNames from 'classnames';
import './Input.scss';

type InputProps = {
    name?: string;
    className?: string;
    htmlFor?: string;
    label?: string;
    id?: string;
    required?: boolean;
    type: 'text' | 'email' | 'number' | 'password' | 'search' | 'tel' | 'url';
    error?: any;
    onChange?: any;
    defaultValue?: string;
    placeholder?: string;
};

export const Input = ({
    name,
    className,
    htmlFor = '',
    label,
    id = '',
    required,
    type,
    error,
    onChange,
    defaultValue,
    placeholder,
    ...props
}: InputProps) => {
    return (
        <div className={`input ${error ? 'input--error' : ''}`}>
            {label && (
                <Label htmlFor={htmlFor}>
                    {label}
                    <small className="text-red">{required && ' *'}</small>
                </Label>
            )}
            <ErrorMessage id={`${error}-message`}>{error}</ErrorMessage>
            <TextInput
                placeholder={placeholder}
                {...props}
                id={id}
                onChange={onChange}
                value={defaultValue ? defaultValue : ''}
                name={name || ''}
                validationStatus={error ? 'error' : undefined}
                aria-describedby={`${error}-message`}
                className={classNames(className)}
                type={type}
            />
        </div>
    );
};
