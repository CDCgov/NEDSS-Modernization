import { useCallback, useEffect, useState } from 'react';
import classNames from 'classnames';
import { withoutProperty, withProperty } from 'utils/object';
import { Numeric } from 'design-system/input/numeric/Numeric';
import { DateEntry, DateEqualsCriteria } from 'design-system/date/entry';

import styles from './exact-date-entry.module.scss';

type Field = keyof DateEntry;

const next = (field: Field, value: number | undefined) =>
    value ? withProperty<DateEntry, number>(field, value) : withoutProperty<DateEntry>(field);

type ExactDateEntryProps = {
    id: string;
    value?: DateEqualsCriteria;
    onChange: (value?: DateEqualsCriteria) => void;
    onBlur?: () => void;
};

const ExactDateEntry = ({ id, value, onChange, onBlur }: ExactDateEntryProps) => {
    const [criteria, setCriteria] = useState<DateEntry | undefined>(value?.equals);

    useEffect(() => {
        setCriteria(value?.equals);
    }, [value]);

    const handleFieldOnChange = useCallback(
        (field: Field) => (value: number | undefined) => {
            const equals = next(field, value)(criteria);

            if (equals) {
                onChange({ equals });
            } else {
                onChange();
            }

            setCriteria(equals);
        },
        [criteria, setCriteria, onChange]
    );

    return (
        <div id={id} className={styles['exact-date-entry']}>
            <div className={classNames(styles['numeric-wrapper'], styles['month'])}>
                <label htmlFor={`${id}-month`}>Month</label>
                <Numeric
                    id={`${id}-month`}
                    name="month"
                    value={criteria?.month}
                    onChange={handleFieldOnChange('month')}
                    onBlur={onBlur}
                />
            </div>
            <div className={classNames(styles['numeric-wrapper'], styles['day'])}>
                <label htmlFor={`${id}-day`}>Day</label>
                <Numeric
                    id={`${id}-day`}
                    name="day"
                    value={criteria?.day}
                    onChange={handleFieldOnChange('day')}
                    onBlur={onBlur}
                />
            </div>
            <div className={classNames(styles['numeric-wrapper'], styles['year'])}>
                <label htmlFor={`${id}-year`}>Year</label>
                <Numeric
                    id={`${id}-year`}
                    name="year"
                    value={criteria?.year}
                    onChange={handleFieldOnChange('year')}
                    onBlur={onBlur}
                />
            </div>
        </div>
    );
};

export { ExactDateEntry };
