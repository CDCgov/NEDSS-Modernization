import { Textarea, TextInput, TextInputMask } from '@trussworks/react-uswds';
import classNames from 'classnames';
import './Input.scss';
import { EntryWrapper } from 'components/Entry';
import { RefObject } from 'react';

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
    required,
    type,
    error,
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
                            className={`${classNames(className)} masked-input`}
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
        </div>
    );
};
