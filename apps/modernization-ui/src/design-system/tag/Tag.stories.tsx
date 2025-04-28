import { Meta, StoryObj } from '@storybook/react';
import { Tag, TagProps } from './Tag';
import React from 'react';

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

export const TagInTableColumn: Story = {
    args: {
        ...Default.args
    },
    render: () => {
        const data = [
            { id: 1, name: 'Item 1', status: 'Open' },
            { id: 2, name: 'Item 2', status: 'Closed' },
            { id: 3, name: 'Item 3', status: 'Open' }
        ];

        type ColumnDef = {
            header: string;
            accessor: keyof (typeof data)[0];
            cell?: (value: any) => React.ReactNode;
        };

        const columns: ColumnDef[] = [
            { header: 'ID', accessor: 'id' },
            { header: 'Name', accessor: 'name' },
            {
                header: 'Status',
                accessor: 'status',
                cell: (value: any) => {
                    if (value === 'Open') {
                        return (
                            <Tag variant="success" size="small">
                                Open
                            </Tag>
                        );
                    } else {
                        return <span style={{ color: 'var(--base-dark)', fontSize: '0.75rem' }}>Closed</span>;
                    }
                }
            }
        ];

        return (
            <div style={{ border: '1px solid #eee', padding: '1rem' }}>
                <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                    <thead>
                        <tr>
                            {columns.map((col) => (
                                <th
                                    key={col.accessor}
                                    style={{ textAlign: 'left', padding: '8px', borderBottom: '1px solid #eee' }}>
                                    {col.header}
                                </th>
                            ))}
                        </tr>
                    </thead>
                    <tbody>
                        {data.map((row) => (
                            <tr key={row.id}>
                                {columns.map((col) => (
                                    <td
                                        key={`${row.id}-${col.accessor}`}
                                        style={{ padding: '8px', borderBottom: '1px solid #eee' }}>
                                        {col.cell ? col.cell(row[col.accessor]) : row[col.accessor]}
                                    </td>
                                ))}
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        );
    }
};
