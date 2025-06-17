import { Meta, StoryObj } from '@storybook/react';
import { LoadingCard } from './LoadingCard';

const meta = {
    title: 'Loading/Card',
    component: LoadingCard
} satisfies Meta<typeof LoadingCard>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        id: 'card',
        title: 'Header text'
    }
};
