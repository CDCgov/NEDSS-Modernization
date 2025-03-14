import { Meta, StoryObj } from '@storybook/react';
import { DateCriteriaEntry } from './DateCriteriaEntry';
import { DateCriteria } from '../entry';

const meta = {
    title: 'Design System/Date/DateCriteriaEntry',
    component: DateCriteriaEntry
} satisfies Meta<typeof DateCriteriaEntry>;

export default meta;

type Story = StoryObj<typeof meta>;

const handleChange = (value?: DateCriteria) => console.log('Value changed', value);

// const today = new Date();
// const equalsCriteria: DateEqualsCriteria = {
//     year: today.getFullYear(),
//     month: today.getMonth() + 1,
//     day: today.getDate()
// };

export const Default: Story = {
    args: {
        id: 'datecriteriaentry-default',
        value: {
            equals: {}
        },
        label: 'Date Criteria',
        onChange: handleChange
    }
};

export const EqualsCriteria: Story = {
    args: {
        id: 'datecriteriaentry-equals',
        value: {
            equals: {
                year: 2024,
                month: 9,
                day: 30
            }
        },
        label: 'Date Criteria',
        onChange: handleChange
    }
};

export const DateRangeCriteria: Story = {
    args: {
        id: 'datecriteriaentry-range',
        value: {
            between: {
                from: '07/15/2024',
                to: '09/30/2024'
            }
        },
        label: 'Date Criteria',
        onChange: handleChange
    }
};
