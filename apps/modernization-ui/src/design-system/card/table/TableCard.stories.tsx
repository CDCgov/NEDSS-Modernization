import { Meta, StoryObj } from '@storybook/react-vite';
import { MemoryRouter } from 'react-router';
import { Column } from 'design-system/table';
import { ColumnPreference } from 'design-system/table/preferences';
import { Button } from 'design-system/button';
import { TableCard } from './TableCard';

type Person = {
    id: string;
    name: string;
    age: number;
};

const meta = {
    title: 'Design System/Cards/Table Card',
    component: TableCard<Person>,
    decorators: [
        (Story: () => JSX.Element) => (
            <MemoryRouter>
                <Story />
            </MemoryRouter>
        )
    ]
} satisfies Meta<typeof TableCard<Person>>;

export default meta;

type Story = StoryObj<typeof meta>;

const columns: Column<Person>[] = [
    {
        id: 'id',
        name: 'ID',
        sortable: true,
        value: (item) => item.id,
        render: (item) => <a href={`#${item.id}`}>{item.id}</a>
    },
    {
        id: 'name',
        name: 'Name',
        sortable: true,
        sortIconType: 'alpha',
        value: (item) => item.name
    },
    {
        id: 'age',
        name: 'Age',
        sortable: true,
        sortIconType: 'numeric',
        value: (item) => item.age
    }
];

const columnPreferences: ColumnPreference[] = [
    { id: 'id', name: 'ID' },
    { id: 'name', name: 'Name', moveable: true, toggleable: true },
    { id: 'age', name: 'Age', moveable: true, toggleable: true }
];

export const Default: Story = {
    args: {
        id: 'tablecard-default',
        title: 'Sample Table Card',
        data: [
            { id: '1', name: 'John Doe', age: 28 },
            { id: '2', name: 'Alicia Smith', age: 34 },
            { id: '3', name: 'Sam Wilson', age: 23 },
            { id: '4', name: 'Cassandra Jones', age: 24 }
        ],
        columns: columns,
        columnPreferencesKey: 'storybook.tablecard',
        defaultColumnPreferences: columnPreferences
    }
};

export const Empty: Story = {
    args: {
        ...Default.args,
        data: []
    }
};

export const Actions: Story = {
    args: {
        ...Default.args,
        actions: (
            <Button sizing="small" secondary icon="add_circle">
                Add Person
            </Button>
        )
    }
};
