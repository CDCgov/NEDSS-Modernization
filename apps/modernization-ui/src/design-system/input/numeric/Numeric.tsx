import { ChangeEvent as ReactChangeEvent, useEffect, useMemo, useReducer } from 'react';
import classNames from 'classnames';
import { onlyNumericKeys } from './onlyNumericKeys';

const asDisplay = (value?: string | number | null) => (value === undefined ? '' : `${value}`);

type NumericOnChange = (value?: number) => void;

type NumericProps = {
    id: string;
    inputMode?: 'decimal' | 'numeric';
    value?: number;
    onChange?: NumericOnChange;
    onBlur?: () => void;
} & Omit<JSX.IntrinsicElements['input'], 'defaultValue' | 'onChange' | 'value' | 'type' | 'inputMode'>;

type Action = { type: 'SET'; payload: number | undefined };

const reducer = (state: number | undefined, action: Action): number | undefined => {
    switch (action.type) {
        case 'SET':
            return action.payload;
        default:
            return state;
    }
};

const Numeric = ({
    id,
    inputMode = 'numeric',
    value,
    onChange,
    onBlur,
    className,
    placeholder,
    ...props
}: NumericProps) => {
    const [current, dispatch] = useReducer(reducer, value);

    useEffect(() => {
        onChange?.(current);
    }, [current]);

    const display = useMemo(() => asDisplay(current), [current]);

    const handleChange = (event: ReactChangeEvent<HTMLInputElement>) => {
        const next = event.target.value;

        if (next === '') {
            dispatch({ type: 'SET', payload: undefined });
        } else if (Number.isNaN(next)) {
            event.preventDefault();
        } else {
            const adjusted = Number(next);
            if (!Number.isNaN(adjusted)) {
                dispatch({ type: 'SET', payload: adjusted });
            }
        }
    };

    useEffect(() => {
        dispatch({ type: 'SET', payload: value });
    }, [value]);

    return (
        <input
            id={id}
            name={props.name ?? id}
            className={classNames('usa-input', className)}
            type="number"
            inputMode={inputMode}
            onChange={handleChange}
            onBlur={onBlur}
            placeholder={placeholder}
            value={display}
            pattern="[0-9]*"
            onKeyDown={onlyNumericKeys}
            {...props}
        />
    );
};

export { Numeric };
export type { NumericProps };
