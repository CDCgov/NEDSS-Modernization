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
        expect(tableHeader[0]).toHaveTextContent('Investigations');

        const tableHeads = container.getElementsByClassName('head-name');

        expect(tableHeads[1]).toHaveTextContent('Start date');
        expect(tableHeads[2]).toHaveTextContent('Condition');
        expect(tableHeads[3]).toHaveTextContent('Status');
        expect(tableHeads[4]).toHaveTextContent('Case status');
        expect(tableHeads[5]).toHaveTextContent('Notification');
        expect(tableHeads[6]).toHaveTextContent('Jurisdiction');
        expect(tableHeads[7]).toHaveTextContent('Investigator');
        expect(tableHeads[8]).toHaveTextContent('Investigation #');
        expect(tableHeads[9]).toHaveTextContent('Co-infection #');
    });
});

describe('when investigations are No Data for a patient', () => {
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

    it('should display No Data', async () => {
        const { findByText } = render(
            <MockedProvider mocks={[response]} addTypename={false}>
                <PatientInvestigationsTable patient={'73'} pageSize={5}></PatientInvestigationsTable>
            </MockedProvider>
        );

        expect(await findByText('No Data')).toBeInTheDocument();
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
                            investigator: 'investigator',
                            comparable: true
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

        expect(tableData[1]).toContainElement(await findByText('03/27/2023'));
        expect(tableData[2]).toHaveTextContent('condition');
        expect(tableData[3]).toHaveTextContent('status');
        expect(tableData[4]).toHaveTextContent('case-status');
        expect(tableData[5]).toHaveTextContent('notification');
        expect(tableData[6]).toHaveTextContent('jurisdiction');
        expect(tableData[7]).toHaveTextContent('investigator');
        expect(tableData[8]).toContainElement(event);
        expect(tableData[9]).toHaveTextContent('co-infection');
    });
});
