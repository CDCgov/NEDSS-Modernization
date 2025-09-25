import { Meta, StoryObj } from '@storybook/react-vite';
import { CollapsiblePanel } from './CollapsiblePanel';

const meta = {
    title: 'Design System/CollapsiblePanel',
    component: CollapsiblePanel
} satisfies Meta<typeof CollapsiblePanel>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        id: 'collapsiblepanel',
        children: 'This is content for the collapsible panel. This is good content. Thank you for reading.'
    }
};
