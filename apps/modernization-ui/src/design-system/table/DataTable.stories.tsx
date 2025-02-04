import { Meta, StoryObj } from '@storybook/react';
import { Column, DataTable } from './DataTable';
import { MemoryRouter } from 'react-router-dom';
import { SortingProvider, useSorting } from 'sorting';
import { ComponentType } from 'react';
import { Checkbox } from 'design-system/checkbox';

type AnyObject = { [key: string]: any };
type AnyObjectWithData = AnyObject & { data?: AnyObject[] };
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
const checkboxColumns: Column<Person>[] = [
    {
        id: 'select',
        name: 'X',
        render: (value: Person) => (
            <Checkbox
                id={value.id}
                label=" "
                onChange={(checked) => console.log('Checkbox changed', checked, value.id)}
            />
        )
    } as Column<Person>,
    ...columns
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

function sortData<V extends AnyObject>(data: V[], property?: string, direction?: string) {
    return data.sort((a: V, b: V) => {
        if (property) {
            if (a[property] < b[property]) {
                return direction === 'desc' ? 1 : -1;
            }
            if (a[property] > b[property]) {
                return direction === 'desc' ? -1 : 1;
            }
        }
        return 0;
    });
}
// , W extends DataTableProps<V>
const withSortable = <W extends AnyObjectWithData>(Component: ComponentType<W>) => {
    const SortableComponent = (props: W) => {
        const { property, direction } = useSorting();
        console.log('SortableComponent', property, direction, props);
        const sortedData = sortData(props.data!, property, direction);
        return <Component {...props} data={sortedData} />;
    };
    SortableComponent.displayName = 'SortableComponent';
    return SortableComponent;
};
// const SortableDataTable = withSortable(DataTable);

export const Default: Story = {
    args: {
        id: 'people',
        columns: columns as Column<Person>[],
        data: data as Person[]
    }
};

export const Sortable: Story = {
    decorators: [
        (Story) => {
            const SortableDataTable = withSortable(Story);
            return (
                <MemoryRouter>
                    <SortingProvider>
                        <SortableDataTable data={data} />
                    </SortingProvider>
                </MemoryRouter>
            );
        }
    ],
    args: {
        id: 'people',
        columns: columns.map((column) => ({ ...column, sortable: true })) as Column<Person>[], // make all columns sortable
        data: data as Person[]
    }
};

export const Checkboxes: Story = {
    args: {
        id: 'people',
        columns: checkboxColumns as Column<Person>[],
        data: data as Person[]
    }
};
