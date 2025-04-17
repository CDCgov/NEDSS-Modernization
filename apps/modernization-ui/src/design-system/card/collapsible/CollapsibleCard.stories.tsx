import { Meta, StoryObj } from '@storybook/react';
import { CollapsibleCard } from './CollapsibleCard';

const meta = {
    title: 'Design System/Cards/Collapsible Card',
    component: CollapsibleCard
} satisfies Meta<typeof CollapsibleCard>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        id: 'card-default',
        header: <h2 style={{ margin: '0' }}>Card Header</h2>,
        children: (
            <p style={{ padding: '1rem' }}>
                This is a long sentence with multiple line breaks. <br />
                Hello
                <br />
                World
            </p>
        )
    }
};
