import { MemoryRouter } from 'react-router';
import { Meta, StoryObj } from '@storybook/react/*';
import { TabNavigation, TabNavigationEntry, TabNavigationProps } from './TabNavigation';

const meta = {
    title: 'Design System/TabNavigation',
    component: TabNavigation
} satisfies Meta<typeof TabNavigation>;

export default meta;

type Story = StoryObj<typeof meta>;

const renderTabs = (args: TabNavigationProps) => (
    <MemoryRouter>
        <TabNavigation {...args}>
            <TabNavigationEntry path={`test1`}>Tab 1</TabNavigationEntry>
            <TabNavigationEntry path={`test2`}>Tab 2</TabNavigationEntry>
            <TabNavigationEntry path={`test3`}>Tab 3</TabNavigationEntry>
        </TabNavigation>
    </MemoryRouter>
);

export const Default: Story = {
    args: {
        sizing: 'large'
    },
    render: renderTabs
};
