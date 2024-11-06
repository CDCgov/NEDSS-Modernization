import { useCallback, useLayoutEffect, useMemo, useRef } from 'react';
import datePicker from '@uswds/uswds/js/usa-date-picker';

import { asDate } from 'date';
import { mapOr } from 'utils/mapping';

type DateValue = string | undefined;

const asFormattedDate = (value: DateValue): string | undefined => {
    if (!value) {
        return undefined;
    } else if (typeof value === 'string') {
        const date = asDate(value);
        return date.toISOString().substring(0, 10);
    }
};

const today = () => new Date().toISOString().substring(0, 10);

const asDateOrToday = mapOr(asFormattedDate, today);

type DatePickerProps = {
    id: string;
    minDate?: DateValue;
    maxDate?: DateValue;
    value?: DateValue;
    onChange?: (value?: string) => void;
} & Omit<JSX.IntrinsicElements['input'], 'defaultValue' | 'onChange' | 'value' | 'type' | 'inputMode'>;

const DatePicker = ({ id, maxDate, minDate, value, onChange, ...remaining }: DatePickerProps) => {
    const datePickerRef = useRef<HTMLDivElement>(null);

    // const key = useMemo(() => value ?? id, [value, id]);
    const adjustedValue = useMemo(() => asFormattedDate(value), [value]);
    const adjustedMaxValue = useMemo(() => asDateOrToday(maxDate), [maxDate]);
    const adjustedMinValue = useMemo(() => asFormattedDate(minDate), [minDate]);

    const handleOnChange = useCallback(
        (event: Event) => {
            if (event.target && 'value' in event.target && typeof event.target.value === 'string') {
                onChange?.(event.target.value);
            } else {
                onChange?.();
            }
        },
        [useCallback]
    );

    useLayoutEffect(() => {
        const datePickerElement = datePickerRef.current as HTMLDivElement;
        const wrapper = datePickerElement.querySelector('.usa-date-picker__wrapper');

        datePicker.on(datePickerElement);

        const external = datePicker.getDatePickerContext(datePickerElement).externalInputEl;

        external.addEventListener('change', handleOnChange);

        return () => {
            external.removeEventListener('change', handleOnChange);

            if (wrapper) {
                datePicker.off(datePickerElement);
            }
        };
    }, [adjustedValue]);

    return (
        <div
            key={adjustedValue}
            ref={datePickerRef}
            className="usa-date-picker"
            data-default-value={adjustedValue}
            data-max-date={adjustedMaxValue}
            data-min-date={adjustedMinValue}>
            <input id={id} className="usa-input" type="text" defaultValue={value} {...remaining} />
        </div>
    );
};

export { DatePicker };
export type { DatePickerProps };
