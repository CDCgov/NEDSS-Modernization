import { Meta, StoryObj } from '@storybook/react';
import { MaskedTextInputField } from './MaskedTextInputField';

const meta = {
    title: 'Design System/Input/MaskedTextInputField',
    component: MaskedTextInputField
} satisfies Meta<typeof MaskedTextInputField>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        id: 'masked-text-input',
        label: 'Masked Text Input',
        placeholder: 'No Data',
        value: '12345-67890',
        mask: '_____-_____',
        pattern: '^.*$',
        onChange: (value) => {
            console.log('Value changed:', value);
        }
    }
};

export const Horizontal: Story = {
    args: {
        ...Default.args,
        id: 'masked-text-input-horizontal',
        orientation: 'horizontal'
    }
};

export const Vertical: Story = {
    args: {
        ...Default.args,
        id: 'masked-text-input-vertical',
        orientation: 'vertical'
    }
};
