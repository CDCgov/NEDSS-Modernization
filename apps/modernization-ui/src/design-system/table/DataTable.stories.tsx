import { Meta, StoryObj } from '@storybook/react';
import { DataTable } from './DataTable';
import { Column } from './header/column';
import { Checkbox } from 'design-system/checkbox';

type Person = {
    id: string;
    name: string;
    email: string;
    dob: Date;
    active?: boolean;
};

const meta = {
    title: 'Design System/Table',
    component: DataTable<Person>
} satisfies Meta<typeof DataTable<Person>>;

export default meta;

type Story = StoryObj<typeof meta>;

const columns: Column<Person>[] = [
    {
        id: 'id',
        name: 'ID',
        value: (item) => item.id,
        render: (item) => <a href={`#${item.id}`}>{item.id}</a> // render link
    },
    {
        id: 'name',
        name: 'Name',
        value: (item) => item.name,
        sortIconType: 'alpha'
    },
    {
        id: 'email',
        name: 'Email',
        value: (item) => item.email
    },
    {
        id: 'dob',
        name: 'DOB',
        value: (item) => item.dob
    },
    {
        id: 'active',
        name: 'Active',
        value: (item) => item.active
    }
];
const checkboxColumns: Column<Person>[] = [
    {
        id: 'select',
        name: 'X',
        render: (item) => (
            <Checkbox
                id={item.id}
                label=" "
                onChange={(checked) => console.log('Checkbox changed', checked, item.id)}
            />
        )
    },
    ...columns
];

const data = [
    {
        id: '1001',
        name: 'Frodo Baggins',
        email: 'frodob@theshire.gov',
        dob: new Date('1990-10-11')
    },
    {
        id: '1002',
        name: 'Samwise Gamgee',
        email: 'bigwisesam@theshire.gov',
        dob: new Date('1993-04-28')
    },
    {
        id: '1003',
        name: 'Meriadoc Brandybuck',
        email: 'merry@theshire.gov',
        dob: new Date('2000-05-21'),
        active: false
    }
];

export const Default: Story = {
    args: {
        id: 'people',
        columns,
        data
    }
};

export const Checkboxes: Story = {
    args: {
        ...Default.args,
        columns: checkboxColumns
    }
};
