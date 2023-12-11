import { PatientTreatmentTable } from './PatientTreatmentTable';
import { render } from '@testing-library/react';
import { FindTreatmentsForPatientDocument } from 'generated/graphql/schema';

import { MockedProvider } from '@apollo/client/testing';

describe('when rendered', () => {
    it('should display sentence cased treatment headers', async () => {
        const response = {
            request: {
                query: FindTreatmentsForPatientDocument,
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

        const { getByRole, getAllByRole } = render(
            <MockedProvider mocks={[response]} addTypename={false}>
                <PatientTreatmentTable patient={'patient'} pageSize={3}></PatientTreatmentTable>
            </MockedProvider>
        );
        expect(getByRole('heading', { name: 'Treatments' })).toBeInTheDocument();

        const tableHeads = getAllByRole('columnheader');

        expect(tableHeads[0]).toHaveTextContent('Date created');
        expect(tableHeads[1]).toHaveTextContent('Provider');
        expect(tableHeads[2]).toHaveTextContent('Treatment date');
        expect(tableHeads[3]).toHaveTextContent('Treatment');
        expect(tableHeads[4]).toHaveTextContent('Associated with');
        expect(tableHeads[5]).toHaveTextContent('Event #');
    });
});

describe('when treatments are No Data for a patient', () => {
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

    it('should display No Data', async () => {
        const { findByText } = render(
            <MockedProvider mocks={[response]} addTypename={false}>
                <PatientTreatmentTable patient={'73'} pageSize={5}></PatientTreatmentTable>
            </MockedProvider>
        );

        expect(await findByText('No Data')).toBeInTheDocument();
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
                            description: 'description',
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
        const { findAllByRole, findByText } = render(
            <MockedProvider mocks={[response]} addTypename={false}>
                <PatientTreatmentTable patient={'1823'} pageSize={5}></PatientTreatmentTable>
            </MockedProvider>
        );

        expect(await findByText('Showing 1 of 1')).toBeInTheDocument();

        const tableData = await findAllByRole('cell');

        const dateCreated = await findByText(/03\/20\/2023/);

        expect(dateCreated).toHaveTextContent('03/20/2023');

        expect(tableData[0]).toContainElement(dateCreated);

        expect(tableData[1]).toHaveTextContent('DR Indiana Jones');
        expect(tableData[2]).toHaveTextContent('03/01/2023');
        expect(tableData[3]).toHaveTextContent('description');

        //  Associated With
        const association = await findByText('CAS10000000GA01');

        expect(tableData[4]).toContainElement(association);

        const associationCondition = await findByText('Pertussis');

        expect(tableData[4]).toContainElement(associationCondition);

        expect(tableData[5]).toHaveTextContent('TRT10000000GA01');
    });
});
