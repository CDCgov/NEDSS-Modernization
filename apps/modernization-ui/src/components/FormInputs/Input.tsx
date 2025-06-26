import { FocusEventHandler } from 'react';
import { Textarea, TextInput, TextInputMask } from '@trussworks/react-uswds';

import { EntryWrapper, Orientation, Sizing } from 'components/Entry';

type InputProps = {
    name?: string;
    className?: string;
    htmlFor?: string;
    label?: string;
    helperText?: string;
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
    mask?: string;
    pattern?: string;
    ariaLabel?: string;
} & Omit<JSX.IntrinsicElements['input'], 'defaultValue'>;

export const Input = ({
    name,
    className,
    label,
    helperText,
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
    mask,
    pattern,
    ariaLabel,
    ...props
}: InputProps) => {
    return (
        <EntryWrapper
            label={label ?? ''}
            helperText={helperText}
            htmlFor={id ?? ''}
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
                        aria-describedby={error ? `${error}-message` : undefined}
                        className={className}
                        type={type}
                        mask={mask}
                        pattern={pattern}
                        aria-label={ariaLabel}
                        aria-required={required}
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
                        aria-describedby={error ? `${error}-message` : undefined}
                        className={className}
                        type={type}
                        aria-label={ariaLabel}
                        aria-required={required}
                    />
                )
            ) : (
                <Textarea
                    autoComplete="off"
                    placeholder={placeholder}
                    id={id}
                    onChange={onChange}
                    onBlur={props.onBlur as FocusEventHandler<HTMLTextAreaElement> | undefined}
                    rows={rows}
                    value={defaultValue ?? ''}
                    name={name ?? ''}
                    error={!!error}
                    aria-describedby={error ? `${error}-message` : undefined}
                    className={className}
                    aria-label={ariaLabel}
                    disabled={props?.disabled}
                    aria-required={required}
                />
            )}
        </EntryWrapper>
    );
};
