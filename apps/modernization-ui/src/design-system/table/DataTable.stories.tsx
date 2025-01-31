import { Meta, StoryObj } from '@storybook/react';
import { Column, DataTable } from './DataTable';

type Person = {
    id: string;
    name: string;
    email: string;
};

const meta = {
    title: 'Design System/DataTable',
    component: DataTable<Person>
} satisfies Meta<typeof DataTable<Person>>;

export default meta;

type Story = StoryObj<typeof meta>;

// const options: Selectable[] = [
//     asSelectable('apple', 'Apple'),
//     asSelectable('banana', 'Banana'),
//     asSelectable('mango', 'Mango'),
//     asSelectable('orange', 'Orange'),
//     asSelectable('watermelon', 'Watermelon')
// ];
// const [, banana, mango] = options;

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
        id: 'email',
        name: 'Email',
        render: (value: Person) => value.email
    }
];

const data: Person[] = [
    {
        id: '1001',
        name: 'Frodo Baggins',
        email: 'frodob@theshire.gov'
    },
    {
        id: '1002',
        name: 'Samwise Gamgee',
        email: 'bigwisesam@theshire.gov'
    },
    {
        id: '1003',
        name: 'Meriadoc Brandybuck',
        email: 'merry@theshire.gov'
    }
];

export const Default: Story = {
    args: {
        id: 'people',
        columns: columns as Column<Person>[],
        data: data as Person[]
    }
};
