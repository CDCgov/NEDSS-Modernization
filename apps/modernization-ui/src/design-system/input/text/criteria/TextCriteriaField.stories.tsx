import { Meta, StoryObj } from '@storybook/react';
import { TextCriteriaField } from './TextCriteriaField';

const meta = {
    title: 'Design System/Input/TextCriteriaField',
    component: TextCriteriaField
} satisfies Meta<typeof TextCriteriaField>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        id: 'criteria-input',
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
        id: 'criteria-horizontal',
        orientation: 'horizontal'
    }
};

export const Vertical: Story = {
    args: {
        ...Default.args,
        id: 'criteria-vertical',
        orientation: 'vertical'
    }
};
