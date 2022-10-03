import { ErrorMessage, Label, TextInput } from '@trussworks/react-uswds';
import classNames from 'classnames';

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
    ...props
}: InputProps) => {
    return (
        <>
            <Label htmlFor={htmlFor}>
                {label}
                <small className="text-red">{required && ' *'}</small>
            </Label>
            <TextInput
                {...props}
                id={id}
                onChange={onChange}
                value={defaultValue ? defaultValue : ''}
                name={name || ''}
                validationStatus={error ? 'error' : undefined}
                aria-describedby={`${error}-message`}
                className={classNames('bg-base-lightest', className)}
                type={type}
            />
            <ErrorMessage id={`${error}-message`}>{error}</ErrorMessage>
        </>
    );
};
