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
        id: 'card',
        title: 'Header text',
        info: 'Info',
        level: 2,
        children: <p>This is the card text</p>
    }
};

export const Subtext: Story = {
    args: {
        ...Default.args,
        subtext: 'subtext'
    }
};
