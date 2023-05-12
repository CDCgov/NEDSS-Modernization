import { PatientTreatmentTable } from './PatientTreatmentTable';
import { render } from '@testing-library/react';
import { FindTreatmentsForPatientDocument } from 'generated/graphql/schema';

import { MockedProvider } from '@apollo/client/testing';

describe('when rendered', () => {
    it('should display sentence cased treatment headers', async () => {
        const { container } = render(
            <MockedProvider addTypename={false}>
                <PatientTreatmentTable pageSize={3}></PatientTreatmentTable>
            </MockedProvider>
        );

        const tableHeader = container.getElementsByClassName('table-header');
        expect(tableHeader[0].innerHTML).toBe('Treatments');

        const tableHeads = container.getElementsByClassName('head-name');

        expect(tableHeads[0].innerHTML).toBe('Date created');
        expect(tableHeads[1].innerHTML).toBe('Provider');
        expect(tableHeads[2].innerHTML).toBe('Treatment date');
        expect(tableHeads[3].innerHTML).toBe('Treatment');
        expect(tableHeads[4].innerHTML).toBe('Associated with');
        expect(tableHeads[5].innerHTML).toBe('Event #');
    });
});

describe('when treatments are No data for a patient', () => {
    const response = {
        request: {
            query: FindTreatmentsForPatientDocument,
            variables: {
                patient: '73',
                page: {
                    pageNumber: 0,
                    pageSize: 5
                }
            }
        },
        result: {
            data: {
                findTreatmentsForPatient: {
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
                <PatientTreatmentTable patient={'73'} pageSize={5}></PatientTreatmentTable>
            </MockedProvider>
        );

        expect(await findByText('No data')).toBeInTheDocument();
    });
});

describe('when treatments are available for a patient', () => {
    const response = {
        request: {
            query: FindTreatmentsForPatientDocument,
            variables: {
                patient: '1823',
                page: {
                    pageNumber: 0,
                    pageSize: 5
                }
            }
        },
        result: {
            data: {
                findTreatmentsForPatient: {
                    content: [
                        {
                            treatment: '2203',
                            createdOn: '2023-03-20T15:36:16.317Z',
                            provider: 'DR Indiana Jones',
                            treatedOn: '2023-03-01T05:00:00Z',
                            description: 'treatment-description',
                            event: 'TRT10000000GA01',
                            associatedWith: {
                                id: '10000013',
                                local: 'CAS10000000GA01',
                                condition: 'Pertussis'
                            }
                        }
                    ],
                    total: 1,
                    number: 0
                }
            }
        }
    };

    it('should display the treatments', async () => {
        const { container, findByText } = render(
            <MockedProvider mocks={[response]} addTypename={false}>
                <PatientTreatmentTable patient={'1823'} pageSize={5}></PatientTreatmentTable>
            </MockedProvider>
        );

        expect(await findByText('Showing 1 of 1')).toBeInTheDocument();

        const tableData = container.getElementsByClassName('table-data');

        const dateCreated = await findByText(/03\/20\/2023/);

        expect(dateCreated).toHaveTextContent('10:36 AM');

        expect(tableData[0]).toContainElement(dateCreated);

        expect(tableData[1]).toHaveTextContent('DR Indiana Jones');
        expect(tableData[2]).toHaveTextContent('03/01/2023');
        expect(tableData[3]).toHaveTextContent('treatment-description');

        //  Associated With
        const association = await findByText('CAS10000000GA01');

        expect(tableData[4]).toContainElement(association);

        const associationCondition = await findByText('Pertussis');

        expect(tableData[4]).toContainElement(associationCondition);

        expect(tableData[5]).toHaveTextContent('TRT10000000GA01');
    });
});
