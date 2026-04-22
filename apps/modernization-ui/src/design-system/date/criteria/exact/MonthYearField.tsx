import { useCallback } from 'react';
import classNames from 'classnames';
import { withoutProperty, withProperty } from 'utils/object';
import { DateEntry } from 'design-system/date/entry';
import { DateEqualsCriteria } from '../dateCriteria';
import { Sizing } from 'design-system/field';
import Select from 'design-system/select/single/Select';
import { Selectable } from 'options';

import styles from './exact-date-field.module.scss';

type Field = keyof DateEntry;

const next = (field: Field, value: Selectable | null) =>
    value ? withProperty<DateEntry, string>(field, value.value) : withoutProperty<DateEntry>(field);

const selectable = (v: number | string): Selectable => ({ value: v.toString(), name: v.toString() });

type MonthYearFieldProps = {
    id: string;
    value?: DateEqualsCriteria;
    startYear: number;
    endYear: number;
    onChange: (value?: DateEqualsCriteria) => void;
    onBlur?: () => void;
    label?: string;
    required?: boolean;
    sizing?: Sizing;
};

const MonthYearField = ({
    id,
    value,
    startYear,
    endYear,
    onChange,
    onBlur,
    label,
    required,
    sizing,
}: MonthYearFieldProps) => {
    const years: Selectable[] = [];
    for (let y = startYear; y <= endYear; y++) {
        years.push(selectable(y));
    }
    const months: Selectable[] = [];
    for (let m = 1; m <= 12; m++) {
        months.push(selectable(m));
    }

    const monthValue = value?.equals?.month ? selectable(value.equals.month) : undefined;
    const yearValue = value?.equals?.year ? selectable(value.equals.year) : undefined;

    const handleFieldOnChange = useCallback(
        (field: Field) => (changed: Selectable | null) => {
            const equals = next(field, changed)(value?.equals);

            if (equals) {
                onChange({ equals });
            } else {
                onChange();
            }
        },
        [value?.equals, onChange]
    );

    return (
        <div role="group" id={id} className={styles['exact-date-entry']} aria-label={label}>
            <div className={classNames(styles['numeric-wrapper'], styles['month'])}>
                <label htmlFor={`${id}-month`}>{label} Month</label>
                <Select
                    id={`${id}-month`}
                    sizing={sizing}
                    onBlur={onBlur}
                    value={monthValue}
                    onChange={handleFieldOnChange('month')}
                    required={required}
                    options={months}
                />
            </div>
            <div className={classNames(styles['numeric-wrapper'], styles['year'])}>
                <label htmlFor={`${id}-year`}>{label} Year</label>
                <Select
                    id={`${id}-year`}
                    sizing={sizing}
                    onBlur={onBlur}
                    value={yearValue}
                    onChange={handleFieldOnChange('year')}
                    required={required}
                    options={years}
                />
            </div>
        </div>
    );
};

export { MonthYearField };
