import { Meta, StoryObj } from '@storybook/react';
import { Chip } from './Chip';

const meta = {
    title: 'Design System/Chip',
    component: Chip
} satisfies Meta<typeof Chip>;

export default meta;

type Story = StoryObj<typeof meta>;

const handleClose = () => console.log('Close chip');

export const Default: Story = {
    args: {
        name: 'Name',
        value: 'Default',
        handleClose: handleClose
    }
};

export const Operator: Story = {
    args: {
        name: 'Name',
        value: 'Operator',
        operator: '!=',
        handleClose: handleClose
    }
};
