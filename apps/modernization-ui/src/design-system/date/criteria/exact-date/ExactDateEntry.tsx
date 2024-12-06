import { DateEqualsCriteria } from 'design-system/date/entry';
import { Numeric } from 'design-system/input/numeric/Numeric';
import { useEffect } from 'react';
import { ExactDateEntryFields, useDateEqualsCriteria } from '../useDateEqualsCriteria';
import classNames from 'classnames';
import styles from './exact-date-entry.module.scss';

type ExactDateEntryProps = {
    id: string;
    value: DateEqualsCriteria;
    onChange: (value: DateEqualsCriteria) => void;
    onBlur?: () => void;
};

export const ExactDateEntry = ({ id, value, onChange, onBlur }: ExactDateEntryProps) => {
    const { state: dateEntry, apply, clear } = useDateEqualsCriteria(value);

    const handleOnChange = (field: ExactDateEntryFields) => (value: number | undefined) => {
        if (value !== undefined) {
            apply(field, value);
        } else {
            clear(field);
        }
    };

    useEffect(() => {
        onChange(dateEntry as DateEqualsCriteria);
    }, [dateEntry, onChange]);

    return (
        <div id={id} aria-label="patient-search-exact-date" className={styles['exact-date-entry']}>
            <div className={classNames(styles['numeric-wrapper'], styles['month'])}>
                <label htmlFor={'month'}>Month</label>
                <Numeric
                    id={`${id}-month`}
                    name="month"
                    aria-label="month"
                    value={value?.equals?.month}
                    onChange={(e) => handleOnChange('month')(e)}
                    onBlur={onBlur}
                />
            </div>
            <div className={classNames(styles['numeric-wrapper'], styles['day'])}>
                <label htmlFor={'day'}>Day</label>
                <Numeric
                    id={`${id}-day`}
                    name="day"
                    aria-label="day"
                    value={value?.equals?.day}
                    onChange={(e) => handleOnChange('day')(e)}
                    onBlur={onBlur}
                />
            </div>
            <div className={classNames(styles['numeric-wrapper'], styles['year'])}>
                <label htmlFor={'year'}>Year</label>
                <Numeric
                    id={`${id}-year`}
                    name="year"
                    aria-label="year"
                    value={value?.equals?.year}
                    onChange={(e) => handleOnChange('year')(e)}
                    onBlur={onBlur}
                />
            </div>
        </div>
    );
};
