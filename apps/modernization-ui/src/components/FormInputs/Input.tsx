import { RefObject } from 'react';
import { Textarea, TextInput, TextInputMask } from '@trussworks/react-uswds';
import classNames from 'classnames';
import { EntryWrapper, Orientation, Sizing } from 'components/Entry';

type InputProps = {
    name?: string;
    className?: string;
    htmlFor?: string;
    label?: string;
    id?: string;
    type: 'text' | 'email' | 'number' | 'password' | 'search' | 'tel' | 'url';
    required?: boolean;
    error?: string;
    orientation?: Orientation;
    sizing?: Sizing;
    onChange?: any;
    defaultValue?: string | null;
    placeholder?: string;
    inputMode?: 'none' | 'text' | 'tel' | 'url' | 'email' | 'numeric' | 'decimal' | 'search';
    flexBox?: boolean;
    multiline?: boolean;
    rows?: number;
    textInputRef?: RefObject<HTMLInputElement>;
    textAreaRef?: RefObject<HTMLTextAreaElement>;
    mask?: string;
    pattern?: string;
    ariaLabel?: string;
} & Omit<JSX.IntrinsicElements['input'], 'defaultValue'>;

export const Input = ({
    name,
    className,
    htmlFor = '',
    label,
    id = '',
    type,
    error,
    required,
    orientation,
    sizing,
    onChange,
    defaultValue,
    placeholder,
    inputMode,
    flexBox,
    multiline,
    rows,
    textInputRef,
    textAreaRef,
    mask,
    pattern,
    ariaLabel,
    ...props
}: InputProps) => {
    return (
        <EntryWrapper
            label={label ?? ''}
            htmlFor={htmlFor ?? ''}
            orientation={flexBox ? 'horizontal' : orientation}
            sizing={sizing}
            required={required}
            error={error}>
            {!multiline ? (
                mask ? (
                    <TextInputMask
                        autoComplete="off"
                        inputMode={inputMode}
                        placeholder={placeholder}
                        {...props}
                        id={id}
                        onChange={onChange}
                        value={defaultValue ?? ''}
                        name={name ?? ''}
                        inputRef={textInputRef}
                        validationStatus={error ? 'error' : undefined}
                        aria-describedby={error ? `${error}-message` : undefined}
                        className={classNames(className)}
                        type={type}
                        mask={mask}
                        pattern={pattern}
                        aria-label={ariaLabel}
                    />
                ) : (
                    <TextInput
                        autoComplete="off"
                        inputMode={inputMode}
                        placeholder={placeholder}
                        {...props}
                        id={id}
                        onChange={onChange}
                        value={defaultValue ?? ''}
                        name={name ?? ''}
                        inputRef={textInputRef}
                        validationStatus={error ? 'error' : undefined}
                        aria-describedby={error ? `${error}-message` : undefined}
                        className={classNames(className)}
                        type={type}
                        aria-label={ariaLabel}
                    />
                )
            ) : (
                <Textarea
                    autoComplete="off"
                    placeholder={placeholder}
                    id={id}
                    onChange={onChange}
                    rows={rows}
                    value={defaultValue ?? ''}
                    name={name ?? ''}
                    inputRef={textAreaRef}
                    aria-describedby={error ? `${error}-message` : undefined}
                    className={classNames(className)}
                    aria-label={ariaLabel}
                    disabled={props?.disabled}
                />
            )}
        </EntryWrapper>
    );
};
