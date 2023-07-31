import { PatientInvestigationsTable } from './PatientInvestigationsTable';
import { render } from '@testing-library/react';
import { FindInvestigationsForPatientDocument } from 'generated/graphql/schema';
import { MockedProvider } from '@apollo/client/testing';

describe('when rendered', () => {
    it('should display sentence cased investigation headers', async () => {
        const response = {
            request: {
                query: FindInvestigationsForPatientDocument,
                variables: {
                    patient: 'patient',
                    openOnly: false,
                    page: {
                        pageNumber: 0,
                        pageSize: 1
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

        const { container } = render(
            <MockedProvider mocks={[response]} addTypename={false}>
                <PatientInvestigationsTable patient={'patient'} pageSize={1}></PatientInvestigationsTable>
            </MockedProvider>
        );

        const tableHeader = container.getElementsByClassName('table-header');
        expect(tableHeader[0].innerHTML).toBe('Investigations');

        const tableHeads = container.getElementsByClassName('head-name');

        expect(tableHeads[0].innerHTML).toBe('Start date');
        expect(tableHeads[1].innerHTML).toBe('Condition');
        expect(tableHeads[2].innerHTML).toBe('Status');
        expect(tableHeads[3].innerHTML).toBe('Case status');
        expect(tableHeads[4].innerHTML).toBe('Notification');
        expect(tableHeads[5].innerHTML).toBe('Jurisdiction');
        expect(tableHeads[6].innerHTML).toBe('Investigator');
        expect(tableHeads[7].innerHTML).toBe('Investigation #');
        expect(tableHeads[8].innerHTML).toBe('Co-infection #');
    });
});

describe('when investigations are No data for a patient', () => {
    const response = {
        request: {
            query: FindInvestigationsForPatientDocument,
            variables: {
                patient: '73',
                openOnly: false,
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

    it('should display No data', async () => {
        const { findByText } = render(
            <MockedProvider mocks={[response]} addTypename={false}>
                <PatientInvestigationsTable patient={'73'} pageSize={5}></PatientInvestigationsTable>
            </MockedProvider>
        );

        expect(await findByText('No data')).toBeInTheDocument();
    });
});

describe('when at least one investigation is available for a patient', () => {
    const response = {
        request: {
            query: FindInvestigationsForPatientDocument,
            variables: {
                patient: '1823',
                openOnly: false,
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
                            startedOn: '2023-03-27',
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
                <PatientInvestigationsTable patient={'1823'} pageSize={5}></PatientInvestigationsTable>
            </MockedProvider>
        );

        expect(await findByText('Showing 1 of 1')).toBeInTheDocument();

        const event = await findByText('event');

        const tableData = container.getElementsByClassName('table-data');

        expect(tableData[0]).toContainElement(await findByText('03/27/2023'));
        expect(tableData[1]).toHaveTextContent('condition');
        expect(tableData[2]).toHaveTextContent('status');
        expect(tableData[3]).toHaveTextContent('case-status');
        expect(tableData[4]).toHaveTextContent('notification');
        expect(tableData[5]).toHaveTextContent('jurisdiction');
        expect(tableData[6]).toHaveTextContent('investigator');
        expect(tableData[7]).toContainElement(event);
        expect(tableData[8]).toHaveTextContent('co-infection');
    });
});
