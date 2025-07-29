import { Meta, StoryObj } from '@storybook/react-vite';
import { DatePicker } from './DatePicker';

const meta = {
    title: 'Design System/Date/Picker',
    component: DatePicker
} satisfies Meta<typeof DatePicker>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        id: 'dte-picker',
        label: 'Date Picker',
        value: '2024-09-30'
    }
};

export const Small: Story = {
    args: {
        ...Default.args,
        sizing: 'small'
    }
};

export const Medium: Story = {
    args: {
        ...Default.args,
        sizing: 'medium'
    }
};

export const Large: Story = {
    args: {
        ...Default.args,
        sizing: 'large'
    }
};
