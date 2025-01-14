import { DocumentsRequiringReviewTable } from './DocumentRequiringReviewTable';
import { render } from '@testing-library/react';
import { WithinTableProvider } from 'components/Table/testing';

describe('when rendered', () => {
    it('should display sentence cased document headers', async () => {
        const { getByRole, getAllByRole } = render(
            <WithinTableProvider>
                <DocumentsRequiringReviewTable
                    patient={'patient'}
                    documents={[]}></DocumentsRequiringReviewTable>
            </WithinTableProvider>
        );

        expect(getByRole('heading', { name: 'Documents requiring review' })).toBeInTheDocument();

        const tableHeads = getAllByRole('columnheader');
        expect(tableHeads[0]).toHaveTextContent('Document type');
        expect(tableHeads[1]).toHaveTextContent('Date received');
        expect(tableHeads[2]).toHaveTextContent('Reporting facility / provider');
        expect(tableHeads[3]).toHaveTextContent('Event date');
        expect(tableHeads[4]).toHaveTextContent('Description');
        expect(tableHeads[5]).toHaveTextContent('Event #');
    });
});

describe('when no documents are available for a patient', () => {
    it('should display No Data', async () => {
        const { findByText } = render(
            <WithinTableProvider>
                <DocumentsRequiringReviewTable
                    patient={'patient'}
                    documents={[]}></DocumentsRequiringReviewTable>
            </WithinTableProvider>
        );

        expect(await findByText('No Data')).toBeInTheDocument();
    });
});

