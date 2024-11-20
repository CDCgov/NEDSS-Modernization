import { DateBetweenCriteria } from 'design-system/date/entry';
import { DateRangeEntryFields, useDateBetweenCriteria } from '../useDateBetweenCriteria';
import { useEffect } from 'react';
import styles from './date-range-entry.module.scss';
import classNames from 'classnames';
import { DatePicker } from 'design-system/date/picker';

type DateRangeEntryProps = {
    id: string;
    value: DateBetweenCriteria;
    onChange: (value: DateBetweenCriteria) => void;
    onBlur?: (value: DateBetweenCriteria) => void;
};

export const DateRangeEntry = ({ id, value, onChange, onBlur }: DateRangeEntryProps) => {
    const { state: rangeEntry, apply, clear } = useDateBetweenCriteria(value);

    const handleOnChange = (field: DateRangeEntryFields) => (value: string | undefined) => {
        if (value) {
            apply(field, value);
        } else {
            clear(field);
        }
    };

    useEffect(() => {
        onChange(rangeEntry as DateBetweenCriteria);
    }, [rangeEntry, onChange]);

    return (
        <div id={id} aria-label="patient-search-date-range" className={styles['date-range-entry']}>
            <div className={classNames(styles['range-wrapper'])}>
                <label htmlFor={'from'}>From</label>
                <DatePicker
                    id={`${id}-from`}
                    name="from"
                    aria-label="from"
                    value={value?.between?.from}
                    onChange={(e) => handleOnChange('from')(e)}
                    onBlur={() => onBlur?.(rangeEntry as DateBetweenCriteria)}
                />
            </div>
            <div className={classNames(styles['range-wrapper'])}>
                <label htmlFor={'to'}>To</label>
                <DatePicker
                    id={`${id}-to`}
                    name="to"
                    minDate={value?.between?.from}
                    aria-label="to"
                    value={value?.between?.to}
                    onChange={(e) => handleOnChange('to')(e)}
                    onBlur={() => onBlur?.(rangeEntry as DateBetweenCriteria)}
                />
            </div>
        </div>
    );
};
