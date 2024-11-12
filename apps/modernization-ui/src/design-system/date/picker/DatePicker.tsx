import { useLayoutEffect, useRef } from 'react';
import { mapOr } from 'utils/mapping';
import { asDateEntry, asISODate } from 'design-system/date/entry';
import { maskedAsDate } from './maskedAsDate';

import datePicker from '@uswds/uswds/js/usa-date-picker';

const handleKeyUp = (event: Event) => {
    const input = event.target as HTMLInputElement;

    input.value = maskedAsDate(input.value);
};

type TextualDate = string | undefined;

const asFormattedDate = (value: TextualDate): string | undefined => {
    if (!value) {
        return undefined;
    } else if (typeof value === 'string') {
        const date = asDateEntry(value);
        return asISODate(date);
    }
};

const today = () => new Date().toISOString().substring(0, 10);

const asDateOrToday = mapOr(asFormattedDate, today);

type DatePickerProps = {
    id: string;
    minDate?: TextualDate;
    maxDate?: TextualDate;
    value?: TextualDate;
    onChange?: (value?: string) => void;
} & Omit<JSX.IntrinsicElements['input'], 'defaultValue' | 'onChange' | 'value' | 'type' | 'inputMode'>;

const DatePicker = ({ id, maxDate, minDate, value, onChange, ...remaining }: DatePickerProps) => {
    const datePickerRef = useRef<HTMLDivElement>(null);

    const adjustedValue = asFormattedDate(value);
    const adjustedMaxValue = asDateOrToday(maxDate);
    const adjustedMinValue = asFormattedDate(minDate);

    const handleOnChange = (event: Event) => {
        if (event.target && 'value' in event.target && typeof event.target.value === 'string') {
            onChange?.(event.target.value);
        } else {
            onChange?.();
        }
    };

    useLayoutEffect(() => {
        const datePickerElement = datePickerRef.current as HTMLDivElement;
        const wrapper = datePickerElement.querySelector('.usa-date-picker__wrapper');

        datePicker.on(datePickerElement);

        const external = datePicker.getDatePickerContext(datePickerElement).externalInputEl as HTMLInputElement;

        external.addEventListener('change', handleOnChange);
        external.addEventListener('keyup', handleKeyUp);

        return () => {
            external.removeEventListener('change', handleOnChange);
            external.removeEventListener('keyup', handleKeyUp);

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
