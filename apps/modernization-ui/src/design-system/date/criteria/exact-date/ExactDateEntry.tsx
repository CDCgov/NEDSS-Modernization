import { DateEqualsCriteria } from 'design-system/date/entry';
import styles from '../date-criteria.module.scss';
import { Numeric } from 'design-system/input/numeric/Numeric';
import { ChangeEvent, useEffect } from 'react';
import { ExactDateEntryFields, useDateEqualsCriteria } from '../useDateEntryCriteria';
import { Label } from '@trussworks/react-uswds';
import classNames from 'classnames';

type ExactDateEntryProps = {
    id: string;
    value: DateEqualsCriteria;
    onChange: (value: DateEqualsCriteria) => void;
    onBlur?: (value: DateEqualsCriteria) => void;
};

export const ExactDateEntry = ({ id, value, onChange, onBlur }: ExactDateEntryProps) => {
    const { state: dateEntry, apply, clear } = useDateEqualsCriteria(value);

    const handleOnChange = (field: ExactDateEntryFields) => (event: ChangeEvent<HTMLInputElement>) => {
        if (event.target.value) {
            apply(field, event.target.value);
        } else {
            clear(field);
        }
    };

    useEffect(() => {
        onChange(dateEntry as DateEqualsCriteria);
    }, [dateEntry, onChange]);

    return (
        <div id={id} className={styles['exact-date-entry']}>
            <div className={classNames(styles['numeric-wrapper'], styles['month'])}>
                <Label htmlFor={'month'}>Month</Label>
                <Numeric
                    name="month"
                    label="Month"
                    value={value?.equals?.month ?? ''}
                    onChange={(e) => handleOnChange('month')(e)}
                    onBlur={() => onBlur?.(dateEntry as DateEqualsCriteria)}
                />
            </div>
            <div className={classNames(styles['numeric-wrapper'], styles['day'])}>
                <Label htmlFor={'day'}>Day</Label>
                <Numeric
                    name="day"
                    label="Day"
                    value={value?.equals?.day ?? ''}
                    onChange={(e) => handleOnChange('day')(e)}
                    onBlur={() => onBlur?.(dateEntry as DateEqualsCriteria)}
                />
            </div>
            <div className={classNames(styles['numeric-wrapper'], styles['year'])}>
                <Label htmlFor={'year'}>Year</Label>
                <Numeric
                    name="year"
                    label="Year"
                    value={value?.equals?.year ?? ''}
                    onChange={(e) => handleOnChange('year')(e)}
                    onBlur={() => onBlur?.(dateEntry as DateEqualsCriteria)}
                />
            </div>
        </div>
    );
};
