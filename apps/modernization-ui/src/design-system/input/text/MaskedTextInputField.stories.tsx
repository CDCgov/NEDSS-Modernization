import { Meta, StoryObj } from '@storybook/react';
import { MaskedTextInputField } from './MaskedTextInputField';

const meta = {
    title: 'Design System/Input/Text/Masked',
    component: MaskedTextInputField
} satisfies Meta<typeof MaskedTextInputField>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        id: 'masked-text-input',
        label: 'Masked Text Input',
        helperText: 'Values will be formatted accorded to the given mask',
        mask: '(_)____-____@_',
        onChange: () => {}
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
