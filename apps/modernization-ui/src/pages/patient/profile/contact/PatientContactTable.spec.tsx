import { render } from '@testing-library/react';
import { PageProvider } from 'page';
import { Headers } from './PatientContacts';
import { PatientContactTable } from './PatientContactTable';
import { MemoryRouter } from 'react-router-dom';
import { ClassicModalProvider } from 'classic/ClassicModalContext';

const headings = [
    { name: Headers.DateCreated, sortable: true },
    { name: Headers.ContactsNamed, sortable: true },
    { name: Headers.DateNamed, sortable: true },
    { name: Headers.Description, sortable: true },
    { name: Headers.AssociatedWith, sortable: true },
    { name: Headers.Event, sortable: true }
];

describe('when rendered', () => {
    it('should display sentence cased contact headers', async () => {
        const { container } = render(
            <ClassicModalProvider>
                <PageProvider>
                    <PatientContactTable
                        patient={'patient'}
                        tracings={[]}
                        title="title-value"
                        headings={[
                            { name: Headers.DateCreated, sortable: true },
                            { name: Headers.ContactsNamed, sortable: true },
                            { name: Headers.NamedBy, sortable: true },
                            { name: Headers.DateNamed, sortable: true },
                            { name: Headers.Description, sortable: true },
                            { name: Headers.AssociatedWith, sortable: true },
                            { name: Headers.Event, sortable: true }
                        ]}></PatientContactTable>
                </PageProvider>
            </ClassicModalProvider>
        );

        const tableHeader = container.getElementsByClassName('table-header');
        expect(tableHeader[0].innerHTML).toBe('title-value');

        const tableHeads = container.getElementsByClassName('head-name');

        expect(tableHeads[0].innerHTML).toBe('Date created');
        expect(tableHeads[1].innerHTML).toBe('Contacts named');
        expect(tableHeads[2].innerHTML).toBe('Named by');
        expect(tableHeads[3].innerHTML).toBe('Date named');
        expect(tableHeads[4].innerHTML).toBe('Description');
        expect(tableHeads[5].innerHTML).toBe('Associated with');
        expect(tableHeads[6].innerHTML).toBe('Event #');
    });
});

describe('when contacts are No data for a patient', () => {
    it('should display No data', async () => {
        const { findByText } = render(
            <ClassicModalProvider>
                <PageProvider>
                    <PatientContactTable
                        patient={'patient'}
                        tracings={[]}
                        title="title-value"
                        headings={headings}></PatientContactTable>
                </PageProvider>
            </ClassicModalProvider>
        );

        expect(await findByText('No data')).toBeInTheDocument();
    });
});

describe('when at least one contact is available for a patient', () => {
    const tracings = [
        {
            contactRecord: '10055380',
            createdOn: new Date('2023-03-17T20:08:45.213Z'),
            contact: { id: '10000008', name: 'Contact Name' },
            namedOn: new Date('2023-01-17T05:00:00Z'),
            condition: {
                id: 'condition-id',
                description: 'condition-description'
            },
            priority: 'priority-value',
            disposition: 'disposition-value',
            event: 'event-value',
            associatedWith: {
                id: 'association-id',
                local: 'association-local',
                condition: 'associated-condition'
            }
        }
    ];

    it('should display the contact', async () => {
        const { container, findByText } = render(
            <MemoryRouter>
                <ClassicModalProvider>
                    <PageProvider>
                        <PatientContactTable
                            patient={'patient'}
                            tracings={tracings}
                            title="title-value"
                            headings={headings}></PatientContactTable>
                    </PageProvider>
                </ClassicModalProvider>
            </MemoryRouter>
        );

        const tableData = container.getElementsByClassName('table-data');

        const dateCreated = await findByText(/03\/17\/2023/);

        expect(dateCreated).toHaveTextContent('03:08 PM');

        expect(tableData[0]).toContainElement(dateCreated);
        expect(tableData[1]).toHaveTextContent('Contact Name');
        expect(tableData[2]).toHaveTextContent('01/17/2023');

        //  Description fields
        const conditionValue = await findByText(/condition-description/);
        expect(tableData[3]).toContainElement(conditionValue);

        const dispositionLabel = await findByText(/Disposition:/);
        expect(tableData[3]).toContainElement(dispositionLabel);

        const dispositionValue = await findByText(/disposition-value/);
        expect(tableData[3]).toContainElement(dispositionValue);

        const priorityLabel = await findByText(/Priority:/);
        expect(tableData[3]).toContainElement(priorityLabel);

        const priorityValue = await findByText(/priority-value/);
        expect(tableData[3]).toContainElement(priorityValue);

        //  Associated With
        const association = await findByText('association-local');

        expect(tableData[4]).toContainElement(association);
        const associationCondition = await findByText('associated-condition');

        expect(tableData[4]).toContainElement(associationCondition);

        expect(tableData[5]).toHaveTextContent('event-value');
    });
});
