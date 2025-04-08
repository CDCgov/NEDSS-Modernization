import { Meta, StoryObj } from '@storybook/react';
import { LoadingPanel } from './LoadingPanel';

const meta = {
    title: 'Design System/LoadingPanel',
    component: LoadingPanel
} satisfies Meta<typeof LoadingPanel>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        loading: true,
        children: <div>Content goes here</div>
    }
};
export const WithoutLoading: Story = {
    args: {
        loading: false,
        children: <div>Content goes here</div>
    }
};
