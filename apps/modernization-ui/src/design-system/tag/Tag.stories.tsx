import { Meta, StoryObj } from '@storybook/react-vite';
import { Tag, TagProps } from './Tag';
import React from 'react';
import { DataTable } from 'design-system/table';

const meta = {
    title: 'Design System/Tag',
    component: Tag
} satisfies Meta<typeof Tag>;

export default meta;

type Data = {
    id: number;
    name: string;
    status: string;
    notification: string | null;
};
type Column = {
    id: keyof Data;
    name: string;
    render: (value: Data) => React.ReactNode | string;
};

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

const data: Data[] = [
    { id: 1, name: 'Item 1', status: 'Open', notification: 'Rejected' },
    { id: 2, name: 'Item 2', status: 'Closed', notification: null },
    { id: 3, name: 'Item 3', status: 'Open', notification: 'Rejected' }
];

const columns: Column[] = [
    { name: 'ID', id: 'id', render: (row) => row.id.toString() },
    { name: 'Name', id: 'name', render: (row) => row.name },
    {
        name: 'Case status',
        id: 'status',
        render: (row) =>
            row.status === 'Open' ? (
                <Tag variant="success" size="small">
                    Open
                </Tag>
            ) : (
                row.status
            )
    },
    {
        id: 'notification',
        name: 'Notification',
        render: (row) =>
            row.notification === 'Rejected' ? (
                <Tag variant="error" size="small">
                    Rejected
                </Tag>
            ) : (
                row.notification
            )
    }
];

export const TagInTableColumn: Story = {
    args: {
        ...Default.args
    },
    render: () => {
        return (
            <div style={{ padding: '1rem' }}>
                <DataTable<Data> id={'tag-table'} columns={columns} data={data} />
            </div>
        );
    }
};
