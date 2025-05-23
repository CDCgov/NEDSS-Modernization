import { Meta, StoryObj } from '@storybook/react';
import { Card } from './Card';

const meta = {
    title: 'Design System/Cards/Card',
    component: Card
} satisfies Meta<typeof Card>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        id: 'card-default',
        title: 'The Card',
        info: 'Info',
        level: 1,
        children: <p>This is the card text</p>
    }
};
