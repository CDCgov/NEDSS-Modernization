import { Meta, StoryObj } from '@storybook/react';
import { Icon } from 'design-system/icon';
import { Tooltip } from './Tooltip';

const meta = {
    title: 'Design System/Tooltip',
    component: Tooltip
} satisfies Meta<typeof Tooltip>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        message: 'Tooltip message',
        children: (id) => <Icon name="announcement" aria-describedby={id} />
    }
};
