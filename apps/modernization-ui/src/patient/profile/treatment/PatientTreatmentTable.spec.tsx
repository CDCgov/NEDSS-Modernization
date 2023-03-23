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

describe('when treatments are not available for a patient', () => {
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

    it('should display Not Available', async () => {
        const { findByText } = render(
            <MockedProvider mocks={[response]} addTypename={false}>
                <PatientTreatmentTable patient={'73'} pageSize={5}></PatientTreatmentTable>
            </MockedProvider>
        );

        expect(await findByText('Not Available')).toBeInTheDocument();
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
        const { container, findByText, getAllByRole } = render(
            <MockedProvider mocks={[response]} addTypename={false}>
                <PatientTreatmentTable patient={'1823'} pageSize={5}></PatientTreatmentTable>
            </MockedProvider>
        );

        expect(await findByText('Showing 1 of 1')).toBeInTheDocument();

        const tableData = container.getElementsByClassName('table-data');

        expect(tableData[0]).toContainHTML('<span class="link">03/20/2023 <br /> 10:36 AM</span>');
        expect(tableData[1].innerHTML).toContain('DR Indiana Jones');
        expect(tableData[2].innerHTML).toContain('03/01/2023');
        expect(tableData[3].innerHTML).toContain('treatment-description');
        expect(tableData[4]).toContainHTML(
            '<div><p class="margin-0 text-primary text-bold link" style="word-break: break-word;">CAS10000000GA01</p><p class="margin-0">Pertussis</p></div></span>'
        );
        expect(tableData[5]).toHaveTextContent('TRT10000000GA01');
    });
});
