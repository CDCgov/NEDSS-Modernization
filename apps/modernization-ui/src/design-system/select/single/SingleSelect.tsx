import { EntryWrapper, Orientation, Sizing } from 'components/Entry';
import { Selectable } from 'options';
import Select from './Select';

type Props = {
    id: string;
    label: string;
    helperText?: string;
    placeholder?: string;
    options: Selectable[];
    value?: Selectable | null;
    onChange?: (value?: Selectable) => void;
    orientation?: Orientation;
    sizing?: Sizing;
    error?: string;
    required?: boolean;
    warning?: string;
} & Omit<JSX.IntrinsicElements['select'], 'defaultValue' | 'onChange' | 'value'>;

const SingleSelect = ({
    id,
    label,
    className,
    helperText,
    options,
    value,
    onChange,
    orientation,
    sizing,
    error,
    required,
    warning,
    placeholder = '- Select -',
    ...inputProps
}: Props) => {
    return (
        <EntryWrapper
            orientation={orientation}
            sizing={sizing}
            label={label}
            helperText={helperText}
            htmlFor={id}
            required={required}
            error={error}
            warning={warning}>
            <Select
                className={className}
                id={id}
                options={options}
                value={value}
                onChange={onChange}
                placeholder={placeholder}
                required={required}
                {...inputProps}
            />
        </EntryWrapper>
    );
};

export { SingleSelect };
