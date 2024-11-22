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
    onBlur?: () => void;
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
            <div className={classNames(styles['range-wrapper'], 'from')}>
                <label htmlFor={'from'}>From</label>
                <DatePicker
                    onBlur={onBlur}
                    id={`${id}-from`}
                    name="from"
                    aria-label={`${id}-from`}
                    value={value?.between?.from}
                    onChange={(e) => handleOnChange('from')(e)}
                />
            </div>
            <div className={classNames(styles['range-wrapper'])}>
                <label htmlFor={'to'}>To</label>
                <DatePicker
                    onBlur={onBlur}
                    id={`${id}-to`}
                    name="to"
                    minDate={value?.between?.from}
                    aria-label={`${id}-to`}
                    value={value?.between?.to}
                    onChange={(e) => handleOnChange('to')(e)}
                />
            </div>
        </div>
    );
};
