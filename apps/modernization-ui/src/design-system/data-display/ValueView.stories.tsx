import { Meta, StoryObj } from '@storybook/react';
import { ValueView } from './ValueView';

const meta = {
    title: 'Design System/ValueView',
    component: ValueView
} satisfies Meta<typeof ValueView>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        title: 'Value Title',
        value: 'value'
    }
};
