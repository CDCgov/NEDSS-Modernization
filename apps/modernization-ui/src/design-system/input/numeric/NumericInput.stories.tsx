import { Meta, StoryObj } from '@storybook/react-vite';
import { NumericInput } from './NumericInput';

const meta = {
    title: 'Design System/Input/NumericInput',
    component: NumericInput
} satisfies Meta<typeof NumericInput>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        id: 'numeric-input',
        label: 'Numeric Input',
        placeholder: 'No Data',
        value: 0,
        onChange: (value) => {
            console.log('Value changed:', value);
        }
    }
};

export const Horizontal: Story = {
    args: {
        ...Default.args,
        id: 'numeric-input-horizontal',
        orientation: 'horizontal'
    }
};

export const Vertical: Story = {
    args: {
        ...Default.args,
        id: 'numeric-input-vertical',
        orientation: 'vertical'
    }
};
