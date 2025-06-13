import { useCallback, useEffect, useLayoutEffect, useRef } from 'react';
import classNames from 'classnames';
import { mapOr } from 'utils/mapping';
import { Sizing } from 'design-system/field';
import { asStrictISODate } from 'design-system/date/asStrictISODate';
import { onlyNumericKeys } from 'design-system/input/numeric';
import { maskedAsDate } from './maskedAsDate';
import { useDate } from './useDate';

import datePicker from '@uswds/uswds/js/usa-date-picker';
import styles from './date-picker.module.scss';

const handleExternalKeyUp = (event: Event) => {
    const input = event.target as HTMLInputElement;

    input.value = maskedAsDate(input.value);
};

const asDateOrMinimum = mapOr(asStrictISODate, '1875-01-01');

const today = () => new Date().toISOString().substring(0, 10);

const asDateOrToday = mapOr(asStrictISODate, today);

type DatePickerProps = {
    id: string;
    label?: string;
    minDate?: string;
    maxDate?: string;
    value?: string | null;
    sizing?: Sizing;
    onChange?: (value?: string) => void;
    onBlur?: () => void;
} & Omit<JSX.IntrinsicElements['input'], 'defaultValue' | 'onChange' | 'value' | 'type' | 'inputMode'>;

const DatePicker = ({
    id,
    label,
    maxDate,
    minDate,
    value,
    sizing,
    onChange,
    onBlur,
    ...remaining
}: DatePickerProps) => {
    const { current, date, clear, change, initialize } = useDate({ value });

    const datePickerRef = useRef<HTMLDivElement>(null);
    const externalInputRef = useRef<HTMLInputElement | null>(null);

    const adjustedMaxValue = asDateOrToday(maxDate);
    const adjustedMinValue = asDateOrMinimum(minDate);

    useEffect(() => {
        initialize(value);
    }, [value]);

    useEffect(() => {
        if (externalInputRef.current) {
            //  keep the external input in sync with the incoming value
            externalInputRef.current.value = current;
        }
    }, [current, externalInputRef.current]);

    const handleExternalOnBlur = useCallback(() => onBlur?.(), [onBlur]);

    const handleExternalOnChange = useCallback(
        (event: Event) => {
            if (
                event.target &&
                'value' in event.target &&
                typeof event.target.value === 'string' &&
                event.target.value
            ) {
                change(event.target.value);
                onChange?.(event.target.value);
            } else {
                clear();
                onChange?.('');
            }
        },
        [onChange]
    );

    useLayoutEffect(() => {
        if (!externalInputRef.current) {
            const datePickerElement = datePickerRef.current as HTMLDivElement;
            const wrapper = datePickerElement.querySelector('.usa-date-picker__wrapper');

            datePicker.on(datePickerElement);

            const context = datePicker.getDatePickerContext(datePickerElement);

            if (label) {
                const toggle = context.toggleBtnEl as HTMLButtonElement;

                toggle.ariaLabel = `Toggle ${label} calendar`;
            }

            externalInputRef.current = context.externalInputEl as HTMLInputElement;

            return () => {
                if (wrapper) {
                    datePicker.off(datePickerElement);
                    externalInputRef.current = null;
                }
            };
        }
    }, [externalInputRef.current]);

    useEffect(() => {
        if (externalInputRef.current) {
            const external = externalInputRef.current;

            external.addEventListener('blur', handleExternalOnBlur);
            external.addEventListener('change', handleExternalOnChange);
            external.addEventListener('keyup', handleExternalKeyUp);
            external.addEventListener('keydown', onlyNumericKeys);

            return () => {
                external.removeEventListener('blur', handleExternalOnBlur);
                external.removeEventListener('change', handleExternalOnChange);
                external.removeEventListener('keyup', handleExternalKeyUp);
                external.removeEventListener('keydown', onlyNumericKeys);
            };
        }
    }, [externalInputRef.current, handleExternalOnBlur, handleExternalOnChange]);

    return (
        <div
            ref={datePickerRef}
            className={classNames('usa-date-picker', {
                [styles.sized]: sizing,
                [styles.small]: sizing === 'small',
                [styles.medium]: sizing === 'medium',
                [styles.large]: sizing === 'large'
            })}
            data-default-value={date}
            data-max-date={adjustedMaxValue}
            data-min-date={adjustedMinValue}>
            <input autoComplete="off" id={id} className="usa-input" type="text" defaultValue={current} {...remaining} />
        </div>
    );
};

export { DatePicker };
export type { DatePickerProps };
