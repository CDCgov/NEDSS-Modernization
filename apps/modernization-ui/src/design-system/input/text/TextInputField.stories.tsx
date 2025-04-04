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
        placeholder: 'No Data',
        value: 'some text',
        onChange: (value) => {
            console.log('Value changed:', value);
        }
    }
};

export const Horizontal: Story = {
    args: {
        ...Default.args,
        id: 'text-input-horizontal',
        orientation: 'horizontal'
    }
};

export const Vertical: Story = {
    args: {
        ...Default.args,
        id: 'text-input-vertical',
        orientation: 'vertical'
    }
};
