import { PatientDocumentTable } from './PatientDocumentTable';
import { render } from '@testing-library/react';
import { WithinTableProvider } from 'components/Table/testing';

describe('when rendered', () => {
    it('should display sentence cased document headers', async () => {
        const { getByRole, getAllByRole } = render(
            <WithinTableProvider>
                <PatientDocumentTable patient={'patient'} documents={[]}></PatientDocumentTable>
            </WithinTableProvider>
        );

        expect(getByRole('heading', { name: 'Documents' })).toBeInTheDocument();

        const tableHeads = getAllByRole('columnheader');

        expect(tableHeads[0]).toHaveTextContent('Date received');
        expect(tableHeads[1]).toHaveTextContent('Type');
        expect(tableHeads[2]).toHaveTextContent('Sending facility');
        expect(tableHeads[3]).toHaveTextContent('Date reported');
        expect(tableHeads[4]).toHaveTextContent('Condition');
        expect(tableHeads[5]).toHaveTextContent('Associated with');
        expect(tableHeads[6]).toHaveTextContent('Event ID');
    });
});

describe('when documents are No Data for a patient', () => {
    it('should display No Data', async () => {
        const { findByText } = render(
            <WithinTableProvider>
                <PatientDocumentTable patient={'patient'} documents={[]}></PatientDocumentTable>
            </WithinTableProvider>
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
        const { findAllByRole, findByText } = render(
            <WithinTableProvider>
                <PatientDocumentTable patient={'patient'} documents={documents}></PatientDocumentTable>
            </WithinTableProvider>
        );

        const tableData = await findAllByRole('cell');

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
