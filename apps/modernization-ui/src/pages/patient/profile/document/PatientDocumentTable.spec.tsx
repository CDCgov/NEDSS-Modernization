import { PageProvider } from 'page';
import { PatientDocumentTable } from './PatientDocumentTable';
import { render } from '@testing-library/react';

describe('when rendered', () => {
    it('should display sentence cased document headers', async () => {
        const { container } = render(
            <PageProvider>
                <PatientDocumentTable patient={'patient'} documents={[]}></PatientDocumentTable>
            </PageProvider>
        );

        const tableHeader = container.getElementsByClassName('table-header');
        expect(tableHeader[0].innerHTML).toBe('Documents');

        const tableHeads = container.getElementsByClassName('head-name');

        expect(tableHeads[0].innerHTML).toBe('Date received');
        expect(tableHeads[1].innerHTML).toBe('Type');
        expect(tableHeads[2].innerHTML).toBe('Sending facility');
        expect(tableHeads[3].innerHTML).toBe('Date reported');
        expect(tableHeads[4].innerHTML).toBe('Condition');
        expect(tableHeads[5].innerHTML).toBe('Associated with');
        expect(tableHeads[6].innerHTML).toBe('Event ID');
    });
});

describe('when documents are No Data for a patient', () => {
    it('should display No Data', async () => {
        const { findByText } = render(
            <PageProvider>
                <PatientDocumentTable patient={'patient'} documents={[]}></PatientDocumentTable>
            </PageProvider>
        );

        expect(await findByText('No Data')).toBeInTheDocument();
    });
});

describe('when at least one document is available for a patient', () => {
    const documents = [
        {
            document: 'document-id',
            receivedOn: new Date('2021-10-07T15:01:10Z'),
            type: 'document-type-value',
            sendingFacility: 'sending-facility-value',
            reportedOn: new Date('2021-09-21T17:04:11Z'),
            condition: 'condition-value',
            event: 'event-value',
            associatedWith: {
                id: 'associated-id',
                local: 'associated-local-value',
                condition: 'associated-condition-value'
            }
        }
    ];

    it('should display the documents', async () => {
        const { container, findByText } = render(
            <PageProvider>
                <PatientDocumentTable patient={'patient'} documents={documents}></PatientDocumentTable>
            </PageProvider>
        );

        const tableData = container.getElementsByClassName('table-data');

        const dateCreated = await findByText(/10\/07\/2021/);

        expect(dateCreated).toHaveTextContent('10:01 AM');

        expect(tableData[0]).toContainElement(dateCreated);

        expect(tableData[1]).toHaveTextContent('document-type-value');
        expect(tableData[2]).toHaveTextContent('sending-facility-value');
        expect(tableData[3]).toHaveTextContent('09/21/2021');
        expect(tableData[4]).toHaveTextContent('condition-value');

        //  Associated With
        const association = await findByText('associated-local-value');

        expect(tableData[5]).toContainElement(association);

        expect(tableData[6]).toHaveTextContent('event-value');
    });
});
