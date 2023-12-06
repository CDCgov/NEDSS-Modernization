import { DocumentsRequiringReviewTable } from './DocumentRequiringReviewTable';
import { render } from '@testing-library/react';
import { WithinTableProvider } from 'components/Table/testing';

describe('when rendered', () => {
    it('should display sentence cased document headers', async () => {
        const { getByRole, getAllByRole } = render(
            <WithinTableProvider>
                <DocumentsRequiringReviewTable
                    setSort={() => {}}
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
                    setSort={() => {}}
                    patient={'patient'}
                    documents={[]}></DocumentsRequiringReviewTable>
            </WithinTableProvider>
        );

        expect(await findByText('No Data')).toBeInTheDocument();
    });
});

describe('when at least one document is available for a patient', () => {
    const documents = [
        {
            id: '1234',
            localId: 'someLocalId',
            dateReceived: new Date('2021-10-07T15:01:10Z'),
            type: 'LabReport',
            eventDate: undefined,
            isElectronic: true,
            descriptions: [{ title: 'description title ', value: 'description value' }],
            facilityProviders: {
                reportingFacility: { name: 'some hospital' }
            }
        }
    ];

    it('should display the documents', async () => {
        const { getByText, getAllByRole } = render(
            <WithinTableProvider>
                <DocumentsRequiringReviewTable
                    setSort={() => {}}
                    patient={'patient'}
                    documents={documents}></DocumentsRequiringReviewTable>
            </WithinTableProvider>
        );

        const tableData = getAllByRole('cell');

        expect(tableData[0]).toHaveTextContent('Lab Report(Electronic)');

        const dateReceived = getByText(/10\/07\/2021/);
        expect(dateReceived).toHaveTextContent('10/07/2021 10:01 am');
        expect(tableData[1]).toContainElement(dateReceived);
        expect(tableData[2]).toHaveTextContent('Reporting facilitysome hospital');
        expect(tableData[3]).toHaveTextContent('No date');
        expect(tableData[4]).toHaveTextContent('description title description value');
        expect(tableData[5]).toHaveTextContent('someLocalId');
    });
});
