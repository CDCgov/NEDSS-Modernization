import { Meta, StoryObj } from '@storybook/react-vite';
import { TextAreaField } from './TextAreaField';

const meta = {
    title: 'Design System/Input/TextAreaField',
    component: TextAreaField
} satisfies Meta<typeof TextAreaField>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        id: 'text-area-input',
        label: 'Text Input',
        placeholder: 'No Data',
        value: 'Some long text',
        onChange: (value) => {
            console.log('Value changed:', value);
        }
    }
};

export const Horizontal: Story = {
    args: {
        ...Default.args,
        id: 'text-area-horizontal',
        orientation: 'horizontal'
    }
};

export const Vertical: Story = {
    args: {
        ...Default.args,
        id: 'text-area-vertical',
        orientation: 'vertical'
    }
};
