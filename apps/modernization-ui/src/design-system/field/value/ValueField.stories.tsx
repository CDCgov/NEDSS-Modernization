import { Meta, StoryObj } from '@storybook/react';
import { ValueField } from './ValueField';

const meta = {
    title: 'Design System/Field/Value',
    component: ValueField
} satisfies Meta<typeof ValueField>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        label: 'Value Title',
        children: 'value'
    }
};

export const Empty: Story = {
    args: {
        ...Default.args,
        children: undefined
    }
};
