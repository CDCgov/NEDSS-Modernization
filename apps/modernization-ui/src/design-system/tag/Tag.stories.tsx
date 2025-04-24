import { Meta, StoryObj } from '@storybook/react';
import { Tag, TagProps } from './Tag';

const meta = {
    title: 'Design System/Tag',
    component: Tag
} satisfies Meta<typeof Tag>;

export default meta;

type Story = StoryObj<typeof meta>;

const renderTags = (args: TagProps) => (
    <div style={{ display: 'flex', gap: '1rem', alignItems: 'center' }}>
        <Tag size="small" {...args} />
        <Tag size="medium" {...args} />
        <Tag size="large" {...args} />
    </div>
);

export const Default: Story = {
    args: {
        children: '10'
    },
    render: renderTags
};

export const Success: Story = {
    args: {
        ...Default.args,
        variant: 'success'
    },
    render: renderTags
};

export const Warning: Story = {
    args: {
        ...Default.args,
        variant: 'warning'
    },
    render: renderTags
};

export const ErrorVariant: Story = {
    name: 'error',
    args: {
        ...Default.args,
        variant: 'error'
    },
    render: renderTags
};

export const Info: Story = {
    args: {
        ...Default.args,
        variant: 'info'
    },
    render: renderTags
};

export const Gray: Story = {
    args: {
        ...Default.args,
        variant: 'gray'
    },
    render: renderTags
};
