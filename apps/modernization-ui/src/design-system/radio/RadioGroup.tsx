import { EntryWrapper, Orientation, Sizing } from 'components/Entry';
import { Selectable } from 'options';
import { Radio } from '@trussworks/react-uswds';
import React from 'react';
import classNames from 'classnames';

type Props = {
    id: string;
    label: string;
    helperText?: string;
    options: Selectable[];
    value?: Selectable | null;
    onChange?: (value: Selectable) => void;
    orientation?: Orientation;
    sizing?: Sizing;
    error?: string;
    required?: boolean;
    warning?: string;
    className?: string;
    disabled?: boolean;
} & Omit<JSX.IntrinsicElements['select'], 'defaultValue' | 'onChange' | 'value'>;

const RadioGroup = ({
    id,
    label,
    helperText,
    options,
    value,
    onChange,
    orientation,
    sizing,
    error,
    required,
    warning,
    className,
    disabled,
}: Props) => {
    return (
        <EntryWrapper
            orientation={orientation}
            sizing={sizing}
            label={label}
            helperText={helperText}
            required={required}
            error={error}
            warning={warning}
        >
            <div className={classNames(className, 'display-flex')} aria-label={label} role="radiogroup">
                {options.map((option) => (
                    <Radio
                        className="margin-right-2 bg-transparent"
                        key={option.value}
                        id={`${id}-${option.value}`}
                        label={option.label ? option.label : option.name}
                        name={option.name}
                        checked={value?.value === option.value}
                        onChange={() => onChange?.(option)}
                        value={option.value}
                        disabled={disabled}
                    />
                ))}
            </div>
        </EntryWrapper>
    );
};

export { RadioGroup };
