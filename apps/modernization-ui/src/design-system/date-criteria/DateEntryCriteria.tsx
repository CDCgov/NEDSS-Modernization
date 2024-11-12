import { Grid, Radio } from '@trussworks/react-uswds';
import { ChangeEvent } from 'react';
import { EntryWrapper, Sizing } from 'components/Entry';
import { DateCriteria } from 'generated/graphql/schema';
import { dateOperationOptions } from './options';
import { ExactDateEntry } from './ExactDateEntry';
import { DateRangeEntry } from './DateRangeEntry';

export type DateOperation = 'equals' | 'between';

export type DateEntryCriteriaProps = {
    id: string;
    value?: DateCriteria | undefined;
    label: string;
    sizing?: Sizing;
    orientation?: 'vertical' | 'horizontal';
    error?: any;
    onChange: (value?: DateCriteria | null) => void;
};

const asDateOperator = (value: DateCriteria | undefined): DateOperation => {
    if (!value) {
        return 'equals';
    }
    if ('equals' in value) {
        return 'equals';
    } else if ('between' in value) {
        return 'between';
    }
    return 'equals';
};

export const DateEntryCriteria = ({ orientation, label, id, sizing, value, onChange }: DateEntryCriteriaProps) => {
    const dateOperator = asDateOperator(value);
    const handleRadioChange = (event: ChangeEvent<HTMLInputElement>) => {
        const newValue = event.target.value === 'equals' ? { equals: value?.equals } : { between: value?.between };
        onChange(newValue);
    };

    return (
        <EntryWrapper orientation={orientation} label={label} htmlFor={id} sizing={sizing}>
            <Grid row>
                <Grid row col={12}>
                    {dateOperationOptions.map((option) => (
                        <Grid col={6} key={option.key}>
                            <Radio
                                id={option.id}
                                name="dateOperation"
                                label={option.label}
                                value={option.key}
                                onChange={handleRadioChange}
                                checked={option.key === dateOperator}
                            />
                        </Grid>
                    ))}
                </Grid>
                <Grid col={12} className="margin-top-2">
                    {dateOperator === 'equals' ? <ExactDateEntry /> : <DateRangeEntry />}
                </Grid>
            </Grid>
        </EntryWrapper>
    );
};
