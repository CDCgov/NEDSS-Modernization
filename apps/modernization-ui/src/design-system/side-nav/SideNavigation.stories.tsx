import { Meta, StoryObj } from '@storybook/react';
import { SideNavigation } from './SideNavigation';

const meta = {
    title: 'Design System/SideNavigation',
    component: SideNavigation
} satisfies Meta<typeof SideNavigation>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        title: 'Side Navigation',
        children: <div>This is a side navigation</div>
    }
};
