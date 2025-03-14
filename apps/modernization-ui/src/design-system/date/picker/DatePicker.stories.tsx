import { Meta, StoryObj } from '@storybook/react';
import { DatePicker } from './DatePicker';

const meta = {
    title: 'Design System/Date/DatePicker',
    component: DatePicker
} satisfies Meta<typeof DatePicker>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        id: 'datepicker-default',
        label: 'Date Picker',
        value: '2024-09-30'
    }
};
