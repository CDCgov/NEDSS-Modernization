import { Meta, StoryObj } from '@storybook/react';
import { TableCard } from './TableCard';
import { Column } from 'design-system/table';
import { Icon } from 'design-system/icon';

type Person = {
    id: string;
    name: string;
    age: number;
};

const meta = {
    title: 'Design System/Cards/Table Card',
    component: TableCard<Person>
} satisfies Meta<typeof TableCard<Person>>;

export default meta;

type Story = StoryObj<typeof meta>;

const columns: Column<Person>[] = [
    {
        id: 'id',
        name: 'ID',
        render: (value: Person) => <a href={`#${value.id}`}>{value.id}</a> // render link
    },
    {
        id: 'name',
        name: 'Name',
        render: (value: Person) => value.name
    },
    {
        id: 'age',
        name: 'Age',
        render: (value: Person) => value.age
    }
];

export const Default: Story = {
    args: {
        id: 'tablecard-default',
        title: 'Sample Table Card',
        data: [
            { id: '1', name: 'John Doe', age: 28 },
            { id: '2', name: 'Jane Smith', age: 34 },
            { id: '3', name: 'Sam Wilson', age: 23 }
        ],
        columns: columns
    }
};

export const Actions: Story = {
    args: {
        ...Default.args,
        id: 'tablecard-actions',
        actions: [
            {
                sizing: 'small',
                secondary: true,
                children: 'Add Person',
                icon: <Icon name="add_circle" />,
                labelPosition: 'right',
                onClick: () => console.log('Add Person clicked')
            }
        ]
    }
};
