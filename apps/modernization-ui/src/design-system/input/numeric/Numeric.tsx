import {
    ChangeEvent as ReactChangeEvent,
    KeyboardEvent as ReactKeyboardEvent,
    useEffect,
    useMemo,
    useState
} from 'react';
import classNames from 'classnames';

const asDisplay = (value?: string | number | null) => (value === undefined ? '' : `${value}`);

const isNonNumericKey = (event: ReactKeyboardEvent<HTMLInputElement>) => {
    const value = event.key as string;
    return value.length == 1 ? !/[0-9]/.test(value) : false;
};

const handleKeydown = (event: ReactKeyboardEvent<HTMLInputElement>) => {
    if (!event.altKey && !event.ctrlKey && !event.metaKey && isNonNumericKey(event)) {
        event.preventDefault();
    }
};

type NumericOnChange = (value?: number) => void;

type NumericProps = {
    id: string;
    inputMode?: 'decimal' | 'numeric';
    value?: number;
    onChange?: NumericOnChange;
} & Omit<JSX.IntrinsicElements['input'], 'defaultValue' | 'onChange' | 'value' | 'type' | 'inputMode'>;

const Numeric = ({ id, inputMode = 'numeric', value, onChange, className, placeholder, ...props }: NumericProps) => {
    const [current, setCurrent] = useState<number | undefined>(value);

    useEffect(() => {
        onChange?.(current);
    }, [current]);

    const display = useMemo(() => asDisplay(current), [current]);

    const handleChange = (event: ReactChangeEvent<HTMLInputElement>) => {
        const next = event.target.value;

        if (next === '') {
            setCurrent(undefined);
        } else if (Number.isNaN(next)) {
            event.preventDefault();
        } else {
            const adjusted = Number(next);
            if (!Number.isNaN(adjusted)) {
                setCurrent(adjusted);
            }
        }
    };

    return (
        <input
            id={id}
            name={props.name ?? id}
            className={classNames('usa-input', className)}
            type="number"
            inputMode={inputMode}
            onChange={handleChange}
            placeholder={placeholder}
            value={display}
            pattern="[0-9]*"
            onKeyDown={handleKeydown}
            {...props}
        />
    );
};

export { Numeric };
export type { NumericProps };
