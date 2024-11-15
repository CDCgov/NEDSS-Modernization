import { DateEqualsCriteria } from 'design-system/date/entry';
import styles from '../date-criteria.module.scss';
import { NumericInputField } from 'design-system/input/numeric/NumericInputField';
import { ChangeEvent } from 'react';

type ExactDateEntryProps = {
    id: string;
    value: DateEqualsCriteria;
    onChange: (value: DateEqualsCriteria) => void;
    onBlur?: (value: DateEqualsCriteria) => void;
};

export const ExactDateEntry = ({ id, value, onChange, onBlur }: ExactDateEntryProps) => {
    const handleOnChange = (field: 'month' | 'day' | 'year') => (event: ChangeEvent<HTMLInputElement>) => {
        let newValue: DateEqualsCriteria;
        if (event.target.value) {
            newValue = {
                equals: { ...value?.equals, [field]: parseInt(event.target.value) }
            };
        } else {
            newValue = {
                equals: { ...value?.equals }
            };
            delete newValue.equals[field];
        }
        onChange(newValue);
    };

    const handleOnBlur = (field: 'month' | 'day' | 'year') => (event: ChangeEvent<HTMLInputElement>) => {
        onBlur?.({ equals: { ...value?.equals, [field]: parseInt(event.target.value) } });
    };

    return (
        <div id={id} className={styles['exact-date-entry']}>
            <NumericInputField
                name="month"
                label="Month"
                value={value?.equals?.month ?? ''}
                onChange={(e) => handleOnChange('month')(e)}
                onBlur={(e) => handleOnBlur('month')(e)}
                className={styles['month']}
            />
            <NumericInputField
                name="day"
                label="Day"
                value={value?.equals?.day ?? ''}
                onChange={(e) => handleOnChange('day')(e)}
                onBlur={(e) => handleOnBlur('day')(e)}
                className={styles['day']}
            />

            <NumericInputField
                name="year"
                label="Year"
                value={value?.equals?.year ?? ''}
                onChange={(e) => handleOnChange('year')(e)}
                onBlur={(e) => handleOnBlur('year')(e)}
                className={styles['year']}
            />
        </div>
    );
};
