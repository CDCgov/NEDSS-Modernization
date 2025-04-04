import { Meta, StoryObj } from '@storybook/react';
import { OperatorInput } from './OperatorInput';

const meta = {
    title: 'Design System/Input/OperatorInput',
    component: OperatorInput
} satisfies Meta<typeof OperatorInput>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        id: 'operator-input',
        label: 'Operator Input',
        operationMode: 'all',
        onChange: (value) => {
            console.log('Value changed:', value);
        }
    }
};

export const Horizontal: Story = {
    args: {
        ...Default.args,
        id: 'operator-horizontal',
        orientation: 'horizontal'
    }
};

export const Vertical: Story = {
    args: {
        ...Default.args,
        id: 'operator-vertical',
        orientation: 'vertical'
    }
};
