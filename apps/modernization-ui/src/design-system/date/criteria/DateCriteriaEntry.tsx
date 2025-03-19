import { useCallback, useEffect, useState } from 'react';
import { EntryWrapper } from 'components/Entry';
import { DateCriteria, isDateBetweenCriteria, isDateEqualsCriteria } from '../entry';
import { ExactDateEntry } from './exact-date';
import { DateRangeEntry } from './date-range';
import { Radio } from 'design-system/radio';
import { FieldProps } from 'design-system/field';
import { Shown } from 'conditional-render';

import styles from './date-criteria.module.scss';

type CriteriaType = 'equals' | 'between';

const resolveCriteriaType = (value: DateCriteria): CriteriaType =>
    isDateBetweenCriteria(value) ? 'between' : 'equals';

const resolveInitialCriteriaType = (value?: DateCriteria | null): CriteriaType =>
    value ? resolveCriteriaType(value) : 'equals';

const asDateEqualsCriteria = (value?: DateCriteria | null) =>
    value && isDateEqualsCriteria(value) ? value : undefined;

const asDateRangeCriteria = (value?: DateCriteria | null) =>
    value && isDateBetweenCriteria(value) ? value : undefined;

export type DateCriteriaEntryProps = {
    id: string;
    value?: DateCriteria | null;
    onChange: (value?: DateCriteria) => void;
    onBlur?: () => void;
} & FieldProps;

const DateCriteriaEntry = ({
    orientation,
    label,
    id,
    sizing,
    value,
    error,
    onChange,
    onBlur
}: DateCriteriaEntryProps) => {
    const [type, setType] = useState<CriteriaType>(resolveInitialCriteriaType(value));

    useEffect(() => {
        setType(resolveInitialCriteriaType(value));
    }, [value]);

    const handleDateOperationChange = useCallback(
        (type: CriteriaType) => () => {
            setType(type);
            onChange();
        },
        [setType, onChange]
    );

    return (
        <EntryWrapper error={error} orientation={orientation} label={label} htmlFor={id} sizing={sizing}>
            <div className={styles['options-wrapper']}>
                <Radio
                    id={'equals'}
                    name="dateOperation"
                    label={'Exact Date'}
                    value={'equals'}
                    onChange={handleDateOperationChange('equals')}
                    checked={type === 'equals'}
                    sizing={sizing}
                />
                <Radio
                    id={'between'}
                    name="dateOperation"
                    label={'Date Range'}
                    value={'between'}
                    onChange={handleDateOperationChange('between')}
                    checked={type === 'between'}
                    sizing={sizing}
                />
            </div>
            <div key={type}>
                <Shown when={type === 'equals'}>
                    <ExactDateEntry
                        id={`${id}-exact-date`}
                        value={asDateEqualsCriteria(value)}
                        onChange={onChange}
                        onBlur={onBlur}
                    />
                </Shown>
                <Shown when={type === 'between'}>
                    <DateRangeEntry
                        sizing={sizing}
                        id={`${id}-range-entry`}
                        value={asDateRangeCriteria(value)}
                        onChange={onChange}
                        onBlur={onBlur}
                    />
                </Shown>
            </div>
        </EntryWrapper>
    );
};

export { DateCriteriaEntry };
