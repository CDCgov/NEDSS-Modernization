import { Meta, StoryObj } from '@storybook/react-vite';
import { TableCard } from './TableCard';
import { Column } from 'design-system/table';
import { Icon } from 'design-system/icon';
import { ColumnPreference } from 'design-system/table/preferences';
import { MemoryRouter } from 'react-router';
import { Button } from 'design-system/button';

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
        render: (value: Person) => <a href={`#${value.id}`}>{value.id}</a> // render link
    },
    {
        id: 'name',
        name: 'Name',
        sortable: true,
        sortIconType: 'alpha',
        render: (value: Person) => value.name
    },
    {
        id: 'age',
        name: 'Age',
        sortable: true,
        sortIconType: 'numeric',
        render: (value: Person) => value.age
    }
];

const columnIDs = columns.map((column) => ({ id: column.id, name: column.name }));

// column preferences
const columnPreferences: ColumnPreference[] = [
    { ...columnIDs[0] },
    { ...columnIDs[1], moveable: true, toggleable: true },
    { ...columnIDs[2], moveable: true, toggleable: true }
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
            <Button sizing="small" secondary icon={<Icon name="add_circle" />}>
                Add Person
            </Button>
        )
    }
};
