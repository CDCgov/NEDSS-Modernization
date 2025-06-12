import { Meta, StoryObj } from '@storybook/react-vite';
import { Checkbox } from './Checkbox';

const meta = {
    title: 'Design System/Checkbox',
    component: Checkbox
} satisfies Meta<typeof Checkbox>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        id: 'checkbox-default',
        label: 'The Card'
    }
};

export const Checked: Story = {
    args: {
        id: 'checkbox-default',
        label: 'The Card',
        selected: true
    }
};
