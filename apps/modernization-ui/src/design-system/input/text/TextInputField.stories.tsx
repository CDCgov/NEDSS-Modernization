import { Meta, StoryObj } from '@storybook/react';
import { TextInputField } from './TextInputField';

const meta = {
    title: 'Design System/Input/TextInputField',
    component: TextInputField
} satisfies Meta<typeof TextInputField>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        id: 'text-input',
        label: 'Text Input',
        onChange: () => {}
    }
};

export const Horizontal: Story = {
    args: {
        ...Default.args,
        orientation: 'horizontal'
    }
};

export const HorizontalHelperText: Story = {
    args: {
        ...Horizontal.args,
        helperText: 'Helper text'
    }
};

export const HorizontalError: Story = {
    args: {
        ...Horizontal.args,
        error: 'Helpful error message'
    }
};

export const HorizontalWarning: Story = {
    args: {
        ...Horizontal.args,
        error: 'Helpful warning message'
    }
};

export const Vertical: Story = {
    args: {
        ...Default.args,
        orientation: 'vertical'
    }
};

export const VerticalHelperText: Story = {
    args: {
        ...Vertical.args,
        helperText: 'Helper text'
    }
};

export const VerticalError: Story = {
    args: {
        ...Vertical.args,
        error: 'Helpful error message'
    }
};

export const VerticalWarning: Story = {
    args: {
        ...Vertical.args,
        warning: 'Helpful warning message'
    }
};
