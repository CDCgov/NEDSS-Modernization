import { useRef } from 'react';
import classNames from 'classnames';
import { DatePicker } from 'design-system/date/picker';
import { Sizing } from 'design-system/field';
import { DateBetweenCriteria } from '../dateCriteria';

import styles from './date-range-field.module.scss';

export type DateRangeFieldProps = {
    id: string;
    value?: DateBetweenCriteria;
    sizing?: Sizing;
    onChange: (value?: DateBetweenCriteria) => void;
    onBlur?: () => void;
    label?: string;
    required?: boolean;
};

const DateRangeField = ({ id, value, sizing, onChange, onBlur, label, required }: DateRangeFieldProps) => {
    const handleFieldOnChange = (v, type) => {
        if (type === 'to') {
            onChange({ between: { to: v, from: value?.between.from || undefined } });
        }

        if (type === 'from') {
            onChange({ between: { to: value?.between.to || undefined, from: v } });
        }
    };

    const dateRangePickerRef = useRef<HTMLDivElement>(null);

    return (
        <div
            id={id}
            data-testid="date-range-editor"
            role="group"
            ref={dateRangePickerRef}
            className={classNames('usa-date-range-picker', styles['date-range-entry'])}
            aria-label={label}
        >
            <div className={classNames(styles['range-wrapper'], 'from')}>
                <label htmlFor={`${id}-from`}>From</label>
                <DatePicker
                    sizing={sizing}
                    onBlur={onBlur}
                    id={`${id}-from`}
                    value={value?.between?.from}
                    onChange={(v) => handleFieldOnChange(v, 'from')}
                    required={required}
                />
            </div>
            <div className={classNames(styles['range-wrapper'])}>
                <label htmlFor={`${id}-to`}>To</label>
                <DatePicker
                    sizing={sizing}
                    onBlur={onBlur}
                    id={`${id}-to`}
                    minDate={value?.between?.from}
                    value={value?.between?.to}
                    onChange={(v) => handleFieldOnChange(v, 'to')}
                    required={required}
                />
            </div>
        </div>
    );
};

export { DateRangeField };
