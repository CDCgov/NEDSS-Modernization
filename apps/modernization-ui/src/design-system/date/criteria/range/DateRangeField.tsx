import { useCallback, useEffect, useLayoutEffect, useRef, useState } from 'react';
import classNames from 'classnames';
import { withoutProperty, withProperty } from 'utils/object';
import { DatePicker } from 'design-system/date/picker';
import { Sizing } from 'design-system/field';
import { DateBetweenCriteria, DateRange } from '../dateCriteria';

import styles from './date-range-field.module.scss';

import dateRangePicker from '@uswds/uswds/js/usa-date-range-picker';

type Field = keyof DateRange;

const next = (field: Field, value: string | undefined) =>
    value ? withProperty<DateRange, string>(field, value) : withoutProperty<DateRange>(field);

type DateRangeFieldProps = {
    id: string;
    value?: DateBetweenCriteria;
    sizing?: Sizing;
    onChange: (value?: DateBetweenCriteria) => void;
    onBlur?: () => void;
    label?: string;
};

const DateRangeField = ({ id, value, sizing, onChange, onBlur, label }: DateRangeFieldProps) => {
    const [range, setRange] = useState<DateRange | undefined>(value?.between);

    useEffect(() => {
        setRange(value?.between);
    }, [value, setRange]);

    const handleFieldOnChange = useCallback(
        (field: Field) => (changed: string | undefined) => {
            const between = next(field, changed)(value?.between);
            if (between) {
                onChange({ between });
            } else {
                onChange({ between: { from: undefined, to: undefined } });
            }
        },
        [onChange, value?.between]
    );

    const dateRangePickerRef = useRef<HTMLDivElement>(null);

    useLayoutEffect(() => {
        const dateRangePickerElement = dateRangePickerRef.current as HTMLDivElement;

        dateRangePicker.on(dateRangePickerElement);

        return () => {
            dateRangePicker.off(dateRangePickerElement);
        };
    });

    return (
        <div
            id={id}
            ref={dateRangePickerRef}
            className={classNames('usa-date-range-picker', styles['date-range-entry'])}>
            <div className={classNames(styles['range-wrapper'], 'from')}>
                <label htmlFor={`${id}-from`}>From</label>
                <DatePicker
                    sizing={sizing}
                    onBlur={onBlur}
                    id={`${id}-from`}
                    value={range?.from}
                    onChange={handleFieldOnChange('from')}
                    aria-label={`${label}, From`}
                />
            </div>
            <div className={classNames(styles['range-wrapper'])}>
                <label htmlFor={`${id}-to`}>To</label>
                <DatePicker
                    sizing={sizing}
                    onBlur={onBlur}
                    id={`${id}-to`}
                    minDate={range?.from}
                    value={range?.to}
                    onChange={handleFieldOnChange('to')}
                    aria-label={`${label}, To`}
                />
            </div>
        </div>
    );
};

export { DateRangeField };
