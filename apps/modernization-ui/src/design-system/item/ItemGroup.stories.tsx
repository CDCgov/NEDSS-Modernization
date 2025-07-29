import type { Meta, StoryObj } from '@storybook/react-vite';

import { ItemGroup } from './ItemGroup';

const meta = {
    title: 'Design System/ItemGroup',
    component: ItemGroup
} satisfies Meta<typeof ItemGroup>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Address: Story = {
    args: {
        type: 'address',
        label: 'Home',
        children: '123 Happy St\nSeattle, WA 98101'
    }
};

export const Name: Story = {
    args: {
        type: 'name',
        label: 'Alias',
        children: 'John Doe'
    }
};
