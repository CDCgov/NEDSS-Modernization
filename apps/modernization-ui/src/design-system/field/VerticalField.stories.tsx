import { Meta, StoryObj } from '@storybook/react';
import { Field } from './Field';

const meta = {
    title: 'Design System/Field/Top down',
    component: Field
} satisfies Meta<typeof Field>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        label: 'Label',
        children: <span>Field</span>,
        orientation: 'vertical',
        htmlFor: 'story-vertical-field'
    }
};

export const Required: Story = {
    args: {
        ...Default.args,
        required: true
    }
};

export const HelperText: Story = {
    args: {
        ...Default.args,
        helperText: 'Helper text'
    }
};

export const Error: Story = {
    args: {
        ...Default.args,
        error: 'Helpful error message'
    }
};

export const Warning: Story = {
    args: {
        ...Default.args,
        warning: 'Helpful warning message'
    }
};
