import classNames from 'classnames';

import { Sizing } from 'design-system/field';
import Select from 'design-system/select/single/Select';
import { Selectable } from 'options';

import { DateBetweenCriteria, DateRange } from '../dateCriteria';

import styles from './date-range-field.module.scss';

type Field = keyof DateRange;

const selectable = (v: number | string): Selectable => ({ value: v.toString(), name: v.toString() });

export type YearRangeFieldProps = {
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

const YearRangeField = ({
    id,
    startYear,
    endYear,
    value,
    sizing,
    onChange,
    onBlur,
    label,
    required,
}: YearRangeFieldProps) => {
    const years: Selectable[] = [];
    for (let y = endYear; y >= startYear; y--) {
        years.push(selectable(y));
    }
    const fromValue = value.between.from ? selectable(value.between.from) : null;
    const toValue = value.between.to ? selectable(value.between.to) : null;

    const handleFieldOnChange = (field: Field) => (changed: Selectable | null) => {
        const between = { ...value.between, [field]: changed };
        onChange({ between });
    };

    return (
        <div id={id} role="group" className={classNames(styles['date-range-entry'])} aria-label={label}>
            <div className={classNames(styles['range-wrapper'], 'from')}>
                <label htmlFor={`${id}-from`}>From</label>
                <Select
                    sizing={sizing}
                    onBlur={onBlur}
                    id={`${id}-from`}
                    value={fromValue}
                    onChange={handleFieldOnChange('from')}
                    required={required}
                    options={years}
                />
            </div>
            <div className={classNames(styles['range-wrapper'])}>
                <label htmlFor={`${id}-to`}>To</label>
                <Select
                    sizing={sizing}
                    onBlur={onBlur}
                    id={`${id}-to`}
                    value={toValue}
                    onChange={handleFieldOnChange('to')}
                    required={required}
                    options={years}
                />
            </div>
        </div>
    );
};

export { YearRangeField };
