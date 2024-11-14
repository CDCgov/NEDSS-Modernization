import { Radio } from '@trussworks/react-uswds';
import { ChangeEvent, useEffect, useState } from 'react';
import { EntryWrapper, Sizing } from 'components/Entry';
import { DateBetweenCriteria, DateCriteria, DateEqualsCriteria, isDateBetweenCriteria } from '../entry';
import { ExactDateEntry } from './exact-date';
import { DateRangeEntry } from './date-range';
import { validateDateEntry } from '../validateDateEntry';
import styles from './date-criteria.module.scss';

type DateOperation = 'equals' | 'between';

export type DateEntryCriteriaProps = {
    id: string;
    value: DateCriteria;
    label: string;
    sizing?: Sizing;
    orientation?: 'vertical' | 'horizontal';
    onChange: (value?: DateCriteria) => void;
};

export const DateEntryCriteria = ({ orientation, label, id, sizing, value, onChange }: DateEntryCriteriaProps) => {
    const [dateOperation, setDateOperation] = useState<DateOperation>();
    const error = validateDateEntry(label)(value as DateEqualsCriteria);

    useEffect(() => {
        setDateOperation(value && isDateBetweenCriteria(value) ? 'between' : 'equals');
    }, [value]);

    const handleDateOperationChange = (event: ChangeEvent<HTMLInputElement>) => {
        setDateOperation(event.target.value as DateOperation);
    };

    return (
        <EntryWrapper
            error={typeof error === 'string' && dateOperation === 'equals' ? error : ''}
            orientation={orientation}
            label={label}
            htmlFor={id}
            sizing={sizing}>
            <div className={styles['options-wrapper']}>
                <Radio
                    id={'equals'}
                    name="dateOperation"
                    label={'Exact Date'}
                    value={'equals'}
                    onChange={handleDateOperationChange}
                    checked={dateOperation === 'equals'}
                />
                <Radio
                    id={'between'}
                    name="dateOperation"
                    label={'Date Range'}
                    value={'between'}
                    onChange={handleDateOperationChange}
                    checked={dateOperation === 'between'}
                />
            </div>
            <div className="margin-bottom-1">
                {dateOperation === 'equals' && (
                    <ExactDateEntry id={`${id}-exact-date`} value={value as DateEqualsCriteria} onChange={onChange} />
                )}
                {dateOperation === 'between' && (
                    <DateRangeEntry id={`${id}-exact-date`} value={value as DateBetweenCriteria} onChange={onChange} />
                )}
            </div>
        </EntryWrapper>
    );
};
