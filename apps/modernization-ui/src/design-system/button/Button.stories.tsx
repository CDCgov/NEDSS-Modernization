import { Meta, StoryObj } from '@storybook/react';
import { Button } from './Button';
import { Icon } from 'design-system/icon';

const meta = {
    title: 'Design System/Button',
    component: Button
} satisfies Meta<typeof Button>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        children: 'Button text'
    }
};

export const WithIcon: Story = {
    args: {
        children: 'Button text',
        icon: <Icon name="add" />
    }
};
