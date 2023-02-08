import { ComponentStory, ComponentMeta } from '@storybook/react';
import { TableComponent } from '../components/TableComponent/TableComponent';

export default {
    title: 'Components/TableComponent',
    component: TableComponent
} as ComponentMeta<typeof TableComponent>;

const Template: ComponentStory<typeof TableComponent> = (args) => <TableComponent {...args} />;

export const BasicTable = Template.bind({});
BasicTable.args = {
    tableHeader: 'Table Header',
    tableHead: [
        { name: 'Start Date', sortable: false },
        { name: 'Condition', sortable: false },
        { name: 'Case status', sortable: false },
        { name: 'Notification', sortable: false },
        { name: 'Jurisdiction', sortable: false },
        { name: 'Investigator', sortable: false },
        { name: 'Investigation #', sortable: false },
        { name: 'Co-infection #', sortable: false }
    ],
    tableBody: [
        {
            id: 1,
            checkbox: true,
            tableDetails: [
                {
                    id: 1,
                    title: '12/1/2021'
                },
                {
                    id: 2,
                    title: 'Acute flaccid myelitis'
                },
                {
                    id: 3,
                    title: null
                },
                {
                    id: 4,
                    title: null
                },
                {
                    id: 5,
                    title: 'Cobb County'
                },
                {
                    id: 6,
                    title: null
                },
                {
                    id: 7,
                    title: 'CAS10004022GA01'
                },
                {
                    id: 8,
                    title: null
                }
            ]
        },
        {
            id: 2,
            checkbox: true,
            tableDetails: [
                {
                    id: 1,
                    title: '12/2/2021'
                },
                {
                    id: 2,
                    title: 'Acute flaccid myelitis'
                },
                {
                    id: 3,
                    title: null
                },
                {
                    id: 4,
                    title: 'Completed'
                },
                {
                    id: 5,
                    title: 'Cobb County'
                },
                {
                    id: 6,
                    title: null
                },
                {
                    id: 7,
                    title: 'CAS10004022GA01'
                },
                {
                    id: 8,
                    title: null
                }
            ]
        }
    ]
};

export const TableWithPagination = Template.bind({});
TableWithPagination.args = {
    tableHeader: 'Table Header',
    tableHead: [
        { name: 'Start Date', sortable: false },
        { name: 'Condition', sortable: false },
        { name: 'Case status', sortable: false },
        { name: 'Notification', sortable: false },
        { name: 'Jurisdiction', sortable: false },
        { name: 'Investigator', sortable: false },
        { name: 'Investigation #', sortable: false },
        { name: 'Co-infection #', sortable: false }
    ],
    tableBody: [
        {
            id: 1,
            checkbox: true,
            tableDetails: [
                {
                    id: 1,
                    title: '12/1/2021'
                },
                {
                    id: 2,
                    title: 'Acute flaccid myelitis'
                },
                {
                    id: 3,
                    title: null
                },
                {
                    id: 4,
                    title: null
                },
                {
                    id: 5,
                    title: 'Cobb County'
                },
                {
                    id: 6,
                    title: null
                },
                {
                    id: 7,
                    title: 'CAS10004022GA01'
                },
                {
                    id: 8,
                    title: null
                }
            ]
        },
        {
            id: 2,
            checkbox: true,
            tableDetails: [
                {
                    id: 1,
                    title: '12/2/2021'
                },
                {
                    id: 2,
                    title: 'Acute flaccid myelitis'
                },
                {
                    id: 3,
                    title: null
                },
                {
                    id: 4,
                    title: 'Completed'
                },
                {
                    id: 5,
                    title: 'Cobb County'
                },
                {
                    id: 6,
                    title: null
                },
                {
                    id: 7,
                    title: 'CAS10004022GA01'
                },
                {
                    id: 8,
                    title: null
                }
            ]
        }
    ],
    isPagination: true
};

export const TableWithSorting = Template.bind({});
TableWithSorting.args = {
    tableHeader: 'Table Header',
    tableHead: [
        { name: 'Start Date', sortable: true },
        { name: 'Condition', sortable: true },
        { name: 'Case status', sortable: true },
        { name: 'Notification', sortable: true },
        { name: 'Jurisdiction', sortable: true },
        { name: 'Investigator', sortable: true },
        { name: 'Investigation #', sortable: true },
        { name: 'Co-infection #', sortable: true }
    ],
    tableBody: [
        {
            id: 1,
            checkbox: true,
            tableDetails: [
                {
                    id: 1,
                    title: '12/1/2021'
                },
                {
                    id: 2,
                    title: 'Acute flaccid myelitis'
                },
                {
                    id: 3,
                    title: null
                },
                {
                    id: 4,
                    title: null
                },
                {
                    id: 5,
                    title: 'Cobb County'
                },
                {
                    id: 6,
                    title: null
                },
                {
                    id: 7,
                    title: 'CAS10004022GA01'
                },
                {
                    id: 8,
                    title: null
                }
            ]
        },
        {
            id: 2,
            checkbox: true,
            tableDetails: [
                {
                    id: 1,
                    title: '12/2/2021'
                },
                {
                    id: 2,
                    title: 'Acute flaccid myelitis'
                },
                {
                    id: 3,
                    title: null
                },
                {
                    id: 4,
                    title: 'Completed'
                },
                {
                    id: 5,
                    title: 'Cobb County'
                },
                {
                    id: 6,
                    title: null
                },
                {
                    id: 7,
                    title: 'CAS10004022GA01'
                },
                {
                    id: 8,
                    title: null
                }
            ]
        }
    ]
};

export const TableWithSetCurrentPage = Template.bind({});
TableWithSetCurrentPage.args = {
    tableHeader: 'Table Header',
    tableHead: [
        { name: 'Start Date', sortable: true },
        { name: 'Condition', sortable: true },
        { name: 'Case status', sortable: true },
        { name: 'Notification', sortable: true },
        { name: 'Jurisdiction', sortable: true },
        { name: 'Investigator', sortable: true },
        { name: 'Investigation #', sortable: true },
        { name: 'Co-infection #', sortable: true }
    ],
    tableBody: [
        {
            id: 1,
            checkbox: true,
            tableDetails: [
                {
                    id: 1,
                    title: '12/1/2021'
                },
                {
                    id: 2,
                    title: 'Acute flaccid myelitis'
                },
                {
                    id: 3,
                    title: null
                },
                {
                    id: 4,
                    title: null
                },
                {
                    id: 5,
                    title: 'Cobb County'
                },
                {
                    id: 6,
                    title: null
                },
                {
                    id: 7,
                    title: 'CAS10004022GA01'
                },
                {
                    id: 8,
                    title: null
                }
            ]
        },
        {
            id: 2,
            checkbox: true,
            tableDetails: [
                {
                    id: 1,
                    title: '12/2/2021'
                },
                {
                    id: 2,
                    title: 'Acute flaccid myelitis'
                },
                {
                    id: 3,
                    title: null
                },
                {
                    id: 4,
                    title: 'Completed'
                },
                {
                    id: 5,
                    title: 'Cobb County'
                },
                {
                    id: 6,
                    title: null
                },
                {
                    id: 7,
                    title: 'CAS10004022GA01'
                },
                {
                    id: 8,
                    title: null
                }
            ]
        }
    ],
    isPagination: true,
    currentPage: 2
};
