import classNames from 'classnames';

import { DateEntry } from 'design-system/date/entry';
import { Numeric } from 'design-system/input/numeric/Numeric';

import { DateEqualsCriteria } from '../dateCriteria';

import styles from './exact-date-field.module.scss';

type Field = keyof DateEntry;

type ExactDateFieldProps = {
    id: string;
    value: DateEqualsCriteria;
    onChange: (value: DateEqualsCriteria) => void;
    onBlur?: () => void;
    label?: string;
};

const ExactDateField = ({ id, value, onChange, onBlur, label }: ExactDateFieldProps) => {
    const criteria = value.equals;

    const handleFieldOnChange = (field: Field) => (value: number | null) => {
        const equals = { ...criteria, [field]: value };
        onChange({ equals });
    };

    return (
        <div role="group" id={id} className={styles['exact-date-entry']} aria-label={label}>
            <div className={classNames(styles['numeric-wrapper'], styles.month)}>
                <label htmlFor={`${id}-month`}>Month</label>
                <Numeric
                    id={`${id}-month`}
                    name="month"
                    value={criteria.month}
                    onChange={handleFieldOnChange('month')}
                    onBlur={onBlur}
                    min={1}
                    max={12}
                />
            </div>
            <div className={classNames(styles['numeric-wrapper'], styles.day)}>
                <label htmlFor={`${id}-day`}>Day</label>
                <Numeric
                    id={`${id}-day`}
                    name="day"
                    value={criteria.day}
                    onChange={handleFieldOnChange('day')}
                    onBlur={onBlur}
                    min={1}
                    max={31}
                />
            </div>
            <div className={classNames(styles['numeric-wrapper'], styles.year)}>
                <label htmlFor={`${id}-year`}>Year</label>
                <Numeric
                    id={`${id}-year`}
                    name="year"
                    value={criteria.year}
                    onChange={handleFieldOnChange('year')}
                    onBlur={onBlur}
                    min={1875}
                />
            </div>
        </div>
    );
};

export { ExactDateField };
