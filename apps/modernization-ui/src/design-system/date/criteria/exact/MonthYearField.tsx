import classNames from 'classnames';

import { MonthYearEntry } from 'design-system/date/entry';
import { Sizing } from 'design-system/field';
import Select from 'design-system/select/single/Select';
import { Selectable } from 'options';

import styles from './exact-date-field.module.scss';

type MonthYearEqualsCriteria = { equals: MonthYearEntry };
type Field = keyof MonthYearEntry;

const selectable = (v: number | string): Selectable => ({ value: v.toString(), name: v.toString() });

type MonthYearFieldProps = {
    id: string;
    value: MonthYearEqualsCriteria;
    startYear: number;
    endYear: number;
    onChange: (value: MonthYearEqualsCriteria) => void;
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
    for (let y = endYear; y >= startYear; y--) {
        years.push(selectable(y));
    }
    const months: Selectable[] = [];
    for (let m = 1; m <= 12; m++) {
        months.push(selectable(m));
    }

    const monthValue = value.equals.month ? selectable(value.equals.month) : null;
    const yearValue = value.equals.year ? selectable(value.equals.year) : null;

    const handleFieldOnChange = (field: Field) => (changed: Selectable | null) => {
        const val = changed?.value ?? null;
        const equals = { ...value.equals, [field]: val };
        onChange({ equals });
    };

    return (
        <div role="group" id={id} className={styles['exact-date-entry']} aria-label={label}>
            <div className={classNames(styles['numeric-wrapper'], styles.month)}>
                <label htmlFor={`${id}-month`}>{label} month</label>
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
            <div className={classNames(styles['numeric-wrapper'], styles.year)}>
                <label htmlFor={`${id}-year`}>{label} year</label>
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
export type { MonthYearEqualsCriteria };
