import { DateEqualsCriteria } from 'design-system/date/entry';
import { ChangeEvent } from 'react';
import { NumberInput } from 'components/FormInputs/numberInput';
import styles from '../date-criteria.module.scss';

type ExactDateEntryProps = {
    id: string;
    value: DateEqualsCriteria;
    onChange: (value: DateEqualsCriteria) => void;
};

export const ExactDateEntry = ({ id, value, onChange }: ExactDateEntryProps) => {
    const handleOnChange = (field: 'month' | 'day' | 'year') => (event: ChangeEvent<HTMLInputElement>) => {
        const newValue: DateEqualsCriteria = { equals: { ...value?.equals, [field]: +event.target.value } };
        onChange(newValue);
    };

    return (
        <div id={id} className={styles['exact-date-entry']}>
            <NumberInput
                name="month"
                ariaLabel="month"
                label="Month"
                defaultValue={value?.equals?.month?.toString()}
                onChange={(e: ChangeEvent<HTMLInputElement>) => {
                    handleOnChange('month')(e);
                }}
                mask="__"
                className={styles['month']}
            />
            <NumberInput
                name="day"
                ariaLabel="day"
                label="Day"
                defaultValue={value?.equals?.day?.toString()}
                onChange={(e: ChangeEvent<HTMLInputElement>) => {
                    handleOnChange('day')(e);
                }}
                mask="__"
                className={styles['day']}
            />

            <NumberInput
                name="year"
                ariaLabel="year"
                label="Year"
                defaultValue={value?.equals?.year?.toString()}
                onChange={(e: ChangeEvent<HTMLInputElement>) => {
                    handleOnChange('year')(e);
                }}
                mask="____"
                className={styles['year']}
            />
        </div>
    );
};
