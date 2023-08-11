import { PageProvider } from 'page';
import { DocumentsRequiringReviewTable } from './DocumentRequiringReviewTable';
import { render } from '@testing-library/react';

describe('when rendered', () => {
    it('should display sentence cased document headers', async () => {
        const { container } = render(
            <PageProvider>
                <DocumentsRequiringReviewTable
                    setSort={() => {}}
                    patient={'patient'}
                    documents={[]}></DocumentsRequiringReviewTable>
            </PageProvider>
        );

        const tableHeader = container.getElementsByClassName('table-header');
        expect(tableHeader[0].innerHTML).toBe('Documents requiring review');

        const tableHeads = container.getElementsByClassName('head-name');
        expect(tableHeads[0].innerHTML).toBe('Document type');
        expect(tableHeads[1].innerHTML).toBe('Date received');
        expect(tableHeads[2].innerHTML).toBe('Reporting facility / provider');
        expect(tableHeads[3].innerHTML).toBe('Event date');
        expect(tableHeads[4].innerHTML).toBe('Description');
        expect(tableHeads[5].innerHTML).toBe('Event #');
    });
});

describe('when no documents are available for a patient', () => {
    it('should display No data', async () => {
        const { findByText } = render(
            <PageProvider>
                <DocumentsRequiringReviewTable
                    setSort={() => {}}
                    patient={'patient'}
                    documents={[]}></DocumentsRequiringReviewTable>
            </PageProvider>
        );

        expect(await findByText('No data')).toBeInTheDocument();
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
        const { container, findByText } = render(
            <PageProvider>
                <DocumentsRequiringReviewTable
                    setSort={() => {}}
                    patient={'patient'}
                    documents={documents}></DocumentsRequiringReviewTable>
            </PageProvider>
        );

        const tableData = container.getElementsByClassName('table-data');

        expect(tableData[0]).toHaveTextContent('Lab Report(Electronic)');

        const dateReceived = await findByText(/10\/07\/2021/);
        expect(dateReceived).toHaveTextContent('10/07/2021 10:01 am');
        expect(tableData[1]).toContainElement(dateReceived);
        expect(tableData[2]).toHaveTextContent('Reporting facilitysome hospital');
        expect(tableData[3]).toHaveTextContent('No date');
        expect(tableData[4]).toHaveTextContent('description title description value');
        expect(tableData[5]).toHaveTextContent('someLocalId');
    });
});
