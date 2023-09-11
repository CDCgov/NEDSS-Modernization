import { ErrorMessage, Grid, Label, TextInput, Textarea } from '@trussworks/react-uswds';
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
    inputMode?: 'none' | 'text' | 'tel' | 'url' | 'email' | 'numeric' | 'decimal' | 'search' | undefined;
    flexBox?: boolean;
    multiline?: boolean;
} & JSX.IntrinsicElements['input'];

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
    return flexBox ? (
        <Grid row className={`input ${error ? 'input--error' : ''}`}>
            <Grid col={6}>
                {label && (
                    <Label className={classNames({ required })} htmlFor={htmlFor}>
                        {label}
                    </Label>
                )}
            </Grid>
            <Grid col={6}>
                <ErrorMessage id={`${error}-message`}>{error}</ErrorMessage>
                <TextInput
                    inputMode={inputMode}
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
            </Grid>
        </Grid>
    ) : (
        <div className={`input ${error ? 'input--error' : ''}`}>
            {label && (
                <Label className={classNames({ required })} htmlFor={htmlFor}>
                    {label}
                </Label>
            )}
            {error && <ErrorMessage id={`${error}-message`}>{error}</ErrorMessage>}
            {!multiline ? (
                <TextInput
                    inputMode={inputMode}
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
            ) : (
                <Textarea
                    placeholder={placeholder}
                    id={id}
                    onChange={onChange}
                    value={defaultValue ? defaultValue : ''}
                    name={name || ''}
                    aria-describedby={`${error}-message`}
                    className={classNames(className)}
                />
            )}
        </div>
    );
};