describe('when at least one document is available for a patient', () => {
    it('should not display an indicator for manual documents', async () => {
        const documents = [
            {
                id: '197',
                localId: 'local-value',
                dateReceived: new Date('2021-10-07T15:01:10Z'),
                type: 'Test Document',
                eventDate: undefined,
                isElectronic: false,
                descriptions: [],
                facilityProviders: {}
            }
        ];

        const { queryByText, getAllByRole } = render(
            <WithinTableProvider>
                <DocumentsRequiringReviewTable
                    patient={'patient'}
                    documents={documents}></DocumentsRequiringReviewTable>
            </WithinTableProvider>
        );

        const tableData = getAllByRole('cell');

        const electronic = await queryByText('(Electronic)');

        expect(tableData[0]).not.toContainElement(electronic);
    });

    it('should display an indicator for electronic documents', () => {
        const documents = [
            {
                id: '197',
                localId: 'local-value',
                dateReceived: new Date('2021-10-07T15:01:10Z'),
                type: 'Test Document',
                eventDate: undefined,
                isElectronic: true,
                descriptions: [],
                facilityProviders: {}
            }
        ];

        const { getByText, getAllByRole } = render(
            <WithinTableProvider>
                <DocumentsRequiringReviewTable
                    patient={'patient'}
                    documents={documents}></DocumentsRequiringReviewTable>
            </WithinTableProvider>
        );

        const tableData = getAllByRole('cell');

        const electronic = getByText('(Electronic)');

        expect(tableData[0]).toContainElement(electronic);
    });

    it('should display the a laboratory report', () => {
        const documents = [
            {
                id: '1234',
                localId: 'someLocalId',
                dateReceived: new Date('2021-10-07T15:01:10Z'),
                type: 'Laboratory Report',
                eventDate: undefined,
                isElectronic: false,
                descriptions: [],
                facilityProviders: {}
            }
        ];

        const { getByRole, getAllByRole } = render(
            <WithinTableProvider>
                <DocumentsRequiringReviewTable
                    patient={'patient'}
                    documents={documents}></DocumentsRequiringReviewTable>
            </WithinTableProvider>
        );

        const tableData = getAllByRole('cell');

        const link = getByRole('link', { name: 'Laboratory Report' });

        expect(tableData[0]).toContainElement(link);
    });

    it('should display the a morbidity report', () => {
        const documents = [
            {
                id: '1234',
                localId: 'someLocalId',
                dateReceived: new Date('2021-10-07T15:01:10Z'),
                type: 'Morbidity Report',
                eventDate: undefined,
                isElectronic: false,
                descriptions: [],
                facilityProviders: {}
            }
        ];

        const { getByRole, getAllByRole } = render(
            <WithinTableProvider>
                <DocumentsRequiringReviewTable
                    patient={'patient'}
                    documents={documents}></DocumentsRequiringReviewTable>
            </WithinTableProvider>
        );

        const tableData = getAllByRole('cell');

        const link = getByRole('link', { name: 'Morbidity Report' });

        expect(tableData[0]).toContainElement(link);
    });

    it('should display the a case report', () => {
        const documents = [
            {
                id: '1234',
                localId: 'someLocalId',
                dateReceived: new Date('2021-10-07T15:01:10Z'),
                type: 'Case Report',
                eventDate: undefined,
                isElectronic: false,
                descriptions: [],
                facilityProviders: {}
            }
        ];

        const { getByRole, getAllByRole } = render(
            <WithinTableProvider>
                <DocumentsRequiringReviewTable
                    patient={'patient'}
                    documents={documents}></DocumentsRequiringReviewTable>
            </WithinTableProvider>
        );

        const tableData = getAllByRole('cell');

        const link = getByRole('link', { name: 'Case Report' });

        expect(tableData[0]).toContainElement(link);
    });

    it('should display the Date received', () => {
        const documents = [
            {
                id: '1234',
                localId: 'someLocalId',
                dateReceived: new Date('2021-10-07T15:01:10Z'),
                type: 'Test Report',
                eventDate: undefined,
                isElectronic: false,
                descriptions: [],
                facilityProviders: {}
            }
        ];

        const { getByText, getAllByRole } = render(
            <WithinTableProvider>
                <DocumentsRequiringReviewTable
                    patient={'patient'}
                    documents={documents}></DocumentsRequiringReviewTable>
            </WithinTableProvider>
        );

        const tableData = getAllByRole('cell');

        const dateReceived = getByText(/10\/07\/2021/);

        expect(tableData[1]).toContainElement(dateReceived);
    });

    it('should display the sending facility', () => {
        const documents = [
            {
                id: '1234',
                localId: 'someLocalId',
                dateReceived: new Date('2021-10-07T15:01:10Z'),
                type: 'Laboratory Report',
                eventDate: undefined,
                isElectronic: true,
                descriptions: [],
                facilityProviders: {
                    sendingFacility: { name: 'sending facility name' }
                }
            }
        ];

        const { getByText, getAllByRole } = render(
            <WithinTableProvider>
                <DocumentsRequiringReviewTable
                    patient={'patient'}
                    documents={documents}></DocumentsRequiringReviewTable>
            </WithinTableProvider>
        );

        const tableData = getAllByRole('cell');

        const title = getByText('Sending facility');
        const value = getByText('sending facility name');

        expect(tableData[2]).toContainElement(title);
        expect(tableData[2]).toContainElement(value);
    });

    it('should display the reporting facility', () => {
        const documents = [
            {
                id: '1234',
                localId: 'someLocalId',
                dateReceived: new Date('2021-10-07T15:01:10Z'),
                type: 'Laboratory Report',
                eventDate: undefined,
                isElectronic: true,
                descriptions: [],
                facilityProviders: {
                    reportingFacility: { name: 'reporting facility name' }
                }
            }
        ];

        const { getByText, getAllByRole } = render(
            <WithinTableProvider>
                <DocumentsRequiringReviewTable
                    patient={'patient'}
                    documents={documents}></DocumentsRequiringReviewTable>
            </WithinTableProvider>
        );

        const tableData = getAllByRole('cell');

        const title = getByText('Reporting facility');
        const value = getByText('reporting facility name');

        expect(tableData[2]).toContainElement(title);
        expect(tableData[2]).toContainElement(value);
    });

    it('should display the ordering provider', () => {
        const documents = [
            {
                id: '1234',
                localId: 'someLocalId',
                dateReceived: new Date('2021-10-07T15:01:10Z'),
                type: 'Laboratory Report',
                eventDate: undefined,
                isElectronic: true,
                descriptions: [{ title: 'description title ', value: 'description value' }],
                facilityProviders: {
                    orderingProvider: { name: 'ordering provider name' }
                }
            }
        ];

        const { getByText, getAllByRole } = render(
            <WithinTableProvider>
                <DocumentsRequiringReviewTable
                    patient={'patient'}
                    documents={documents}></DocumentsRequiringReviewTable>
            </WithinTableProvider>
        );

        const tableData = getAllByRole('cell');

        const title = getByText('Ordering provider');
        const value = getByText('ordering provider name');

        expect(tableData[2]).toContainElement(title);
        expect(tableData[2]).toContainElement(value);
    });

    it('should display the Event date', () => {
        const documents = [
            {
                id: '1234',
                localId: 'someLocalId',
                dateReceived: new Date('2021-10-07T15:01:10Z'),
                type: 'Test Report',
                eventDate: new Date('2013-08-17T15:01:10Z'),
                isElectronic: false,
                descriptions: [],
                facilityProviders: {}
            }
        ];

        const { getByText, getAllByRole } = render(
            <WithinTableProvider>
                <DocumentsRequiringReviewTable
                    patient={'patient'}
                    documents={documents}></DocumentsRequiringReviewTable>
            </WithinTableProvider>
        );

        const tableData = getAllByRole('cell');

        const eventDate = getByText(/08\/17\/2013/);

        expect(tableData[3]).toContainElement(eventDate);
    });

    it('should display a Description with title and value', () => {
        const documents = [
            {
                id: '1234',
                localId: 'someLocalId',
                dateReceived: new Date('2021-10-07T15:01:10Z'),
                type: 'Test Report',
                eventDate: undefined,
                isElectronic: false,
                descriptions: [{ title: 'description title', value: 'description value' }],
                facilityProviders: {}
            }
        ];

        const { getByText, getAllByRole } = render(
            <WithinTableProvider>
                <DocumentsRequiringReviewTable
                    patient={'patient'}
                    documents={documents}></DocumentsRequiringReviewTable>
            </WithinTableProvider>
        );

        const tableData = getAllByRole('cell');

        const title = getByText('description title');
        const value = getByText('description value');

        expect(tableData[4]).toContainElement(title);
        expect(tableData[4]).toContainElement(value);
    });

    it('should display a Description with only a title', () => {
        const documents = [
            {
                id: '1234',
                localId: 'someLocalId',
                dateReceived: new Date('2021-10-07T15:01:10Z'),
                type: 'Test Report',
                eventDate: undefined,
                isElectronic: false,
                descriptions: [{ title: 'description title' }],
                facilityProviders: {}
            }
        ];

        const { getByText, getAllByRole } = render(
            <WithinTableProvider>
                <DocumentsRequiringReviewTable
                    patient={'patient'}
                    documents={documents}></DocumentsRequiringReviewTable>
            </WithinTableProvider>
        );

        const tableData = getAllByRole('cell');

        const title = getByText('description title');

        expect(tableData[4]).toContainElement(title);
    });

    it('should display the Event #', () => {
        const documents = [
            {
                id: '1234',
                localId: 'event-id-value',
                dateReceived: new Date('2021-10-07T15:01:10Z'),
                type: 'Test Report',
                eventDate: undefined,
                isElectronic: false,
                descriptions: [],
                facilityProviders: {}
            }
        ];

        const { getByText, getAllByRole } = render(
            <WithinTableProvider>
                <DocumentsRequiringReviewTable
                    patient={'patient'}
                    documents={documents}></DocumentsRequiringReviewTable>
            </WithinTableProvider>
        );

        const tableData = getAllByRole('cell');

        const eventDate = getByText('event-id-value');

        expect(tableData[5]).toContainElement(eventDate);
    });
});
