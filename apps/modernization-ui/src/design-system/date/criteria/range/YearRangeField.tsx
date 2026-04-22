import { useCallback } from 'react';
import classNames from 'classnames';
import { withoutProperty, withProperty } from 'utils/object';
import { Sizing } from 'design-system/field';
import { DateBetweenCriteria, DateRange } from '../dateCriteria';

import styles from './date-range-field.module.scss';
import Select from 'design-system/select/single/Select';
import { Selectable } from 'options';

type Field = keyof DateRange;

const next = (field: Field, value: Selectable | null) =>
    value ? withProperty<DateRange, string>(field, value.value) : withoutProperty<DateRange>(field);

const selectable = (v: number | string): Selectable => ({ value: v.toString(), name: v.toString() });

export type YearRangeFieldProps = {
    id: string;
    startYear: number;
    endYear: number;
    value?: DateBetweenCriteria;
    sizing?: Sizing;
    onChange: (value?: DateBetweenCriteria) => void;
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
    const fromValue = value?.between?.from ? selectable(value.between.from) : undefined;
    const toValue = value?.between?.to ? selectable(value.between.to) : undefined;

    const handleFieldOnChange = useCallback(
        (field: Field) => (changed: Selectable | null) => {
            const between = next(field, changed)(value?.between);
            if (between) {
                onChange({ between });
            } else {
                onChange({ between: { from: undefined, to: undefined } });
            }
        },
        [onChange, value?.between]
    );

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
