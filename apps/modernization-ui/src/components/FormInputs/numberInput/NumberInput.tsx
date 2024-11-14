import { TextInputMask } from '@trussworks/react-uswds';
import classNames from 'classnames';
import { Label } from '@trussworks/react-uswds';
import styles from './NumberInput.module.scss';

type NumberInputProps = {
    name?: string;
    className?: string;
    label?: string;
    id?: string;
    required?: boolean;
    error?: string;
    onChange?: any;
    defaultValue?: string | null;
    placeholder?: string;
    mask: string;
    pattern?: string;
    ariaLabel?: string;
};

export const NumberInput = ({
    name,
    className,
    label,
    id = '',
    error,
    required,
    onChange,
    defaultValue,
    placeholder,
    mask,
    pattern,
    ariaLabel,
    ...props
}: NumberInputProps) => {
    return (
        <div className={classNames(styles['mask-input-wrapper'], className)}>
            {label && (
                <Label className={classNames({ required })} htmlFor={id}>
                    {label}
                </Label>
            )}
            <TextInputMask
                autoComplete="off"
                placeholder={placeholder}
                {...props}
                id={id}
                onChange={onChange}
                value={defaultValue ?? ''}
                name={name ?? ''}
                validationStatus={error ? 'error' : undefined}
                aria-describedby={error ? `${error}-message` : undefined}
                className={classNames(styles['mask-input'])}
                type={'text'}
                mask={mask}
                pattern={pattern}
                aria-label={ariaLabel}
            />
        </div>
    );
};
