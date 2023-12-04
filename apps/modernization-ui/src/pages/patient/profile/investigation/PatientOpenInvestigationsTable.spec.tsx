import { render } from '@testing-library/react';
import { MockedProvider } from '@apollo/client/testing';
import { FindInvestigationsForPatientDocument } from 'generated/graphql/schema';
import { PatientOpenInvestigationsTable } from './PatientOpenInvestigationsTable';

describe('when rendered', () => {
    it('should display sentence cased investigation headers', async () => {
        const { getByRole, getAllByRole } = render(
            <MockedProvider addTypename={false}>
                <PatientOpenInvestigationsTable pageSize={1}></PatientOpenInvestigationsTable>
            </MockedProvider>
        );

        expect(getByRole('heading', { name: 'Open investigations' })).toBeInTheDocument();

        const tableHeads = getAllByRole('columnheader');

        expect(tableHeads[0]).toHaveTextContent('Start date');
        expect(tableHeads[1]).toHaveTextContent('Condition');
        expect(tableHeads[2]).toHaveTextContent('Case status');
        expect(tableHeads[3]).toHaveTextContent('Notification');
        expect(tableHeads[4]).toHaveTextContent('Jurisdiction');
        expect(tableHeads[5]).toHaveTextContent('Investigator');
        expect(tableHeads[6]).toHaveTextContent('Investigation #');
        expect(tableHeads[7]).toHaveTextContent('Co-infection #');
    });
});

describe('when investigations are No Data for a patient', () => {
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

    it('should display No Data', async () => {
        const { findByText } = render(
            <MockedProvider mocks={[response]} addTypename={false}>
                <PatientOpenInvestigationsTable patient={'73'} pageSize={5}></PatientOpenInvestigationsTable>
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
        const { findByText, findAllByRole } = render(
            <MockedProvider mocks={[response]} addTypename={false}>
                <PatientOpenInvestigationsTable patient={'1823'} pageSize={5}></PatientOpenInvestigationsTable>
            </MockedProvider>
        );

        expect(await findByText('Showing 1 of 1')).toBeInTheDocument();

        const tableData = await findAllByRole('cell');

        expect(tableData[0]).toHaveTextContent('03/27/2023');
        expect(tableData[1]).toHaveTextContent('condition');
        expect(tableData[2]).toHaveTextContent('case-status');
        expect(tableData[3]).toHaveTextContent('notification');
        expect(tableData[4]).toHaveTextContent('jurisdiction');
        expect(tableData[5]).toHaveTextContent('investigator');
        expect(tableData[6]).toContainElement(await findByText('event'));
        expect(tableData[7]).toHaveTextContent('co-infection');
    });
});
