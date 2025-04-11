import { Meta, StoryObj } from '@storybook/react';
import { Field } from './Field';

const meta = {
    title: 'Design System/Field',
    component: Field
} satisfies Meta<typeof Field>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        label: 'Name',
        helperText: 'This is a helper text',
        children: <span>Field</span>,
        sizing: 'medium',
        orientation: 'horizontal',
        htmlFor: 'name'
    }
};

export const ErrorWarning: Story = {
    args: {
        label: 'Name',
        error: 'This is an error message',
        warning: 'This is an warning message',
        children: <span>Field</span>,
        sizing: 'medium',
        htmlFor: 'name'
    }
};
