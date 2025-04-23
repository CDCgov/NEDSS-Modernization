import { Meta, StoryObj } from '@storybook/react';
import { Tag } from './Tag';

const meta = {
    title: 'Design System/Tag',
    component: Tag
} satisfies Meta<typeof Tag>;

export default meta;

type Story = StoryObj<typeof meta>;

const renderTag = (args: any) => <Tag {...args} />;

const renderTags = (args: any) => (
    <div style={{ display: 'flex', gap: '1rem', alignItems: 'center' }}>
        <Tag {...args} size="small" weight="bold" />
        <Tag {...args} size="medium" weight="regular" />
        <Tag {...args} size="large" weight="bold" />
    </div>
);

export const Default: Story = {
    args: {
        children: '10',
        variant: 'default',
        size: 'medium',
        weight: 'bold'
    },
    render: renderTag
};

export const DefaultTags: Story = {
    args: {
        children: '10',
        variant: 'default'
    },
    render: renderTags
};

export const Success: Story = {
    args: {
        children: '10',
        variant: 'success'
    },
    render: renderTags
};

export const Warning: Story = {
    args: {
        children: '10',
        variant: 'warning'
    },
    render: renderTags
};

export const Error: Story = {
    args: {
        children: '10',
        variant: 'error'
    },
    render: renderTags
};

export const Info: Story = {
    args: {
        children: '10',
        variant: 'info'
    },
    render: renderTags
};

export const Gray: Story = {
    args: {
        children: '10',
        variant: 'gray'
    },
    render: renderTags
};
