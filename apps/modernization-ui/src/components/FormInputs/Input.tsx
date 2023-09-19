import { TextInput, Textarea } from '@trussworks/react-uswds';
import classNames from 'classnames';
import './Input.scss';
import { EntryWrapper } from 'components/Entry';

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
    defaultValue?: string | null;
    placeholder?: string;
    inputMode?: 'none' | 'text' | 'tel' | 'url' | 'email' | 'numeric' | 'decimal' | 'search';
    flexBox?: boolean;
    multiline?: boolean;
} & Omit<JSX.IntrinsicElements['input'], 'defaultValue'>;

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
    inputMode,
    flexBox,
    multiline,
    ...props
}: InputProps) => {
    const orientation = flexBox ? 'horizontal' : 'vertical';

    return (
        <div className={classNames('input', { 'input--error': error })}>
            <EntryWrapper
                orientation={orientation}
                label={label ?? ''}
                htmlFor={htmlFor ?? ''}
                required={required}
                error={error}>
                {!multiline ? (
                    <TextInput
                        inputMode={inputMode}
                        placeholder={placeholder}
                        {...props}
                        id={id}
                        onChange={onChange}
                        value={defaultValue ?? ''}
                        name={name ?? ''}
                        validationStatus={error ? 'error' : undefined}
                        aria-describedby={`${error}-message`}
                        className={classNames(className)}
                        type={type}
                    />
                ) : (
                    <Textarea
                        placeholder={placeholder}
                        id={id}
                        onChange={onChange}
                        value={defaultValue ?? ''}
                        name={name ?? ''}
                        aria-describedby={`${error}-message`}
                        className={classNames(className)}
                    />
                )}
            </EntryWrapper>
        </div>
    );
};
