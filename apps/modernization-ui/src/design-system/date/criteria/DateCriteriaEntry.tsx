import { Radio } from '@trussworks/react-uswds';
import { ChangeEvent } from 'react';
import { EntryWrapper, Sizing } from 'components/Entry';
import { DateCriteria, isDateBetweenCriteria, isDateEqualsCriteria } from '../entry';
import { ExactDateEntry } from './exact-date';
import { DateRangeEntry } from './date-range';
import styles from './date-criteria.module.scss';

export type DateCriteriaEntryProps = {
    id: string;
    value: DateCriteria | undefined;
    label: string;
    sizing?: Sizing;
    orientation?: 'vertical' | 'horizontal';
    error?: string;
    onChange: (value?: DateCriteria) => void;
    onBlur?: () => void;
};

export const DateCriteriaEntry = ({
    orientation,
    label,
    id,
    sizing,
    value,
    error,
    onChange,
    onBlur
}: DateCriteriaEntryProps) => {
    const handleDateOperationChange = (event: ChangeEvent<HTMLInputElement>) => {
        onChange(event.target.value === 'equals' ? { equals: {} } : { between: {} });
    };

    return (
        <EntryWrapper error={error} orientation={orientation} label={label} htmlFor={id} sizing={sizing}>
            <div className={styles['options-wrapper']}>
                <Radio
                    id={'equals'}
                    name="dateOperation"
                    label={'Exact Date'}
                    value={'equals'}
                    onChange={handleDateOperationChange}
                    checked={value && isDateEqualsCriteria(value)}
                />
                <Radio
                    id={'between'}
                    name="dateOperation"
                    label={'Date Range'}
                    value={'between'}
                    onChange={handleDateOperationChange}
                    checked={value && isDateBetweenCriteria(value)}
                />
            </div>
            <div className="margin-bottom-1">
                {value && isDateEqualsCriteria(value) && (
                    <ExactDateEntry id={`${id}-exact-date`} value={value} onChange={onChange} onBlur={onBlur} />
                )}
                {value && isDateBetweenCriteria(value) && (
                    <DateRangeEntry id={`${id}-range-entry`} value={value} onChange={onChange} onBlur={onBlur} />
                )}
            </div>
        </EntryWrapper>
    );
};
