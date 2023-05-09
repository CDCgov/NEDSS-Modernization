import { PatientOpenInvestigationsTable } from './PatientOpenInvestigationsTable';
import { render } from '@testing-library/react';
import { FindInvestigationsForPatientDocument } from 'generated/graphql/schema';
import { MockedProvider } from '@apollo/client/testing';

describe('when rendered', () => {
    it('should display sentence cased investigation headers', async () => {
        const { container } = render(
            <MockedProvider addTypename={false}>
                <PatientOpenInvestigationsTable pageSize={1}></PatientOpenInvestigationsTable>
            </MockedProvider>
        );

        const tableHeader = container.getElementsByClassName('table-header');
        expect(tableHeader[0].innerHTML).toBe('Open Investigations');

        const tableHeads = container.getElementsByClassName('head-name');

        expect(tableHeads[0].innerHTML).toBe('Start date');
        expect(tableHeads[1].innerHTML).toBe('Condition');
        expect(tableHeads[2].innerHTML).toBe('Case status');
        expect(tableHeads[3].innerHTML).toBe('Notification');
        expect(tableHeads[4].innerHTML).toBe('Jurisdiction');
        expect(tableHeads[5].innerHTML).toBe('Investigator');
        expect(tableHeads[6].innerHTML).toBe('Investigation #');
        expect(tableHeads[7].innerHTML).toBe('Co-infection #');
    });
});

describe('when investigations are not available for a patient', () => {
    const response = {
        request: {
            query: FindInvestigationsForPatientDocument,
            variables: {
                patient: '73',
                openOnly: true,
                page: {
                    pageNumber: 0,
                    pageSize: 5
                }
            }
        },
        result: {
            data: {
                findInvestigationsForPatient: {
                    content: [],
                    total: 0,
                    number: 0
                }
            }
        }
    };

    it('should display Not Available', async () => {
        const { findByText } = render(
            <MockedProvider mocks={[response]} addTypename={false}>
                <PatientOpenInvestigationsTable patient={'73'} pageSize={5}></PatientOpenInvestigationsTable>
            </MockedProvider>
        );

        expect(await findByText('Not Available')).toBeInTheDocument();
    });
});

describe('when at least one investigation is available for a patient', () => {
    const response = {
        request: {
            query: FindInvestigationsForPatientDocument,
            variables: {
                patient: '1823',
                openOnly: true,
                page: {
                    pageNumber: 0,
                    pageSize: 5
                }
            }
        },
        result: {
            data: {
                findInvestigationsForPatient: {
                    content: [
                        {
                            investigation: 'investigation-id',
                            startedOn: '2023-03-27T00:00:00Z',
                            condition: 'condition',
                            status: 'status',
                            caseStatus: 'case-status',
                            jurisdiction: 'jurisdiction',
                            event: 'event',
                            coInfection: 'co-infection',
                            notification: 'notification',
                            investigator: 'investigator'
                        }
                    ],
                    total: 1,
                    number: 0
                }
            }
        }
    };

    it('should display the investigations', async () => {
        const { container, findByText } = render(
            <MockedProvider mocks={[response]} addTypename={false}>
                <PatientOpenInvestigationsTable patient={'1823'} pageSize={5}></PatientOpenInvestigationsTable>
            </MockedProvider>
        );

        expect(await findByText('Showing 1 of 1')).toBeInTheDocument();

        const event = await findByText('event');

        const tableData = container.getElementsByClassName('table-data');

        expect(tableData[0]).toHaveTextContent('03/26/2023');
        expect(tableData[1]).toHaveTextContent('condition');
        expect(tableData[2]).toHaveTextContent('case-status');
        expect(tableData[3]).toHaveTextContent('notification');
        expect(tableData[4]).toHaveTextContent('jurisdiction');
        expect(tableData[5]).toHaveTextContent('investigator');
        expect(tableData[6]).toContainElement(event);
        expect(tableData[7]).toHaveTextContent('co-infection');
    });
});
