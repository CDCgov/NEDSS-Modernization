import { Grid, Radio } from '@trussworks/react-uswds';
import { ChangeEvent, useState } from 'react';
import { EntryWrapper, Sizing } from 'components/Entry';
import { DateCriteria, DateEqualsCriteria, isDateBetweenCriteria } from '../entry';
import { ExactDateEntry } from './exact-date';
import { DateRangeEntry } from './date-range';

type DateOperation = 'equals' | 'between';

export type DateEntryCriteriaProps = {
    id: string;
    value: DateCriteria;
    label: string;
    sizing?: Sizing;
    orientation?: 'vertical' | 'horizontal';
    error?: any;
    onChange: (value?: DateCriteria) => void;
};

export const DateEntryCriteria = ({ orientation, label, id, sizing, value, onChange }: DateEntryCriteriaProps) => {
    const [dateOperation, setDateOperation] = useState<DateOperation>(
        isDateBetweenCriteria(value) ? 'between' : 'equals'
    );

    const handleRadioChange = (event: ChangeEvent<HTMLInputElement>) => {
        setDateOperation(event.target.value as DateOperation);
    };

    return (
        <EntryWrapper orientation={orientation} label={label} htmlFor={id} sizing={sizing}>
            <Grid row>
                <Grid row col={12}>
                    <Grid col={6}>
                        <Radio
                            id={'equals'}
                            name="dateOperation"
                            label={'Exact Date'}
                            value={'equals'}
                            onChange={handleRadioChange}
                            checked={dateOperation === 'equals'}
                        />
                    </Grid>
                    <Grid col={6}>
                        <Radio
                            id={'between'}
                            name="dateOperation"
                            label={'Date Range'}
                            value={'between'}
                            onChange={handleRadioChange}
                            checked={dateOperation === 'between'}
                        />
                    </Grid>
                </Grid>
                <Grid col={12} className="margin-top-2">
                    {dateOperation === 'equals' && (
                        <ExactDateEntry value={value as DateEqualsCriteria} onChange={onChange} />
                    )}
                    {dateOperation === 'between' && <DateRangeEntry />}
                </Grid>
            </Grid>
        </EntryWrapper>
    );
};
