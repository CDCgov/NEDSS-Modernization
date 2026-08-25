import classNames from 'classnames';

import { Sizing } from 'design-system/field';

import { DateBetweenCriteria, DateRange } from '../dateCriteria';
import { MonthYearEqualsCriteria, MonthYearField } from '../exact/MonthYearField';

import styles from './date-range-field.module.scss';

type Field = keyof DateRange;

// format to mm/yyyy string
const formatMonth = (n: number) => new Intl.NumberFormat('en-US', { minimumIntegerDigits: 2 }).format(n);
const toDateString = ({ equals }: MonthYearEqualsCriteria) =>
    equals ? `${formatMonth(equals.month ?? 0)}/${equals.year ?? 0}` : undefined;
const parseDateString = (dtStr: string | null): MonthYearEqualsCriteria => {
    if (!dtStr) return { equals: { month: null, year: null } };
    const parts = dtStr.split('/');
    const month = parseInt(parts[0]);
    const year = parseInt(parts[1]);
    return { equals: { month, year } };
};

export type MonthYearRangeFieldProps = {
    id: string;
    startYear: number;
    endYear: number;
    value: DateBetweenCriteria;
    sizing?: Sizing;
    onChange: (value: DateBetweenCriteria) => void;
    onBlur?: () => void;
    label?: string;
    required?: boolean;
};

const MonthYearRangeField = ({
    id,
    startYear,
    endYear,
    value,
    sizing,
    onChange,
    onBlur,
    label,
    required,
}: MonthYearRangeFieldProps) => {
    const fromValue = parseDateString(value.between.from);
    const toValue = parseDateString(value.between.to);

    const handleFieldOnChange = (field: Field) => (changed: MonthYearEqualsCriteria) => {
        onChange({ between: { ...value.between, [field]: toDateString(changed) } });
    };

    return (
        <div id={id} role="group" className={classNames(styles['date-range-entry'])} aria-label={label}>
            <MonthYearField
                id={`${id}-from`}
                label="From"
                value={fromValue}
                startYear={startYear}
                endYear={endYear}
                onChange={handleFieldOnChange('from')}
                required={required}
                sizing={sizing}
                onBlur={onBlur}
            />
            <MonthYearField
                id={`${id}-to`}
                label="To"
                value={toValue}
                startYear={startYear}
                endYear={endYear}
                onChange={handleFieldOnChange('to')}
                required={required}
                sizing={sizing}
                onBlur={onBlur}
            />
        </div>
    );
};

export { MonthYearRangeField };
