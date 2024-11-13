import { Grid } from '@trussworks/react-uswds';
import { Input } from 'components/FormInputs/Input';
import { DateEqualsCriteria } from 'design-system/date/entry';
import { ChangeEvent } from 'react';

type ExactDateEntryProps = {
    value: DateEqualsCriteria;
    onChange: (value: DateEqualsCriteria) => void;
};

export const ExactDateEntry = ({ value, onChange }: ExactDateEntryProps) => {
    const handleOnChange = (field: 'month' | 'day' | 'year') => (event: ChangeEvent<HTMLInputElement>) => {
        const newValue: DateEqualsCriteria = { equals: { ...value?.equals, [field]: +event.target.value } };
        onChange(newValue);
    };

    return (
        <Grid row gap={2}>
            <Grid col={3}>
                <Input
                    name="month"
                    ariaLabel="month"
                    label="Month"
                    defaultValue={value?.equals?.month?.toString()}
                    onChange={(e: ChangeEvent<HTMLInputElement>) => {
                        handleOnChange('month')(e);
                    }}
                    type={'text'}
                    mask="__"
                />
            </Grid>
            <Grid col={3}>
                <Input
                    name="day"
                    ariaLabel="day"
                    label="Day"
                    defaultValue={value?.equals?.day?.toString()}
                    onChange={(e: ChangeEvent<HTMLInputElement>) => {
                        handleOnChange('day')(e);
                    }}
                    type={'text'}
                    mask="__"
                />
            </Grid>
            <Grid col={6}>
                <Input
                    name="year"
                    ariaLabel="year"
                    label="Year"
                    defaultValue={value?.equals?.year?.toString()}
                    onChange={(e: ChangeEvent<HTMLInputElement>) => {
                        handleOnChange('year')(e);
                    }}
                    type={'text'}
                    mask="____"
                />
            </Grid>
        </Grid>
    );
};
