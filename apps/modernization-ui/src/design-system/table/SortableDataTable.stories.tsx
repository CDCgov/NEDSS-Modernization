import { Meta, StoryObj } from '@storybook/react-vite';
import { MemoryRouter } from 'react-router';
import { Column } from './header/column';
import { SortableDataTable } from './SortableDataTable';

type Person = {
    id: string;
    name: string;
    email?: string;
    dob: Date;
};

const meta = {
    title: 'Design System/Table/Sortable',
    component: SortableDataTable<Person>
} satisfies Meta<typeof SortableDataTable<Person>>;

export default meta;

type Story = StoryObj<typeof meta>;

const columns: Column<Person>[] = [
    {
        id: 'id',
        name: 'ID',
        sortable: true,
        value: (value: Person) => value.id,
        render: (value: Person) => <a href={`#${value.id}`}>{value.id}</a> // render as link
    },
    {
        id: 'name',
        name: 'Name',
        sortable: true,
        value: (value: Person) => value.name,
        sortIconType: 'alpha'
    },
    {
        id: 'email',
        name: 'Email',
        sortable: true,
        value: (value: Person) => value.email
    },
    {
        id: 'dob',
        name: 'DOB',
        sortable: true,
        value: (value: Person) => value.dob
    }
];

const data = [
    {
        id: '1002',
        name: 'Samwise Gamgee',
        dob: new Date('1993-04-28')
    },
    {
        id: 'A257',
        name: 'Frodo Baggins',
        email: 'frodob@theshire.gov',
        dob: new Date('1990-10-11')
    },
    {
        id: 'A107',
        name: 'Meriadoc Brandybuck',
        email: 'merry@theshire.gov',
        dob: new Date('2000-05-21')
    }
];

export const Default: Story = {
    decorators: [(story) => <MemoryRouter>{story()}</MemoryRouter>],
    args: {
        id: '',
        // make all columns sortable
        columns: columns.map((column) => ({ ...column, sortable: true })),
        data
    }
};
